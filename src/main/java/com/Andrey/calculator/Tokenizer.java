package com.Andrey.calculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Токенизатор для разбора математических выражений на токены
 */
public class Tokenizer {
    private final String expression;
    private int position;

    /**
     * Создает токенизатор для заданного выражения
     * @param expression математическое выражение для разбора
     */
    public Tokenizer(String expression) {
        this.expression = expression.replaceAll("\\s+", ""); // Удаляем пробелы
        this.position = 0;
    }

    /**
     * Разбивает выражение на список токенов
     * @return список токенов
     * @throws IllegalArgumentException если встречен неизвестный символ
     */
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (position < expression.length()) {
            char current = expression.charAt(position);

            if (Character.isDigit(current) || current == '.') {
                tokens.add(readNumber());
            } else if (Character.isLetter(current)) {
                tokens.add(readIdentifier());
            } else if (current == '+') {
                tokens.add(new Token(TokenType.PLUS, "+"));
                position++;
            } else if (current == '-') {
                tokens.add(new Token(TokenType.MINUS, "-"));
                position++;
            } else if (current == '*') {
                tokens.add(new Token(TokenType.MULTIPLY, "*"));
                position++;
            } else if (current == '/') {
                tokens.add(new Token(TokenType.DIVIDE, "/"));
                position++;
            } else if (current == '^') {
                tokens.add(new Token(TokenType.POWER, "^"));
                position++;
            } else if (current == '(') {
                tokens.add(new Token(TokenType.LPAREN, "("));
                position++;
            } else if (current == ')') {
                tokens.add(new Token(TokenType.RPAREN, ")"));
                position++;
            } else {
                throw new IllegalArgumentException("Неизвестный символ: " + current);
            }
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    /**
     * Читает число из выражения
     * @return токен с числом
     */
    private Token readNumber() {
        StringBuilder number = new StringBuilder();
        boolean hasDot = false;

        while (position < expression.length()) {
            char current = expression.charAt(position);

            if (Character.isDigit(current)) {
                number.append(current);
                position++;
            } else if (current == '.' && !hasDot) {
                hasDot = true;
                number.append(current);
                position++;
            } else {
                break;
            }
        }

        return new Token(TokenType.NUMBER, number.toString());
    }

    /**
     * Читает идентификатор (функцию или переменную)
     * @return токен с функцией или переменной
     */
    private Token readIdentifier() {
        StringBuilder identifier = new StringBuilder();

        while (position < expression.length() && Character.isLetterOrDigit(expression.charAt(position))) {
            identifier.append(expression.charAt(position));
            position++;
        }

        String name = identifier.toString();

        // Проверяем, является ли это функцией
        if (isFunction(name)) {
            return new Token(TokenType.FUNCTION, name);
        } else {
            return new Token(TokenType.VARIABLE, name);
        }
    }

    /**
     * Проверяет, является ли имя математической функцией
     * @param name имя для проверки
     * @return true если это функция, иначе false
     */
    private boolean isFunction(String name) {
        return name.equals("sin") || name.equals("cos") || name.equals("tan") ||
                name.equals("sqrt") || name.equals("log") || name.equals("ln") ||
                name.equals("abs") || name.equals("exp");
    }
}