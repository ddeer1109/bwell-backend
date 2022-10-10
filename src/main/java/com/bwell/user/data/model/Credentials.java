package com.bwell.user.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
public class Credentials {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;



    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    @ToString.Exclude
    private User user;

    private String name;

    private String email;

    private String imageUrl;

    private boolean isVerified = false;

    @JsonIgnore
    private String password;

    private AuthProvider provider;

    private String providerId;
}
