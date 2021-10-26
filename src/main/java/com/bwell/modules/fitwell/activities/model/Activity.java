package com.bwell.modules.fitwell.activities.model;

import com.bwell.modules.base.Entry;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.*;

@JsonTypeName("activity")
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name="Activity")
public class Activity extends Entry {

    public Activity() {
        setModule("activity");
    }

}
