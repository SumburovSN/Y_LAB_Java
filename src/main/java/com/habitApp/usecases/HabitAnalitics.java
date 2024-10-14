package com.habitApp.usecases;

import com.habitApp.entities.Habit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.TreeSet;

public class HabitAnalitics {
    private final UsersList users;
    private final int clientId;
    // Считать номера дней и недель будем от 01.01.2024. Это как раз понедельник
    private final LocalDate BASE_DATE = LocalDate.of(2024, 1, 1);


    public HabitAnalitics(int clientId, UsersList users) {
        this.users = users;
        this.clientId = clientId;
    }

    public String getReportForPeriod(int indexHabit, LocalDate startDate, LocalDate endDate) {
        HabitManagement management = new HabitManagement(clientId, users);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Habit habit = management.getHabit(indexHabit);
        String report = "Привычка: " + habit.getName() + " с периодичностью: " + habit.getFrequency() +
                " за период с " + formatter.format(startDate) + " по " + formatter.format(startDate) + "\n";

        //делаем выборку за период startDate - endDate
        ArrayList<LocalDate> executionsForPeriod = new ArrayList<>();
        for (LocalDate date: management.getHabitExecutions(habit)) {
            if ((date.compareTo(startDate) >= 0) & (date.compareTo(endDate) <= 0)) {
                executionsForPeriod.add(date);
            }
        }
        report += "Количество дней: " + executionsForPeriod.size() + ": \n";
        if (!executionsForPeriod.isEmpty()) {
            for (LocalDate date: executionsForPeriod) {
                report += formatter.format(date) + "; ";
            }
            report = report.substring(0, report.length() - 2) + "\n";
        }
        return report;
    }

    public String getReportOnStreaks() {
        StringBuilder report = new StringBuilder();
        HabitManagement management = new HabitManagement(clientId, users);
        for (Habit habit: management.getHabits()) {
            report.append(getHabitStreak(habit));
        }
        return report.toString();
    }

    public String getHabitStreak(Habit habit) {
        // список порядковых номеров периодов передаем в метод для составления серий
        ArrayList<Integer> streaks = getListOfStreaks(getOrdinalListFromDates(habit));
        String report = "Отчет по выполнению привычки " + habit.getName() + "\n";
        report += "Ваша текущая серия: " + streaks.get(streaks.size()-1) + "\n";
        Collections.sort(streaks);
        report += "Ваша лучшая серия: " + streaks.get(streaks.size()-1) + "\n";
        return report;
    }

    //для унификации вычислений преобразим даты в порядковые номера дней или месяцев,
    //отсчитывая от BASE_DATE - 01.01.2024, понедельника
    private TreeSet<Long> getOrdinalListFromDates(Habit habit) {
        //для уникальности выберем сортируемый список
        TreeSet<Long> intSeries = new TreeSet<>();
        if (Objects.equals(habit.getFrequency(), "ежедневно")) {
            for (LocalDate date: habit.getExecutions()){
                intSeries.add(ChronoUnit.DAYS.between(BASE_DATE, date));
            }
        } else if (Objects.equals(habit.getFrequency(), "еженедельно")){
            // для подсчета еженедельной не важно, сколько раз пользователь занимался в течение недели
            for (LocalDate date: habit.getExecutions()){
                intSeries.add(ChronoUnit.WEEKS.between(BASE_DATE, date));
            }
        }
        return intSeries;
    }

    private ArrayList<Integer> getListOfStreaks(TreeSet<Long> intSeries) {
        ArrayList<Integer> listOfStreaks = new ArrayList<>();
        ArrayList<Long> streak = new ArrayList<>();
        for (Long i: intSeries){
            if (streak.isEmpty()) {streak.add(i);}
            else {
                if ((i - streak.get(streak.size()-1) == 1)) {streak.add(i);}
                else {
                    listOfStreaks.add(streak.size());
                    streak.clear();
                    streak.add(i);
                }
            }
        }
        return listOfStreaks;
    }

    public String getReportOnSuccess(LocalDate startDate, LocalDate endDate) {
        StringBuilder report = new StringBuilder();
        HabitManagement management = new HabitManagement(clientId, users);
        for (Habit habit: management.getHabits()) {
            report.append(getSuccessForPeriod(habit, startDate, endDate));
        }
        return report.toString();
    }

    //Процент успешного выполнения привычек за определенный период будем считать как отношение
    //успешных интервалов к общему количеству интервалов

    public String getSuccessForPeriod(Habit habit, LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String report = "Привычка: " + habit.getName() + " с периодичностью: " + habit.getFrequency() +
                " за период с " + formatter.format(startDate) + " по " + formatter.format(endDate) + "\n";

        //делаем выборку за период startDate - endDate
        ArrayList<LocalDate> executionsForPeriod = new ArrayList<>();
        for (LocalDate date: habit.getExecutions()) {
            if ((date.compareTo(startDate) >= 0) & (date.compareTo(endDate) <= 0)) {
                executionsForPeriod.add(date);
            }
        }
        report += "Количество дней: " + executionsForPeriod.size() + ": \n";
        //Воспользуемся перечнем серий, передав результаты выборки
        // список порядковых номеров периодов передаем в метод для составления серий
        ArrayList<Integer> streaks = getListOfStreaks(getOrdinalListFromDates(habit));
        int successInterval = 0;
        for (int i: streaks) {successInterval += i;};
        int amountInterval = getAmountInterval(startDate, endDate, habit.getFrequency());

        float success = (float) successInterval /amountInterval;

        report+= "Процент успешного выполнения привычек за определенный период: " +
                String.format("%.2f%%", success * 100) + "\n";
        return report;
    }

    private int getAmountInterval(LocalDate startDate, LocalDate endDate, String habitFrequency) {
        if (Objects.equals(habitFrequency, "ежедневно")) {
            return (int) ChronoUnit.DAYS.between(startDate, endDate);
        } else {
            return (int) ChronoUnit.WEEKS.between(startDate, endDate);
        }
    }

    public boolean isIntervalValid(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate) > 7;
    }


}
