package com.bwell.modules.eatwell.recipes.ingredients.repositories;

import com.bwell.modules.eatwell.recipes.ingredients.nutrition.NutrientsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NutrientsDaoRepository extends JpaRepository<NutrientsDao, Long> {
    Optional<NutrientsDao> findByRecipe_Id(long id);
    Optional<NutrientsDao> findByIngredient_DetailedId(long id);
    boolean deleteAllByRecipe_Id(long recipeId);
}
