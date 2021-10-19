package com.bwell.modules.eatwell.calculator;

import com.bwell.modules.eatwell.calculator.Strategies.CalculatorFactory;
import com.bwell.modules.eatwell.calculator.Strategies.CalculatorStrategy;
import com.bwell.modules.eatwell.calculator.Strategies.StrategyCodes;

public class NutritionStatisticsCalculator {
    private CalculatorFactory factory = new CalculatorFactory();
    private CalculatorStrategy calculator;
    private CalculatorData data;

    public void setCalculator(StrategyCodes code) {
        calculator = factory.createCalculator(code);
    }

    public void setupCalculator(){
        calculator.extractRequiredData(data);
    }

    public NutrientsDemand calculateCaloriesDemand(){
        setupCalculator();
        return new NutrientsDemand(calculator.calculate());
    }

    public CalculatorData getData() {
        return data;
    }

    public void setData(CalculatorData data) {
        this.data = data;
    }


}
