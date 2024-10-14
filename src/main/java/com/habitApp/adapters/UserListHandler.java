package com.habitApp.adapters;

import com.habitApp.entities.User;
import com.habitApp.usecases.UsersList;

public class UserListHandler {
    static void infoLogin(UsersList users) {
        System.out.println("Список пользователей, вошедших в систему:");
        if (users.getLoginUsers().isEmpty()) System.out.println("Список пуст");
        else {
            for (User user : users.getLoginUsers()) {
                System.out.printf(" - %s %s %s \n", user.getName(), user.getEmail(), user.getPassword());
            }
        }
    }

    static int registerNewClient(UsersList users) {
        System.out.println("Регистрация нового клиента:");
        ConsoleInputHandler console = new ConsoleInputHandler();

        String name = console.getInput("Введите имя: ");
        String email = console.getInput("Введите email: ");
        String password = console.getInput("Введите Пароль: ");

        int clientId = -1;

        try {
            users.registerClient(name, email, password);
            clientId = users.getUserId(email, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return clientId;
    }

    static int loginClient(UsersList users) {
        System.out.println("Вход клиента в систему:");
        ConsoleInputHandler console = new ConsoleInputHandler();

        String email = console.getInput("Введите email: ");
        String password = console.getInput("Введите Пароль: ");
        int clientId = -1;

        try {
            users.authenticate(email, password);
            clientId = users.getUserId(email, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return clientId;
    }

    static void logoutClient(UsersList users, int clientId) {
        System.out.println("Выход клиента из системы:");

        try {
            users.logout(clientId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void infoUsers(UsersList users) {
        System.out.println("Список зарегистрированных пользователей:");
        if (users.getUsers().isEmpty()) System.out.println("Список пуст");
        else {
            for (User user : users.getUsers()) {
                System.out.printf(" - id: %d %s %s %s блокировка: %s \n", users.getUserId(user.getEmail(), user.getPassword()),
                        user.getName(), user.getEmail(), user.getPassword(), users.isBlocked(user));
            }
        }
    }

    static void blockUser(UsersList users) {
        System.out.println("Блокировка пользователя:");
        infoUsers(users);
        ConsoleInputHandler console = new ConsoleInputHandler();
        int id = console.getInteger("Введите id пользователя: ");
        try {
            User chosenUser = users.getUser(id);
            if (users.blockUser(chosenUser)) {
                System.out.printf("Пользователь: %s успешно заблокирован \n", chosenUser.getEmail());}
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void unBlockUser(UsersList users) {
        System.out.println("Разблокировка пользователя:");
        infoUsers(users);
        ConsoleInputHandler console = new ConsoleInputHandler();
        int id = console.getInteger("Введите id пользователя: ");
        try {
            User chosenUser = users.getUser(id);
            if (users.unBlockUser(chosenUser)) {
                System.out.printf("Пользователь: %s успешно разблокирован \n", chosenUser.getEmail());}
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    static void deleteUser(UsersList users) {
        System.out.println("Удаление пользователя:");
        infoUsers(users);
        ConsoleInputHandler console = new ConsoleInputHandler();
        int id = console.getInteger("Введите id пользователя: ");
        try {
            User chosenUser = users.getUser(id);
            if (users.deleteUser(chosenUser)) {
                System.out.printf("Пользователь: %s успешно удален \n", chosenUser.getEmail());}
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static boolean isUserBlocked(int userId, UsersList users) {
        try {
            return users.isBlocked(users.getUser(userId));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
