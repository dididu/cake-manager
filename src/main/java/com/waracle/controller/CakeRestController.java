package com.waracle.controller;

import java.util.Collection;

import com.waracle.domain.Cake;
import com.waracle.repository.CakeRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
public class CakeRestController {

    private CakeRepository cakeRepository;

    @Autowired
    public CakeRestController(CakeRepository repository) {
        Assert.notNull(repository, "CakeRepository must not be null!");
        this.cakeRepository = repository;
    }

    @ApiOperation(value = "getCakes", response = Cake.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of cakes returned successfully"),
            @ApiResponse(code = 500, message = "Error returning the list of cakes")})
    @GetMapping(value = "/cakes", produces = "application/json")
    public ResponseEntity<?> cakes() {

        try {
            Collection<Cake> cakes = cakeRepository.findAll();
            return new ResponseEntity<Collection<Cake>>(cakes, null, HttpStatus.OK);
        }
        catch (Exception e) { // Should be a lot more sophisticated error handling
            return new ResponseEntity<Cake>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "createCake", response = Cake.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New cake created"),
            @ApiResponse(code = 409, message = "Conflict - cake with the same " +
                    "combination of title, description and image already exists"),
            @ApiResponse(code = 500, message = "Unexpected failure")})
    @PostMapping(value = "/cakes", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createCake(@RequestBody Cake cake) {

        try {
            Cake savedCake = cakeRepository.save(cake);
            return new ResponseEntity<Cake>(savedCake, null, HttpStatus.CREATED);
        }
        catch (DataIntegrityViolationException e) {
            return new ResponseEntity<Cake>(null, null, HttpStatus.CONFLICT);
        }
        catch (Exception e) { // Should be a lot more sophisticated error handling
            return new ResponseEntity<Cake>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}