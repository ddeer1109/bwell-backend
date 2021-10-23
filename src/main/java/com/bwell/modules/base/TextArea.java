package com.bwell.modules.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("text_area")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextArea extends ContentElement {

    public TextArea() {
        setType("text_area");
    }
}
