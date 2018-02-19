/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring.swapi.controllers;

import com.spring.swapi.models.Planet;
import com.spring.swapi.repositories.PlanetRepository;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.http.ResponseEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.ResultActions.*;

/**
 *
 * @author diogo
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlanetController planetController;

    @Test
    public void getAllPlanets() throws Exception {
        Planet planet = new Planet();
        planet.setAparicoes(2);
        planet.setClima("Seco");
        planet.setNome("PlanetaTeste");
        planet.setId("123");
        planet.setTerreno("Montanhoso");

        Iterable<Planet> allPlanets = singletonList(planet);
        given(planetController.planet())
                .willReturn(allPlanets);

        mvc.perform(get("/planet")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(planet.getId())));
    }

    @Test
    public void getSinglePlanet() throws Exception {
        Planet planet = new Planet();
        planet.setAparicoes(2);
        planet.setClima("Seco");
        planet.setNome("PlanetaTeste");
        planet.setId("123");
        planet.setTerreno("Montanhoso");
        given(planetController.show(planet.getId())).willReturn(ResponseEntity.ok(planet));
        mvc.perform(get("/planet/" + planet.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(planet.getId())));
    }

    @Test
    public void getPlanetWithInvalidId() throws Exception {
        given(planetController.show("234234234234234")).willReturn(ResponseEntity.badRequest().body("ID invalido"));
        mvc.perform(get("/planet/234234234234234")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deletePlanet() throws Exception {
        Planet planet = new Planet();
        planet.setAparicoes(2);
        planet.setClima("Seco");
        planet.setNome("PlanetaTeste");
        planet.setId("123");
        planet.setTerreno("Montanhoso");
        given(planetController.delete(planet.getId())).willReturn(ResponseEntity.ok("Planeta deletado!"));
        mvc.perform(delete("/planet/" + planet.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePlanetWithInvalidId() throws Exception {
        given(planetController.delete("234234234234234")).willReturn(ResponseEntity.badRequest().body("ID invalido"));
        mvc.perform(delete("/planet/234234234234234")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void savePlanet() throws Exception {
        mvc.perform(post("/planet")
                .content("{  \"nome\" : \"savePlanetTest\",  \"clima\" : \"Seco\", \"terreno\" : \"Montanhoso\" }")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void savePlanetInvalidAttributes() throws Exception {
        mvc.perform(post("/planet")
                .content("")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
