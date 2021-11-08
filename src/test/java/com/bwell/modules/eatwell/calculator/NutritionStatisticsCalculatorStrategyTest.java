package com.bwell.modules.eatwell.calculator;

import com.bwell.modules.eatwell.calculator.model.CalculatorData;
import com.bwell.modules.eatwell.calculator.model.NutrientsDemand;
import com.bwell.modules.eatwell.calculator.model.NutritionStatisticsCalculator;
import com.bwell.modules.eatwell.calculator.model.Strategies.StrategyCodes;
import com.bwell.modules.eatwell.calculator.model.dtos.NutrientsDemandDao;
import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrient;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrients;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.NutritionElement;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

class NutritionStatisticsCalculatorStrategyTest {

    @Test
    void checkCalculation() {
        NutritionStatisticsCalculator calculator = new NutritionStatisticsCalculator();

        CalculatorData data = new CalculatorData();
        data.setAge(25);
        data.setHeightInCm(170);
        data.setIsMan(true);
        data.setBodyMassInKg(70);
        data.setActivityRatio(1.5);
        data.setGoal("GainMass");
        data.setStrategy("Complete");


        calculator.setCalculator(StrategyCodes.valueOf(data.getStrategy()));
        calculator.setData(data);
        NutrientsDemand nutrientsDemand = calculator.calculateCaloriesDemand();
        NutrientsDemandDao dao = nutrientsDemand.createDao();
        System.out.println(dao);


    }
}