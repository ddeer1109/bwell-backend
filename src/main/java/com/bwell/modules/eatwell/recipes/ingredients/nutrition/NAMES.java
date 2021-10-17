package com.bwell.modules.eatwell.recipes.ingredients.nutrition;

public enum NAMES {
    FAT("Fat"),
    CARBS("Carbohydrates"),
    PROTEIN("Protein"),
    KCAL("Calories"),
    UNKNOWN("Unknown");


    public final String name;


    NAMES(String name) {
        this.name = name;
    }
}
