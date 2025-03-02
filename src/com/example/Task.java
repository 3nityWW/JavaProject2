package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 * Класс для разбора и вычисления математических выражений.
 * Поддерживает числа, операции (+,-,*,/), скобки, переменные и функции.
 */
public class Task {
    private static final Map<String, Double> variables = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Вычисляет значение выражения.
     *
     * @param expression выражение для вычисления
     * @return результат вычисления
     * @throws IllegalArgumentException если выражение некорректно
     */
    public static double evaluate(String expression) {
        expression = expression.replaceAll("\\s+", ""); // Удаляем пробелы
        return parseExpression(expression);
    }

    /**
     * Разбирает выражение и вычисляет его значение.
     *
     * @param expression выражение для разбора
     * @return результат вычисления
     * @throws IllegalArgumentException если выражение некорректно
     */
    private static double parseExpression(String expression) {
        try {
            return evaluateExpression(expression);
        } catch (Exception e) {
            throw new IllegalArgumentException("Некорректное выражение: " + expression);
        }
    }

    /**
     * Вычисляет значение выражения с учетом приоритетов операций.
     *
     * @param expression выражение для вычисления
     * @return результат вычисления
     */
    private static double evaluateExpression(String expression) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            // Если текущий символ — цифра или точка, извлекаем число
            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder numBuilder = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    numBuilder.append(expression.charAt(i++));
                }
                i--;
                numbers.push(Double.parseDouble(numBuilder.toString()));
            }
            // Если текущий символ — переменная
            else if (Character.isLetter(ch)) {
                StringBuilder varBuilder = new StringBuilder();
                while (i < expression.length() && Character.isLetter(expression.charAt(i))) {
                    varBuilder.append(expression.charAt(i++));
                }
                i--;
                String varName = varBuilder.toString();
                if (!variables.containsKey(varName)) {
                    System.out.print("Введите значение переменной " + varName + ": ");
                    variables.put(varName, scanner.nextDouble());
                }
                numbers.push(variables.get(varName));
            }
            // Если текущий символ — открывающая скобка
            else if (ch == '(') {
                operators.push(ch);
            }
            // Если текущий символ — закрывающая скобка
            else if (ch == ')') {
                while (operators.peek() != '(') {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.pop();
            }
            // Если текущий символ — операция
            else if (isOperator(ch)) {
                while (!operators.isEmpty() && hasPrecedence(ch, operators.peek())) {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.push(ch);
            }
        }

        // Вычисляем оставшиеся операции
        while (!operators.isEmpty()) {
            numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
        }

        return numbers.pop();
    }

    /**
     * Проверяет, является ли символ операцией.
     *
     * @param ch символ
     * @return true, если символ — операция
     */
    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    /**
     * Проверяет приоритет операций.
     *
     * @param op1 первая операция
     * @param op2 вторая операция
     * @return true, если op1 имеет меньший или равный приоритет по сравнению с op2
     */
    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }

    /**
     * Применяет операцию к двум числам.
     *
     * @param op операция
     * @param b  второе число
     * @param a  первое число
     * @return результат операции
     * @throws IllegalArgumentException если операция некорректна
     */
    private static double applyOperation(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new IllegalArgumentException("Деление на ноль");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Некорректная операция: " + op);
        }
    }
}