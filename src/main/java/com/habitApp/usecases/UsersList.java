package com.habitApp.usecases;

import com.habitApp.entities.Client;
import com.habitApp.entities.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class UsersList {
    // общий список пользователей с указанием определенного статуса
    private final ArrayList<User> users = new ArrayList<>();
    // список заблокированных пользователей
    private final HashSet<User> blockedUsers = new HashSet<>();
    // список пользователей, вошедших в систему
    private final HashSet<User> loginUsers = new HashSet<>();
    // список администраторов
    private final ArrayList<User> admins = new ArrayList<>();

    public ArrayList<User> getUsers() {
        return users;
    }

    public void registerClient(String name, String email, String password) throws Exception {
        // проверка данных пользователя на валидность: не нулевая строка с оповещением
        try {
            isClientProfileValid(name, email, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        // проверка на уникальность нового пользователя
        if (getUser(email, password) == null) {
            // создаем нового пользователя-клиента и вносим его в общий список и список, вошедших в систему
            Client client = new Client(name, email, password);
            users.add(client);
            loginUsers.add(client);
            System.out.printf("Пользователь %s %s %s успешно зарегистрирован \n", name, email, password);
        } else throw new Exception("Пользователь с таким же email и паролем уже существует");

    }

    public void isClientProfileValid(String name, String email, String password) throws Exception {
        // проверка данных пользователя на валидность: не нулевая строка с оповещением
        if (isNameNotValid(name)) throw new Exception("Имя не может быть пустым");
        if (isEmailNotValid(email)) throw new Exception("Email не может быть пустым");
        if (isPasswordNotValid(password)) throw new Exception("Пароль не может быть пустым");
    }

    private boolean isNameNotValid(String name) {return Objects.equals(name, "");}

    private boolean isEmailNotValid(String email) {return Objects.equals(email, "");}

    public boolean isPasswordNotValid(String password) {return Objects.equals(password, "");}

    public void authenticate(String email, String password) throws Exception {
        User user = getUser(email, password);
        // Проверяем, зарегистрирован ли пользователь
        if (user == null) {
            throw new Exception("Пользователь в системе еще не зарегистрирован");
            // Проверяем, вошел ли пользователь в систему
        } else if (isAuthenticated(user)) {
            // Не нужно добавлять в список пользователей, вошедших в систему
            // Просто оповещаем
            System.out.printf("Пользователь %s %s %s успешно вошел в систему \n", user.getName(), email, password);


            // Проверяем, заблокирован ли пользователь
        } else if (isBlocked(user)) {
            throw new Exception("Пользователь заблокирован");
        } else {
            // Добавляем в список пользователей, вошедших в систему
            loginUsers.add(user);
            // и оповещаем
            System.out.printf("Пользователь %s %s %s успешно вошел в систему \n", user.getName(), email, password);
        }
    }

    private boolean isAuthenticated(User user) {
        for (User loginedUser: loginUsers) {
            if (loginedUser.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBlocked(User user) {
        for (User blocked: blockedUsers) {
            if (blocked.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAdmin(User user) {
        return admins.contains(user);
    }

    public void logout(int userId) throws Exception {
        User user = getUser(userId);
        // Проверяем, зарегистрирован ли пользователь
        if (user == null) throw new Exception("Пользователь в системе еще не зарегистрирован");
        else {
            // Проверяем, вошел ли пользователь в систему
            if (loginUsers.contains(user)) throw new Exception("Пользователь не входил в систему");
            else {
                loginUsers.remove(user);
                System.out.printf("Пользователь %s %s %s успешно вышел из системы \n", user.getName(), user.getEmail(),
                        user.getPassword());
            }
        }
    }

    public User getUser(String email, String password) {
        for (User user: users) {
            if (Objects.equals(user.getEmail(), email) & (Objects.equals(user.getPassword(), password))) {
                return user;
            }
        }
        return null;
    }

    public User getUser(int id) throws Exception {
        if (id < 0 | id >= users.size()) {throw new Exception("Некорректный id");}
        else return users.get(id);
    }

    public int getUserId(String email, String password) {
        for (User user: users) {
            if (Objects.equals(user.getEmail(), email) & (Objects.equals(user.getPassword(), password))) {
                return users.indexOf(user);
            }
        }
        return -1;
    }

    public void addAdmin(User user) {
        users.add(user);
        admins.add(user);
    }

    public boolean blockUser(User user) {
        if (isAdmin(user)) {
            System.out.println("Администратор не подлежит блокировке");
            return false;
        } else return blockedUsers.add(user);
    }

    public boolean unBlockUser(User user) {
        if (!blockedUsers.contains(user)) {
            System.out.printf("Пользователь %s отсутствует в списке блокировок \n", user.getEmail());
            return false;
        } else return blockedUsers.remove(user);
    }

    public boolean deleteUser(User user) {
        if (isAdmin(user)) {
            System.out.println("Администратор не подлежит удалению");
            //В удалении отказано
            return false;
        } else {
            try {
                users.remove(user);
                loginUsers.remove(user);
                blockedUsers.remove(user);
                //Удален успешно
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                //Сбой
                return false;
            }
        }
    }

    public HashSet<User> getLoginUsers() {
        return loginUsers;
    }

    public HashSet<User> getBlockedUsers() {
        return blockedUsers;
    }
}
