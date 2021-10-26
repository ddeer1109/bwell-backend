package com.bwell.modules.eatwell.recipes.model;

import com.bwell.modules.base.Entry;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.*;

@JsonTypeName("recipe")
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name="Recipe")
public class Recipe extends Entry {

    public Recipe() {
        setModule("recipe");
    }


}
