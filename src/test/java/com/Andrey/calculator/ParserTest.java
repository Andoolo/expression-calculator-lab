package com.Andrey.calculator;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса Parser
 */
class ParserTest {

    @Test
    void testSimpleNumber() {
        Tokenizer tokenizer = new Tokenizer("42");
        List<Token> tokens = tokenizer.tokenize();
        Parser parser = new Parser(tokens, new VariableManager());

        assertEquals(42.0, parser.parse(), 0.0001);
    }

    @Test
    void testAddition() {
        Tokenizer tokenizer = new Tokenizer("10 + 20");
        List<Token> tokens = tokenizer.tokenize();
        Parser parser = new Parser(tokens, new VariableManager());

        assertEquals(30.0, parser.parse(), 0.0001);
    }

    @Test
    void testMultiplication() {
        Tokenizer tokenizer = new Tokenizer("5 * 6");
        List<Token> tokens = tokenizer.tokenize();
        Parser parser = new Parser(tokens, new VariableManager());

        assertEquals(30.0, parser.parse(), 0.0001);
    }

    @Test
    void testOperatorPrecedence() {
        Tokenizer tokenizer = new Tokenizer("2 + 3 * 4");
        List<Token> tokens = tokenizer.tokenize();
        Parser parser = new Parser(tokens, new VariableManager());

        assertEquals(14.0, parser.parse(), 0.0001);
    }

    @Test
    void testParenthesesPrecedence() {
        Tokenizer tokenizer = new Tokenizer("(2 + 3) * 4");
        List<Token> tokens = tokenizer.tokenize();
        Parser parser = new Parser(tokens, new VariableManager());

        assertEquals(20.0, parser.parse(), 0.0001);
    }

    @Test
    void testPowerOperation() {
        Tokenizer tokenizer = new Tokenizer("2 ^ 3");
        List<Token> tokens = tokenizer.tokenize();
        Parser parser = new Parser(tokens, new VariableManager());

        assertEquals(8.0, parser.parse(), 0.0001);
    }

    @Test
    void testPowerRightAssociative() {
        Tokenizer tokenizer = new Tokenizer("2 ^ 2 ^ 3");
        List<Token> tokens = tokenizer.tokenize();
        Parser parser = new Parser(tokens, new VariableManager());

        assertEquals(256.0, parser.parse(), 0.0001); // 2^(2^3) = 2^8 = 256
    }

    @Test
    void testUnaryMinus() {
        Tokenizer tokenizer = new Tokenizer("-5 + 3");
        List<Token> tokens = tokenizer.tokenize();
        Parser parser = new Parser(tokens, new VariableManager());

        assertEquals(-2.0, parser.parse(), 0.0001);
    }

    @Test
    void testVariableWithValue() {
        Map<String, Double> vars = new HashMap<>();
        vars.put("x", 10.0);

        Tokenizer tokenizer = new Tokenizer("x * 2");
        List<Token> tokens = tokenizer.tokenize();
        Parser parser = new Parser(tokens, new VariableManager(vars));

        assertEquals(20.0, parser.parse(), 0.0001);
    }

    @Test
    void testFunctionSqrt() {
        Tokenizer tokenizer = new Tokenizer("sqrt(16)");
        List<Token> tokens = tokenizer.tokenize();
        Parser parser = new Parser(tokens, new VariableManager());

        assertEquals(4.0, parser.parse(), 0.0001);
    }

    @Test
    void testFunctionAbs() {
        Tokenizer tokenizer = new Tokenizer("abs(-7)");
        List<Token> tokens = tokenizer.tokenize();
        Parser parser = new Parser(tokens, new VariableManager());

        assertEquals(7.0, parser.parse(), 0.0001);
    }

    @Test
    void testNestedFunctions() {
        Tokenizer tokenizer = new Tokenizer("sqrt(abs(-16))");
        List<Token> tokens = tokenizer.tokenize();
        Parser parser = new Parser(tokens, new VariableManager());

        assertEquals(4.0, parser.parse(), 0.0001);
    }

    @Test
    void testDivisionByZeroThrows() {
        Tokenizer tokenizer = new Tokenizer("10 / 0");
        List<Token> tokens = tokenizer.tokenize();
        Parser parser = new Parser(tokens, new VariableManager());

        assertThrows(ArithmeticException.class, parser::parse);
    }

    @Test
    void testMismatchedParentheses() {
        Tokenizer tokenizer = new Tokenizer("(2 + 3");
        List<Token> tokens = tokenizer.tokenize();
        Parser parser = new Parser(tokens, new VariableManager());

        assertThrows(IllegalArgumentException.class, parser::parse);
    }

    @Test
    void testComplexExpression() {
        Tokenizer tokenizer = new Tokenizer("((2 + 3) * 4) - 5 / 2");
        List<Token> tokens = tokenizer.tokenize();
        Parser parser = new Parser(tokens, new VariableManager());

        assertEquals(17.5, parser.parse(), 0.0001);
    }
}