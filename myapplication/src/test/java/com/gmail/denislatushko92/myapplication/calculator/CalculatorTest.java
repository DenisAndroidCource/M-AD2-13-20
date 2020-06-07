package com.gmail.denislatushko92.myapplication.calculator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorTest {

    private CalculatorController calculatorController;
    private Calculator calculator;

    @Before
    public void setUp() {
        calculatorController = mock(CalculatorController.class);
        when(calculatorController.getNumberGenerator()).thenReturn(new NumberGenerator());
        calculator = new Calculator(calculatorController);
    }

    @Test
    public void validateAddResult() {
        int result = calculator.add(2, 3);

        assertEquals(5, result);
    }

    @Test
    public void validateMultiplicationResult() {
        int result = calculator.multiply(2, 3);

        assertEquals(6, result);
    }

    @Test
    public void validateDivisionResult() {
        int result = calculator.divide(4, 2);

        assertEquals(2, result);
    }

    @Test(expected = ArithmeticException.class)
    public void validateDivisionResultWithZeroValue() {
        calculator.divide(4, 0);
    }

    @Test
    public void validateIsNumberResultWithPositiveNumber() {
        boolean result = calculator.isNumber("5");

        assertTrue(result);
    }

    @Test
    public void validateIsNumberResultWithNegativeNumber() {
        boolean result = calculator.isNumber("-5");

        assertTrue(result);
    }

    @Test
    public void validateIsNumberResultWithPositiveIrrationalNumber() {
        boolean result = calculator.isNumber("5.05");

        assertTrue(result);
    }

    @Test
    public void validateIsNumberResultWithNegativeIrrationalNumber() {
        boolean result = calculator.isNumber("5.05");

        assertTrue(result);
    }

    @Test
    public void validateIsNumberResultWithString() {
        boolean result = calculator.isNumber("abc");

        assertFalse(result);
    }

    @Test
    public void validateIsNumberResultWithStringWithMinus() {
        boolean result = calculator.isNumber("-abc");

        assertFalse(result);
    }

    @Test
    public void validateIsNumberResultWithStringWithNumber() {
        boolean result = calculator.isNumber("-5abc");

        assertFalse(result);
    }

    @Test
    public void validateEvenNumber() {
        when(calculatorController.getRandom()).thenReturn(2);

        assertTrue(calculator.isEven());

    }

    @Test
    public void validateOddNumber() {
        when(calculatorController.getRandom()).thenReturn(3);

        assertFalse(calculator.isEven());
        verify(calculatorController).getRandom();
    }
}