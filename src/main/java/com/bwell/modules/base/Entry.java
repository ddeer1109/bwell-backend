package com.bwell.modules.base;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.util.Set;

@Data
public class Entry {
    @Id
    private long id;
    private String title;
    private String description;
    @JoinColumn(name = "rating_id", referencedColumnName = "id")
    private Rating rating;
    private Set<ContentElement> content;
}
