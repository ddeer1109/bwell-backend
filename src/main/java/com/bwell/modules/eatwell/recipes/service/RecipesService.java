package com.bwell.modules.eatwell.recipes.service;

import com.bwell.base.*;
import com.bwell.base.content.ContentRepository;
import com.bwell.base.entry.Entry;
import com.bwell.base.entry.EntryRepository;
import com.bwell.base.rating.repository.RatingRepository;
import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredientDto;
import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientDto;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrients;
import com.bwell.modules.eatwell.recipes.ingredients.service.IngredientService;
import com.bwell.modules.eatwell.recipes.model.Recipe;
import com.bwell.user.data.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecipesService extends BaseService implements IRecipeServiceWithNutritionExtension {

    private final IngredientService ingredientService;
    private final Map<Long, Nutrients> recipesNutrientsCache = new ConcurrentHashMap<>();
    private final Map<IngredientDto.IngredientRecord, Nutrients> ingredientNutrientCache = new ConcurrentHashMap<>();


    private static int requestsCounter = 0;

    public RecipesService(ContentRepository content,
                          EntryRepository entry,
                          RatingRepository rating,
                          UserService user,
                          IngredientService ingredientService) {
        super(content, entry, rating, user);
        this.ingredientService = ingredientService;
    }
//
//    @Autowired
//    public RecipesService(ContentRepository content, EntryRepository entry, RatingRepository rating, IngredientService ingrService) {
//        super(content, entry, rating);
//        ingredientService = ingrService;
//    }

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
    public Nutrients sumRecipesIngredientsNutrition(List<Recipe> recipe) {

        Nutrients empty = Nutrients.empty();
        if (recipe.size() == 0){
            return empty;
        }
        if (recipe.size() == 1){
            return sumIngredientsNutrition(recipe.get(0).getId());
        }

        //                            return nutr1;
        return recipe
                .stream()
                .map(rec -> sumIngredientsNutrition(rec.getId()))
                .reduce(
                        empty,
                        Nutrients::addNutrients
                );

    }

    @Override
    public RecipeIngredientsDto getNutrientsOfRecipeIngredients(Recipe recipe) {
        Set<RecipeIngredientsDto.IngredientNutrientPair> collect = recipe.getIngredients()
                .stream()
                .map(
                        detailedIngr -> {
                            IngredientDto.IngredientRecord record = detailedIngr.simplifyToIngredientDto().getRecord();
                            Nutrients nutrients = requestIngredientNutrients(detailedIngr);
                            return new RecipeIngredientsDto.IngredientNutrientPair(record, nutrients);
                        }
                ).collect(Collectors.toSet());
        return new RecipeIngredientsDto(
                recipe,
                collect
        );
    }

    @Override
    public boolean deleteRecipe(Long id) {
        entry.deleteById(id);

        return true;
    }

    @Override
    public Nutrients sumIngredientsNutrition(long recipeId){
        log.debug("Requests sent: {}", getRequestsCounter());
        log.debug("Currently cached recipe {}", recipesNutrientsCache);

        return getRecipe(recipeId).getIngredients().stream()
                .map(this::requestIngredientNutrients)
                .reduce(Nutrients::addNutrients)
                .orElse(Nutrients.empty());
    }

    private Nutrients requestIngredientNutrients(DetailedIngredientDto dto) {
        log.info("Requests sent: {} \nIngredients cache {} ", getRequestsCounter(), ingredientNutrientCache);
        IngredientDto.IngredientRecord record = dto.simplifyToIngredientDto().getRecord();
        return ingredientNutrientCache.computeIfAbsent(record, (ingredientRecord -> {
            log.info("dto v1: {} ", dto);
            DetailedIngredient ingredientDetails_api = ingredientService.getIngredientDetails_API(dto.simplifyToIngredientDto());
            Nutrients nutrition = ingredientDetails_api.getNutrition();
            requestsCounter += 1;
            return nutrition;
        }));
    }



    public static int getRequestsCounter() {
        return requestsCounter;
    }

}
