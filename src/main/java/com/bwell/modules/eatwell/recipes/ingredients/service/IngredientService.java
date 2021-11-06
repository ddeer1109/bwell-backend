package com.bwell.modules.eatwell.recipes.ingredients.service;

import com.bwell.modules.eatwell.recipes.ingredients.SpoonacularApi.RequestingService;
import com.bwell.modules.eatwell.recipes.ingredients.model.*;
import com.bwell.modules.eatwell.recipes.ingredients.repositories.CommonIngredientsRepository;
import com.bwell.modules.eatwell.recipes.ingredients.repositories.DetailedIngredientsRepository;
import com.bwell.modules.eatwell.recipes.ingredients.repositories.IngredientsRepository;
import com.bwell.modules.eatwell.recipes.ingredients.repositories.UnitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService implements IIngredientService {
    private Logger logger = LoggerFactory.getLogger(IngredientService.class);
    private RequestingService spoonacularApi;
    private CommonIngredientsRepository commonRepository;
    private DetailedIngredientsRepository detailedIngredientsRepository;
    private IngredientsRepository ingredientsRepository;
    private UnitRepository unitRepository;

    @Autowired
    public IngredientService(RequestingService spoonacularApi,
                             CommonIngredientsRepository commonRepository,
                             DetailedIngredientsRepository detailedIngredientsRepository,
                             IngredientsRepository ingredientsRepository,
                             UnitRepository unitRepository) {
        this.spoonacularApi = spoonacularApi;
        this.commonRepository = commonRepository;
        this.detailedIngredientsRepository = detailedIngredientsRepository;
        this.ingredientsRepository = ingredientsRepository;
        this.unitRepository = unitRepository;
    }

    public List<DetailedIngredient> getDetailedIngredients() {
        return detailedIngredientsRepository.findAll();
    }

    public List<Ingredient> getIngredients() {
        return ingredientsRepository.findAll();
    }

    public List<Unit> getUnits() {
        return unitRepository.findAll();
    }

    public List<Ingredient> queryIngredients_API(String query){
        List<Ingredient> ingredientsFromApi = spoonacularApi.queryIngredient(query);
        return commonRepository.persistentSave(ingredientsFromApi);
    }

    public DetailedIngredient getIngredientDetails_API(int id, int amount, String unit){
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(id);
        ingredientDto.setAmount(amount);
        ingredientDto.setUnit(unit);

        return getIngredientDetails_API(ingredientDto);
    }
    public DetailedIngredient getIngredientDetails_API(IngredientDto dto) {
        Optional<Long> detailedId = Optional.ofNullable(dto.getDetailedId());
        return detailedIngredientsRepository
                .findById(detailedId.orElse((long) 0))
                .orElseGet(() -> {
                    DetailedIngredient apiDetailedIngredient = spoonacularApi.getIngredient(dto.getId(), dto.getAmount(), dto.getUnit());
                    if (dto.getDetailedId() != null && dto.getDetailedId() != 0)
                        apiDetailedIngredient.setId(dto.getDetailedId());
                    try {
                        return commonRepository.persistentSave(apiDetailedIngredient);
                    } catch (Exception e) {
                        logger.info("Something went wrong with saveing {} {}, ", apiDetailedIngredient, e.getMessage());
                        e.printStackTrace();
                        return apiDetailedIngredient;
                    }
        });

    }
    public DetailedIngredient getIngredientDetails_API(DetailedIngredientDto dto) {
            return detailedIngredientsRepository
                    .findById(dto.getDetailedId())
                    .orElseGet(() -> {
                        DetailedIngredient ingredient = spoonacularApi.getIngredient(
                                (int) dto.getId(), dto.getAmount(), dto.getUnit().getName()
                        );
                        ingredient.setId(dto.getDetailedId());
                        try {
                            return commonRepository.persistentSave(ingredient);
                        } catch (Exception e) {
                            logger.info("Something went wrong with saveing {} {}, ", ingredient, e.getMessage());
                            e.printStackTrace();
                            return ingredient;
                        }
                    });
    }
}

