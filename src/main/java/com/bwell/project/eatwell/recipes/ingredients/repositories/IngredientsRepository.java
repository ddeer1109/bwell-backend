package com.bwell.project.eatwell.recipes.ingredients.repositories;

import com.bwell.project.eatwell.recipes.ingredients.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredient, Long> {

    public List<Ingredient> findAllByNameContains(String s);
    @Query(
            value =
                    "insert into ingredient (id, name) values " +
                            "(:id, :name) returning id;",
            nativeQuery = true)
    public long insertWithoutUnits(@Param("id") long ingredientId, @Param("name") String name);


}
