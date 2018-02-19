package com.spring.swapi.repositories;

import com.spring.swapi.models.Planet;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "planet", path = "planet")
public interface PlanetRepository extends MongoRepository<Planet, String> {

	List<Planet> findByNomeLike(@Param("nome") String nome);
        
        Planet findByNome(@Param("nome") String nome);

}