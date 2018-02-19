/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring.swapi.controllers;

import com.spring.swapi.models.Planet;
import com.spring.swapi.repositories.PlanetRepository;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author diogo
 */
@RestController
public class PlanetController {

    @Autowired
    PlanetRepository planetRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/planet")
    public Iterable<Planet> planet() {
        return planetRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/planet/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        Planet planet = planetRepository.findOne(id);
        if (planet == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID invalido");
        } else {
            return ResponseEntity.ok("Planeta deletado!");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/planet/{id}")
    public ResponseEntity show(@PathVariable String id) {
        Planet planet = planetRepository.findOne(id);
        if (planet == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID invalido");
        } else {
            return ResponseEntity.ok(planet);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/planet")
    @ResponseBody
    public ResponseEntity save(@RequestBody Planet planet) throws IOException {
        Planet planetOnDb = planetRepository.findByNome(planet.getNome());
        if (planetOnDb == null) {
            if (validateField(planet.getNome())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Por favor entre com o nome do planeta");
            } else if (validateField(planet.getClima())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Por favor entre com o clima do planeta");
            } else if (validateField(planet.getTerreno())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Por favor entre com o terreno do planeta");
            } else {
                int aparicoes = planet.getAparitionFromAPI(planet.getNome());
                planet.setAparicoes(aparicoes);
                planetRepository.save(planet);
                return ResponseEntity.ok(planet);
            }
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Planeta ja cadastrado no sistema");
        }

    }

    private boolean validateField(String field) {
        if (field == null) {
            return true;
        } else if (field.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}
