package com.bwell.modules.eatwell.recipes.ingredients.nutrition;

public enum Names {
    FAT("Fat"),
    CARBS("Carbohydrates"),
    PROTEIN("Protein"),
    KCAL("Calories"),
    UNKNOWN("Unknown"),
    GRAMS_UNIT("g"),
    KCAL_UNIT("kcal");


    public final String name;


    Names(String name) {
        this.name = name;
    }
}
