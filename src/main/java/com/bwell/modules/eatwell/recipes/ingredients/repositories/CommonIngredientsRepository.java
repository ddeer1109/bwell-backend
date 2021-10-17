package com.bwell.modules.eatwell.recipes.ingredients.repositories;

import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.Ingredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.Unit;

import java.util.List;
import java.util.Set;

public interface CommonIngredientsRepository {
    DetailedIngredient persistentSave(DetailedIngredient ing);
    Ingredient persistentSave(Ingredient ing);
    List<Ingredient> persistentSave(List<Ingredient> ingredients);
    Set<Unit> persistentSave(long ingredientId, Set<Unit> ingredientUnits);
}
