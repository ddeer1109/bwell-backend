package com.bwell.modules.eatwell.recipes.ingredients.nutrition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Nutrients {
    private List<String> PROCESSED_NUTRIENTS = Arrays.asList("Calories", "Protein", "Fat", "Carbohydrates");

    private List<NutritionElement> nutrients;

    public List<NutritionElement> getNutrients() {
        return nutrients;
    }

    NutritionElement get(String nutritionElementTitle) {
        return nutrients.stream().filter(nutr -> nutr.getTitle().equals(nutritionElementTitle)).findFirst().orElseThrow();
    }

    public void updateNutrientsAmounts(BigDecimal multiplier) {
        nutrients.forEach((nutr) -> {
            nutr.setAmount(nutr.getAmount().multiply(multiplier));
        });
    }


    public void setNutrients(List<NutritionElement> nutrients) {
        this.nutrients = nutrients
                .stream()
                .filter(nutrient -> PROCESSED_NUTRIENTS.contains(nutrient.getTitle()))
                .collect(Collectors.toList());
    }

    public BigDecimal getPercentageFat(){
        return get(NAMES.FAT.name).inCalories()
                .divide(get(NAMES.KCAL.name).getAmount(), new MathContext(2));
    }
    public BigDecimal getPercentageProtein(){
        return get(NAMES.PROTEIN.name).inCalories()
                .divide(get(NAMES.KCAL.name).getAmount(), new MathContext(2));
    }
    public BigDecimal getPercentageCarbohydrates(){
        return get(NAMES.CARBS.name).inCalories()
                .divide(get(NAMES.KCAL.name).getAmount(), new MathContext(2));
    }
}
