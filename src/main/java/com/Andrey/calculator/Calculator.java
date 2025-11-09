package com.Andrey.calculator;

import java.util.List;

/**
 * Главный класс калькулятора математических выражений
 */
public class Calculator {
    private final VariableManager variableManager;

    /**
     * Создает новый калькулятор
     */
    public Calculator() {
        this.variableManager = new VariableManager();
    }

    /**
     * Создает калькулятор с заданным менеджером переменных
     * @param variableManager менеджер переменных
     */
    public Calculator(VariableManager variableManager) {
        this.variableManager = variableManager;
    }

    /**
     * Вычисляет значение математического выражения
     * @param expression строка с математическим выражением
     * @return результат вычисления
     * @throws IllegalArgumentException если выражение некорректно
     * @throws ArithmeticException при математических ошибках (деление на ноль и т.д.)
     */
    public double evaluate(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Выражение не может быть пустым");
        }

        try {
            // Токенизация
            Tokenizer tokenizer = new Tokenizer(expression);
            List<Token> tokens = tokenizer.tokenize();

            // Парсинг и вычисление
            Parser parser = new Parser(tokens, variableManager);
            return parser.parse();
        } catch (ArithmeticException e) {
            // Пробрасываем арифметические ошибки как есть
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка при вычислении выражения: " + e.getMessage(), e);
        }
    }

    /**
     * Получает менеджер переменных
     * @return менеджер переменных
     */
    public VariableManager getVariableManager() {
        return variableManager;
    }
}