package com.bwell.modules.eatwell.recipes.service;

import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientDto;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrients;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.NutritionElement;
import com.bwell.modules.eatwell.recipes.model.Recipe;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeIngredientsDto {

    private final Long recipeId;
    private final String recipeName;
    private final Set<IngredientNutrientPair> ingredientsNutrients;

    public RecipeIngredientsDto(Recipe recipe, Set<IngredientNutrientPair> ingredientsNutrients) {
        this.recipeId = recipe.getId();
        this.recipeName = recipe.getTitle();
        this.ingredientsNutrients = ingredientsNutrients;
    }

    static class IngredientNutrientPair {
        final IngredientDto.IngredientRecord ingredient;
        final List<NutritionElement> nutrients;

        IngredientNutrientPair(IngredientDto.IngredientRecord ingredientRecord, Nutrients nutrients) {
            this.ingredient = ingredientRecord;
            this.nutrients = nutrients.getNutrients();
        }

        public IngredientDto.IngredientRecord getIngredient() {
            return ingredient;
        }

        public List<NutritionElement> getNutrients() {
            return nutrients;
        }
    }
}
