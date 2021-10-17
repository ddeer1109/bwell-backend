package com.bwell.modules.eatwell.recipes.ingredients.nutrition;

import java.math.BigDecimal;

public enum NUTRIENT {

    FATS(NAMES.FAT.name, 9),
    CARBS(NAMES.CARBS.name, 4),
    PROTEIN(NAMES.PROTEIN.name, 4),
    KCAL(NAMES.KCAL.name, 1),
    UNKNOWN(NAMES.UNKNOWN.name, 1);


    public final String name;
    public final BigDecimal kcalMultiplier;

    NUTRIENT(String name, int kcalMultiplier) {
        this.name = name;
        this.kcalMultiplier = BigDecimal.valueOf(kcalMultiplier);
    }

    public static NUTRIENT ofName(String name) {
        if (name.equals(NAMES.FAT.name))
            return FATS;
        else if (name.equals(NAMES.CARBS.name))
            return CARBS;
        else if (name.equals(NAMES.PROTEIN.name))
            return PROTEIN;
        else if (name.equals(NAMES.KCAL.name))
            return KCAL;
        return UNKNOWN;
    }
}
