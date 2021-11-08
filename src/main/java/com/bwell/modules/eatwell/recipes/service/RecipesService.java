package com.bwell.modules.eatwell.recipes.service;

import com.bwell.modules.base.*;
import com.bwell.modules.base.content.ContentRepository;
import com.bwell.modules.base.entry.Entry;
import com.bwell.modules.base.entry.EntryRepository;
import com.bwell.modules.base.rating.RatingRepository;
import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredientDto;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrients;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.NutrientsDto;
import com.bwell.modules.eatwell.recipes.ingredients.service.IngredientService;
import com.bwell.modules.eatwell.recipes.model.Recipe;
import com.bwell.modules.mockcenter.MockObjectsFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Nutrients sumIngredientsNutrition(long recipeId){
        Nutrients nutrientsSum = Nutrients.empty();
        List<DetailedIngredientDto> ingredients = getRecipe(recipeId).getIngredients();

        ingredients.forEach(dto -> {
            log.info("now doing this {}", dto);
            DetailedIngredient ingredientDetails_api = ingredientService.getIngredientDetails_API(dto);
            Nutrients ingredientNutrition = ingredientDetails_api.getNutrition();
            nutrientsSum.addNutrients(ingredientNutrition);
        });
        return nutrientsSum;
    }

    @Override
    public Nutrients sumRecipesIngredientsNutrition(List<Recipe> recipe) {

        Nutrients empty = Nutrients.empty();
        if (recipe.size() == 0){
            return empty;
        }
        if (recipe.size() == 1){
            return sumIngredientsNutrition(recipe.get(0).getId());
        }

        Nutrients reduce = recipe
                .stream()
                .flatMap(rec -> rec.getIngredients().stream())
                .map( rec -> ingredientService.getIngredientDetails_API(rec).getNutrition())
                .reduce(
                        empty,
                        (nutr1, nutr2) -> {
                            nutr1.addNutrients(nutr2);
                            return nutr1;
                        }
                );
        return reduce;

    }

    @Override
    public boolean deleteRecipe(Long id) {
        entry.deleteById(id);

        return true;
    }
}
