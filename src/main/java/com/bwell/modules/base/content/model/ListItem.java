package com.bwell.modules.base.content.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ListItem implements Serializable {
    private long id;
    private int order;
    private List<ItemCell> cells;
    private boolean isHeaderRow;


    @JsonGetter("isHeaderRow")
    public boolean isHeaderRow() {
        return isHeaderRow;
    }

    @JsonSetter("isHeaderRow")
    public void setHeaderRow(boolean headerRow) {
        isHeaderRow = headerRow;
    }
}
