package com.habitApp.usecases;

import com.habitApp.entities.Client;

import java.util.Objects;


public class ClientProfile {
    Client client;
    int clientId;
    UsersList users;

    public ClientProfile(int clientId, UsersList users) {
        this.clientId = clientId;
        this.users = users;
        try {
            this.client = (Client) users.getUser(clientId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void editClientProfile(String newName, String NewEmail, String newPassword) {
        try {
            clientBlocking();
            users.isClientProfileValid(newName, NewEmail, newPassword);
            client.setName(newName);
            client.setEmail(NewEmail);
            client.setPassword(newPassword);
            System.out.println("Новый профиль сохранен успешно");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void changePassword(String password, String newPassword) throws Exception {
        // проверка на блокировку
        try {
            clientBlocking();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // проверка действующего пароля
        if (!Objects.equals(client.getPassword(), password)) throw new Exception("Неверный пароль");
        // проверка нового пароля на валидность
        else if (users.isPasswordNotValid(newPassword)) throw new Exception("Пароль не может быть пустым");
        // проверка на уникальность email и нового пароля
        // не может быть два пользователя с одинаковым email и паролем
        // на один и тот же email можно зарегистрировать другого пользователя при условии другого пароля
        else if (users.getUser(client.getEmail(), newPassword) != null) throw new Exception("Пароль уже существует");
        else {
            client.setPassword(newPassword);
            System.out.println("Новый пароль успешно сохранен");
        }
    }

    public void deleteAccount() {
        try {
            clientBlocking();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Аккаунт НЕ удален");
        }
        client.clearHabits();
        users.deleteUser(client);
        System.out.println("Аккаунт успешно удален");
    }

    public void clientBlocking() throws Exception {
        if (users.isBlocked(client)) throw new Exception("Пользователь заблокирован");
    }
}
