package com.bwell.project.eatwell.recipes.ingredients.repositories;

import com.bwell.project.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.project.eatwell.recipes.ingredients.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailedIngredientsRepository extends JpaRepository<DetailedIngredient, Long> {

    DetailedIngredient findDetailedIngredientByIngredient(Ingredient ing);
    @Query(
            value =
                    "insert into detailed_ingredient (ingredient_id, amount, unit_id) values " +
                            "(:ingredient_id, :amount, :unit_id) returning id;",
            nativeQuery = true)
    long insertWithExistingIngredient(@Param("ingredient_id") long ingredientId, @Param("amount") double amount,
                    @Param("unit_id") long unit_id);

}
