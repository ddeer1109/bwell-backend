package com.bwell.modules.eatwell.recipes.ingredients.nutrition;

import java.math.BigDecimal;

public enum Nutrient {

    Fat(NAMES.FAT.name, 9),
    Carbohydrates(NAMES.CARBS.name, 4),
    Protein(NAMES.PROTEIN.name, 4),
    Calories(NAMES.KCAL.name, 1),
    UNKNOWN(NAMES.UNKNOWN.name, 1);


    public final String name;
    public BigDecimal kcalMultiplier;

    Nutrient(String name, int kcalMultiplier) {
        this.name = name;
        this.kcalMultiplier = BigDecimal.valueOf(kcalMultiplier);
    }

    public static Nutrient ofName(String name) {
        if (name.equals(NAMES.FAT.name))
            return Fat;
        else if (name.equals(NAMES.CARBS.name))
            return Carbohydrates;
        else if (name.equals(NAMES.PROTEIN.name))
            return Protein;
        else if (name.equals(NAMES.KCAL.name))
            return Calories;
        return UNKNOWN;
    }

    public NutritionElement create(double amount) {
        NutritionElement nutritionElement = new NutritionElement();
        nutritionElement.setTitle(this.name);
        nutritionElement.setAmount(BigDecimal.valueOf(amount));
        nutritionElement.setUnit(this == Calories ? NAMES.KCAL_UNIT.name : NAMES.GRAMS_UNIT.name);
        return nutritionElement;
    }
    public NutritionElement create(BigDecimal amount) {
        NutritionElement nutritionElement = new NutritionElement();
        nutritionElement.setTitle(this.name);
        nutritionElement.setAmount(amount);
        nutritionElement.setUnit(this == Calories ? NAMES.KCAL_UNIT.name : NAMES.GRAMS_UNIT.name);
        return nutritionElement;
    }
}
