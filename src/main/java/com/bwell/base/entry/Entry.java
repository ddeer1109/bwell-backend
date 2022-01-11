package com.bwell.base.entry;

import com.bwell.base.rating.model.Rating;
import com.bwell.base.content.model.ContentElement;
import com.bwell.modules.eatwell.recipes.model.Recipe;
import com.bwell.modules.fitwell.activities.model.Activity;
import com.bwell.modules.restwell.ideas.model.Idea;
import com.bwell.modules.thinkwell.exercises.model.Exercise;
import com.bwell.user.data.model.User;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "module")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Recipe.class, name = "recipe"),
        @JsonSubTypes.Type(value = Activity.class, name = "activity"),
        @JsonSubTypes.Type(value = Idea.class, name = "idea"),
        @JsonSubTypes.Type(value = Exercise.class, name = "exercise"),
})
//@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
public abstract class Entry {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private long id;
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @ToString.Exclude
    private User author;

    private String module;
    @JoinColumn(name = "rating_id", referencedColumnName = "id")
    @OneToOne
    private Rating rating;

    @OneToMany
    @JoinTable(name = "entry_content",
            joinColumns = @JoinColumn(name = "entry_id"),
            inverseJoinColumns = @JoinColumn(name = "content_id"))
    private List<ContentElement> content;

    @Column(name = "CONTENT")
    @JsonGetter("content")
    public List<ContentElement> getContents() {
        return content;
    }
    @JsonSetter("content")
    public void setContents(List<ContentElement> content) {
        this.content = content;
    }

    @JsonGetter("author")
    public Map<String, Object> getAuthorDto(){
        HashMap<String, Object> auth = new HashMap<>();
        auth.put("id", author.getId());
        auth.put("email", author.getEmail());
        return auth;
    }

    @JsonTypeId
    public String getModule() {
        return module;
    }

    public void setModule(String type) {
        this.module = type;
    }
}
