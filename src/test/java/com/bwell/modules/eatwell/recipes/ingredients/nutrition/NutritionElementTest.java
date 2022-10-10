package com.bwell.modules.eatwell.recipes.ingredients.nutrition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class NutritionElementTest {

    NutritionElement calories;
    NutritionElement protein;
    NutritionElement carbs;
    NutritionElement fats;
    @BeforeEach
    void setUp() {
        setupConfig();
    }

    void setupConfig(){
        calories = new NutritionElement();
        calories.setTitle(Names.KCAL.name);
        calories.setAmount(BigDecimal.valueOf(200));
        calories.setUnit("kcal");
        protein = new NutritionElement();
        protein.setTitle(Names.PROTEIN.name);
        protein.setAmount(BigDecimal.valueOf(20));
        protein.setUnit("g");
        carbs = new NutritionElement();
        carbs.setTitle(Names.CARBS.name);
        carbs.setAmount(BigDecimal.valueOf(4));
        carbs.setUnit("kcal");
        fats = new NutritionElement();
        fats.setTitle(Names.FAT.name);
        fats.setAmount(BigDecimal.valueOf(12));
        fats.setUnit("kcal");
    }

    @Test
    void inCalories() {
        BigDecimal caloriesMultiplicandForProtein = BigDecimal.valueOf(4);
        BigDecimal caloriesMultiplicandForFat = BigDecimal.valueOf(9);
        BigDecimal caloriesMultiplicandForCarbs = BigDecimal.valueOf(4);
        BigDecimal proteinInKcal = protein.getAmount().multiply(caloriesMultiplicandForProtein);
        BigDecimal fatInKcal = fats.getAmount().multiply(caloriesMultiplicandForFat);
        BigDecimal carbsInKcal = carbs.getAmount().multiply(caloriesMultiplicandForCarbs);

        assertAll(
                        () -> assertEquals(calories.gramsToKcal().getAmount(), calories.getAmount()),
                        () -> assertNotEquals(calories.gramsToKcal().getAmount(), calories.getAmount().subtract(BigDecimal.TEN)),
                        () -> assertEquals(protein.gramsToKcal().getAmount(), proteinInKcal),
                        () -> assertEquals(fats.gramsToKcal().getAmount(), fatInKcal),
                        () -> assertEquals(carbs.gramsToKcal().getAmount(), carbsInKcal)
                );
    }
}