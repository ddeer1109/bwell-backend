package com.bwell.modules.user.favourites.model;
import com.bwell.modules.user.data.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
