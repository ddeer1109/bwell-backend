package com.bwell.modules.eatwell.calculator.model.Strategies;

public enum StrategyCodes {
    HarrisBenedict("HarrisBenedict"),
    Miffin("Miffin"),
    Complete("Complete");
    String value;
    StrategyCodes(String harrisBenedict) {
        value = harrisBenedict;
    }
}