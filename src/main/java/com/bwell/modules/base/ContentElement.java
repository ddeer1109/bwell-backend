package com.bwell.modules.base;

import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredientDto;
import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientsList;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CustomList.class, name = "custom_list"),
        @JsonSubTypes.Type(value = IngredientsList.class, name = "ingredients_list"),
        @JsonSubTypes.Type(value = TextArea.class, name = "text_area")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ContentElement {
    private long id;
    @JsonPropertyOrder(value = "1")
    private String type;
    private int order;
    private Set<DetailedIngredientDto> ingredients;
    private String text;
    private Set<ListItem> customList;

    @JsonTypeId
    public String getType() {
        return type;
    }
}
