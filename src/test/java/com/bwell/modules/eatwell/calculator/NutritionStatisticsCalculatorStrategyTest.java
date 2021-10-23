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
//        calculator.setCalculator(StrategyCodes.Complete);
        calculator.setData(data);
        NutrientsDemand nutrientsDemand = calculator.calculateCaloriesDemand();
        NutrientsDemandDao dao = nutrientsDemand.createDao();
        System.out.println(dao);

//        nutrientsDemand.setProportion(40, 30, 30);
//        nutrientsDemand.defaultProportion();
//        System.out.println(nutrientsDemand.getCaloriesDemand());
//        System.out.println(nutrientsDemand.getElementDemand(Nutrient.Calories));
//        System.out.println(nutrientsDemand.getElementDemand(Nutrient.Protein));
//        System.out.println(nutrientsDemand.getElementDemand(Nutrient.Carbohydrates));
//        System.out.println(nutrientsDemand.getElementDemand(Nutrient.Fat));
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        NutritionElement multiply = nutrientsDemand.getElementDemand(Nutrient.Protein);
//        System.out.println(multiply);
//        NutritionElement multiply1 = nutrientsDemand.getElementDemand(Nutrient.Carbohydrates);
//        System.out.println(multiply1);
//        NutritionElement multiply2 = nutrientsDemand.getElementDemand(Nutrient.Fat);
//        System.out.println(multiply2);
//        System.out.println(       );
//        System.out.println(       );
//        System.out.println(       );
//        System.out.println(       );
//        DetailedIngredient ingredient = new DetailedIngredient();
//        Nutrients nutrients = new Nutrients();
//        nutrients.setNutrients(Set.of(
//                Nutrient.Carbohydrates.create(0),
//                Nutrient.Fat.create(2.59),
//                Nutrient.Protein.create(21.23),
//                Nutrient.Calories.create(BigDecimal.valueOf(21.23).multiply(BigDecimal.valueOf(4)).add(BigDecimal.valueOf(9).multiply(BigDecimal.valueOf(2.59))).doubleValue())
//            )
//        );
//        ingredient.setNutrition(nutrients);
//
//        System.out.println(ingredient);
//        System.out.println(nutrients.getNutrients());
//
//
//        nutrientsDemand.getIngredientCoverage(ingredient);
//
//
//        BigDecimal cd = BigDecimal.ZERO;

//        Nutrient carbs = Nutrient.valueOf("Carbohydrates");
//        System.out.println(carbs.kcalMultiplier + " HERE I AM" );
//
//        ingredient.getNutrition().getNutrients().forEach(nutr -> {
//            Nutrient nutrient = Nutrient.valueOf(nutr.getTitle());
//            NutritionElement nutritionElement = nutrient.create(nutr.getAmount().doubleValue());
//            System.out.println(nutritionElement.gramsToKcal());
////            BigDecimal multiply3 = nutr.getAmount().multiply(nutritionElement.getAmount());
////            System.out.println(multiply3);
////            cd.add(multiply3);
//            System.out.println(cd + " sum");
//        });


    }
}