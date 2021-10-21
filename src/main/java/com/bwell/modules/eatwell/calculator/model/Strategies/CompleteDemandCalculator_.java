package com.bwell.modules.eatwell.calculator.model.Strategies;

import com.bwell.modules.eatwell.calculator.model.CalculatorData;

import java.math.BigDecimal;

public class CompleteDemandCalculator_ implements CalculatorStrategy{
    CalculatorStrategy basalMetabolismCalculator = new CalculatorFactory().createCalculator(StrategyCodes.HarrisBenedict);
    private BigDecimal basalMetabolism;
    private BigDecimal metabolismDemand = BigDecimal.valueOf(0.1);
    private BigDecimal physicalActivityMultiplier;
    private BigDecimal caloriesModifierForGoal;

    @Override
    public void extractRequiredData(CalculatorData data) {
        basalMetabolismCalculator.extractRequiredData(data);
        basalMetabolism = basalMetabolismCalculator.calculate();
        physicalActivityMultiplier = BigDecimal.valueOf(data.getActivityRatio());
//        caloriesModifierForGoal = data.getGoal().caloriesModifier;
    }

    @Override
    public BigDecimal calculate() {
        return basalMetabolism
                .multiply(physicalActivityMultiplier)
                .add(basalMetabolism.multiply(metabolismDemand));
//                .add(caloriesModifierForGoal);
    }
}
