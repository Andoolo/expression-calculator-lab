package com.Andrey.calculator;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Интеграционные тесты для проверки работы всей системы
 */
class IntegrationTest {

    @Test
    void testComplexExpressionWithAllFeatures() {
        Map<String, Double> vars = new HashMap<>();
        vars.put("x", 2.0);
        vars.put("y", 3.0);

        Calculator calculator = new Calculator(new VariableManager(vars));

        // (sqrt(x^2 + y^2) + abs(-5)) * sin(0) + cos(0) = 0 + 1 = 1
        double result = calculator.evaluate("(sqrt(x^2 + y^2) + abs(-5)) * sin(0) + cos(0)");
        assertEquals(1.0, result, 0.0001);
    }

    @Test
    void testNestedFunctionsWithVariables() {
        Map<String, Double> vars = new HashMap<>();
        vars.put("a", 16.0);

        Calculator calculator = new Calculator(new VariableManager(vars));

        // sqrt(sqrt(a)) = sqrt(sqrt(16)) = sqrt(4) = 2
        double result = calculator.evaluate("sqrt(sqrt(a))");
        assertEquals(2.0, result, 0.0001);
    }

    @Test
    void testLongExpression() {
        Calculator calculator = new Calculator();

        String expression = "((2 + 3) * (4 - 1) + 10 / 2) * 2 - 5";
        // ((5) * (3) + 5) * 2 - 5 = (15 + 5) * 2 - 5 = 20 * 2 - 5 = 35
        double result = calculator.evaluate(expression);
        assertEquals(35.0, result, 0.0001);
    }

    @Test
    void testMultipleVariablesAndFunctions() {
        Map<String, Double> vars = new HashMap<>();
        vars.put("radius", 5.0);
        vars.put("height", 10.0);

        Calculator calculator = new Calculator(new VariableManager(vars));

        // Объем цилиндра (упрощенно без π): radius^2 * height
        double result = calculator.evaluate("radius^2 * height");
        assertEquals(250.0, result, 0.0001);
    }

    @Test
    void testDecimalPrecision() {
        Calculator calculator = new Calculator();

        double result = calculator.evaluate("0.1 + 0.2");
        assertEquals(0.3, result, 0.0001);
    }
}