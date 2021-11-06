package com.bwell.modules.base;

import com.bwell.modules.eatwell.recipes.ingredients.model.DetailedIngredientDto;
import com.bwell.modules.eatwell.recipes.ingredients.model.IngredientsList;
import com.fasterxml.jackson.annotation.*;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CustomList.class, name = "custom_list"),
        @JsonSubTypes.Type(value = IngredientsList.class, name = "ingredients_list"),
        @JsonSubTypes.Type(value = TextArea.class, name = "text_area")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "CONTENT")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
public abstract class ContentElement implements Serializable {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private long id;
    @JsonPropertyOrder(value = "1")
    private String type;
    private String header;

    @Column(name = "index")
    private int order;


    @Type(type = "jsonb")
    @Column(columnDefinition =  "jsonb")
    private List<DetailedIngredientDto> ingredients;
    @Type(type = "text")
    private String text;
    @Type(type = "jsonb")
    @Column(columnDefinition =  "jsonb")
    private List<ListItem> content;



    @JsonGetter("order")
    public int getOrder() {
        return order;
    }
    @JsonGetter("order")
    public void setOrder(int order) {
        this.order = order;
    }

    @JsonTypeId
    public String getType() {
        return type;
    }
}
