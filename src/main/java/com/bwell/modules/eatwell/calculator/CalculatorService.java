package com.bwell.modules.eatwell.calculator;

import com.bwell.modules.eatwell.calculator.model.CalculatorData;
import com.bwell.modules.eatwell.calculator.model.DietGoal;
import com.bwell.modules.eatwell.calculator.model.NutrientsDemand;
import com.bwell.modules.eatwell.calculator.model.NutritionStatisticsCalculator;
import com.bwell.modules.eatwell.calculator.model.Strategies.StrategyCodes;
import com.bwell.modules.eatwell.calculator.model.dtos.NutrientsDemandDao;
import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientDto;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrient;
import com.bwell.modules.eatwell.recipes.ingredients.service.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CalculatorService {

    private final CalculatorResultsRepository resultsRepository;
    private final IngredientService ingredientService;
    private final Logger logger = LoggerFactory.getLogger(CalculatorService.class);

    @Autowired
    public CalculatorService(CalculatorResultsRepository resultsRepository, IngredientService ingredientService) {
        this.resultsRepository = resultsRepository;
        this.ingredientService = ingredientService;
    }

    public NutrientsDemandDao getDemandForUser(long id) {
        NutrientsDemandDao byId = resultsRepository
                .findById(id).orElseThrow();

        return byId;
    }


    public NutrientsDemandDao calculateUserDemand(CalculatorData calculatorData) {
        NutritionStatisticsCalculator calculator = new NutritionStatisticsCalculator();

        calculator.setData(calculatorData);
        NutrientsDemand nutrientsDemand = calculator.calculateCaloriesDemand();
        System.out.println(calculatorData);
        boolean proportionsAreProvided =
                !Stream.of(calculatorData.getCarbohydratesPercentage(), calculatorData.getFatPercentage(), calculatorData.getProteinPercentage()).anyMatch(n -> n == null);

        if (proportionsAreProvided){
            nutrientsDemand.setProportion(
                    calculatorData.getProteinPercentage(),
                    calculatorData.getFatPercentage(),
                    calculatorData.getCarbohydratesPercentage()
                    );
        }
        DietGoal goal = calculatorData.getGoal();
//        goal.setModifiers(700, 0.6, 0, 0.4);
        if (goal != null) {
            nutrientsDemand.applyGoalModifiers(goal);
        }
        NutrientsDemandDao dao = nutrientsDemand.createDao();
        dao.setUserId(calculatorData.getId());

        return resultsRepository.save(dao);
    }

}
