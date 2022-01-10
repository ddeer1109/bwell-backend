package com.bwell.base.content.model;

import com.bwell.utils.IdGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class ItemCell implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private int order;
    private String value;


    public long getId() {
        if (id == 0L){
            setId(IdGenerator.nextId());
        }
        return id;
    }
}
