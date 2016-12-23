package com.waracle.controller;

import java.util.Collection;

import com.waracle.domain.Cake;
import com.waracle.repository.CakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CakeRestController {

    private CakeRepository cakeRepository;

    @Autowired
    public CakeRestController(CakeRepository repository) {
        Assert.notNull(repository, "CakeRepository must not be null!");
        this.cakeRepository = repository;
    }

    @RequestMapping(value = "/cakes", produces = "application/json")
    public Collection<Cake> cakes() {
        return cakeRepository.findAll();
    }
}