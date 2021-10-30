package com.bwell.modules.eatwell.recipes.ingredients.nutrition;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NutritionElement {

    @JsonIgnore
    private Nutrient type;
    @JsonIgnore
    public Nutrient getType() {
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
        try {
            type = Nutrient.valueOf(title);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @JsonSetter("amount")
    public void setAmount(double amount){
        this.amount = BigDecimal.valueOf(amount);
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    /**
     * @return BigDecimal multiplied by
     */
    public NutritionElement gramsToKcal(){
        amount = amount.multiply(type.kcalMultiplier);
        setUnit(Names.KCAL_UNIT.name);
        return this;
    }

    public NutritionElement kcalToGrams() {
        amount = BigDecimal.ONE
                .divide(type.kcalMultiplier, new MathContext(8))
                .multiply(amount);
        setUnit(Names.GRAMS_UNIT.name);
        return this;
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
