package com.bwell.modules.eatwell.recipes.ingredients.SpoonacularApi;

import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.Ingredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientWithNutrients;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrients;
import com.bwell.mockcenter.MockObjectsFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class RequestingService implements IIRequestingService {
    private final HttpClient client;
    private final ObjectMapper mapper;
    private final UrlBuilder urlBuilder;

    @Autowired
    public RequestingService(UrlBuilder urlBuilder, ObjectMapper jacksonObjectMapper) {
        this.client = HttpClient.newHttpClient();
        this.mapper = jacksonObjectMapper;
        this.urlBuilder = urlBuilder;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }


    public String requestIngredient(int id){
        String url = urlBuilder.ingredient(id).build();
        return sendRequest(url);
    }

    @Override
    public String requestIngredient(int id, int amount, String unit){
        String url = urlBuilder
                .ingredient(id)
                .amount(amount)
                .unit(unit)
                .build();
        return sendRequest(url);
    }

    @Override
    public String requestIngredient(int id, double amount, String unit){
        String url = urlBuilder
                .ingredient(id)
                .amount(amount)
                .unit(unit)
                .build();
        return sendRequest(url);
    }

    @Override
    public String requestIngredientsQuery(String query){
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
    @Override
    public DetailedIngredient getIngredient(int id, double amount, String unit) {
        String stringJson = requestIngredient(id, amount, unit);

        log.info("Ingredient requested from API : ======= {} : {}", LocalDateTime.now(), stringJson);

        try {
            DetailedIngredient detailedIngredient = mapper.readValue(stringJson, DetailedIngredient.class);
            if (detailedIngredient.getNutrition() == null){
                Nutrients empty = Nutrients.empty();
                empty.setNutrients(MockObjectsFactory.nutritionElements());
                detailedIngredient.setNutrition(empty);
            }
            detailedIngredient.getNutrition().setIngredient(detailedIngredient);
            return detailedIngredient;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public IngredientWithNutrients getIngredientWithNutrients(int id, double amount, String unit) {
        String stringJson = requestIngredient(id, amount, unit);

        log.info("requesting from API : ======= {} : {}", LocalDateTime.now(), stringJson);

        try {
            JsonNode jsonNode = mapper.readTree(stringJson);
//            System.out.println(jsonNode.toPrettyString());
            IngredientWithNutrients detailedIngredient = mapper.readValue(stringJson, IngredientWithNutrients.class);
            return detailedIngredient;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Ingredient> queryIngredient(String query){
        String stringJson = requestIngredientsQuery(query);
        log.info("API Query results: ======= {} : {}", LocalDateTime.now(), stringJson);
        try {
            JsonNode jsonNode = mapper.readTree(stringJson);
            List<Ingredient> list = mapper.readValue(jsonNode.get("results").toString(), new TypeReference<List<Ingredient>>(){});
            return list;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public String sendRequest(String url) {
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

}
