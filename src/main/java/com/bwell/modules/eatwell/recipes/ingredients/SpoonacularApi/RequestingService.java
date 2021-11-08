package com.bwell.modules.eatwell.recipes.ingredients.SpoonacularApi;

import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.Ingredient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class RequestingService {
    private final HttpClient client;
    private final ObjectMapper mapper;
    private final UrlBuilder urlBuilder;

    @Autowired
    public RequestingService(UrlBuilder urlBuilder) {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
        this.urlBuilder = urlBuilder;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }


    private String sendRequest(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();
        try {
            return client
                    .send(request, HttpResponse.BodyHandlers.ofString())
                    .body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }


    private String requestIngredient(int id){
        String url = urlBuilder.ingredient(id).build();
        return sendRequest(url);
    }

    private String requestIngredient(int id, int amount, String unit){
        String url = urlBuilder
                .ingredient(id)
                .amount(amount)
                .unit(unit)
                .build();
        return sendRequest(url);
    }

    private String requestIngredient(int id, double amount, String unit){
        String url = urlBuilder
                .ingredient(id)
                .amount(amount)
                .unit(unit)
                .build();
        return sendRequest(url);
    }

    private String requestIngredientsQuery(String query){
        String url = urlBuilder
                .query(query)
                .build();
        return sendRequest(url);
    }


    public DetailedIngredient getIngredient(int id) {
        String stringJson = requestIngredient(id);
        try {
            return mapper.readValue(stringJson, DetailedIngredient.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public DetailedIngredient getIngredient(int id, int amount, String unit) {
        String stringJson = requestIngredient(id, amount, unit);
        try {
            return mapper.readValue(stringJson, DetailedIngredient.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public DetailedIngredient getIngredient(int id, double amount, String unit) {
        String stringJson = requestIngredient(id, amount, unit);
        try {
            return mapper.readValue(stringJson, DetailedIngredient.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Ingredient> queryIngredient(String query){
        String stringJson = requestIngredientsQuery(query);
        try {
            JsonNode jsonNode = mapper.readTree(stringJson);
            List<Ingredient> list = mapper.readValue(jsonNode.get("results").toString(), new TypeReference<List<Ingredient>>(){});
            return list;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }



}
