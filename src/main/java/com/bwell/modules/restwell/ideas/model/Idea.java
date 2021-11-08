package com.bwell.modules.restwell.ideas.model;

import com.bwell.modules.base.entry.Entry;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;

@JsonTypeName("idea")
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "Idea")
public class Idea extends Entry {

    public Idea() {
        setModule("idea");
    }
}
