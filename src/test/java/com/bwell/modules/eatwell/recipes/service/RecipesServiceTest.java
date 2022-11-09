package com.bwell.modules.eatwell.recipes.service;

import com.bwell.base.entry.Entry;
import com.bwell.base.entry.EntryRepository;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrient;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrients;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.NutritionElement;
import com.bwell.modules.eatwell.recipes.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Slf4j
class RecipesServiceTest {
    int threshhold;
    private RecipesService recipesService;
    private EntryRepository repository;

    private Nutrients nutrients1;

    @Autowired
    public RecipesServiceTest(RecipesService recipesService, EntryRepository repository) {
        this.recipesService = recipesService;
        this.repository = repository;
    }

    @RepeatedTest(value = 5)
    @Transactional
    public void test() {

        List<Entry> allRecipes = recipesService.getAllRecipes();
        if (allRecipes.size() > 0) {


            Recipe recipe = (Recipe) allRecipes.get(0);
            recipesService.getNutrientsOfRecipeIngredients(recipe);
            Nutrients nutrients = recipesService.sumIngredientsNutrition(recipe.getId());
            log.info("{} ", nutrients);

            if (nutrients1 == null ) {
                nutrients1 = nutrients;
                log.info("inputting the requested valuie to memory{} ", nutrients1);
            }

            int i = nutrients.compareTo(nutrients1);
            log.info("{} ", i);
            assertTrue(i == 0);
        }
    }

}