package com.habitApp.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    public void testClientConstructor() {
        Client client = new Client("John Doe", "john@example.com", "password123");
        assertEquals("John Doe", client.getName());
        assertEquals("john@example.com", client.getEmail());
        assertEquals("password123", client.getPassword());
    }

    @Test
    void getHabits() {
        Client client = new Client("John Doe", "john@example.com", "password123");
        ArrayList<Habit> test = new ArrayList<>();
        assertInstanceOf(test.getClass(), client.getHabits());
        Habit habit = new Habit("Jogging", "Jogging in Park", 1);
        client.addHabit("Jogging", "Jogging in Park", 1);
        ArrayList<Habit> expectation = new ArrayList<>();
        expectation.add(habit);
        for (int i=0; i<client.getHabits().size(); i++) {
            assertEquals(expectation.get(i).getName(), client.getHabits().get(i).getName());
        }
    }

    @Test
    void addHabit() {
        Client client = new Client("John Doe", "john@example.com", "password123");
        client.addHabit("Jogging", "Jogging in Park", 1);
        assertEquals(1, client.getHabits().size());
    }

    @Test
    void getHabitForIndex() {
        Client client = new Client("John Doe", "john@example.com", "password123");
        Habit habit = new Habit("Jogging", "Jogging in Park", 1);
        client.addHabit("Jogging", "Jogging in Park", 1);
        assertEquals(habit.getName(), client.getHabitForIndex(0).getName());
    }

    @Test
    void getHabitForName() {
        Client client = new Client("John Doe", "john@example.com", "password123");
        Habit habit = new Habit("Jogging", "Jogging in Park", 1);
        client.addHabit("Jogging", "Jogging in Park", 1);
        assertEquals(habit.getName(), client.getHabitForName("Jogging").getName());
    }

    @Test
    void deleteHabit() {
        Client client = new Client("John Doe", "john@example.com", "password123");
        client.addHabit("Jogging", "Jogging in Park", 1);
        client.addHabit("Jogging", "Jogging in Park", 1);
        client.deleteHabit(1);
        assertEquals(1, client.getHabits().size());
    }

    @Test
    void clearHabits() {
        Client client = new Client("John Doe", "john@example.com", "password123");
        client.addHabit("Jogging", "Jogging in Park", 1);
        client.deleteHabit(0);
        client.clearHabits();
        assertEquals(0, client.getHabits().size());
    }
}