package com.example.students.eatwell.recipes.ingredients.nutrition;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NutritionElement {

    @JsonIgnore
    private NUTRIENT type;
    @JsonIgnore
    public NUTRIENT getType() {
        return type;
    }


    private String title;
    private BigDecimal amount;
    private String unit;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        type = NUTRIENT.ofName(title);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    public BigDecimal inCalories(){
        return amount.multiply(type.kcalMultiplier);
    }

    @Override
    public String toString() {
        return "NutritionElement{" +
                "title='" + title + '\'' +
                ", amount=" + amount +
                ", unit='" + unit + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NutritionElement nutritionElement = (NutritionElement) o;
        return title.equals(nutritionElement.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
