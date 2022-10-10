package com.bwell.modules.eatwell.recipes.ingredients.nutrition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class NutrientsTest {

    @Mock
    Nutrients nutrients;
    private int initAmountCalories;
    private int initAmount;


    @BeforeEach
    void setUp() {
        nutrients = new Nutrients();
        initAmountCalories = 344;
        initAmount = 15;
        nutrients.setNutrients(List.of(
                Nutrient.Calories.create(initAmountCalories),
                Nutrient.Protein.create(initAmount),
                Nutrient.Carbohydrates.create(initAmount),
                Nutrient.Fat.create(initAmount)));
    }

    @Test
    void updateNutrientsAmounts() {
        BigDecimal calculationProportion = BigDecimal.valueOf(150)
                .divide(BigDecimal.valueOf(100), new MathContext(2));
    
        nutrients.updateNutrientsAmounts(calculationProportion);
        assertAll(
                () -> assertEquals(nutrients.get(Nutrient.Fat).getAmount().doubleValue(), BigDecimal.valueOf(initAmount).multiply(calculationProportion).doubleValue()),
                () -> assertEquals(nutrients.get(Nutrient.Protein).getAmount().doubleValue(), BigDecimal.valueOf(initAmount).multiply(calculationProportion).doubleValue()),
                () -> assertEquals(nutrients.get(Nutrient.Carbohydrates).getAmount().doubleValue(), BigDecimal.valueOf(initAmount).multiply(calculationProportion).doubleValue()),
                () -> assertEquals(nutrients.get(Nutrient.Calories).getAmount().doubleValue(), BigDecimal.valueOf(initAmountCalories).multiply(calculationProportion).doubleValue())
        );
    }
}