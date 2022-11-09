package com.bwell.modules.eatwell.recipes.ingredients.SpoonacularApi;

import com.bwell.modules.eatwell.recipes.ingredients.SpoonacularApi.RequestingService;
import com.bwell.modules.eatwell.recipes.ingredients.SpoonacularApi.UrlBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@TestConfiguration
public class MockRequestingServiceContextConfiguration {
//    ObjectMapper jacksonObjectMapper;
//    UrlBuilder urlBuilder;
//
//    @Autowired
//    public MockRequestingServiceContextConfiguration(ObjectMapper jacksonObjectMapper, UrlBuilder urlBuilder) {
//        super(urlBuilder, jacksonObjectMapper);
//        this.jacksonObjectMapper = jacksonObjectMapper;
//        this.urlBuilder = urlBuilder;
//    }
//    @Override
//    public String sendRequest(String url) {
//        try {
//            return new FileReader("./ingredient-request.json").toString();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    @Override
//    public String requestIngredient(int id, int amount, String unit) {
//        return super.requestIngredient(id, amount, unit);
//    }

    @Bean
    public RequestingService requestingService(UrlBuilder urlBuilder, ObjectMapper jacksonObjectMapper) {
        return new RequestingService(urlBuilder, jacksonObjectMapper) {
            @Override
            public String sendRequest(String url) {
                return getStringFromJsonFile();
            }

            @Override
            public String requestIngredient(int id, int amount, String unit) {
                return super.requestIngredient(id, amount, unit);
            }
            // implement methods
        };
    }

    private String getStringFromJsonFile() {
        try {
            String path = "src/test/resources/ingredient-request.json";
            String s = new String(Files.readAllBytes(Paths.get(path)));
            System.out.println(s);
            return s;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}