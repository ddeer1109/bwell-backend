package com.bwell.modules.eatwell.calculator.service;

import com.bwell.modules.eatwell.calculator.model.CalculatorData;
import com.bwell.modules.eatwell.calculator.model.dtos.IngredientCoverageDto;
import com.bwell.modules.eatwell.calculator.model.dtos.NutrientsDemandDao;
import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientDto;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrients;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.NutrientsDto;

import java.util.List;

public interface ICalculatorService {
    IngredientCoverageDto getCoverageFor(long userId, long recipeId);
    IngredientCoverageDto getCoverageFor(long userId, IngredientDto ingredientDto);
//    IngredientCoverageDto getCoverageFor(long userId, long ingredientDto);
    NutrientsDemandDao calculateUserDemand(CalculatorData calculatorData);
    NutrientsDemandDao getDemandForUser(long id);
    IngredientCoverageDto getCoverageOfDietPlan(long userId);
    NutrientsDto getNutrientsSumOfDietPlan(long userId);
}
