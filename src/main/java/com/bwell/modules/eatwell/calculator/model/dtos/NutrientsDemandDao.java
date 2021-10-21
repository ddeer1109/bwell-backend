package com.bwell.modules.eatwell.calculator.model.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(
        name = "user_demand"
)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class NutrientsDemandDao {
    @Id
    private long userId;

    private BigDecimal caloriesDemand;
    private BigDecimal proteinDemand;
    private BigDecimal carbohydratesDemand;
    private BigDecimal fatDemand;
    private BigDecimal proteinPercentage;
    private BigDecimal fatPercentage;
    private BigDecimal carbohydratesPercentage;
    private BigDecimal caloriesPercentage;
}
