package com.bwell.modules.eatwell.recipes.ingredients.SpoonacularApi;

import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.Ingredient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class RequestingService {
    private HttpClient client = HttpClient.newHttpClient();
    private ObjectMapper mapper = new ObjectMapper();

    public ObjectMapper getMapper() {
        return mapper;
    }

    public RequestingService() {
        client = HttpClient.newHttpClient();
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

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
        String url = new UrlBuilder(id).build();
        String stringJson = sendRequest(url);
        return stringJson;
    }

    private String requestIngredient(int id, int amount, String unit){
        String url = new UrlBuilder(id, amount, unit).build();
        String stringJson = sendRequest(url);
        return stringJson;
    }

    private String requestIngredientsQuery(String query){
        String url = new UrlBuilder(query).build();
        String stringJson = sendRequest(url);
        return stringJson;
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
