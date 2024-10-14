package com.habitApp.adapters;

public class MenuHandler {
    static String getChoiceFromMenu(String[] menu) {
        for (int i=0; i<menu.length; i++) {
            System.out.println((i+1) + ". " + menu[i]);
        }
        ConsoleInputHandler console = new ConsoleInputHandler();
        return console.getInput("Ваш выбор: ");
    }
}
