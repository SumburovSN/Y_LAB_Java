package com.habitApp.adapters;

import com.habitApp.entities.Client;
import com.habitApp.entities.Habit;
import com.habitApp.usecases.HabitAnalitics;
import com.habitApp.usecases.HabitManagement;
import com.habitApp.usecases.UsersList;

import java.time.LocalDate;

import static com.habitApp.adapters.HabitHandler.chooseHabit;
import static com.habitApp.adapters.HabitHandler.isEmptyHabitsList;

public class AnaliticsHandler {

    public static void getGeneralReportForPeriod(int clientId, UsersList users) {
        System.out.println("Подготовка статистики выполнения привычки за период:");
        int indexHabit = chooseHabit(clientId, users);
        ConsoleInputHandler console = new ConsoleInputHandler();
        LocalDate startDate = console.getDate("Введите начальную дату в формате \"dd-MM-yyyy\": ");
        LocalDate endDate = console.getDate("Введите конечную дату в формате \"dd-MM-yyyy\": ");

        HabitAnalitics analytics = new HabitAnalitics(clientId, users);
        if (!analytics.isIntervalValid(startDate, endDate)) {
            System.out.println("Временной интервал должен быть более 7 дней");
            return;
        }
        System.out.println(analytics.getReportForPeriod(indexHabit, startDate, endDate));
    }

    public static void getReportOfStreaks(int clientId, UsersList users) {
        System.out.println("Подготовка статистики выполнения привычек");
        HabitAnalitics analytics = new HabitAnalitics(clientId, users);
        System.out.println(analytics.getReportOnStreaks());
    }

    public static void getReportOfSuccess(int clientId, UsersList users) {
        System.out.println("Подсчет процент успешного выполнения привычек за определенный период:");
        ConsoleInputHandler console = new ConsoleInputHandler();
        LocalDate startDate = console.getDate("Введите начальную дату в формате \"dd-MM-yyyy\": ");
        LocalDate endDate = console.getDate("Введите конечную дату в формате \"dd-MM-yyyy\": ");

        HabitAnalitics analytics = new HabitAnalitics(clientId, users);
        if (!analytics.isIntervalValid(startDate, endDate)) {
            System.out.println("Временной интервал должен быть более 7 дней");
            return;
        }
        System.out.println(analytics.getReportOnSuccess(startDate, endDate));
    }




}
