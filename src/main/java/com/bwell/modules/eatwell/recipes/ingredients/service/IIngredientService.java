package com.bwell.modules.eatwell.recipes.ingredients.service;

import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.Ingredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientDto;
import com.bwell.modules.eatwell.recipes.ingredients.model.Unit;

import java.util.List;

public interface IIngredientService {
    List<DetailedIngredient> getDetailedIngredients();
    List<Ingredient> getIngredients();
    List<Unit> getUnits();
    List<Ingredient> queryIngredients_API(String query);
    DetailedIngredient getIngredientDetails_API(int id, int amount, String unit);
    DetailedIngredient getIngredientDetails_API(IngredientDto dto);
}
