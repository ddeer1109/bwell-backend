package com.bwell.modules.eatwell.calculator.model.Strategies;

import com.bwell.modules.eatwell.calculator.model.CalculatorData;

import java.math.BigDecimal;

public interface CalculatorStrategy {
    void extractRequiredData(CalculatorData data);
    BigDecimal calculate();
}
