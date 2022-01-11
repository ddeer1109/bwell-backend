package com.bwell.modules.eatwell.recipes.ingredients.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class DetailedIngredientDto implements Serializable {
//    private
    private long detailedId;
    private long id;
    private String ingredient;
    private double amount;
    private Unit unit;
    private Set<Unit> possibleUnits;


    public IngredientDto simplifyToIngredientDto(){
        IngredientDto ingredientDto = new IngredientDto();
        if(detailedId != 0L) {
            ingredientDto.setDetailedId(detailedId);
        }
        ingredientDto.setId((int)id);
        ingredientDto.setAmount((int)amount);
        ingredientDto.setUnit(unit.getName());
        return ingredientDto;
    }

}
