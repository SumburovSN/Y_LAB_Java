package com.habitApp.usecases;

import com.habitApp.entities.Client;
import com.habitApp.entities.Habit;
import com.habitApp.entities.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import java.util.ArrayList;
import java.util.HashMap;

public class Notifications {
    private final UsersList users;

    public Notifications(UsersList users) {
        this.users = users;
    }

    public void generalMailing() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (User user: users.getUsers()) {
            try {
                Client client = (Client) user;
                for (Habit habit: client.getHabits()) {
                    LocalDate date = habit.getExecutions().last();
                    if (isGap(date, habit.getFrequency())) {
                        sendNotification(client.getEmail(), client.getName(), habit.getName(), habit.getFrequency(),
                                formatter.format(date));
                    }
                }
            } catch (Exception ignored) {}
        }
    }

    private boolean isGap(LocalDate date, String habitFrequency) {
        if (Objects.equals(habitFrequency, "ежедневно")) {
            return ChronoUnit.DAYS.between(date, LocalDate.now()) > 1;
        } else {
            return ChronoUnit.WEEKS.between(date, LocalDate.now()) > 1;
        }
    }

    public void sendNotification(String email, String clientName, String habitName, String habitFrequency,
                                 String lastDate) {
        System.out.println("Send to: " + email);
        System.out.println("Email title: Напоминание о практике привычки");
        System.out.println("Тело сообщения:");
        System.out.println("Уважаемый(ая) " + clientName + "!");
        System.out.println("Вы поставили целью практиковать " + habitName + " " + habitFrequency + ".");
        System.out.println("Последний раз Вы этим занимались " + lastDate + ".");
        System.out.println("Не забудьте, пожалуйста, этим сегодня заняться.");
        System.out.println("С уважением!");
        System.out.println("Ваше HabitTrackingApp");

    }
/* ToDo: для отправки единого письма клиенту по всем привычкам
    public HashMap<ArrayList<String>, ArrayList<ArrayList<String>>> getListForNotifications() {
        HashMap<ArrayList<String>, ArrayList<ArrayList<String>>> notifications = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (User user: users.getUsers()) {
            try {
                Client client = (Client) user;
                ArrayList<ArrayList<String>> allHabitsInfo = new ArrayList<>();
                for (Habit habit: client.getHabits()) {
                    LocalDate date = habit.getExecutions().last();
                    if (isGap(date, habit.getFrequency())) {
                        ArrayList<String> habitInfo = new ArrayList<>();
                        habitInfo.add(habit.getName());
                        habitInfo.add(habit.getFrequency());
                        habitInfo.add(formatter.format(date));
                        allHabitsInfo.add(habitInfo);
                    }
                }
                if (!allHabitsInfo.isEmpty()) {
                    ArrayList<String> clientInfo = new ArrayList<>();
                    clientInfo.add(client.getName());
                    clientInfo.add(client.getEmail());
                    notifications.put(clientInfo, allHabitsInfo);
                }
            } catch (Exception ignored) {}
        }
        return notifications;
    }
    HashMap<ArrayList<String>, ArrayList<ArrayList<String>>> map = alarm.getListForNotifications();
        for (ArrayList<String> mapKey : map.keySet()) {
            System.out.println("Ключ: ");
            for (String keyElement : mapKey) {
                System.out.printf(keyElement + " ");
            }
            System.out.println();
            ArrayList<ArrayList<String>> mapValue = map.get(mapKey);
            System.out.println("Значение: ");
            for (ArrayList<String> valueList : mapValue) {
                for (String valueElement : valueList) {
                    System.out.println(valueElement);
                }
            }
        }
    */
}
