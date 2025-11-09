package com.Andrey.calculator;

/**
 * Типы токенов для парсера математических выражений
 */
public enum TokenType {
    NUMBER,      // Число
    PLUS,        // +
    MINUS,       // -
    MULTIPLY,    // *
    DIVIDE,      // /
    POWER,       // ^
    LPAREN,      // (
    RPAREN,      // )
    FUNCTION,    // Функция (sin, cos, sqrt и т.д.)
    VARIABLE,    // Переменная
    EOF          // Конец выражения
}