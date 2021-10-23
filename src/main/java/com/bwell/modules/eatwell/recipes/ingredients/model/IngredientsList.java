package com.bwell.modules.eatwell.recipes.ingredients.model;

import com.bwell.modules.base.ContentElement;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("ingredients_list")
@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredientsList extends ContentElement {


    public IngredientsList() {
        setType("ingredients_list");
    }
}
