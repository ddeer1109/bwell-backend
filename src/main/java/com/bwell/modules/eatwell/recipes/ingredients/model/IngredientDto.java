package com.bwell.modules.eatwell.recipes.ingredients.model;

import lombok.Data;

@Data
public class IngredientDto {
    private int id;
    private double amount;
    private String unit;
    private Long detailedId;
}
