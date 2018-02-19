# SWAPI - stars wars api

Projeto feito para o desafio b2w

## Pre requisitos

Para rodar a api, sera necessario ter instalado o JDK 8 no seu sistema, alem do banco de dados MongoDB.
As configuracoes do banco estao em application.properties.
## Executando

O projeto foi criado utilizando spring boot e gradle, entao primeiro rode

```
.\gradlew compileJava
```
Esse comando ira compilar as dependencias necessarias para o projeto, em seguida execute

```
.\gradlew bootRun
```
Esse comando ira inicializar o servidor, por padrao na em localhost:8080

## API endpoints
           
      GET  |  /planet                                         |  Lista de todos planetas                                        
      GET  |  /planet/{id}                                    |  Busca o planeta com o id fornecido        
      GET  |  /planet/search/findByNome?nome=nome             |  Busca um planeta com o nome exatamente igual a 'nome'            
      GET  |  /planet/search/findByNomeLike?nome=string       |  Busca planetas que o nome contenham 'string'              
     DELETE|  /planet/{id}                                    |  Deleta o planeta com o id fornecido                             
      POST |  /planet                                         |  Recebe um json com nome, clima e terreno e insere o planeta no banco

## Tests

Para rodar os testes use o comando a seguir:

```
.\gradlew test
```
