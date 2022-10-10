package com.bwell.modules.eatwell.calculator.model.dtos;

import com.bwell.user.data.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(
        name = "user_demand"
)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class NutrientsDemandDao {
    @Id
    @GeneratedValue
    private long id;


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    @JsonBackReference
    @ToString.Exclude
    private User user;


    private BigDecimal caloriesDemand;
    private BigDecimal proteinDemand;
    private BigDecimal carbohydratesDemand;
    private BigDecimal fatDemand;
    private BigDecimal proteinPercentage;
    private BigDecimal fatPercentage;
    private BigDecimal carbohydratesPercentage;
    private BigDecimal caloriesPercentage;


    public static NutrientsDemandDao createDefault(){
        NutrientsDemandDao nutrientsDemandDao = new NutrientsDemandDao();
        nutrientsDemandDao.caloriesDemand = BigDecimal.valueOf(2050);
        nutrientsDemandDao.proteinDemand = BigDecimal.valueOf(150);
        nutrientsDemandDao.carbohydratesDemand = BigDecimal.valueOf(250);
        nutrientsDemandDao.fatDemand = BigDecimal.valueOf(50);
        nutrientsDemandDao.proteinPercentage = BigDecimal.valueOf(0.29);
        nutrientsDemandDao.carbohydratesPercentage = BigDecimal.valueOf(0.49);
        nutrientsDemandDao.caloriesPercentage = BigDecimal.valueOf(1);
        nutrientsDemandDao.fatPercentage = BigDecimal.valueOf(0.22);
        nutrientsDemandDao.id = 0;
        return nutrientsDemandDao;
    }
}
