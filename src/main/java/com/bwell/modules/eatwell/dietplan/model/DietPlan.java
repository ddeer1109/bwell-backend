package com.bwell.modules.eatwell.dietplan.model;

import com.bwell.modules.eatwell.recipes.model.Recipe;
import com.bwell.modules.user.data.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity(name="diet_plan")
@Data
public class DietPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @JoinColumn
    @OneToOne
    private Recipe breakfast;

    @JoinColumn
    @OneToOne
    private Recipe lunch;

    @JoinColumn
    @OneToOne
    private Recipe dinner;

    @JoinColumn
    @OneToOne
    private Recipe supper;

    @Transient
    @JsonIgnore
    public List<Recipe> getSetMeals(){
        Recipe[] array = {breakfast, lunch, dinner, supper};
        return Arrays.stream(array)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
