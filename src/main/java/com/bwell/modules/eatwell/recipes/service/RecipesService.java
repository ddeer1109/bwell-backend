package com.bwell.modules.eatwell.recipes.service;

import com.bwell.modules.base.*;
import com.bwell.modules.base.content.ContentRepository;
import com.bwell.modules.base.entry.Entry;
import com.bwell.modules.base.entry.EntryRepository;
import com.bwell.modules.base.rating.RatingRepository;
import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredientDto;
import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientDto;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrients;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.NutrientsDao;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.NutrientsDto;
import com.bwell.modules.eatwell.recipes.ingredients.repositories.IngredientDtoRepository;
import com.bwell.modules.eatwell.recipes.ingredients.repositories.NutrientsDaoRepository;
import com.bwell.modules.eatwell.recipes.ingredients.service.IngredientService;
import com.bwell.modules.eatwell.recipes.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RecipesService extends BaseService implements IRecipesService {

    private final IngredientService ingredientService;
    private final NutrientsDaoRepository nutrientsDaoRepository;
    private final IngredientDtoRepository ingredientDtoRepository;

    public RecipesService(ContentRepository content, EntryRepository entry, RatingRepository rating, IngredientService ingredientService, NutrientsDaoRepository nutrientsDaoRepository, IngredientDtoRepository ingredientDtoRepository) {
        super(content, entry, rating);
        this.ingredientService = ingredientService;
        this.nutrientsDaoRepository = nutrientsDaoRepository;
        this.ingredientDtoRepository = ingredientDtoRepository;
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
    public Nutrients sumIngredientsNutrition(long recipeId){
        Optional<NutrientsDao> byRecipeId = nutrientsDaoRepository.findByRecipe_Id(recipeId);
        if (byRecipeId.isPresent()){
            return byRecipeId.get().getNutrients().toNutrients();
        }
        Nutrients nutrientsSum = Nutrients.empty();
        List<DetailedIngredientDto> ingredients = getRecipe(recipeId).getIngredients();

        ingredients.forEach(dto -> {
            log.info("now doing this {}", dto);
            Nutrients nutrients = nutrientsDaoRepository
                    .findByIngredient_DetailedId(dto.getDetailedId())
                        .map(nutrientsDao -> nutrientsDao.getNutrients().toNutrients())
                        .orElseGet(() -> cacheIngredientNutrients(dto));
            nutrientsSum.addNutrients(nutrients);
        });

        cacheRecipeNutrients(recipeId, nutrientsSum);

        return nutrientsSum;
    }

    private void cacheRecipeNutrients(long recipeId, Nutrients nutrientsSum) {
        NutrientsDao nutrientsDao = new NutrientsDao();
        Optional<Entry> byId = entry.findById(recipeId);
        if (byId.isPresent() && nutrientsDaoRepository.findByRecipe_Id(recipeId).isEmpty()){
            nutrientsDao.setNutrients(NutrientsDto.ofNutrients(nutrientsSum));
            nutrientsDao.setRecipe((Recipe)byId.get());
            nutrientsDaoRepository.save(nutrientsDao);
        }
    }

    private Nutrients cacheIngredientNutrients(DetailedIngredientDto dto) {
        DetailedIngredient ingredientDetails_api = ingredientService.getIngredientDetails_API(dto);
        Nutrients ingredientNutrition = ingredientDetails_api.getNutrition();

        IngredientDto ingredient = dto.simplifyToIngredientDto();
        nutrientsDaoRepository.save(NutrientsDao.create(ingredientDtoRepository.save(ingredient), NutrientsDto.ofNutrients(ingredientNutrition)));
        return ingredientNutrition;
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
//
//        Nutrients reduce = recipe
//                .stream()
//                .flatMap(rec -> rec.getIngredients().stream())
//                .map( rec -> ingredientService.getIngredientDetails_API(rec).getNutrition())
//                .reduce(
//                        empty,
//                        (nutr1, nutr2) -> {
//                            nutr1.addNutrients(nutr2);
//                            return nutr1;
//                        }
//                );
        return reduce;

    }

    @Override
    public boolean deleteRecipe(Long id) {
        entry.deleteById(id);

        return true;
    }
}
