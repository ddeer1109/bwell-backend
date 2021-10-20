package com.bwell.modules.eatwell.calculator.model.Strategies;

@FunctionalInterface
public interface Command {
    CalculatorStrategy create();
}
