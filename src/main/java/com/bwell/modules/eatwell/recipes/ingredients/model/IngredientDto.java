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
    private String name;

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
        return IngredientRecord.generate(id, amount, unit, name);
    }

    public final static class IngredientRecord {
        private final int id;
        private final double amount;
        private final String unit;
        private final String name;

        public int getId() {
            return id;
        }

        public double getAmount() {
            return amount;
        }

        public String getUnit() {
            return unit;
        }

        public String getName() {
            return name;
        }

        private IngredientRecord(int id, double amount, String unit, String name) {
            this.id = id;
            this.amount = amount;
            this.unit = unit;
            this.name = name;
        }

        static IngredientRecord generate(int id, double amount, String unit, String name) {
            return new IngredientRecord(id, amount, unit, name);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IngredientRecord that = (IngredientRecord) o;
            return id == that.id && Double.compare(that.amount, amount) == 0 && Objects.equals(unit, that.unit) && Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, amount, unit, name);
        }

        @Override
        public String toString() {
            return "IR{" +
                    "id=" + id +
                    ", amount=" + amount +
                    ", unit='" + unit + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
