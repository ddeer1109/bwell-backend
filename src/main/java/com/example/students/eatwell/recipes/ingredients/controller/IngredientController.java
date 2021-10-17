package com.example.students.eatwell.recipes.ingredients.controller;

import com.example.students.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.example.students.eatwell.recipes.ingredients.model.Ingredient;
import com.example.students.eatwell.recipes.ingredients.model.Unit;
import com.example.students.eatwell.recipes.ingredients.repositories.IngredientRepositoryImpl;
import com.example.students.eatwell.recipes.ingredients.service.IngredientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/eatwell/ingredient")
public class IngredientController {
    private IngredientService service;

    @Autowired
    public IngredientController(IngredientService requestService, IngredientRepositoryImpl repository) {
        this.service = requestService;
    }

    @GetMapping("/")
    public List<Ingredient> getIngredients(){
        return service.getIngredients();
    }
    @GetMapping("/{id}")
    public DetailedIngredient getIngredient(
            @PathVariable(value = "id") int id,
            @RequestParam(value = "unit", defaultValue = "g") String unit,
            @RequestParam(value = "amount", defaultValue = "100") int amount
    ){
        DetailedIngredient detailedIngredient = service.API_requestIngredientOfId(id, amount, unit);
        System.out.println(detailedIngredient);
        return detailedIngredient;
    }
    @GetMapping("/q")
    public List<Ingredient> getHintsOnQueryBase(
            @RequestParam(required = false) String query
    ){
        return service.API_requestIngredientsQuery(query);
    }
    @GetMapping("/detailed")
    public List<DetailedIngredient> getDetailedIngredients(){
        return service.getDetailedIngredients();
    }

    @GetMapping("/units")
    public List<Unit> getUnits(){
        return service.getUnits();
    }

}
