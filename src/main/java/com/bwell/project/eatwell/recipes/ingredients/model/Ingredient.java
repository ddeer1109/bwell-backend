package com.bwell.project.eatwell.recipes.ingredients.model;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ingredient {
    @Id
    private long id;
    private String name;

    @JoinTable(
            name = "ingredient_unit",
            joinColumns = @JoinColumn(name = "ingredient_id"),
            inverseJoinColumns = @JoinColumn(name = "unit_id")
    )
    @ManyToMany(
            cascade = CascadeType.ALL
    )
    private Set<Unit> units;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonGetter(value = "possibleUnits")
    @Column(name = "unit")
    public Set<Unit> getUnits() {
        return units;
    }

    @JsonSetter(value = "possibleUnits")
    public void setUnits(Set<Unit> possibleUnits) {
        this.units = possibleUnits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }



}
