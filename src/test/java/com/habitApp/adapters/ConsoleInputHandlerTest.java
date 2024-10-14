package com.habitApp.adapters;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class ConsoleInputHandlerTest {

    @Test
    void getInput() {
        // Имитация ввода "test input" через System.in
        String simulatedInput = "test input\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Создаем объект класса, где используется Scanner
        ConsoleInputHandler console = new ConsoleInputHandler();

        // Тестируем метод getInput
        String result = console.getInput("Введите данные:");

        // Сравниваем результат
        assertEquals("test input", result);

        // Восстанавливаем System.in (лучше делать это после теста)
        System.setIn(System.in);
    }

    @Test
    void getInteger() {
        // Имитация ввода "test input" через System.in
        String simulatedInput = "5\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ConsoleInputHandler console = new ConsoleInputHandler();
        int result = console.getInteger("Введите данные:");
        assertEquals(5, result);

        simulatedInput = "Incorrect\n";
        inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        result = console.getInteger("Введите данные:");
        assertEquals(0, result);

        System.setIn(System.in);
    }

    @Test
    void getDate() {
        // Имитация ввода "test input" через System.in
        String simulatedInput = "01-10-2024\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        ConsoleInputHandler console = new ConsoleInputHandler();
        LocalDate result = console.getDate("Введите данные:");
        assertEquals(LocalDate.of(2024, 10, 1), result);

        simulatedInput = "Incorrect\n";
        inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        result = console.getDate("Введите данные:");
        assertEquals(LocalDate.now(), result);

        System.setIn(System.in);
    }
}