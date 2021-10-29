package com.bwell.modules.eatwell.recipes.service;

import com.bwell.modules.base.Entry;
import com.bwell.modules.eatwell.recipes.model.Recipe;

import java.util.List;

public interface IRecipesService {
    Recipe getRecipe(Long id);
    List<Entry> getAllRecipes();
    Recipe addRecipe(Recipe recipe);
    boolean deleteRecipe(Long id);
}
