package com.bwell.base.content.model;

import com.bwell.utils.IdGenerator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class ListItem implements Serializable {

    @Id @GeneratedValue
    private long id;
    private int order;
    @OneToMany
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

    public long getId() {
        if (id == 0L){
            setId(IdGenerator.nextId());
        }
        return id;
    }
}
