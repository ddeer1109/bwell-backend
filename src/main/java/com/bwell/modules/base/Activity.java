package com.bwell.modules.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.*;

@JsonTypeName("activity")
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name="Activity")
public class Activity extends Entry{


    public Activity() {
        setModule("activity");
    }

}
