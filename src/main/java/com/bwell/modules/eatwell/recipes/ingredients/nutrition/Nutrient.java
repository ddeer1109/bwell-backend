package com.bwell.modules.eatwell.recipes.ingredients.nutrition;

import java.math.BigDecimal;

public enum Nutrient {

    Fat(Names.FAT.name, 9),
    Carbohydrates(Names.CARBS.name, 4),
    Protein(Names.PROTEIN.name, 4),
    Calories(Names.KCAL.name, 1),
    UNKNOWN(Names.UNKNOWN.name, 1);


    public final String name;
    public BigDecimal kcalMultiplier;

    Nutrient(String name, int kcalMultiplier) {
        this.name = name;
        this.kcalMultiplier = BigDecimal.valueOf(kcalMultiplier);
    }

    public static Nutrient ofName(String name) {
        if (name.equals(Names.FAT.name))
            return Fat;
        else if (name.equals(Names.CARBS.name))
            return Carbohydrates;
        else if (name.equals(Names.PROTEIN.name))
            return Protein;
        else if (name.equals(Names.KCAL.name))
            return Calories;
        return UNKNOWN;
    }

    public NutritionElement create(double amount) {
        NutritionElement nutritionElement = new NutritionElement();
        nutritionElement.setTitle(this.name);
        nutritionElement.setAmount(BigDecimal.valueOf(amount));
        nutritionElement.setUnit(this == Calories ? Names.KCAL_UNIT.name : Names.GRAMS_UNIT.name);

        return nutritionElement;
    }
    public NutritionElement create(BigDecimal amount) {
        NutritionElement nutritionElement = new NutritionElement();
        nutritionElement.setTitle(this.name);
        nutritionElement.setAmount(amount);
        nutritionElement.setUnit(this == Calories ? Names.KCAL_UNIT.name : Names.GRAMS_UNIT.name);
        return nutritionElement;
    }
}
