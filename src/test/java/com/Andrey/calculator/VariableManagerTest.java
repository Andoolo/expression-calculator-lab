package com.Andrey.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса VariableManager
 */
class VariableManagerTest {
    private VariableManager variableManager;

    @BeforeEach
    void setUp() {
        Map<String, Double> predefined = new HashMap<>();
        predefined.put("x", 10.0);
        predefined.put("y", 5.0);
        variableManager = new VariableManager(predefined);
    }

    @Test
    void testGetValueForPredefinedVariable() {
        assertEquals(10.0, variableManager.getValue("x"), 0.0001);
        assertEquals(5.0, variableManager.getValue("y"), 0.0001);
    }

    @Test
    void testSetValue() {
        variableManager.setValue("z", 20.0);
        assertEquals(20.0, variableManager.getValue("z"), 0.0001);
    }

    @Test
    void testOverwriteValue() {
        variableManager.setValue("x", 15.0);
        assertEquals(15.0, variableManager.getValue("x"), 0.0001);
    }

    @Test
    void testGetVariableNames() {
        assertTrue(variableManager.getVariableNames().contains("x"));
        assertTrue(variableManager.getVariableNames().contains("y"));
        assertEquals(2, variableManager.getVariableNames().size());
    }

    @Test
    void testClear() {
        variableManager.clear();
        assertEquals(0, variableManager.getVariableNames().size());
    }

    @Test
    void testUndefinedVariableThrows() {
        assertThrows(IllegalArgumentException.class, () -> variableManager.getValue("undefined"));
    }
}