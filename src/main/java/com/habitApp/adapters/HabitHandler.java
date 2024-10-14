package com.habitApp.adapters;

import com.habitApp.entities.Client;
import com.habitApp.entities.Habit;
import com.habitApp.usecases.HabitManagement;
import com.habitApp.usecases.UsersList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;

public class HabitHandler {
    static void readHabits(int clientId, UsersList users) {
        HabitManagement management = new HabitManagement(clientId, users);
        if (management.getHabits().isEmpty()) {
            System.out.println("Список привычек пуст");
        } else {
            int i = -1;
            for (Habit habit: management.getHabits()) {
                i++;
                System.out.printf("%d. %s %s %s \n", i, habit.getName(), habit.getDescription(), habit.getFrequency());
            }
        }
    }

    static int chooseHabit(int clientId, UsersList users) {
        System.out.println("Выбор привычки: ");
        readHabits(clientId, users);
        ConsoleInputHandler console = new ConsoleInputHandler();
        return console.getInteger("Введите номер выбранной привычки: ");
    }

    static void updateHabit(int clientId, UsersList usersList) {
        HabitManagement management = new HabitManagement(clientId, usersList);
        System.out.println("Редактирование привычки");
        int indexHabit = chooseHabit(clientId, usersList);
        ConsoleInputHandler console = new ConsoleInputHandler();

        String newName = console.getInput("Введите новое имя привычки: ");
        String newDescription = console.getInput("Введите новое описание привычки: ");
        int newFrequency = console.getInteger("Введите периодичность (ежедневно = 1, еженедельно = 2): ");

        management.editHabit(indexHabit ,newName, newDescription, newFrequency);
    }

    static void createHabit(int clientId, UsersList users) {
        HabitManagement management = new HabitManagement(clientId, users);
        System.out.println("Создание новой привычки");

        ConsoleInputHandler console = new ConsoleInputHandler();

        String name = console.getInput("Введите имя новой привычки: ");
        String description = console.getInput("Введите описание новой привычки: ");
        int frequency = console.getInteger("Введите периодичность (ежедневно = 1, еженедельно = 2): ");

        management.addHabit(name, description, frequency);
    }

    static void deleteHabit(int clientId, UsersList users) {
        HabitManagement management = new HabitManagement(clientId, users);
        System.out.println("Удаление привычки");
        int indexHabit = chooseHabit(clientId, users);
        management.deleteHabit(indexHabit);
    }

    static void markHabitExecution(int clientId, UsersList users) {
        HabitManagement management = new HabitManagement(clientId, users);
        System.out.println("Отметить выполнение привычки");
        int indexHabit = chooseHabit(clientId, users);
        ConsoleInputHandler console = new ConsoleInputHandler();
        LocalDate date = console.getDate("Введите дату в формате \"dd-MM-yyyy\": ");
        management.markHabitExecution(management.getHabit(indexHabit), date);
    }

    static void showHabitsExecutions(int clientId, UsersList usersList) {
        HabitManagement management = new HabitManagement(clientId, usersList);
        System.out.println("Просмотр привычек и их выполнения:");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Формат даты

        for (Habit habit: management.getHabits()) {
            System.out.printf("Привычка %s %s %s \n", habit.getName(), habit.getDescription(), habit.getFrequency());
            TreeSet<LocalDate> executions = management.getHabitExecutions(habit);
            if (executions.isEmpty()) {System.out.println("Статистика выполнения пуста");}
            else {
                System.out.println("Статистика выполнения привычки:");
                for (LocalDate date : executions) {
                    System.out.print(formatter.format(date) + ". ");
                }
                System.out.println();
            }
        }
    }

    static boolean isEmptyHabitsList(int clientId, UsersList users){
        Client client = null;
        try {
            client = (Client) users.getUser(clientId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assert client != null;
        return client.getHabits().isEmpty();
    }

}
