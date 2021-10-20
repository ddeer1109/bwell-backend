package com.bwell.modules.eatwell.calculator.model;

import java.math.BigDecimal;

public enum DietGoal {
    KeepCurrent(0),
    GainMass(300),
    IntenseMass(500),
    Reduction(-300),
    IntenseReduction(-500);

    public final BigDecimal caloriesModifier;

    DietGoal(int caloriesModifier) {
        this.caloriesModifier = BigDecimal.valueOf(caloriesModifier);
    }
}
