package com.bwell.modules.eatwell.recipes.ingredients.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class DetailedIngredientDto implements Serializable {
//    private
    private long id;
    private String ingredient;
    private double quantity;
    private String measure;
    private Set<Unit> possibleMeasures;


}
