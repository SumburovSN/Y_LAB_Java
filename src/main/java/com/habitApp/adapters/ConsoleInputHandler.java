package com.habitApp.adapters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

//Адаптер для корректной обработки ввода в консоль
public class ConsoleInputHandler {
    private Scanner scanner = new Scanner(System.in);

    public String getInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public int getInteger(String prompt) {
        System.out.println(prompt);
        int number;
        try {
            number = scanner.nextInt();
            return number;
        } catch (Exception e) {
            System.out.println("Некорректный ввод. Будет введен 0");
            return 0;
        }
    }

    public LocalDate getDate(String prompt) {
        System.out.println(prompt);
        LocalDate date;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Формат даты

        try {
            date = LocalDate.parse(scanner.nextLine(), formatter); // Преобразование строки в дату
            System.out.println("Дата: " + date);
            return date;
        } catch (Exception e) {
            System.out.println("Некорректный ввод даты");
            System.out.println("Будет введена текущая дата");
            return LocalDate.now();
        }
    }

}