package com.bwell.modules.eatwell.recipes.ingredients.SpoonacularApi;

import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientWithNutrients;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

@Import(MockRequestingServiceContextConfiguration.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class RequestingServiceTest {

    IIRequestingService requestingService;

    @Autowired
    public RequestingServiceTest(RequestingService requestingService) {
        this.requestingService = requestingService;
    }

    @Test
    void nutrientsShouldBeMappedCorrectly() {
        IngredientWithNutrients g = requestingService.getIngredientWithNutrients(11529, 200, "g");
        assertAll(
                () -> assertNotNull(g),
                () -> assertNotNull(g.getNutrition()),
                () -> assertNotEquals( 0, g.getNutrition().getNutrients().size())
        );

    }
    @Test
    void nutrientsShouldBeMappedCorrectlyInDetailedIngredient() {
        DetailedIngredient g = requestingService.getIngredient(11529, 200, "g");
        assertAll(
                () -> assertNotNull(g),
                () -> assertNotNull(g.getNutrition()),
                () -> assertNotEquals( 0, g.getNutrition().getNutrients().size())
        );

    }


}