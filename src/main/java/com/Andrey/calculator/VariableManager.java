package com.Andrey.calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Управляет переменными в математических выражениях
 */
public class VariableManager {
    private final Map<String, Double> variables;
    private final Scanner scanner;

    /**
     * Создает новый менеджер переменных
     */
    public VariableManager() {
        this.variables = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Создает менеджер с предопределенными значениями переменных (для тестов)
     * @param predefinedVariables карта переменных и их значений
     */
    public VariableManager(Map<String, Double> predefinedVariables) {
        this.variables = new HashMap<>(predefinedVariables);
        this.scanner = null;
    }

    /**
     * Получает значение переменной, запрашивая у пользователя если необходимо
     * @param name имя переменной
     * @return значение переменной
     */
    public double getValue(String name) {
        if (!variables.containsKey(name)) {
            if (scanner != null) {
                System.out.print("Введите значение для переменной " + name + ": ");
                double value = scanner.nextDouble();
                variables.put(name, value);
            } else {
                throw new IllegalArgumentException("Значение для переменной " + name + " не определено");
            }
        }
        return variables.get(name);
    }

    /**
     * Устанавливает значение переменной
     * @param name имя переменной
     * @param value значение переменной
     */
    public void setValue(String name, double value) {
        variables.put(name, value);
    }

    /**
     * Возвращает множество всех имен переменных
     * @return множество имен переменных
     */
    public Set<String> getVariableNames() {
        return variables.keySet();
    }

    /**
     * Очищает все переменные
     */
    public void clear() {
        variables.clear();
    }
}