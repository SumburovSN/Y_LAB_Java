package com.habitApp.usecases;

import com.habitApp.entities.Client;
import com.habitApp.entities.Habit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeSet;

public class HabitManagement {
    UsersList users;
    Client client;
    int clientId = -1;

    public HabitManagement(int clientId, UsersList users) {
        this.users = users;
        this.clientId = clientId;
        try {
            client = (Client) users.getUser(clientId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void editHabit(int habitIndex, String newName, String newDescription, int newFrequency){
        Habit habit = getHabit(habitIndex);
        habit.setName(newName);
        habit.setDescription(newDescription);
        try {
            habit.setFrequency(newFrequency);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Новые параметры привычки успешно сохранены");
    }

    public ArrayList<Habit> getHabits() {
        return client.getHabits();
    }

    public Habit getHabit(int habitIndex) {
        return client.getHabitForIndex(habitIndex);
    }

    public void deleteHabit(int habitIndex) {
        client.deleteHabit(habitIndex);
        System.out.println("Привычка успешно удалена");
    }

    public void addHabit(String name, String description, int frequency){
        client.addHabit(name, description, frequency);
        System.out.println("Новая привычка успешно сохранена");
    }

    public void markHabitExecution(Habit habit, LocalDate date){
        getHabitExecutions(habit).add(date);
    }

    public TreeSet<LocalDate> getHabitExecutions(Habit habit) {
        return habit.getExecutions();
    }
}
