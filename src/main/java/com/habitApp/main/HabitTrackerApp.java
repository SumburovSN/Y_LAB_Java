package com.habitApp.main;

import com.habitApp.adapters.AppLoop;
import com.habitApp.usecases.Notifications;
import com.habitApp.usecases.UsersList;


import static com.habitApp.adapters.DataPopulation.populateSystemWithData;

//Основное приложение для запуска
public class HabitTrackerApp {
    public static void main(String[] args) {
        UsersList users = new UsersList();
        populateSystemWithData(users);

        //Запуск консольного приложения
        AppLoop appLoop = new AppLoop(users);
        appLoop.runApp();

        //Под конец запускаем эмуляцию отправки уведомлений
        Notifications alarm = new Notifications(users);
        alarm.generalMailing();

    }
}
