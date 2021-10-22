package com.bwell.modules.eatwell.calculator.model.dtos;

import com.bwell.modules.eatwell.recipes.ingredients.nutrition.Names;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class IngredientCoverageDto {
    private BigDecimal calories;
    private BigDecimal protein;
    private BigDecimal fat;
    private BigDecimal carbohydrates;

    public IngredientCoverageDto() {
        this.calories = BigDecimal.ZERO;
        this.protein = BigDecimal.ZERO;
        this.fat = BigDecimal.ZERO;
        this.carbohydrates = BigDecimal.ZERO;
    }

    public IngredientCoverageDto(Map<String, BigDecimal> map) {
        this.calories = map.get(Names.KCAL.name);
        this.protein = map.get(Names.PROTEIN.name);
        this.fat = map.get(Names.FAT.name);
        this.carbohydrates = map.get(Names.CARBS.name);
    }

    public IngredientCoverageDto addCoverage(IngredientCoverageDto dto){
        this.calories = this.calories.add(dto.getCalories());
        this.protein = this.protein.add(dto.getProtein());
        this.fat = this.fat.add(dto.getFat());
        this.carbohydrates = this.carbohydrates.add(dto.getCarbohydrates());

        return dto;
    }
}
