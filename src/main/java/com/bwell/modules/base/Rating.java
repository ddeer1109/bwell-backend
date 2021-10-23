package com.bwell.modules.base;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Rating {
    @Id
    private long id;

    private int up;
    private int down;
}
