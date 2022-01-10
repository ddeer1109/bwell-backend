package com.bwell.user.favourites.model;
import com.bwell.user.data.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Favourites {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @JsonIgnore
    private Long id;

    @OneToOne(mappedBy = "favourites")
    @JsonIgnore
    @ToString.Exclude
    private User user;

    @ElementCollection
    private List<Integer> activities;

    @ElementCollection
    private List<Integer> recipes;

    @ElementCollection
    private List<Integer> ideas;

    @ElementCollection
    private List<Integer> exercises;


}
