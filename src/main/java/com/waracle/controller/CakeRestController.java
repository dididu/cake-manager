package com.waracle.controller;

import java.util.Collection;

import com.waracle.domain.Cake;
import com.waracle.repository.CakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@RestController
public class CakeRestController {

    private CakeRepository cakeRepository;

    @Autowired
    public CakeRestController(CakeRepository repository) {
        Assert.notNull(repository, "CakeRepository must not be null!");
        this.cakeRepository = repository;
    }

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

    @PostMapping(value = "/cakes", produces = "application/json")
    public ResponseEntity<?> createCake(@RequestBody Cake cake) {

        try {
            Cake savedCake = cakeRepository.save(cake);
            return new ResponseEntity<Cake>(savedCake, null, HttpStatus.CREATED);
        }
        catch (DataIntegrityViolationException e) {
            return new ResponseEntity<Cake>(null, null, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) { // Should be a lot more sophisticated error handling
            return new ResponseEntity<Cake>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}