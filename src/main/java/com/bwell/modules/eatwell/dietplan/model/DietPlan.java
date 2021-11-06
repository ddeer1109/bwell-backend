package com.bwell.modules.eatwell.dietplan.model;

import com.bwell.modules.eatwell.recipes.model.Recipe;
import com.bwell.modules.user.data.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

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
}
