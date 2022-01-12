package com.bwell.modules.eatwell.recipes.ingredients.model;

import com.bwell.modules.eatwell.recipes.model.Recipe;
import com.bwell.utils.IdGenerator;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
public class IngredientDto implements Serializable {
    private int id;
    private double amount;
    private String unit;

    @Id
    private Long detailedId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientDto that = (IngredientDto) o;
        return id == that.id && Double.compare(that.amount, amount) == 0 && Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, unit);
    }
}
