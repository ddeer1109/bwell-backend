package com.bwell.modules.eatwell.recipes.ingredients.nutrition;

import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientDto;
import com.bwell.modules.eatwell.recipes.model.Recipe;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "nutrients")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
@Data
public class NutrientsDao implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn(unique = true)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Recipe recipe;

    @JoinColumn(unique = true)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private IngredientDto ingredient;

    @Type(type = "jsonb")
    @Column(columnDefinition =  "jsonb")
    private NutrientsDto nutrients;

    public static NutrientsDao create(IngredientDto ingredient, NutrientsDto nutrients){
        NutrientsDao nutrientsDao = new NutrientsDao();
        nutrientsDao.nutrients = nutrients;
        nutrientsDao.ingredient = ingredient;
        return nutrientsDao;
    }
    public static NutrientsDao create(Recipe recipe, NutrientsDto nutrients){
        NutrientsDao nutrientsDao = new NutrientsDao();
        nutrientsDao.nutrients = nutrients;
        nutrientsDao.recipe = recipe;
        return nutrientsDao;
    }
}
