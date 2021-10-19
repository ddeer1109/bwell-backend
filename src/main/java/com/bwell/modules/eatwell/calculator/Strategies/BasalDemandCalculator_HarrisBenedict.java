package com.bwell.modules.eatwell.calculator.Strategies;

import com.bwell.modules.eatwell.calculator.CalculatorData;

import java.math.BigDecimal;

public class BasalDemandCalculator_HarrisBenedict implements CalculatorStrategy{
    private BigDecimal massRatio = BigDecimal.valueOf(13.75);
    private BigDecimal heightRatio = BigDecimal.valueOf(5);
    private BigDecimal ageRatio = BigDecimal.valueOf(6.76);
    protected BigDecimal genderModifier = BigDecimal.valueOf(66.47);

    protected BigDecimal bodyMassInKg;
    protected BigDecimal heightInCm;
    protected BigDecimal age;

    @Override
    public void extractRequiredData(CalculatorData data) {
        bodyMassInKg = BigDecimal.valueOf(data.getBodyMassInKg());
        heightInCm = BigDecimal.valueOf(data.getHeightInCm());
        age = BigDecimal.valueOf(data.getAge());
        genderModifier = BigDecimal.valueOf(data.isMan() ? 66.47 : 655.1);

        setMultipliers(data.isMan());
    }

    private void setMultipliers(boolean man) {
        if (!man) {
            massRatio = BigDecimal.valueOf(9.567);
            heightRatio = BigDecimal.valueOf(1.85);
            ageRatio = BigDecimal.valueOf(4.68);
            genderModifier = BigDecimal.valueOf(655.1);
        }
    }

    @Override
    public BigDecimal calculate() {

        return genderModifier
                .add(massRatio.multiply(bodyMassInKg))
                .add(heightRatio.multiply(heightInCm))
                .subtract(ageRatio.multiply(age));
    }
}
