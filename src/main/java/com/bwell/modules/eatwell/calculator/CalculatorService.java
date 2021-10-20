package com.bwell.modules.eatwell.calculator;

import com.bwell.modules.eatwell.calculator.model.CalculatorData;
import com.bwell.modules.eatwell.calculator.model.NutrientsDemand;
import com.bwell.modules.eatwell.calculator.model.NutritionStatisticsCalculator;
import com.bwell.modules.eatwell.calculator.model.Strategies.StrategyCodes;
import com.bwell.modules.eatwell.calculator.model.dtos.NutrientsDemandDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    private final CalculatorResultsRepository resultsRepository;

    @Autowired
    public CalculatorService(CalculatorResultsRepository resultsRepository) {
        this.resultsRepository = resultsRepository;
    }

    public NutrientsDemandDao getDemandForUser(long id) {
        return resultsRepository
                .getById(id);
    }

    public void setFakeData() {
        NutritionStatisticsCalculator calculator = new NutritionStatisticsCalculator();

        CalculatorData data = new CalculatorData();
        data.setId(1234L);
        data.setAge(25);
        data.setHeightInCm(170);
        data.setMan(true);
        data.setBodyMassInKg(70);
        data.setActivityRatio(1.5);
        data.setGoal("GainMass");
        data.setStrategy("Complete");


        calculator.setCalculator(StrategyCodes.valueOf(data.getStrategy()));
        calculator.setData(data);

        NutrientsDemand nutrientsDemand = calculator.calculateCaloriesDemand();
        NutrientsDemandDao dao = nutrientsDemand.createDao();
        dao.setUserId(data.getId());

        resultsRepository.save(dao);
    }
}
