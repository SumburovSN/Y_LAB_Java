package com.habitApp.entities;

import java.time.LocalDate;
import java.util.TreeSet;

public class Habit {
    private String name;
    private String description;
    private enum Frequency {DAILY, WEEKLY};
    private Frequency frequency;
    private final TreeSet<LocalDate> executions = new TreeSet<>();

    public Habit(String name, String description, int frequency) {
        this.name = name;
        this.description = description;
        try {
            this.frequency = setFrequency(frequency);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Frequency setFrequency(int enumId) throws Exception {
        if (enumId == 1) {return Frequency.DAILY;}
        else if (enumId == 2) {return Frequency.WEEKLY;}
        else {throw new Exception("Некорректный код для частоты привычки");}
    }

    public String getFrequency() {
        if (frequency == Frequency.DAILY) {return "ежедневно";}
        else {return "еженедельно";}
    }

    public TreeSet<LocalDate> getExecutions() {
        return executions;
    }

    public void clearExecutions() {executions.clear();}

    public int getMaxIntervalInDays () {
        if (frequency == Frequency.DAILY) {return 1;}
        else {return 7;}
    }
}
