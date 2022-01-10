package com.bwell.base.content.model;

import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientsList;

public enum ContentTypes {
    custom_list(CustomList.class),
    ingredients_list(IngredientsList.class),
    text_area(TextArea.class),
    ;

    public final Class<? extends ContentElement> CLASS;
    ContentTypes(Class<? extends ContentElement > ingredientsListClass) {
        CLASS = ingredientsListClass;
    }
}
