package com.Andrey.calculator;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса Tokenizer
 */
class TokenizerTest {

    @Test
    void testSimpleExpression() {
        Tokenizer tokenizer = new Tokenizer("2 + 3");
        List<Token> tokens = tokenizer.tokenize();

        assertEquals(4, tokens.size()); // 2, +, 3, EOF
        assertEquals(TokenType.NUMBER, tokens.get(0).getType());
        assertEquals(TokenType.PLUS, tokens.get(1).getType());
        assertEquals(TokenType.NUMBER, tokens.get(2).getType());
        assertEquals(TokenType.EOF, tokens.get(3).getType());
    }

    @Test
    void testDecimalNumber() {
        Tokenizer tokenizer = new Tokenizer("3.14");
        List<Token> tokens = tokenizer.tokenize();

        assertEquals(2, tokens.size()); // 3.14, EOF
        assertEquals(TokenType.NUMBER, tokens.get(0).getType());
        assertEquals("3.14", tokens.get(0).getValue());
    }

    @Test
    void testFunction() {
        Tokenizer tokenizer = new Tokenizer("sin(0)");
        List<Token> tokens = tokenizer.tokenize();

        assertEquals(TokenType.FUNCTION, tokens.get(0).getType());
        assertEquals("sin", tokens.get(0).getValue());
    }

    @Test
    void testVariable() {
        Tokenizer tokenizer = new Tokenizer("x + y");
        List<Token> tokens = tokenizer.tokenize();

        assertEquals(TokenType.VARIABLE, tokens.get(0).getType());
        assertEquals("x", tokens.get(0).getValue());
        assertEquals(TokenType.VARIABLE, tokens.get(2).getType());
        assertEquals("y", tokens.get(2).getValue());
    }

    @Test
    void testAllOperators() {
        Tokenizer tokenizer = new Tokenizer("+-*/^");
        List<Token> tokens = tokenizer.tokenize();

        assertEquals(TokenType.PLUS, tokens.get(0).getType());
        assertEquals(TokenType.MINUS, tokens.get(1).getType());
        assertEquals(TokenType.MULTIPLY, tokens.get(2).getType());
        assertEquals(TokenType.DIVIDE, tokens.get(3).getType());
        assertEquals(TokenType.POWER, tokens.get(4).getType());
    }

    @Test
    void testParentheses() {
        Tokenizer tokenizer = new Tokenizer("(2+3)");
        List<Token> tokens = tokenizer.tokenize();

        assertEquals(TokenType.LPAREN, tokens.get(0).getType());
        assertEquals(TokenType.NUMBER, tokens.get(1).getType());
        assertEquals(TokenType.PLUS, tokens.get(2).getType());
        assertEquals(TokenType.NUMBER, tokens.get(3).getType());
        assertEquals(TokenType.RPAREN, tokens.get(4).getType());
    }

    @Test
    void testWhitespaceRemoval() {
        Tokenizer tokenizer = new Tokenizer("  2   +   3  ");
        List<Token> tokens = tokenizer.tokenize();

        assertEquals(4, tokens.size()); // Пробелы удалены
        assertEquals("2", tokens.get(0).getValue());
        assertEquals("3", tokens.get(2).getValue());
    }

    @Test
    void testComplexExpression() {
        Tokenizer tokenizer = new Tokenizer("sin(x) + sqrt(25) * 2");
        List<Token> tokens = tokenizer.tokenize();

        assertTrue(tokens.size() > 0);
        assertEquals(TokenType.FUNCTION, tokens.get(0).getType());
        assertEquals("sin", tokens.get(0).getValue());
    }

    @Test
    void testInvalidCharacter() {
        Tokenizer tokenizer = new Tokenizer("2 @ 3");
        assertThrows(IllegalArgumentException.class, tokenizer::tokenize);
    }

    @Test
    void testMultipleFunctions() {
        Tokenizer tokenizer = new Tokenizer("abs(cos(tan(0)))");
        List<Token> tokens = tokenizer.tokenize();

        assertEquals(TokenType.FUNCTION, tokens.get(0).getType());
        assertEquals("abs", tokens.get(0).getValue());
    }
}