package com.bwell.modules.eatwell.calculator.model.Strategies;

import com.bwell.modules.eatwell.calculator.model.CalculatorData;

import java.math.BigDecimal;

public class BasalDemandCalculator_Miffin implements CalculatorStrategy{
    private BigDecimal massRatio = BigDecimal.valueOf(10);
    private BigDecimal heightRatio = BigDecimal.valueOf(6.25);
    private BigDecimal ageRatio = BigDecimal.valueOf(5);
    protected BigDecimal genderModifier = BigDecimal.valueOf(5);

    protected BigDecimal bodyMassInKg;
    protected BigDecimal heightInCm;
    protected BigDecimal age;
    @Override
    public void extractRequiredData(CalculatorData data) {
        bodyMassInKg = BigDecimal.valueOf(data.getBodyMassInKg());
        heightInCm = BigDecimal.valueOf(data.getHeightInCm());
        age = BigDecimal.valueOf(data.getAge());
        genderModifier = BigDecimal.valueOf(data.isMan() ? 5 : -161);
    }

    @Override
    public BigDecimal calculate() {

        return
               massRatio.multiply(bodyMassInKg)
                .add(heightRatio.multiply(heightInCm))
                .subtract(ageRatio.multiply(age))
                .add(genderModifier);
    }
}
