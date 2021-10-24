package com.bwell.modules.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;

@JsonTypeName("text_area")
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class TextArea extends ContentElement {

    public TextArea() {
        setType("text_area");
    }
}
