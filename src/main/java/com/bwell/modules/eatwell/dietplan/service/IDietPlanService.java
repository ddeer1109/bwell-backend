package com.bwell.modules.eatwell.dietplan.service;

import com.bwell.modules.eatwell.dietplan.model.DietPlan;
import com.bwell.modules.eatwell.recipes.model.Recipe;

import java.util.List;

public interface IDietPlanService {
    DietPlan getDietPlan(long userId);
    Recipe setBreakfast(long recipeId, long userId);
    Recipe setLunch(long recipeId, long userId);
    Recipe setDinner(long recipeId, long userId);
    Recipe setSupper(long recipeId, long userId);
    Recipe getBreakfast(long userId);
    Recipe getLunch(long userId);
    Recipe getDinner(long userId);
    Recipe getSupper(long userId);
    List<Recipe> getAllMeals(long userId);
}
