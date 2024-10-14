package com.habitApp.entities;

import java.util.ArrayList;
import java.util.Objects;

public class Client extends User{
    private ArrayList <Habit> habits = new ArrayList<>();

    public Client(String name, String email, String password) {
        super(name, email, password);
    }

    public ArrayList<Habit> getHabits() {
        return habits;
    }

    public void addHabit(String name, String description, int frequency) {
        habits.add(new Habit(name, description, frequency));
    }

    public Habit getHabitForIndex(int index) {
        return habits.get(index);
    }

    public Habit getHabitForName(String name) {
        for (Habit habit: habits) {
            if (Objects.equals(habit.getName(), name)) {
                return habit;
            }
        }
        return null;
    }

    public void deleteHabit(int index) {
        habits.remove(index);
    }

    public void clearHabits() {
        for (Habit habit: habits) habit.clearExecutions();
        habits.clear();
    }
}
