package com.bwell.modules.thinkwell.exercises.model;

import com.bwell.modules.base.Entry;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;

@JsonTypeName("exercise")
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name="Exercise")

public class Exercise extends Entry {

    public Exercise() {
        setModule("exercise");
    }
}