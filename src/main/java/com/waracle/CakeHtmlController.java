package com.waracle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Controller
public class CakeHtmlController {

    private CakeRepository cakeRepository;

    @Autowired
    public CakeHtmlController(CakeRepository repository) {
        Assert.notNull(repository, "CakeRepository must not be null!");
        this.cakeRepository = repository;
    }

    @RequestMapping(value = "/", produces = "application/html")
    public String cakes(Model model) {
        model.addAttribute("cakesNumber", cakeRepository.count());
        model.addAttribute("cakes", cakeRepository.findAll());
        return "index";
    }
}