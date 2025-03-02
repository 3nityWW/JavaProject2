package com.example;

import java.util.Scanner;

/**
 * Главный класс для демонстрации работы ExpressionParser.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String expression = scanner.nextLine();

        try {
            double result = Task.evaluate(expression);
            System.out.println("Результат: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}