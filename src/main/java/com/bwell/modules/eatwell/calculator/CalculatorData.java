package com.bwell.modules.eatwell.calculator;

import lombok.Data;

@Data
public class CalculatorData {
    private int bodyMassInKg;
    private int heightInCm;
    private int age;
    private boolean isMan;
    private Double activityRatio;
    private String strategy;
    private DietGoal goal;
    public void setGoal(String goal) {
        this.goal = DietGoal.valueOf(goal);
    }
}
