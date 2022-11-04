package com.bwell.modules.eatwell.recipes.ingredients.model;

import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrients;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class IngredientWithNutrients {
    private long id;
    private String name;

    @JsonGetter("originalName")
    public String getName() {
        return name;
    }

    @JsonSetter("originalName")
    public void setName(String name) {
        this.name = name;
    }

    private BigDecimal amount;
    private Unit unit;
    private Nutrients nutrition;

    public Nutrients getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrients nutrition) {
        this.nutrition = nutrition;
    }
}
