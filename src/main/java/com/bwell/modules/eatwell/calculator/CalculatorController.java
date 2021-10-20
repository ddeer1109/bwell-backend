package com.bwell.modules.eatwell.calculator;

import com.bwell.modules.eatwell.calculator.model.dtos.NutrientsDemandDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            service.setFakeData();

            return service.getDemandForUser(id);
    }

}
