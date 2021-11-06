package com.bwell.modules.eatwell.recipes.service;

import com.bwell.modules.base.*;
import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredientDto;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrient;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrients;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.NutrientsDto;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.NutritionElement;
import com.bwell.modules.eatwell.recipes.ingredients.service.IngredientService;
import com.bwell.modules.eatwell.recipes.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RecipesService extends BaseService implements IRecipesService {

    private final IngredientService ingredientService;
    @Autowired
    public RecipesService(ContentRepository content, EntryRepository entry, RatingRepository rating, IngredientService ingrService) {
        super(content, entry, rating);
        ingredientService = ingrService;
    }

    @Override
    public Recipe getRecipe(Long id) {
        return (Recipe)entry
                .findById(id)
                .orElseThrow();
    }

    @Override
    public List<Entry> getAllRecipes() {
        return entry.findAllByModuleEquals("recipe");
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        rating.save(recipe.getRating());
        content.saveAll(recipe.getContent());
        Recipe tempRecipe = entry.save(recipe);

        log.info("Returning {}", tempRecipe);

        return tempRecipe;
    }

    @Override
    public NutrientsDto sumIngredientsNutrition(long recipeId){
        Nutrients nutrientsSum = Nutrients.empty();
        List<DetailedIngredientDto> ingredients = getRecipe(recipeId).getIngredients();
        ingredients.forEach(dto -> {
            log.info("now doing this {}", dto);
            Nutrients ingredientNutrition = ingredientService.getIngredientDetails_API(dto).getNutrition();
            nutrientsSum.addNutrients(ingredientNutrition);
        });
        return NutrientsDto.ofNutrients(nutrientsSum);
    }

    @Override
    public boolean deleteRecipe(Long id) {
        entry.deleteById(id);

        return true;
    }
}
