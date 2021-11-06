package com.bwell.modules.user.data.model;

import com.bwell.modules.eatwell.calculator.model.CalculatorData;
import com.bwell.modules.eatwell.calculator.model.dtos.NutrientsDemandDao;
import com.bwell.modules.eatwell.dietplan.model.DietPlan;
import com.bwell.modules.user.favourites.model.Favourites;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.persistence.*;

@Entity(name="users")
@Data
public class User {
    @Transient
    public static Long defaultUserId;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "favourites")
    private Favourites favourites;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="diet_plan_id", referencedColumnName = "id")
    private DietPlan dietPlan;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    @JsonManagedReference
    private NutrientsDemandDao nutrientsDemand;


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    @JsonManagedReference
    private CalculatorData calculatorData;


    public static User createEmpty(){
        User user = new User();
        user.id = 0l;
        user.dietPlan = new DietPlan();
        user.favourites = new Favourites();
        return user;
    }




}
