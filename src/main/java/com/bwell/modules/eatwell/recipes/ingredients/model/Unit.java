package com.bwell.modules.eatwell.recipes.ingredients.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(
        name = "UNIT",
        uniqueConstraints=
            @UniqueConstraint(columnNames = {"name"})
)
public class Unit implements Serializable {

    public Unit() {
    }

    public Unit(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Unit{ " +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Id
    @GeneratedValue
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return name.equals(unit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Getter
    @Setter
    private String name;
}
