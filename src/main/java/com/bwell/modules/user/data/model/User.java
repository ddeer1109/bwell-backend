package com.bwell.modules.user.data.model;

import com.bwell.modules.user.favourites.model.Favourites;
import lombok.*;
import javax.persistence.*;

@Entity(name="users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "favourites")
    private Favourites favourites;

}
