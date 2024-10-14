package com.habitApp.adapters;

import com.habitApp.entities.User;
import com.habitApp.usecases.UsersList;

import java.util.Objects;

import static com.habitApp.adapters.AnaliticsHandler.*;
import static com.habitApp.adapters.HabitHandler.*;
import static com.habitApp.adapters.MenuHandler.getChoiceFromMenu;
import static com.habitApp.adapters.ProfileHandler.*;
import static com.habitApp.adapters.UserListHandler.*;

//Класс для использования в основном приложении
//запускает циклы с меню для работы в консоли
public class AppLoop {
    private final UsersList users;
    private int clientId = -1;

    public AppLoop(UsersList users) {
        this.users = users;
    }

    public void runApp() {
        boolean isGoing = true;
        String[] menu = new String[]{
                "Регистрация нового пользователя",
                "Войти в систему",
                "Выйти из системы",
                "Войти в меню Пользователя",
                "Войти в меню Администратора",
                "Выйти из программы",
        };

        do {
            String choice = getChoiceFromMenu(menu);

            //Регистрация нового пользователя
            if (Objects.equals(choice, "1")) {
                clientId = registerNewClient(users);}
            //Войти в систему
            else if (Objects.equals(choice, "2")) {clientId = loginClient(users);}
            //Выйти из системы
            else if (Objects.equals(choice, "3")) {
                if (clientId == -1) {
                    System.out.println("Необходимо войти в систему");
                }
                else {
                    logoutClient(users, clientId);
                    clientId = -1;
                }
            }
            //Войти в меню Пользователя
            else if (Objects.equals(choice, "4")) {
                if (clientId == -1) {System.out.println("Необходимо войти в систему");}
                else if (isUserBlocked(clientId, users)) {System.out.println("Пользователь заблокирован");}
                else {runUserSession();}
            }
            //Войти в меню Администратора
            else if (Objects.equals(choice, "5")) {
                User user = null;
                try {
                    user = users.getUser(clientId);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                if (clientId == -1) {System.out.println("Необходимо войти в систему");}
                else if (!users.isAdmin(user)){System.out.println("Пользователь не имеет прав Администратора");}
                else {runAdminSession();}
            }
            //Выйти из программы
            else isGoing = false;
        } while (isGoing);
    }

    private void runAdminSession() {
        boolean isGoing = true;
        String[] menu = new String[]{
                "Список пользователей",
                "Блокировка пользователя",
                "Разблокировка пользователя",
                "Удаление пользователя",
                "Предыдущее меню",
        };

        do {
            String choice = getChoiceFromMenu(menu);

            //Список пользователей
            if (Objects.equals(choice, "1")) {
                infoUsers(users);
            }
            //Блокировка пользователя
            else if (Objects.equals(choice, "2")) {
                blockUser(users);
            }
            //Разблокировка пользователя
            else if (Objects.equals(choice, "3")) {
                unBlockUser(users);
            }
            //Удаление пользователя
            else if (Objects.equals(choice, "4")) {
                deleteUser(users);
            }
            //Предыдущее меню
            else isGoing = false;
        } while (isGoing);
    }

    void runUserSession() {
        boolean isGoing = true;
        String[] menu = new String[]{
                "Личный кабинет",
                "Привычки",
                "Предыдущее меню",
        };

        do {
            String choice = getChoiceFromMenu(menu);

            //Личный кабинет
            if (Objects.equals(choice, "1")) {
                runProfileSession();
                if (clientId == -1) {isGoing = false;}
            }
            //Привычки
            else if (Objects.equals(choice, "2")) {
                runHabitsSession();
            }
            //Предыдущее меню
            else isGoing = false;
        } while (isGoing);
    }

    void runProfileSession() {
        boolean isGoing = true;
        String[] menu = new String[]{
                "Редактирование профиля клиента",
                "Смена пароля",
                "Удаление аккаунта",
                "Предыдущее меню",
        };

        do {
            String choice = getChoiceFromMenu(menu);

            //Редактирование профиля клиента
            if (Objects.equals(choice, "1")) {editProfile(users, clientId);}
            //Смена пароля
            else if (Objects.equals(choice, "2")) {changePassword(users, clientId);}
            //Удаление аккаунта
            else if (Objects.equals(choice, "3"))
            {
                if (deleteAccount(users, clientId)) {
                    clientId = -1;
                    isGoing = false;
                }
            }
            //Предыдущее меню
            else isGoing = false;
        } while (isGoing);
    }

     void runHabitsSession() {
        boolean isGoing = true;
        String[] menu = new String[]{
                "Просмотр привычек",
                "Редактирование привычки",
                "Удаление привычки",
                "Создание новой привычки",
                "Отметка о выполнении привычки",
                "Отчет по прогрессу выполнения",
                "Отчет по привычке за период",
                "Аналитика по сериям выполнения привычек",
                "Процент успешного выполнения привычек за определенный период",
                "Предыдущее меню",
        };

        do {
            String choice = getChoiceFromMenu(menu);

            //Просмотр привычек
            if (Objects.equals(choice, "1")) {
                readHabits(clientId, users);
            }
            //Редактирование привычки
            else if (Objects.equals(choice, "2")) {
                if (isEmptyHabitsList(clientId, users)) {System.out.println("Список привычек пуст");}
                else {updateHabit(clientId, users);}
            }
            //Удаление привычки
            else if (Objects.equals(choice, "3")) {
                if (isEmptyHabitsList(clientId, users)) {System.out.println("Список привычек пуст");}
                else {deleteHabit(clientId, users);}
            }
            //Создание новой привычки
            else if (Objects.equals(choice, "4")) {
                createHabit(clientId, users);
            }
            //Отметка о выполнении привычки
            else if (Objects.equals(choice, "5")) {
                markHabitExecution(clientId, users);
            }
            //Отчет по прогрессу выполнения
            else if (Objects.equals(choice, "6")) {
                showHabitsExecutions(clientId, users);
            }
            //Отчет по привычке за период
            else if (Objects.equals(choice, "7")) {
                getGeneralReportForPeriod(clientId, users);
            }
            //Аналитика по сериям выполнения привычек
            else if (Objects.equals(choice, "8")) {
                getReportOfStreaks(clientId, users);
            }
            //Процент успешного выполнения привычек за определенный период
            else if (Objects.equals(choice, "9")) {
                getReportOfSuccess(clientId, users);
            }
            //Предыдущее меню
            else isGoing = false;
        } while (isGoing);
    }
}
