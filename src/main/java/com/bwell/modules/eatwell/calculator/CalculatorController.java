package com.bwell.modules.eatwell.calculator;

import com.bwell.modules.eatwell.calculator.model.CalculatorData;
import com.bwell.modules.eatwell.calculator.model.dtos.NutrientsDemandDao;
import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientDto;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.NutritionElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/eatwell/calculator")
public class CalculatorController {

    private CalculatorService service;

    @Autowired
    public CalculatorController(CalculatorService service) {
        this.service = service;
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
