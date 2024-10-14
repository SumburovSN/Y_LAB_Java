package com.habitApp.adapters;

import com.habitApp.entities.Client;
import com.habitApp.usecases.ClientProfile;
import com.habitApp.usecases.UsersList;

import java.util.Objects;

public class ProfileHandler {
    static void editProfile(UsersList users, int clientId) {
        ClientProfile profile = new ClientProfile(clientId, users);

        System.out.println("Редактирование профиля");
        ConsoleInputHandler console = new ConsoleInputHandler();

        String newName = console.getInput("Введите новое имя: ");
        String newEmail = console.getInput("Введите новый email: ");
        String newPassword = console.getInput("Введите новый пароль: ");

        profile.editClientProfile(newName, newEmail, newPassword);
    }

    static boolean deleteAccount(UsersList users, int clientId) {
        ConsoleInputHandler console = new ConsoleInputHandler();
        String password = console.getInput("Введите пароль для подтверждения удаления: ");
        Client client = null;
        try {
            client = (Client) users.getUser(clientId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assert client != null;
        if (!Objects.equals(client.getPassword(), password)) {
            System.out.println("Неверный пароль");
            System.out.println("Аккаунт НЕ удален");
            return false;
        }
        ClientProfile profile = new ClientProfile(clientId, users);
        System.out.println("Удаление аккаунта");
        profile.deleteAccount();
        return true;
    }

    static void changePassword(UsersList users, int clientId) {
        ClientProfile profile = new ClientProfile(clientId, users);
        System.out.println("Изменение пароля");
        ConsoleInputHandler console = new ConsoleInputHandler();

        String password = console.getInput("Введите действующий пароль: ");
        String newPassword = console.getInput("Введите новый пароль: ");

        try {
            profile.changePassword(password, newPassword);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
