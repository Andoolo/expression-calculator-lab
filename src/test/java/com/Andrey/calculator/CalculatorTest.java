package com.Andrey.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса Calculator
 */
class CalculatorTest {
    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void testSimpleAddition() {
        assertEquals(5.0, calculator.evaluate("2 + 3"), 0.0001);
    }

    @Test
    void testSimpleSubtraction() {
        assertEquals(1.0, calculator.evaluate("5 - 4"), 0.0001);
    }

    @Test
    void testSimpleMultiplication() {
        assertEquals(12.0, calculator.evaluate("3 * 4"), 0.0001);
    }

    @Test
    void testSimpleDivision() {
        assertEquals(2.5, calculator.evaluate("5 / 2"), 0.0001);
    }

    @Test
    void testPower() {
        assertEquals(8.0, calculator.evaluate("2 ^ 3"), 0.0001);
    }

    @Test
    void testComplexExpression() {
        assertEquals(14.0, calculator.evaluate("2 + 3 * 4"), 0.0001);
    }

    @Test
    void testParentheses() {
        assertEquals(20.0, calculator.evaluate("(2 + 3) * 4"), 0.0001);
    }

    @Test
    void testNestedParentheses() {
        assertEquals(26.0, calculator.evaluate("((2 + 3) * 4) + 6"), 0.0001);
    }

    @Test
    void testUnaryMinus() {
        assertEquals(-5.0, calculator.evaluate("-5"), 0.0001);
        assertEquals(-2.0, calculator.evaluate("3 + -5"), 0.0001);
    }

    @Test
    void testDecimalNumbers() {
        assertEquals(5.5, calculator.evaluate("2.5 + 3.0"), 0.0001);
    }

    @Test
    void testSinFunction() {
        assertEquals(Math.sin(0), calculator.evaluate("sin(0)"), 0.0001);
    }

    @Test
    void testCosFunction() {
        assertEquals(Math.cos(0), calculator.evaluate("cos(0)"), 0.0001);
    }

    @Test
    void testSqrtFunction() {
        assertEquals(5.0, calculator.evaluate("sqrt(25)"), 0.0001);
    }

    @Test
    void testAbsFunction() {
        assertEquals(5.0, calculator.evaluate("abs(-5)"), 0.0001);
    }

    @Test
    void testVariables() {
        Map<String, Double> vars = new HashMap<>();
        vars.put("x", 5.0);
        vars.put("y", 3.0);

        Calculator calcWithVars = new Calculator(new VariableManager(vars));
        assertEquals(8.0, calcWithVars.evaluate("x + y"), 0.0001);
    }

    @Test
    void testComplexExpressionWithVariables() {
        Map<String, Double> vars = new HashMap<>();
        vars.put("x", 2.0);

        Calculator calcWithVars = new Calculator(new VariableManager(vars));
        assertEquals(7.0, calcWithVars.evaluate("x^2 + 3"), 0.0001);
    }

    @Test
    void testDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> calculator.evaluate("5 / 0"));
    }

    @Test
    void testSqrtNegative() {
        assertThrows(ArithmeticException.class, () -> calculator.evaluate("sqrt(-1)"));
    }

    @Test
    void testInvalidExpression() {
        assertThrows(IllegalArgumentException.class, () -> calculator.evaluate("2 +"));
    }

    @Test
    void testEmptyExpression() {
        assertThrows(IllegalArgumentException.class, () -> calculator.evaluate(""));
    }

    @Test
    void testMismatchedParentheses() {
        assertThrows(IllegalArgumentException.class, () -> calculator.evaluate("(2 + 3"));
    }
}