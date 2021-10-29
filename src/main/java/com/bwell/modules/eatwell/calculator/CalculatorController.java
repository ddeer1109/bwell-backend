package com.bwell.modules.eatwell.calculator;

import com.bwell.modules.eatwell.calculator.model.CalculatorData;
import com.bwell.modules.eatwell.calculator.model.dtos.IngredientCoverageDto;
import com.bwell.modules.eatwell.calculator.model.dtos.NutrientsDemandDao;
import com.bwell.modules.eatwell.calculator.service.CalculatorService;
import com.bwell.modules.eatwell.calculator.service.ICalculatorService;
import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{userId}/recipe")
    public IngredientCoverageDto getCoverageFor(
            @RequestBody List<IngredientDto> ingredientsDto,
            @PathVariable(value = "userId") long userId) {

        return service.getCoverageFor(userId, ingredientsDto);

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
        System.out.println(calculatorData);

        logger.info("-------------------");
        logger.info("Hello data {} ", calculatorData);
        logger.info("-------------------");

        NutrientsDemandDao nutrientsDemandDao = service.calculateUserDemand(calculatorData);
        logger.info("Hello results {} ", nutrientsDemandDao);
        return nutrientsDemandDao;
    }

}
