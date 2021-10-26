package com.bwell.modules.eatwell.recipes.ingredients.service;

import com.bwell.modules.eatwell.recipes.ingredients.SpoonacularApi.RequestingService;
import com.bwell.modules.eatwell.recipes.ingredients.model.Ingredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientDto;
import com.bwell.modules.eatwell.recipes.ingredients.model.Unit;
import com.bwell.modules.eatwell.recipes.ingredients.repositories.CommonIngredientsRepository;
import com.bwell.modules.eatwell.recipes.ingredients.repositories.DetailedIngredientsRepository;
import com.bwell.modules.eatwell.recipes.ingredients.repositories.IngredientsRepository;
import com.bwell.modules.eatwell.recipes.ingredients.repositories.UnitRepository;
import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        DetailedIngredient apiDetailedIngredient = spoonacularApi.getIngredient(id, amount, unit);
        DetailedIngredient detailedIngredient = commonRepository.persistentSave(apiDetailedIngredient);
        return detailedIngredient;
    }
    public DetailedIngredient getIngredientDetails_API(IngredientDto dto){
        DetailedIngredient apiDetailedIngredient = spoonacularApi.getIngredient(dto.getId(), dto.getAmount(), dto.getUnit());
        DetailedIngredient detailedIngredient = commonRepository.persistentSave(apiDetailedIngredient);
        return detailedIngredient;
    }




}
