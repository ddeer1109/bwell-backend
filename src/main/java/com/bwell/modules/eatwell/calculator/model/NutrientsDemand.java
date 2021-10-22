package com.bwell.modules.eatwell.calculator.model;

import com.bwell.modules.eatwell.calculator.model.dtos.IngredientCoverageDto;
import com.bwell.modules.eatwell.calculator.model.dtos.NutrientsDemandDao;
import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrient;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.NutritionElement;
import org.hibernate.mapping.KeyValue;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class NutrientsDemand {
    private BigDecimal caloriesDemand;
    private Map<Nutrient, BigDecimal> elementsPercentage = new HashMap<>();

    public NutrientsDemand(BigDecimal caloriesDemand) {
        this.caloriesDemand = caloriesDemand;
        defaultProportion();
    }

    public void defaultProportion() {
        setNutrientPercentage(Nutrient.Carbohydrates, 0.45);
        setNutrientPercentage(Nutrient.Fat, 0.25);
        setNutrientPercentage(Nutrient.Protein, 0.30);
        setNutrientPercentage(Nutrient.Calories, 1);
    }

    public void  setProportion(CalculatorData calculatorData) {
        System.out.println(calculatorData);
        boolean proportionsAreProvided =
                !Stream.of(calculatorData.getCarbohydratesPercentage(), calculatorData.getFatPercentage(), calculatorData.getProteinPercentage()).anyMatch(n -> n == null);

        if (proportionsAreProvided){
            setProportion(
                    calculatorData.getProteinPercentage(),
                    calculatorData.getFatPercentage(),
                    calculatorData.getCarbohydratesPercentage()
            );
        }
    }

    public void setProportion(double proteinPercentage, double fatsPercentage, double carbsPercentage) {
        setNutrientPercentage(Nutrient.Carbohydrates, carbsPercentage);
        setNutrientPercentage(Nutrient.Fat, fatsPercentage);
        setNutrientPercentage(Nutrient.Protein, proteinPercentage);
        double percentage = carbsPercentage + proteinPercentage + fatsPercentage;
        setNutrientPercentage(Nutrient.Calories, percentage);
        caloriesDemand = caloriesDemand.multiply(BigDecimal.valueOf(percentage));
    }

    public void applyGoalModifiers(DietGoal goal){
        goal.getModifiers().forEach((nutrient, modifier) -> {
            elementsPercentage.compute(nutrient, (nutr1, currModifier) -> currModifier.add(modifier.divide(caloriesDemand, new MathContext(4))));
        });
        caloriesDemand = caloriesDemand.multiply(getNutrientPercentage(Nutrient.Calories));
    }

    private void setNutrientPercentage(Nutrient nutrient, double percentage) {
        BigDecimal asBigDecimal = BigDecimal.valueOf(percentage);
//        BigDecimal proportion = asBigDecimal.divide(BigDecimal.valueOf(100), new MathContext(4));
        elementsPercentage.put(nutrient, asBigDecimal);
    }

    public BigDecimal getNutrientPercentage(Nutrient nutrient) {
            return elementsPercentage.get(nutrient);
    }

    public void applyGoalIfProvided(CalculatorData calculatorData) {
        DietGoal goal = calculatorData.getGoal();
        if (goal != null) {
            applyGoalModifiers(goal);
        }
    }

    public NutritionElement getCaloriesDemand() {
        return Nutrient.Calories.create(caloriesDemand.intValue());
    }

    public NutritionElement getElementDemand(Nutrient nutrient) {
        BigDecimal percentage = elementsPercentage.get(nutrient);
        BigDecimal inCalories = percentage.multiply(caloriesDemand).round(new MathContext(6));
        BigDecimal inGrams = inCalories.divide(nutrient.kcalMultiplier, new MathContext(4));

        return nutrient.create(inGrams.doubleValue());
    }

    public IngredientCoverageDto getIngredientCoverage(DetailedIngredient ingredient){
        Map<String, BigDecimal> map = new HashMap<>();
        ingredient
                .getNutrition()
                .getNutrients()
                .forEach(
                        nutrient -> map.put(nutrient.getTitle(), getNutrientCoverage(nutrient))
                );

        System.out.println("Map: " + map);

        IngredientCoverageDto dto = new IngredientCoverageDto(map);

        System.out.println("Dto: " + dto);

        return dto;
    }

    private BigDecimal getNutrientCoverage(NutritionElement nutrient){
        BigDecimal nutrientAmount = nutrient.getAmount();
        return nutrientAmount.divide(getElementDemand(nutrient.getType()).getAmount(), new MathContext(4));
    }

    public IngredientCoverageDto getIngredientsCoverage(List<DetailedIngredient> ingredients){
        IngredientCoverageDto dto = new IngredientCoverageDto();

        ingredients.forEach(ingredient -> {
            IngredientCoverageDto ingredientCoverage = getIngredientCoverage(ingredient);

            dto.addCoverage(ingredientCoverage);

        });
        return dto;
    }

    public NutrientsDemandDao createDao() {
        NutrientsDemandDao nutrientsDemandDao = new NutrientsDemandDao();
        nutrientsDemandDao.setCaloriesDemand(caloriesDemand);
        nutrientsDemandDao.setProteinDemand(getElementDemand(Nutrient.Protein).getAmount());
        nutrientsDemandDao.setCarbohydratesDemand(getElementDemand(Nutrient.Carbohydrates).getAmount());
        nutrientsDemandDao.setFatDemand(getElementDemand(Nutrient.Fat).getAmount());
        nutrientsDemandDao.setFatPercentage(getNutrientPercentage(Nutrient.Fat));
        nutrientsDemandDao.setCarbohydratesPercentage(getNutrientPercentage(Nutrient.Carbohydrates));
        nutrientsDemandDao.setProteinPercentage(getNutrientPercentage(Nutrient.Protein));
        nutrientsDemandDao.setCaloriesPercentage(getNutrientPercentage(Nutrient.Calories));

        return nutrientsDemandDao;
    }

    public static NutrientsDemand ofDao(NutrientsDemandDao dao){
        NutrientsDemand nutrientsDemand = new NutrientsDemand(dao.getCaloriesDemand());
        nutrientsDemand.setProportion(
                dao.getProteinPercentage().doubleValue(),
                dao.getFatPercentage().doubleValue(),
                dao.getCarbohydratesPercentage().doubleValue());
        return nutrientsDemand;

    }


}
