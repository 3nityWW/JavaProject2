package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class TaskTest {

    @Test
    public void testSimpleExpression() {
        assertEquals(7, Task.evaluate("3 + 4"), 0.001);
    }

    @Test
    public void testComplexExpression() {
        assertEquals(14, Task.evaluate("(3 + 4) * 2"), 0.001);
    }

    @Test
    public void testDivision() {
        assertEquals(2.5, Task.evaluate("5 / 2"), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidExpression() {
        Task.evaluate("3 + ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDivisionByZero() {
        Task.evaluate("5 / 0");
    }
}