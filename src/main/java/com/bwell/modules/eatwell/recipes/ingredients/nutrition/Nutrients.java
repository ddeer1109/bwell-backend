package com.bwell.modules.eatwell.recipes.ingredients.nutrition;

import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredient;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;

import static javax.persistence.GenerationType.SEQUENCE;

@JsonIgnoreProperties(ignoreUnknown = true)
//@Table(name = "nutrients")
//@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
//@Entity
public class Nutrients implements Serializable {

    private List<String> PROCESSED_NUTRIENTS = Arrays.asList("Calories", "Protein", "Fat", "Carbohydrates");

    private long id;
//
//    @OneToOne
//    @JoinColumn(name="ingredient_id", referencedColumnName = "id")
    @JsonBackReference
    private DetailedIngredient ingredient;

    @JsonIgnore
    public DetailedIngredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(DetailedIngredient ingredient) {
        this.ingredient = ingredient;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        id = id;
    }

    public long getIngredientId(){
        return ingredient.getId();
    }

    public void setIngredientId(long id){
        if (ingredient == null)
            ingredient = new DetailedIngredient();
        ingredient.setId(id);
    }

    @Type(type = "jsonb")
    @Column(columnDefinition =  "jsonb")
    private List<NutritionElement> nutrients;

    public List<NutritionElement> getNutrients() {
        return nutrients;
    }

    public NutritionElement get(String nutritionElementTitle) {
        return nutrients.stream().filter(nutr -> nutr.getTitle().equals(nutritionElementTitle)).findFirst().orElseThrow();
    }
    public NutritionElement get(Nutrient nutrient) {
        return nutrients.stream().filter(nutr -> nutr.getTitle().equals(nutrient.name)).findFirst().orElseThrow();
    }

    public void updateNutrientsAmounts(BigDecimal multiplier) {
        nutrients.forEach((nutr) -> {
            nutr.setAmount(nutr.getAmount().multiply(multiplier));
        });
    }

    public void addNutrients(Nutrients incomingNutrients){

        incomingNutrients.getNutrients().forEach(incomingNutritionElement -> {
            Nutrient elementType = incomingNutritionElement.getType();
            BigDecimal incomingNutritionElementAmount = incomingNutritionElement.getAmount();

            NutritionElement instanceNutritionElement = get(elementType);
            BigDecimal currentAmount = instanceNutritionElement.getAmount();
            instanceNutritionElement.setAmount(currentAmount.add(incomingNutritionElementAmount));
        });

    }

    public static Nutrients empty(){
        Nutrients nutrients = new Nutrients();
        List<NutritionElement> emptyNutrElements = Arrays
                .stream(Nutrient.values())
                .map(nutrient -> nutrient.create(0))
                .collect(Collectors.toList());
        nutrients.setNutrients(emptyNutrElements);
        return nutrients;
    }


//    @JsonSetter("nutrients")
    public void setNutrients(List<NutritionElement> nutrients) {
        this.nutrients = nutrients
                .stream()
                .filter(nutrient -> PROCESSED_NUTRIENTS.contains(nutrient.getTitle()))
                .collect(Collectors.toList());
    }
    @JsonIgnore
    public BigDecimal getPercentageFat(){
        return get(Names.FAT.name)
                .gramsToKcal()
                .getAmount()
                .divide(get(Names.KCAL.name).getAmount(), new MathContext(2));
    }
    @JsonIgnore
    public BigDecimal getPercentageProtein(){
        return get(Names.PROTEIN.name)
                .gramsToKcal()
                .getAmount()
                .divide(get(Names.KCAL.name).getAmount(), new MathContext(2));
    }
    @JsonIgnore
    public BigDecimal getPercentageCarbohydrates(){
        return get(Names.CARBS.name)
                .gramsToKcal()
                .getAmount()
                .divide(get(Names.KCAL.name).getAmount(), new MathContext(2));
    }


    @Override
    public String toString() {
        return "Nutrients{" +
//                "id=" + ingredient.getName() +
                ", nutrients=" + nutrients +
                '}';
    }
}

