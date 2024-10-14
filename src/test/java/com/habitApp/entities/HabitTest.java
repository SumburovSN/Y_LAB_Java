package com.habitApp.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class HabitTest {

    @Test
    public void testHabitConstructor() {
        Habit habit = new Habit("Jogging", "Jogging in Park", 1);
        assertEquals("Jogging", habit.getName());
        assertEquals("Jogging in Park", habit.getDescription());
        assertEquals("ежедневно", habit.getFrequency());
    }

    @Test
    void getName() {
        Habit habit = new Habit("Jogging", "Jogging in Park", 1);
        assertEquals("Jogging", habit.getName());
    }

    @Test
    void setName() {
        Habit habit = new Habit("Jogging", "Jogging in Park", 1);
        habit.setName("Running");
        assertEquals("Running", habit.getName());
    }

    @Test
    void getDescription() {
        Habit habit = new Habit("Jogging", "Jogging in Park", 1);
        assertEquals("Jogging in Park", habit.getDescription());
    }

    @Test
    void setDescription() {
        Habit habit = new Habit("Jogging", "Jogging in Park", 1);
        habit.setDescription("Jogging in forrest");
        assertEquals("Jogging in forrest", habit.getDescription());
    }

    @Test
    void setFrequency() throws Exception {
        Habit habit = new Habit("Jogging", "Jogging in Park", 1);
        Exception thrown = assertThrows(Exception.class, () -> habit.setFrequency(3));
        // Дополнительно можно проверить сообщение исключения
        assertEquals("Некорректный код для частоты привычки", thrown.getMessage());
        habit.setFrequency(2);
        assertEquals("еженедельно", habit.getFrequency());
    }

    @Test
    void getFrequency() {
        Habit habit = new Habit("Jogging", "Jogging in Park", 2);
        assertEquals("еженедельно", habit.getFrequency());
    }

    @Test
    void setExecution() {
        Habit habit = new Habit("Jogging", "Jogging in Park", 1);
        habit.setExecution(LocalDate.of(2024, 1, 1));
        assertEquals(1, habit.getExecutions().size());
    }

    @Test
    void getExecutions() {
        Habit habit = new Habit("Jogging", "Jogging in Park", 1);
        TreeSet<LocalDate> test = new TreeSet<>();
        assertInstanceOf(test.getClass(), habit.getExecutions());
    }

    @Test
    void clearExecutions() {
        Habit habit = new Habit("Jogging", "Jogging in Park", 1);
        habit.setExecution(LocalDate.of(2024, 1, 1));
        habit.setExecution(LocalDate.of(2024, 1, 2));

        habit.clearExecutions();
        assertEquals(0, habit.getExecutions().size());
    }

}