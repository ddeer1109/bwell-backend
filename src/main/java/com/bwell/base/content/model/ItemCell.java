package com.bwell.base.content.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItemCell implements Serializable {
    private long id;
    private int order;
    private String value;
}
