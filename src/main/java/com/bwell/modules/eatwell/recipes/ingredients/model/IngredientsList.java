package com.bwell.modules.eatwell.recipes.ingredients.model;

import com.bwell.base.content.model.ContentElement;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;

@JsonTypeName("ingredients_list")
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class IngredientsList extends ContentElement {

    public IngredientsList() {
        setType("ingredients_list");
    }
}
