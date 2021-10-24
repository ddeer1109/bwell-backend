package com.bwell.modules.base;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ListItem implements Serializable {
    private long id;
    private int order;
    private List<ItemCell> cells;
}
