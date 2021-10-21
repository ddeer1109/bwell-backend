package com.bwell.modules.eatwell.recipes.ingredients.model;

import lombok.Data;

@Data
public class IngredientDto {
    private int id;
    private int amount;
    private String unit;
}
