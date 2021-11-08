package com.bwell.modules.eatwell.recipes.ingredients.nutrition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutrientsDto {
    private NutritionElement calories;
    private NutritionElement protein;
    private NutritionElement fat;
    private NutritionElement carbohydrates;

    public static NutrientsDto ofNutrients(Nutrients nutrients){
        NutrientsDto nutrientsDto = new NutrientsDto();
        if (nutrients == null) {
            nutrients = Nutrients.empty();
        }
        nutrientsDto.calories = nutrients.get(Nutrient.Calories);
        nutrientsDto.carbohydrates = nutrients.get(Nutrient.Carbohydrates);
        nutrientsDto.fat = nutrients.get(Nutrient.Fat);
        nutrientsDto.protein = nutrients.get(Nutrient.Protein);
        return nutrientsDto;
    }
}
