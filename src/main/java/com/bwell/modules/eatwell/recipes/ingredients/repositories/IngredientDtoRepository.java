package com.bwell.modules.eatwell.recipes.ingredients.repositories;

import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientDtoRepository extends JpaRepository<IngredientDto, Long> {
}
