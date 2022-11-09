package com.bwell.modules.eatwell.recipes.ingredients.model;

import com.bwell.utils.IdGenerator;
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


    public Long getDetailedId(){
        if (detailedId == 0L){
            setDetailedId(IdGenerator.nextId());
        }
        return detailedId;
    }

    public void setDetailedId(long id){
        detailedId = id;
    }
    public IngredientDto simplifyToIngredientDto(){
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId((int)id);
        ingredientDto.setDetailedId(detailedId);
        ingredientDto.setAmount((int)amount);
        ingredientDto.setUnit(unit.getName());
        ingredientDto.setName(ingredient);
        return ingredientDto;
    }

}
