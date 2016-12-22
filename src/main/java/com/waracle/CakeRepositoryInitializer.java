package com.waracle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CakeRepositoryInitializer {

    private static Logger log = LoggerFactory.getLogger(CakeRepositoryInitializer.class);

    private CakeRepository cakeRepository;
    private ObjectMapper mapper;

    @Value("${cakeInfoUrl}")
    private String cakeInfoUrl;

    @Autowired
    public CakeRepositoryInitializer(CakeRepository repository, ObjectMapper mapper) {
        Assert.notNull(repository, "CakeRepository param must not be null!");
        this.cakeRepository = repository;
        this.mapper = mapper;
    }

    @PostConstruct
    public void initCakeRepository() {
        try {
            List<CakeEntity> cakes = mapper.readValue(new URL(cakeInfoUrl),
                    new TypeReference<List<CakeEntity>>() {});
            List<CakeEntity> dedupedCakes = cakes.stream().distinct().collect(Collectors.toList());
            cakeRepository.save(dedupedCakes);

        } catch (Exception e) {
            log.error("Error initialising the database. Will start empty.", e);
        }
    }
}
