package com.Andrey.calculator;

import java.util.Scanner;

/**
 * Главный класс приложения - точка входа
 */
public class Main {
    /**
     * Точка входа в программу
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        System.out.println("=== Калькулятор математических выражений ===");
        System.out.println("Поддерживаемые операции: +, -, *, /, ^ (степень)");
        System.out.println("Поддерживаемые функции: sin, cos, tan, sqrt, log, ln, abs, exp");
        System.out.println("Поддержка переменных и скобок");
        System.out.println("Для выхода введите 'exit'\n");

        while (true) {
            System.out.print("Введите выражение: ");
            String expression = scanner.nextLine().trim();

            if (expression.equalsIgnoreCase("exit")) {
                System.out.println("Выход из программы.");
                break;
            }

            if (expression.isEmpty()) {
                continue;
            }

            try {
                double result = calculator.evaluate(expression);
                System.out.printf("Результат: %.6f\n\n", result);
            } catch (IllegalArgumentException e) {
                System.err.println("Ошибка: " + e.getMessage() + "\n");
            } catch (ArithmeticException e) {
                System.err.println("Математическая ошибка: " + e.getMessage() + "\n");
            } catch (Exception e) {
                System.err.println("Неожиданная ошибка: " + e.getMessage() + "\n");
            }
        }

        scanner.close();
    }
}