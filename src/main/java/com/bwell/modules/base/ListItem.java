package com.bwell.modules.base;

import lombok.Data;

import java.util.List;

@Data
public class ListItem {
    private long id;
    private int order;
    private List<ItemCell> cells;
}
