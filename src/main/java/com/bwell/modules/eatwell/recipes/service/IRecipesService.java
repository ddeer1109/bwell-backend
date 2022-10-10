package com.bwell.modules.eatwell.recipes.service;

import com.bwell.base.IBaseService;
import com.bwell.base.entry.Entry;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrients;
import com.bwell.modules.eatwell.recipes.model.Recipe;

import java.util.List;

public interface IRecipesService  extends IBaseService {
    Recipe getRecipe(Long id);
    List<Entry> getAllRecipes();
    Recipe addRecipe(Recipe recipe);
    boolean deleteRecipe(Long id);
    Nutrients sumIngredientsNutrition(long id);
    Nutrients sumRecipesIngredientsNutrition(List<Recipe> recipe);
}
