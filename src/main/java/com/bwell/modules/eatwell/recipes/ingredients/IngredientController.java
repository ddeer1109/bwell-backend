package com.bwell.modules.eatwell.recipes.ingredients;

import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.Ingredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.Unit;
import com.bwell.modules.eatwell.recipes.ingredients.service.IIngredientService;
import com.bwell.modules.eatwell.recipes.ingredients.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/eatwell/ingredient")
public class IngredientController {
    private final IIngredientService service;

    @Autowired
    public IngredientController(IngredientService requestService) {
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
        DetailedIngredient detailedIngredient = service.getIngredientDetails_API(id, amount, unit);
        return detailedIngredient;
    }
    @GetMapping("/q")
    public List<Ingredient> getHintsOnQueryBase(
            @RequestParam(required = false) String query
    ){
        return service.queryIngredients_API(query);
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
