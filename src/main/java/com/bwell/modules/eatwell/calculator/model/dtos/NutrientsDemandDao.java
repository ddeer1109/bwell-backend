package com.bwell.modules.eatwell.calculator.model.dtos;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Data
@Table(
        name = "user_demand"
)
public class NutrientsDemandDao {
    @Id
    private long userId;

    private BigDecimal caloriesDemand;
    private BigDecimal proteinDemand;
    private BigDecimal carbohydratesDemand;
    private BigDecimal fatDemand;
}
