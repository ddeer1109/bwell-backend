package com.bwell.modules.eatwell.recipes.ingredients.model;

import com.bwell.base.content.model.ContentElement;
import com.bwell.base.content.model.ListItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;
import java.util.List;

@JsonTypeName("ingredients_list")
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class IngredientsList extends ContentElement {

    public IngredientsList() {
        setType("ingredients_list");
    }


    @Override
    public List<DetailedIngredientDto> getIngredients() {
        return super.getIngredients();
    }

    @Override
    public void setIngredients(List<DetailedIngredientDto> content) {
        super.setIngredients(content);
    }
}
