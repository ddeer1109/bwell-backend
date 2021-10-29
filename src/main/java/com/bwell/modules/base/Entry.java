package com.bwell.modules.base;

import com.bwell.modules.eatwell.recipes.model.Recipe;
import com.bwell.modules.fitwell.activities.model.Activity;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "module")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Recipe.class, name = "recipe"),
        @JsonSubTypes.Type(value = Activity.class, name = "activity"),
})
//@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
public abstract class Entry {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private long id;
    private String title;
    private String description;


    private String module;
    @JoinColumn(name = "rating_id", referencedColumnName = "id")
    @OneToOne
    private Rating rating;

////
//    @JoinTable(
//            name = "entry_content",
//            joinColumns = @JoinColumn(name = "entry_id"),
//            inverseJoinColumns = @JoinColumn(name = "content_id")
//    )
//    @ManyToMany(
//            cascade = CascadeType.ALL
//    )
    @OneToMany
    @JoinTable(name = "entry_content",
            joinColumns = @JoinColumn(name = "entry_id"),
            inverseJoinColumns = @JoinColumn(name = "content_id"))
    private Set<ContentElement> content;

    @Column(name = "CONTENT")
    @JsonGetter("content")
    public Set<ContentElement> getContents() {
        return content;
    }
    @JsonSetter("content")
    public void setContents(Set<ContentElement> content) {
        this.content = content;
    }


    @JsonTypeId
    public String getModule() {
        return module;
    }

    public void setModule(String type) {
        this.module = type;
    }
}
