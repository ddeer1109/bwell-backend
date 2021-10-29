package com.bwell.modules.eatwell.calculator.service;

import com.bwell.modules.eatwell.calculator.model.CalculatorData;
import com.bwell.modules.eatwell.calculator.model.dtos.IngredientCoverageDto;
import com.bwell.modules.eatwell.calculator.model.dtos.NutrientsDemandDao;
import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientDto;

import java.util.List;

public interface ICalculatorService {
    IngredientCoverageDto getCoverageFor(long userId, List<IngredientDto> ingredientsDto);
    IngredientCoverageDto getCoverageFor(long userId, IngredientDto ingredientDto);
    NutrientsDemandDao calculateUserDemand(CalculatorData calculatorData);
    NutrientsDemandDao getDemandForUser(long id);
}
