package com.bwell.modules.eatwell.recipes.ingredients.model;

import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Nutrients;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class DetailedIngredient {
    @Id
//    @SequenceGenerator(
//            name = "my_sequence",
//            allocationSize = 5
//    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal amount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    private Unit unit = new Unit("g");

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private Nutrients nutrition;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    private Ingredient ingredient = new Ingredient();


    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @Transient
    @JsonIgnore
    public long getIngredientId() {
        return ingredient.getId();
    }

    @JsonSetter("id")
    public void setIngredientId(long id) {
        ingredient.setId(id);
    }

    @JsonIgnore
    @Transient
    public String getName() {
        return ingredient.getName();
    }
    @JsonSetter("name")
    public void setName(String name) {
        this.ingredient.setName(name);
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Column(name="amount")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        if (nutrition != null) {
            nutrition.updateNutrientsAmounts(amount.divide(this.amount, new MathContext(4)));
        }
        this.amount = amount;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit newUnit) {
        unit = newUnit;
    }

    @JsonGetter("unit")
    public String getUnitName() {
        return unit.getName();
    }
    @JsonSetter("unit")
    public void setUnitName(String name) {
        this.unit.setName(name);
    }


    @JsonSetter("possibleUnits")
    public void setPossibleUnits(Set<Unit> units){
        getIngredient().setUnits(units);
    }



    @Transient
    public Nutrients getNutrition() {
        return nutrition;
    }

    @JsonPropertyOrder("1")
    public void setNutrition(Nutrients nutrition) {
        this.nutrition = nutrition;
    }

    @Transient
    public DetailedIngredientDto createDto(){
        DetailedIngredientDto detailedIngredientDto = new DetailedIngredientDto();
        detailedIngredientDto.setId(ingredient.getId());
        detailedIngredientDto.setIngredient(ingredient.getName());
        detailedIngredientDto.setAmount(amount.doubleValue());
        detailedIngredientDto.setUnit(unit);
        detailedIngredientDto.setPossibleUnits(ingredient.getUnits());

        return detailedIngredientDto;
    }

    @Override
    public String toString() {
        return "DetailedIngredient{ " +
                "id=" + id +
                ", ingredient_id=" + ingredient.getId() +
                ", name='" + ingredient.getName() + '\'' +
                ", unit='" + unit + '\'' +
                ", amount=" + amount +
                '}';
    }

}
