package com.gmail.denislatushko92.myapplication.calculator;

import java.util.Random;

public class CalculatorController {
    private NumberGenerator numberGenerator;

    public CalculatorController(NumberGenerator numberGenerator) {
        this.numberGenerator = numberGenerator;
    }

    public NumberGenerator getNumberGenerator() {
        return numberGenerator;
    }

    public int getRandom() {
        return (int) (Math.random() * 10);
    }
}
