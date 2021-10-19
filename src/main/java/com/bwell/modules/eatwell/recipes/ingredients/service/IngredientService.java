package com.bwell.modules.eatwell.recipes.ingredients.service;

import com.bwell.modules.eatwell.recipes.ingredients.SpoonacularApi.RequestingService;
import com.bwell.modules.eatwell.recipes.ingredients.model.Ingredient;
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
public class IngredientService {
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

    public List<Ingredient> API_requestIngredientsQuery(String query){
        List<Ingredient> ingredientsFromApi = spoonacularApi.queryIngredient(query);
        return commonRepository.persistentSave(ingredientsFromApi);
    }

    public DetailedIngredient API_requestIngredientOfId(int id, int amount, String unit){
        DetailedIngredient apiDetailedIngredient = spoonacularApi.getIngredient(id, amount, unit);
        try {
            DetailedIngredient detailedIngredient = commonRepository.persistentSave(apiDetailedIngredient);
            return detailedIngredient;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }




}
