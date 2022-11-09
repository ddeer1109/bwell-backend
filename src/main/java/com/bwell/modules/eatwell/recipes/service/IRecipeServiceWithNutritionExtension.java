package com.bwell.modules.eatwell.recipes.service;

import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrients;
import com.bwell.modules.eatwell.recipes.model.Recipe;

import java.util.List;

public interface IRecipeServiceWithNutritionExtension extends IRecipesService {
    Nutrients sumIngredientsNutrition(long id);
    Nutrients sumRecipesIngredientsNutrition(List<Recipe> recipe);
    RecipeIngredientsDto getNutrientsOfRecipeIngredients(Recipe recipe);
}