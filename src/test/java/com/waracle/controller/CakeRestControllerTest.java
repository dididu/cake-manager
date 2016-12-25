package com.waracle.controller;

import com.waracle.domain.Cake;
import com.waracle.repository.CakeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CakeRestControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CakeRepository mockCakeRepository;

    @Before
    public void setup() {
        Cake cake1 = new Cake();
        cake1.setTitle("cake1");
        Cake cake2 = new Cake();
        cake2.setTitle("cake2");
        ArrayList cakeList = new ArrayList<Cake>();
        cakeList.add(cake1);
        cakeList.add(cake2);

        given(this.mockCakeRepository.findAll())
                .willReturn(cakeList);
    }

    @Test
    public void getCakesTest() {
        ResponseEntity<List<Cake>> cakesResponse = this.restTemplate.exchange(
                "/cakes", HttpMethod.GET, null, new ParameterizedTypeReference<List<Cake>>() {});

        Assert.assertEquals(HttpStatus.OK, cakesResponse.getStatusCode());
        List<Cake> cakes = cakesResponse.getBody();
        Assert.assertEquals(2, cakes.size());

        Assert.assertEquals("cake1", cakes.get(0).getTitle());
        Assert.assertEquals("cake2", cakes.get(1).getTitle());
    }

    @Test
    public void postNewCakeTest() {

        Cake newCake = new Cake();
        newCake.setTitle("cake1");

        ResponseEntity cakesResponse = this.restTemplate.postForEntity(
                "/cakes", newCake, Cake.class);

        Assert.assertEquals(HttpStatus.CREATED, cakesResponse.getStatusCode());
        verify(mockCakeRepository, times(1)).save(newCake);
    }
}