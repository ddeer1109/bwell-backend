package com.example.students.eatwell.recipes.ingredients.service;

import com.example.students.eatwell.recipes.ingredients.SpoonacularApi.RequestingService;
import com.example.students.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.example.students.eatwell.recipes.ingredients.model.Ingredient;
import com.example.students.eatwell.recipes.ingredients.model.Unit;
import com.example.students.eatwell.recipes.ingredients.repositories.*;
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
        return commonRepository.persistentSave(apiDetailedIngredient);
    }




}
