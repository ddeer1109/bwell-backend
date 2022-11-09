package com.bwell.modules.eatwell.calculator.service;

import com.bwell.modules.eatwell.calculator.model.CalculatorData;
import com.bwell.modules.eatwell.calculator.model.NutrientsDemand;
import com.bwell.modules.eatwell.calculator.model.NutritionStatisticsCalculator;
import com.bwell.modules.eatwell.calculator.model.dtos.IngredientCoverageDto;
import com.bwell.modules.eatwell.calculator.model.dtos.NutrientsDemandDao;
import com.bwell.modules.eatwell.calculator.repository.CalculationDataRepository;
import com.bwell.modules.eatwell.calculator.repository.CalculatorResultsRepository;
import com.bwell.modules.eatwell.dietplan.service.IDietPlanService;
import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientDto;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrients;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.NutrientsDto;
import com.bwell.modules.eatwell.recipes.ingredients.service.IngredientService;
import com.bwell.modules.eatwell.recipes.service.RecipesService;
import com.bwell.user.data.model.User;
import com.bwell.user.data.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CalculatorService implements ICalculatorService {

    private final CalculatorResultsRepository resultsRepository;
    private final IngredientService ingredientService;
    private final UserRepository usersRepository;
    private final CalculationDataRepository calculationDataRepository;
    private final IDietPlanService dietPlanService;
    private final Logger logger = LoggerFactory.getLogger(CalculatorService.class);

    private final RecipesService recipeService;

    public CalculatorService(CalculatorResultsRepository resultsRepository, IngredientService ingredientService, UserRepository usersRepository, CalculationDataRepository calculationDataRepository, IDietPlanService dietPlanService, RecipesService recipeService) {
        this.resultsRepository = resultsRepository;
        this.ingredientService = ingredientService;
        this.usersRepository = usersRepository;
        this.calculationDataRepository = calculationDataRepository;
        this.dietPlanService = dietPlanService;
        this.recipeService = recipeService;
    }


    public NutrientsDemandDao getDemandForUser(long id) {
        NutrientsDemandDao byId = resultsRepository
                .findById(id).orElseGet(() -> resultsRepository
                        .findAll()
                        .stream()
                        .findAny()
                        .orElse(NutrientsDemandDao.createDefault()));

        return byId;
    }


    public NutrientsDemandDao calculateUserDemand(CalculatorData calculatorData) {
        NutritionStatisticsCalculator calculator = new NutritionStatisticsCalculator();
        calculator.setData(calculatorData);

        NutrientsDemand nutrientsDemand = calculator.calculateCaloriesDemand();

        nutrientsDemand.setProportion(calculatorData);
        nutrientsDemand.applyGoalIfProvided(calculatorData);

        NutrientsDemandDao dao = nutrientsDemand.createDao();

        return updateUsersData(calculatorData, dao);
    }

    private NutrientsDemandDao updateUsersData(CalculatorData calculatorData, NutrientsDemandDao dao) {
        User user = usersRepository.getById(calculatorData.getUser().getId());
        dao.setUser(user);
        NutrientsDemandDao resultsSaved = resultsRepository.save(dao);
        calculatorData.setCarbohydratesPercentage(dao.getCarbohydratesPercentage().doubleValue());
        calculatorData.setProteinPercentage(dao.getProteinPercentage().doubleValue());
        calculatorData.setFatPercentage(dao.getFatPercentage().doubleValue());
        calculatorData.setUser(resultsSaved.getUser());
        CalculatorData calcDataSaved = calculationDataRepository.save(calculatorData);

        user.setCalculatorData(calcDataSaved);
        user.setNutrientsDemand(resultsSaved);

        usersRepository.save(user);

        return resultsSaved;
    }

    public IngredientCoverageDto getCoverageFor(long userId, IngredientDto ingredientDto) {
        log.info("Coverage for Ingredient - before: {}", RecipesService.getRequestsCounter());
        NutrientsDemandDao dao = getDemandForUser(userId);
        DetailedIngredient ingredientDetails_api = ingredientService.getIngredientDetails_API(ingredientDto);

        NutrientsDemand nutrientsDemand = NutrientsDemand.ofDao(dao);

        log.info("Coverage for Ingredient - after: {}", RecipesService.getRequestsCounter());


        return nutrientsDemand.getIngredientCoverage(ingredientDetails_api);
    }

    @Override
    public IngredientCoverageDto getCoverageFor(long userId, long recipeId) {
        log.info("Coverage for Recipe - before: {}", RecipesService.getRequestsCounter());

        NutrientsDemandDao dao = getDemandForUser(userId);

        NutrientsDemand nutrientsDemand = NutrientsDemand.ofDao(dao);
        Nutrients nutrients = recipeService.sumIngredientsNutrition(recipeId);
        IngredientCoverageDto ingredientsCoverage = nutrientsDemand.getIngredientsCoverage(NutrientsDto.ofNutrients(nutrients));
        log.info("Coverage for Recipe - after: {}", RecipesService.getRequestsCounter());
        return ingredientsCoverage;
    }

    @Override
    public IngredientCoverageDto getCoverageOfDietPlan(long userId){
        log.info("Coverage for DietPlan - before: {}", RecipesService.getRequestsCounter());

        NutrientsDemandDao dao = getDemandForUser(userId);

        NutrientsDemand nutrientsDemand = NutrientsDemand.ofDao(dao);

        Nutrients nutrients = recipeService.sumRecipesIngredientsNutrition(dietPlanService.getDietPlan(userId).getSetMeals());
        log.info("Coverage for DietPlan - after: {}", RecipesService.getRequestsCounter());

        return nutrientsDemand.getIngredientsCoverage(NutrientsDto.ofNutrients(nutrients));
    }

    @Override
    public NutrientsDto getNutrientsSumOfDietPlan(long userId) {
        log.info("Coverage for SumDietplan - before: {}", RecipesService.getRequestsCounter());

        Nutrients nutrients = recipeService.sumRecipesIngredientsNutrition(dietPlanService.getDietPlan(userId).getSetMeals());
        log.info("Coverage for SumDietplan - after: {}", RecipesService.getRequestsCounter());

        return NutrientsDto.ofNutrients(nutrients);
    }
}
