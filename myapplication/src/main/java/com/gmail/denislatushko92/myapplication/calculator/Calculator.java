package com.gmail.denislatushko92.myapplication.calculator;

import java.util.regex.Pattern;

public class Calculator {

    private CalculatorController calculatorController;

    public Calculator(CalculatorController calculatorController) {
        this.calculatorController = calculatorController;
    }

    public int add(int a, int b) {
        return a + b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        return a / b;
    }

    public boolean isNumber(String text) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        return pattern.matcher(text).matches();
    }

    public boolean isEven() {
        int number = calculatorController.getRandom();
        return number % 2 == 0;
    }

    private void foo(){

    }
}
