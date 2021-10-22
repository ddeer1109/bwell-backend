package com.bwell.modules.eatwell.calculator;

import com.bwell.modules.eatwell.calculator.model.CalculatorData;
import com.bwell.modules.eatwell.calculator.model.DietGoal;
import com.bwell.modules.eatwell.calculator.model.NutrientsDemand;
import com.bwell.modules.eatwell.calculator.model.NutritionStatisticsCalculator;
import com.bwell.modules.eatwell.calculator.model.Strategies.StrategyCodes;
import com.bwell.modules.eatwell.calculator.model.dtos.IngredientCoverageDto;
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

        nutrientsDemand.setProportion(calculatorData);

        nutrientsDemand.applyGoalIfProvided(calculatorData);

        NutrientsDemandDao dao = nutrientsDemand.createDao();

        dao.setUserId(calculatorData.getId());

        return resultsRepository.save(dao);
    }



    public IngredientCoverageDto getCoverageFor(long userId, IngredientDto ingredientDto) {

        NutrientsDemandDao dao = getDemandForUser(userId);
        DetailedIngredient ingredientDetails_api = ingredientService.getIngredientDetails_API(ingredientDto);

        NutrientsDemand nutrientsDemand = NutrientsDemand.ofDao(dao);

        return nutrientsDemand.getIngredientCoverage(ingredientDetails_api);
    }

    public IngredientCoverageDto getCoverageFor(long userId, List<IngredientDto> ingredientsDto) {

        NutrientsDemandDao dao = getDemandForUser(userId);

        List<DetailedIngredient> detailedIngredients = ingredientsDto
                .stream()
                .map(ingredientService::getIngredientDetails_API)
                .collect(Collectors.toList());

        NutrientsDemand nutrientsDemand = NutrientsDemand.ofDao(dao);

        return nutrientsDemand.getIngredientsCoverage(detailedIngredients);
    }
}
