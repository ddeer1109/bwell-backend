package com.bwell.modules.eatwell.recipes.ingredients.model;

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
    @GeneratedValue
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

    public IngredientRecord getRecord() {
        return IngredientRecord.generate(id, amount, unit);
    }

    public final static class IngredientRecord {
        private final int id;
        private final double amount;
        private final String unit;

        private IngredientRecord(int id, double amount, String unit) {
            this.id = id;
            this.amount = amount;
            this.unit = unit;
        }

        static IngredientRecord generate(int id, double amount, String unit) {
            return new IngredientRecord(id, amount, unit);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IngredientRecord that = (IngredientRecord) o;
            return id == that.id && Double.compare(that.amount, amount) == 0 && unit.equals(that.unit);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, amount, unit);
        }
    }
}
