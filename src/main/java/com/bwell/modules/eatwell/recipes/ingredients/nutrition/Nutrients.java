package com.bwell.modules.eatwell.recipes.ingredients.nutrition;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Nutrients {
    @JsonIgnore
    private List<String> PROCESSED_NUTRIENTS = Arrays.asList("Calories", "Protein", "Fat", "Carbohydrates");

    private Set<NutritionElement> nutrients;

    public Set<NutritionElement> getNutrients() {
        return nutrients;
    }

    public NutritionElement get(String nutritionElementTitle) {
        return nutrients.stream().filter(nutr -> nutr.getTitle().equals(nutritionElementTitle)).findFirst().orElseThrow();
    }
    public NutritionElement get(Nutrient nutrient) {
        return nutrients.stream().filter(nutr -> nutr.getTitle().equals(nutrient.name)).findFirst().orElseThrow();
    }

    public void updateNutrientsAmounts(BigDecimal multiplier) {
        nutrients.forEach((nutr) -> {
            nutr.setAmount(nutr.getAmount().multiply(multiplier));
        });
    }


    public void setNutrients(Set<NutritionElement> nutrients) {
        this.nutrients = nutrients
                .stream()
                .filter(nutrient -> PROCESSED_NUTRIENTS.contains(nutrient.getTitle()))
                .collect(Collectors.toSet());
    }

    public BigDecimal getPercentageFat(){
        return get(Names.FAT.name)
                .gramsToKcal()
                .getAmount()
                .divide(get(Names.KCAL.name).getAmount(), new MathContext(2));
    }
    public BigDecimal getPercentageProtein(){
        return get(Names.PROTEIN.name)
                .gramsToKcal()
                .getAmount()
                .divide(get(Names.KCAL.name).getAmount(), new MathContext(2));
    }
    public BigDecimal getPercentageCarbohydrates(){
        return get(Names.CARBS.name)
                .gramsToKcal()
                .getAmount()
                .divide(get(Names.KCAL.name).getAmount(), new MathContext(2));
    }
}
