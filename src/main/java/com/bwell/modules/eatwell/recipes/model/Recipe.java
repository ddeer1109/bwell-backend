package com.bwell.modules.eatwell.recipes.model;

import com.bwell.modules.base.entry.Entry;
import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredientDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@JsonTypeName("recipe")
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name="Recipe")
public class Recipe extends Entry {

    public Recipe() {
        setModule("recipe");
    }

    @JsonIgnore
    public List<DetailedIngredientDto> getIngredients(){
        return getContent().stream()
                .filter(element -> element.getType().equals("ingredients_list"))
                .flatMap((element) -> element.getIngredients().stream())
                .collect(Collectors.toList());
    }

}
