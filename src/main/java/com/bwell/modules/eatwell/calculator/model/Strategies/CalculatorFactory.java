package com.bwell.modules.eatwell.calculator.model.Strategies;

import java.util.HashMap;
import java.util.Map;

public class CalculatorFactory {
    private static final Map<StrategyCodes, Command> CALCULATORS;


    static {
        CALCULATORS = new HashMap<>();
        CALCULATORS.put(StrategyCodes.HarrisBenedict, BasalDemandCalculator_HarrisBenedict::new);
//        CALCULATORS.put(StrategyCodes.HarrisBenedict, () -> new BasalDemandCalculator_HarrisBenedict());
//        CALCULATORS.put(StrategyCodes.HarrisBenedict, new Command() {
//            @Override
//            public CalculatorStrategy create() {
//                return new BasalDemandCalculator_HarrisBenedict();
//            }
//        });
        CALCULATORS.put(StrategyCodes.Miffin, BasalDemandCalculator_Miffin::new);
        CALCULATORS.put(StrategyCodes.Complete, CompleteDemandCalculator_::new);
    }

    public CalculatorStrategy createCalculator(StrategyCodes type) {
        Command command = CALCULATORS.get(type);

        if (command == null) {
            throw new IllegalArgumentException("Invalid calculation strategy");
        }

        return command.create();
    }
}
