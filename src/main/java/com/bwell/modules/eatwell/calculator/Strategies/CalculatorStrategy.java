package com.bwell.modules.eatwell.calculator.Strategies;

import com.bwell.modules.eatwell.calculator.CalculatorData;

import java.math.BigDecimal;

public interface CalculatorStrategy {
    void extractRequiredData(CalculatorData data);
    BigDecimal calculate();
}
