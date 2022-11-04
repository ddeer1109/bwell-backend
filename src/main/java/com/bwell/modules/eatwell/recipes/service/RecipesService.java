package com.bwell.modules.eatwell.recipes.service;

import com.bwell.base.*;
import com.bwell.base.content.ContentRepository;
import com.bwell.base.entry.Entry;
import com.bwell.base.entry.EntryRepository;
import com.bwell.base.rating.repository.RatingRepository;
import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredientDto;
import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientDto;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrient;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrients;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.NutrientsDao;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.NutrientsDto;
import com.bwell.modules.eatwell.recipes.ingredients.repositories.IngredientDtoRepository;
import com.bwell.modules.eatwell.recipes.ingredients.repositories.NutrientsDaoRepository;
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
public class RecipesService extends BaseService implements IRecipesService {

    private final IngredientService ingredientService;
    private final NutrientsDaoRepository nutrientsDaoRepository;
    private final Map<Long, Nutrients> recipesNutrientsCache = new ConcurrentHashMap<>();
    private final Map<IngredientDto.IngredientRecord, Nutrients> ingredientNutrientCache = new ConcurrentHashMap<>();


    public RecipesService(ContentRepository content,
                          EntryRepository entry,
                          RatingRepository rating,
                          UserService user,
                          IngredientService ingredientService,
                          NutrientsDaoRepository nutrientsDaoRepository) {
        super(content, entry, rating, user);
        this.ingredientService = ingredientService;
        this.nutrientsDaoRepository = nutrientsDaoRepository;
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
//
//        nutrientsDaoRepository
//                .findByRecipe_Id(tempRecipe.getId())
//                .ifPresent(nutr -> nutrientsDaoRepository.deleteById(nutr.getId()));
//        List<DetailedIngredientDto> ingredients = recipe.getIngredients();
//        ingredientDtoRepository.saveAll(ingredients.stream()
//                .map(ing -> {
//                    IngredientDto ingredientDto = ing.simplifyToIngredientDto();
//                    ingredientDto.setRecipe(tempRecipe);
//                    return ingredientDto;
//                })
//                .collect(Collectors.toList()));

        log.info("Returning {}", tempRecipe);

        return tempRecipe;
    }

    @Override
    public Nutrients sumIngredientsNutrition(long recipeId){
//        Optional<NutrientsDao> byRecipeId = nutrientsDaoRepository.findByRecipe_Id(recipeId);
//        if (byRecipeId.isPresent()){
//            return byRecipeId.get().getNutrients().toNutrients();
//        }
        log.info("Currently cached recipe {} ", recipesNutrientsCache);

        return Optional.ofNullable(recipesNutrientsCache.get(recipeId)).or(() -> {
            List<DetailedIngredientDto> ingredients = getRecipe(recipeId).getIngredients();

            return ingredients.stream().map(this::requestIngredientNutrients).reduce((nutrients, nutrients2) -> {
                nutrients.addNutrients(nutrients2);
                return nutrients;
            }).map(nutrients -> {
                recipesNutrientsCache.put(recipeId, nutrients);
                return nutrients;
            });
        }).orElse(Nutrients.empty());
//
//        return nutrientsDaoRepository.findByRecipe_Id(recipeId).orElseGet(() -> {
//            Nutrients nutrientsSum = Nutrients.empty();
//            List<DetailedIngredientDto> ingredients = getRecipe(recipeId).getIngredients();
//
//            ingredients.forEach(dto -> {
//                log.info("now doing this {}", dto);
//                Nutrients nutrients = cacheIngredientNutrients(dto);
////                        .findByIngredient_DetailedId(dto.getDetailedId())
////                        .map(nutrientsDao -> nutrientsDao.getNutrients().toNutrients())
////                        .orElseGet(() -> cacheIngredientNutrients(dto));
//                nutrientsSum.addNutrients(nutrients);
//            });
//            return cacheRecipeNutrients(recipeId, nutrientsSum);
//        }).getNutrients().toNutrients();
//        if (byRecipeId.isEmpty() || !byRecipeId.get().getNutrients().getCalories().equals(nutrientsSum.get(Nutrient.Calories))){
//            cacheRecipeNutrients(recipeId, nutrientsSum);
//        }
//
//        return nutrientsSum;
    }

    private NutrientsDao cacheRecipeNutrients(long recipeId, Nutrients nutrientsSum) {
        NutrientsDao nutrientsDao = new NutrientsDao();
        Optional<NutrientsDao> byRecipe_id = nutrientsDaoRepository.findByRecipe_Id(recipeId);
        if (byRecipe_id.isEmpty()){
            Optional<Entry> byId = entry.findById(recipeId);
            nutrientsDao.setNutrients(NutrientsDto.ofNutrients(nutrientsSum));
            nutrientsDao.setRecipe((Recipe)byId.get());
            return nutrientsDaoRepository.save(nutrientsDao);
        } else {
            NutrientsDao nutrientsDao1 = byRecipe_id.get();
            nutrientsDao1.setNutrients(NutrientsDto.ofNutrients(nutrientsSum));
            return nutrientsDaoRepository.save(nutrientsDao1);
        }
    }

    private Nutrients requestIngredientNutrients(DetailedIngredientDto dto) {
        IngredientDto.IngredientRecord record = dto.simplifyToIngredientDto().getRecord();
        log.info("Ingredients cache {} ", ingredientNutrientCache.toString());
        return ingredientNutrientCache.computeIfAbsent(record, (ingredientRecord -> {
            log.info("dto v1: {} ", dto);
            DetailedIngredient ingredientDetails_api = ingredientService.getIngredientDetails_API(dto.simplifyToIngredientDto());
            Nutrients nutrition = ingredientDetails_api.getNutrition();
            return nutrition;
        }));
//        IngredientDto inDb = ingredientDtoRepository.save(ingredientDetails_api.createDto().simplifyToIngredientDto());

//        NutrientsDto nutrientsDto = NutrientsDto.ofNutrients(nutrition);
//        NutrientsDao nutrientsDao = NutrientsDao.create(inDb, nutrientsDto);
//
//        return nutrientsDaoRepository.save(nutrientsDao).getNutrients().toNutrients();
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
                .map(rec -> sumIngredientsNutrition(rec.getId()))
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
