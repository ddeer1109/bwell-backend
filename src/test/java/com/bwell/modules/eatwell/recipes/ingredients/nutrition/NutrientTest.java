package com.bwell.modules.eatwell.recipes.ingredients.nutrition;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class NutrientTest {

    @Test
    void create() {
        double amount = 10.0;
        NutritionElement fat = Nutrient.Fat.create(amount);
        NutritionElement carbs = Nutrient.Carbohydrates.create(amount);
        NutritionElement protein = Nutrient.Protein.create(amount);
        NutritionElement kcal = Nutrient.Calories.create(amount);

        assertAll(
                () -> assertEquals(fat.getAmount(), BigDecimal.valueOf(amount)),
                () -> assertEquals(fat.getUnit(), "g"),
                () -> assertEquals(fat.getTitle(), "Fat"),

                () -> assertEquals(carbs.getAmount(), BigDecimal.valueOf(amount)),
                () -> assertEquals(carbs.getUnit(), "g"),
                () -> assertEquals(carbs.getTitle(), "Carbohydrates"),

                () -> assertEquals(protein.getAmount(), BigDecimal.valueOf(amount)),
                () -> assertEquals(protein.getUnit(), "g"),
                () -> assertEquals(protein.getTitle(), "Protein"),

                () -> assertEquals(kcal.getAmount(), BigDecimal.valueOf(amount)),
                () -> assertEquals(kcal.getUnit(), "kcal"),
                () -> assertEquals(kcal.getTitle(), "Calories")
        );
    }
}