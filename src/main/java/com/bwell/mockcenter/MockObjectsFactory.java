package com.bwell.mockcenter;

import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrient;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.NutritionElement;

import java.util.List;

public class MockObjectsFactory {
    public static List<NutritionElement> nutritionElements(){
        return nutritionElements(300, 30, 20, 12);
    }
    public static List<NutritionElement> nutritionElements(int kcal, int carb, int protein, int fat){
        return List.of(
                Nutrient.Calories.create(kcal),
                Nutrient.Fat.create(fat),
                Nutrient.Carbohydrates.create(carb),
                Nutrient.Protein.create(protein)
        );
    }
}
