package com.bwell.modules.eatwell.recipes.ingredients.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class IngredientDto {
    private int id;
    private double amount;
    private String unit;
    @Id
    @GeneratedValue
    private Long detailedId;
}
