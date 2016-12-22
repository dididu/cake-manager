package com.waracle;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CakeController {

    private CakeRepository cakeRepository;

    @Autowired
    public CakeController(CakeRepository repository) {
        Assert.notNull(repository, "CakeRepository must not be null!");
        this.cakeRepository = repository;
    }

    @RequestMapping("/cakes")
    public Collection<CakeEntity> cakes() {
        return cakeRepository.findAll();
    }
}