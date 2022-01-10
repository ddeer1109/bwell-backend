package com.bwell.base.content.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.Entity;
import java.util.List;

@JsonTypeName("custom_list")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class CustomList extends ContentElement{


    public CustomList() {
        setType("custom_list");
    }

    @Override
    public List<ListItem> getContent() {
        return super.getContent();
    }

    @Override
    public void setContent(List<ListItem> content) {
        super.setContent(content);
    }

    //    @JsonGetter("text")
//    public Set<ListItem> getCustomList() {
//        return super.getContent();
//    }
//
////    @JsonGetter("text")
//    public Set<ListItem> getContent() {
//        return getCustomList();
//    }
//
//    @JsonSetter("text")
//    public void setContent(Set<ListItem> customList) {
//        setContent(customList);
//    }
}
