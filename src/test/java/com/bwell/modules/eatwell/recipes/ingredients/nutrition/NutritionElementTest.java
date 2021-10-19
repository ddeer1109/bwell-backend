package com.bwell.modules.eatwell.recipes.ingredients.nutrition;

import org.junit.jupiter.api.BeforeEach;
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
        calories.setTitle(NAMES.KCAL.name);
        calories.setAmount(BigDecimal.valueOf(200));
        calories.setUnit("kcal");
        protein = new NutritionElement();
        protein.setTitle(NAMES.PROTEIN.name);
        protein.setAmount(BigDecimal.valueOf(20));
        protein.setUnit("g");
        carbs = new NutritionElement();
        carbs.setTitle(NAMES.CARBS.name);
        carbs.setAmount(BigDecimal.valueOf(4));
        carbs.setUnit("kcal");
        fats = new NutritionElement();
        fats.setTitle(NAMES.FAT.name);
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
                        () -> assertEquals(calories.gramsToKcal(), calories.getAmount()),
                        () -> assertNotEquals(calories.gramsToKcal(), calories.getAmount().subtract(BigDecimal.TEN)),
                        () -> assertEquals(protein.gramsToKcal(), proteinInKcal),
                        () -> assertEquals(fats.gramsToKcal(), fatInKcal),
                        () -> assertEquals(carbs.gramsToKcal(), carbsInKcal)
                );
    }
}