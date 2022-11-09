package com.bwell.modules.eatwell.recipes;


import com.bwell.base.entry.Entry;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrients;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.NutrientsDto;
import com.bwell.modules.eatwell.recipes.model.Recipe;
import com.bwell.modules.eatwell.recipes.service.IRecipeServiceWithNutritionExtension;
import com.bwell.modules.eatwell.recipes.service.IRecipesService;
import com.bwell.modules.eatwell.recipes.service.RecipeIngredientsDto;
import com.bwell.security.CurrentUser;
import com.bwell.security.UserPrincipal;
import com.bwell.user.data.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("${FRONTEND_HOST}")
@RequestMapping("/api/v1/eatwell/recipes")
public class RecipesController {

    private final IRecipeServiceWithNutritionExtension service;

    @Autowired
    public RecipesController(IRecipeServiceWithNutritionExtension service) {
        this.service = service;
    }


//    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
    public List<Entry> getAllRecipes(@CurrentUser UserPrincipal user){
        return service.getAllRecipes();
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable("id") Long id){
        return service.getRecipe(id);
    }

    @GetMapping("/{id}/nutrition")
    public NutrientsDto getRecipeIngredientsSum(@PathVariable("id") Long id){
        return NutrientsDto.ofNutrients(service.sumIngredientsNutrition(id));
    }

    @GetMapping("/{id}/ingredients/nutrition")
    public RecipeIngredientsDto getNutrientsOfRecipeIngredients(@PathVariable("id") Long recipeId) {
        return service.getNutrientsOfRecipeIngredients(service.getRecipe(recipeId));
    }
    //
//    @PostMapping("/recipes/nutrition")
//    public NutrientsDto getRecipesIngredientsSum(@RequestBody List<Recipe> recipes){
//        return NutrientsDto.ofNutrients(service.sumRecipesIngredientsNutrition(recipes));
//    }

    @PostMapping("/")
    public Recipe addRecipe(@RequestBody Recipe recipe){
        log.info("recipe: {}", recipe);

        return service.addRecipe(recipe);
    }



    @DeleteMapping("/{id}")
    public boolean deleteRecipe(@PathVariable("id") Long entryId, @CurrentUser UserPrincipal userPrincipal) {
        return service.isAuthor(userPrincipal, entryId) && service.deleteRecipe(entryId);
    }

}
