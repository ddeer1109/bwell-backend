package com.bwell.modules.eatwell.recipes.ingredients.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Data
@Entity
public class IngredientDto {
    private int id;
    private double amount;
    private String unit;
    @Id
    @GeneratedValue
    private Long detailedId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientDto that = (IngredientDto) o;
        return id == that.id && Double.compare(that.amount, amount) == 0 && Objects.equals(unit, that.unit) && Objects.equals(detailedId, that.detailedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, unit);
    }
}
