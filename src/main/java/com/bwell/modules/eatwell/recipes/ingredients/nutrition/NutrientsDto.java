package com.bwell.modules.eatwell.recipes.ingredients.nutrition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutrientsDto implements Serializable {
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

    public Nutrients toNutrients(){
        Nutrients nutrients = new Nutrients();
        nutrients.setNutrients(List.of(calories,protein,fat,carbohydrates));
        return nutrients;
    }
}
