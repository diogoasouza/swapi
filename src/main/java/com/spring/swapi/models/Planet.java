package com.spring.swapi.models;

import ch.qos.logback.core.CoreConstants;
import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.web.client.RestTemplate;

public class Planet {

    @Id
    private String id;

    private String nome;
    private String clima;
    private String terreno;
    private Integer aparicoes;

    public String getId() {
        return id;
    }

    public Planet() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public String getTerreno() {
        return terreno;
    }

    public void setTerreno(String terreno) {
        this.terreno = terreno;
    }

    public Integer getAparicoes() {
        return aparicoes;
    }

    public void setAparicoes(Integer aparicoes) {
        this.aparicoes = aparicoes;

    }

    public Integer getAparitionFromAPI(String nome) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        System.setProperty("http.agent", "Chrome");
        String response = restTemplate.getForObject("https://swapi.co/api/planets/?search="+nome, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        if (jsonNode.get("count").asInt() == 1) {
            int aparitions = jsonNode.withArray("results").get(0).withArray("films").size();
            System.out.println(aparitions);
            return aparitions;
        } else {
            //teve nenhum match ou varios matchs na procura por planetas, logo ou eh um planeta que nao ta na api
            return 0;
        }
    }
}
