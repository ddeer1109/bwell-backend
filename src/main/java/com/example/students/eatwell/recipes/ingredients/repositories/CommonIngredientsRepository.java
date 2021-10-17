package com.example.students.eatwell.recipes.ingredients.repositories;

import com.example.students.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.example.students.eatwell.recipes.ingredients.model.Ingredient;
import com.example.students.eatwell.recipes.ingredients.model.Unit;

import java.util.List;
import java.util.Set;

public interface CommonIngredientsRepository {
    DetailedIngredient persistentSave(DetailedIngredient ing);
    Ingredient persistentSave(Ingredient ing);
    List<Ingredient> persistentSave(List<Ingredient> ingredients);
    Set<Unit> persistentSave(long ingredientId, Set<Unit> ingredientUnits);
}
