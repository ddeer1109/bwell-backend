package com.bwell.modules.eatwell.calculator.model;

import com.bwell.modules.eatwell.calculator.model.dtos.NutrientsDemandDao;
import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrient;
import com.bwell.modules.eatwell.recipes.ingredients.nutrition.NutritionElement;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

public class NutrientsDemand {
    private BigDecimal caloriesDemand;
    private Map<Nutrient, BigDecimal> elementsPercentage = new HashMap<>();

    public NutrientsDemand(BigDecimal caloriesDemand) {
        this.caloriesDemand = caloriesDemand;
        defaultProportion();
    }

    public void defaultProportion() {
        setNutrientPercentage(Nutrient.Carbohydrates, 35);
        setNutrientPercentage(Nutrient.Fat, 30);
        setNutrientPercentage(Nutrient.Protein, 35);
        setNutrientPercentage(Nutrient.Calories, 100);
    }

    public void setProportion(int proteinPercentage, int fatsPercentage, int carbsPercentage) {
        setNutrientPercentage(Nutrient.Carbohydrates, carbsPercentage);
        setNutrientPercentage(Nutrient.Fat, fatsPercentage);
        setNutrientPercentage(Nutrient.Protein, proteinPercentage);
    }

    private void setNutrientPercentage(Nutrient nutrient, int percentage) {
        BigDecimal asBigDecimal = BigDecimal.valueOf(percentage);
        BigDecimal proportion = asBigDecimal.divide(BigDecimal.valueOf(100), new MathContext(4));
        elementsPercentage.put(nutrient, proportion);
    }

    public BigDecimal getNutrientPercentage(Nutrient nutrient) {
            return elementsPercentage.get(nutrient).divide(BigDecimal.valueOf(100), new MathContext(16));
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

    public void getIngredientCoverage(DetailedIngredient ingredient){
        ingredient.getNutrition().getNutrients().forEach(nutrient -> {
            BigDecimal multiply = nutrient.getAmount().multiply(nutrient.getType().kcalMultiplier);
            System.out.println(nutrient.getTitle() + "  " +  multiply.divide(ingredient.getNutrition().get(Nutrient.Calories).getAmount(), new MathContext(8)));
//            System.out.println(nutrient.kcalToGrams()).multiply(nutrient.getType().kcalMultiplier));
        });
    }

    public NutrientsDemandDao createDao() {
        NutrientsDemandDao nutrientsDemandDao = new NutrientsDemandDao();
        nutrientsDemandDao.setCaloriesDemand(caloriesDemand);
        nutrientsDemandDao.setProteinDemand(getElementDemand(Nutrient.Protein).getAmount());
        nutrientsDemandDao.setCarbohydratesDemand(getElementDemand(Nutrient.Carbohydrates).getAmount());
        nutrientsDemandDao.setFatDemand(getElementDemand(Nutrient.Fat).getAmount());

        return nutrientsDemandDao;
    }


}
