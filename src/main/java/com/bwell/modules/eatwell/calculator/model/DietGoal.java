package com.bwell.modules.eatwell.calculator.model;

import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrient;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum DietGoal {
    KeepCurrent(0, 0, 0, 0),
    GainMass(300, 0.4, 0.3, 0.3),
    IntenseMass(500, 0.4, 0.3, 0.3),
    Reduction(-300, 0, 0.7, 0.3),
    IntenseReduction(-500, 0, 0.7, 0.3);



    private Map<Nutrient, BigDecimal> modifiers = new HashMap<>();
    public Map<Nutrient, BigDecimal> getModifiers() {
        return modifiers;
    }

    DietGoal(int caloriesModifier, double proteinModifier, double carbohydratesModifier, double fatModifier) {
        setModifiers(caloriesModifier, proteinModifier, carbohydratesModifier, fatModifier);
    }

    public void setModifiers(int caloriesModifier, double proteinModifier, double carbohydratesModifier, double fatModifier) {
        modifiers.put(Nutrient.Calories, BigDecimal.valueOf(caloriesModifier));
        modifiers.put(Nutrient.Protein, BigDecimal.valueOf(proteinModifier).multiply(modifiers.get(Nutrient.Calories)));
        modifiers.put(Nutrient.Fat, BigDecimal.valueOf(fatModifier).multiply(modifiers.get(Nutrient.Calories)));
        modifiers.put(Nutrient.Carbohydrates, BigDecimal.valueOf(carbohydratesModifier).multiply(modifiers.get(Nutrient.Calories)));
    }


}
