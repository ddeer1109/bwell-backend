package com.bwell.modules.eatwell.dietplan.controller;

import com.bwell.modules.eatwell.dietplan.model.DietPlan;
import com.bwell.modules.eatwell.dietplan.service.IDietPlanService;
import com.bwell.modules.eatwell.recipes.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin("https://bwell-frontend.herokuapp.com")
@RequestMapping("/api/v1/eatwell/dietplan")
public class DietPlanController {

    private final IDietPlanService service;

    @Autowired
    public DietPlanController(IDietPlanService service) {
        this.service = service;
    }

    @GetMapping("/{user_id}")
    public DietPlan getDietPlan(@PathVariable("user_id") Long userId){
        return service.getDietPlan(userId);
    }

    @GetMapping("/{user_id}/breakfast/{recipe_id}")
    public Recipe setBreakfast(@PathVariable("user_id") Long userId, @PathVariable("recipe_id") Long recipeId){
        log.info("recipe: {} {}", userId, recipeId);

        return service.setBreakfast(recipeId, userId);
    }

    @GetMapping("/{user_id}/lunch/{recipe_id}")
    public Recipe setLunch(@PathVariable("user_id") Long userId, @PathVariable("recipe_id") Long recipeId){
        log.info("recipe: {} {}", userId, recipeId);

        return service.setLunch(recipeId, userId);
    }

    @GetMapping("/{user_id}/dinner/{recipe_id}")
    public Recipe setDinner(@PathVariable("user_id") Long userId, @PathVariable("recipe_id") Long recipeId){
        log.info("recipe: {} {}", userId, recipeId);

        return service.setDinner(recipeId, userId);
    }

    @GetMapping("/{user_id}/supper/{recipe_id}")
    public Recipe setSupper(@PathVariable("user_id") Long userId, @PathVariable("recipe_id") Long recipeId){
        log.info("recipe: {} {}", userId, recipeId);

        return service.setSupper(recipeId, userId);
    }



}
