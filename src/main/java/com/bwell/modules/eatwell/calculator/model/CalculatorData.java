package com.bwell.modules.eatwell.calculator.model;

import com.bwell.modules.user.data.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
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


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    @JsonBackReference
    @ToString.Exclude
    private User user;

    private int bodyMassInKg;
    private int heightInCm;
    private int age;
    private boolean isMan;
    private double activityRatio;
    private String strategy;
    private DietGoal goal;
    private double proteinPercentage;
    private double fatPercentage;
    private double carbohydratesPercentage;

    public boolean isMan() {
        return isMan;
    }
    public void setIsMan(boolean man) {
        isMan = man;
    }

    public void setGoal(String goal) {
        this.goal = DietGoal.valueOf(goal);
    }
}
