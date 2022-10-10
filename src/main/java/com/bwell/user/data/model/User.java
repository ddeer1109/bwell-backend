package com.bwell.user.data.model;

import com.bwell.modules.eatwell.calculator.model.CalculatorData;
import com.bwell.modules.eatwell.calculator.model.dtos.NutrientsDemandDao;
import com.bwell.modules.eatwell.dietplan.model.DietPlan;
import com.bwell.user.favourites.model.Favourites;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import javax.persistence.*;

@Entity(name="user_profile")
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


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @JsonIgnore
    private Credentials credentials;

    @Transient
    private String email;

    public String getEmail() {
        return getCredentials().getEmail();
    }

    @JsonGetter("isVerified")
    @Transient
    public boolean isVerified() {
        return credentials.isVerified();
    }

    @JsonIgnore
    public Credentials getCredentials() {
        if (credentials == null)
            credentials = new Credentials();
            credentials.setUser(this);
        return credentials;
    }


}
