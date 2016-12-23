package com.waracle.controller;

import com.waracle.domain.Cake;
import com.waracle.repository.CakeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@Controller
public class CakeHtmlController {

    private static Logger log = LoggerFactory.getLogger(CakeHtmlController.class);
    private CakeRepository cakeRepository;

    @Autowired
    public CakeHtmlController(CakeRepository repository) {
        Assert.notNull(repository, "CakeRepository must not be null!");
        this.cakeRepository = repository;
    }

    @GetMapping(value = "/", produces = "application/html")
    public String cakes(Model model) {

        populateModel(model);
        return "index";
    }

    @PostMapping(value = "/", produces = "application/html")
    public String greetingSubmit(@ModelAttribute Cake cake, Model model) {

        try {
            cakeRepository.save(cake);
        }
        catch (Exception e) { // Should be a lot more sophisticated error processing
            log.error("Unable to save a new cake", e);

            populateModel(model);
            model.addAttribute("error", "Error adding a new cake. Try again.");
            return "index";
        }

        return "redirect:";
    }

    private void populateModel(Model model) {
        model.addAttribute("cakes", cakeRepository.findAll());
        model.addAttribute("cake", new Cake());
    }
}