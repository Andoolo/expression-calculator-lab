package com.Andrey.calculator;

import java.util.List;

/**
 * Парсер математических выражений с использованием рекурсивного спуска
 */
public class Parser {
    private final List<Token> tokens;
    private final VariableManager variableManager;
    private int position;

    /**
     * Создает парсер для списка токенов
     * @param tokens список токенов для разбора
     * @param variableManager менеджер переменных
     */
    public Parser(List<Token> tokens, VariableManager variableManager) {
        this.tokens = tokens;
        this.variableManager = variableManager;
        this.position = 0;
    }

    /**
     * Вычисляет значение выражения
     * @return результат вычисления
     * @throws IllegalArgumentException при синтаксической ошибке
     */
    public double parse() {
        double result = parseExpression();

        if (getCurrentToken().getType() != TokenType.EOF) {
            throw new IllegalArgumentException("Неожиданный токен: " + getCurrentToken().getValue());
        }

        return result;
    }

    /**
     * Парсит выражение (обрабатывает + и -)
     */
    private double parseExpression() {
        double result = parseTerm();

        while (true) {
            Token token = getCurrentToken();

            if (token.getType() == TokenType.PLUS) {
                position++;
                result += parseTerm();
            } else if (token.getType() == TokenType.MINUS) {
                position++;
                result -= parseTerm();
            } else {
                break;
            }
        }

        return result;
    }

    /**
     * Парсит терм (обрабатывает * и /)
     */
    private double parseTerm() {
        double result = parsePower();

        while (true) {
            Token token = getCurrentToken();

            if (token.getType() == TokenType.MULTIPLY) {
                position++;
                result *= parsePower();
            } else if (token.getType() == TokenType.DIVIDE) {
                position++;
                double divisor = parsePower();
                if (divisor == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                result /= divisor;
            } else {
                break;
            }
        }

        return result;
    }

    /**
     * Парсит степень (обрабатывает ^)
     */
    private double parsePower() {
        double result = parseFactor();

        if (getCurrentToken().getType() == TokenType.POWER) {
            position++;
            result = Math.pow(result, parsePower()); // Правая ассоциативность
        }

        return result;
    }

    /**
     * Парсит фактор (число, переменную, функцию или выражение в скобках)
     */
    private double parseFactor() {
        Token token = getCurrentToken();

        if (token.getType() == TokenType.NUMBER) {
            position++;
            return Double.parseDouble(token.getValue());
        } else if (token.getType() == TokenType.VARIABLE) {
            position++;
            return variableManager.getValue(token.getValue());
        } else if (token.getType() == TokenType.FUNCTION) {
            return parseFunction();
        } else if (token.getType() == TokenType.LPAREN) {
            position++;
            double result = parseExpression();

            if (getCurrentToken().getType() != TokenType.RPAREN) {
                throw new IllegalArgumentException("Ожидалась закрывающая скобка");
            }
            position++;
            return result;
        } else if (token.getType() == TokenType.MINUS) {
            position++;
            return -parseFactor();
        } else if (token.getType() == TokenType.PLUS) {
            position++;
            return parseFactor();
        }

        throw new IllegalArgumentException("Неожиданный токен: " + token.getValue());
    }

    /**
     * Парсит математическую функцию
     */
    private double parseFunction() {
        Token functionToken = getCurrentToken();
        position++;

        if (getCurrentToken().getType() != TokenType.LPAREN) {
            throw new IllegalArgumentException("После функции ожидается '('");
        }
        position++;

        double argument = parseExpression();

        if (getCurrentToken().getType() != TokenType.RPAREN) {
            throw new IllegalArgumentException("Ожидалась закрывающая скобка после аргумента функции");
        }
        position++;

        return applyFunction(functionToken.getValue(), argument);
    }

    /**
     * Применяет математическую функцию к аргументу
     * @param functionName имя функции
     * @param argument аргумент функции
     * @return результат применения функции
     */
    private double applyFunction(String functionName, double argument) {
        return switch (functionName) {
            case "sin" -> Math.sin(argument);
            case "cos" -> Math.cos(argument);
            case "tan" -> Math.tan(argument);
            case "sqrt" -> {
                if (argument < 0) {
                    throw new ArithmeticException("Корень из отрицательного числа");
                }
                yield Math.sqrt(argument);
            }
            case "log" -> {
                if (argument <= 0) {
                    throw new ArithmeticException("Логарифм от неположительного числа");
                }
                yield Math.log10(argument);
            }
            case "ln" -> {
                if (argument <= 0) {
                    throw new ArithmeticException("Натуральный логарифм от неположительного числа");
                }
                yield Math.log(argument);
            }
            case "abs" -> Math.abs(argument);
            case "exp" -> Math.exp(argument);
            default -> throw new IllegalArgumentException("Неизвестная функция: " + functionName);
        };
    }

    /**
     * Получает текущий токен
     */
    private Token getCurrentToken() {
        return tokens.get(position);
    }
}