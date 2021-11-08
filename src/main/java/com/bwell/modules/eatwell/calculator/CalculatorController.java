package com.bwell.modules.eatwell.calculator;

import com.bwell.modules.eatwell.calculator.model.CalculatorData;
import com.bwell.modules.eatwell.calculator.model.dtos.IngredientCoverageDto;
import com.bwell.modules.eatwell.calculator.model.dtos.NutrientsDemandDao;
import com.bwell.modules.eatwell.calculator.service.CalculatorService;
import com.bwell.modules.eatwell.calculator.service.ICalculatorService;
import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientDto;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrients;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.NutrientsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/eatwell/calculator")
public class CalculatorController {

    private final ICalculatorService service;
    Logger logger = LoggerFactory.getLogger(CalculatorController.class);

    @Autowired
    public CalculatorController(CalculatorService service) {
        this.service = service;
    }

    @GetMapping("/{userId}/ingredient")
    public IngredientCoverageDto getCoverageFor(
            @RequestBody IngredientDto ingredientDto,
            @PathVariable(value = "userId") long userId) {

        return service.getCoverageFor(userId, ingredientDto);

    }

    @GetMapping("/{userId}/recipe/{recipeId}")
    public IngredientCoverageDto getCoverageFor(
            @PathVariable(value = "userId") long userId, @PathVariable long recipeId) {

        return service.getCoverageFor(userId, recipeId);

    }
    @GetMapping("/{userId}/dietplan/coverage")
    public IngredientCoverageDto getCoverageForNutrients(@PathVariable(value = "userId") long userId) {
        return service.getCoverageOfDietPlan(userId);
    }

    @GetMapping("/{userId}/dietplan/sum")
    public NutrientsDto getSumOfNutrients(@PathVariable(value = "userId") long userId) {
        return service.getNutrientsSumOfDietPlan(userId);
    }




    @GetMapping("/{id}")
    public NutrientsDemandDao getDemandForUser(@PathVariable(value = "id") long id) {
//            service.setFakeData();
            return service.getDemandForUser(id);
    }

    @GetMapping
    public CalculatorData getSchema() {
        return new CalculatorData();
    }

    @PostMapping
    @ResponseBody
    public NutrientsDemandDao setDemandForUser(@RequestBody CalculatorData calculatorData) {

        return service.calculateUserDemand(calculatorData);
    }

}
