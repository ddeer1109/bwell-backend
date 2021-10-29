package com.bwell.modules.base;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.Entity;
import java.util.List;
import java.util.Set;

@JsonTypeName("custom_list")
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class CustomList extends ContentElement{


    public CustomList() {
        setType("custom_list");
    }

    @Override
    @JsonIgnore
    public Set<ListItem> getCustomList() {
        return super.getCustomList();
    }

    @JsonGetter("text")
    public Set<ListItem> getContent() {
        return getCustomList();
    }

    @JsonSetter("text")
    public void setContent(Set<ListItem> customList) {
        setCustomList(customList);
    }
}
