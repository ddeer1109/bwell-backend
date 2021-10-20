package com.bwell.modules.eatwell.calculator.Strategies;

@FunctionalInterface
public interface Command {
    CalculatorStrategy create();
}
