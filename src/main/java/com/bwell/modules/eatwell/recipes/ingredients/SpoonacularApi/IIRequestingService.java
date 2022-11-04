package com.bwell.modules.eatwell.recipes.ingredients.SpoonacularApi;

import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientWithNutrients;

public interface IIRequestingService {

    String requestIngredient(int id, int amount, String unit);

    String requestIngredient(int id, double amount, String unit);

    String requestIngredientsQuery(String query);

    DetailedIngredient getIngredient(int id, double amount, String unit);

    IngredientWithNutrients getIngredientWithNutrients(int id, double amount, String unit);

    String sendRequest(String url);
}
