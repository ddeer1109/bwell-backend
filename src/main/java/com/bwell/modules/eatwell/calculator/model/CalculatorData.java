package com.bwell.modules.eatwell.calculator.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@Table (
        name = "user_data"
)
public class CalculatorData {
    @Id
    @GeneratedValue
    private long id;

    private int bodyMassInKg;
    private int heightInCm;
    private int age;
    private boolean isMan;
    private Double activityRatio;
    private String strategy;
    private DietGoal goal;
    private Double proteinPercentage;
    private Double fatPercentage;
    private Double carbohydratesPercentage;

    public void setGoal(String goal) {
        this.goal = DietGoal.valueOf(goal);
    }
}
