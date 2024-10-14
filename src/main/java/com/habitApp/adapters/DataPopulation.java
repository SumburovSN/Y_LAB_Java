package com.habitApp.adapters;

import com.habitApp.entities.Client;
import com.habitApp.entities.Habit;
import com.habitApp.entities.User;
import com.habitApp.usecases.ClientProfile;
import com.habitApp.usecases.UsersList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import static com.habitApp.adapters.UserListHandler.infoLogin;

public class DataPopulation {
    static String[][] persons = new String[][] {
            {"Иван", "ivan@mail.com", "123"},
            {"Петр", "petr@mail.com", "123"},
            {"Елена", "elena@mail.com", "324"},
            // одинаковый email и пароль (не должен быть зарегистрирован)
            {"Вова", "petr@mail.com", "123"},
            // одинаковый email, но пароль другой (должен быть зарегистрирован)
            {"Вова2", "petr@mail.com", "312"},
            {"Максим", "max@mail.com", "123"},
            {"Клим", "klim@mail.com", "312"},
    };
    static String[][] habits = new String[][] {
            {"Бег", "Утренняя пробежка", "1"},
            {"Закаливание", "Холодный душ", "1"},
            {"Чтение", "20 страниц в день мировой литературы", "1"},
            {"Баня", "Парилка, бассейн, массаж", "2"},
            {"Поход", "Однодневный поход без ночевки", "2"},
            {"Английский", "Занятия по английскому с преподавателем", "2"}
    };
    static String[][] executions = new String[][] {
            {"01-10-2024", "02-10-2024","03-10-2024","04-10-2024","05-10-2024","07-10-2024","08-10-2024","09-10-2024",},
            {"01-10-2024", "02-10-2024","03-10-2024","04-10-2024","05-10-2024","06-10-2024","07-10-2024","08-10-2024",},
            {"01-10-2024", "02-10-2024","03-10-2024", "08-10-2024","09-10-2024",},
            {"01-09-2024", "12-09-2024","19-09-2024","20-09-2024","05-10-2024"},
            {"01-09-2024", "08-09-2024","15-09-2024","22-09-2024","07-10-2024"},
            {"01-09-2024", "01-10-2024","02-10-2024","05-10-2024","06-10-2024"},
    };

    public static void populateSystemWithData(UsersList users) {
        // Добавляем Администратора. Его id = 0
        User Sergey = new User("Сергей", "sergey@mail.com", "123");
        users.addAdmin(Sergey);

        for (String[] person: persons) {
            try {
                users.registerClient(person[0], person[1], person[2]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        for (User user: users.getUsers()) {
            System.out.printf("Пользователь: %s; email: %s; блокировка: %s \n", user.getName(), user.getEmail(),
                    users.isBlocked(user));
        }

        for (int i=1; i<users.getUsers().size(); i++) {
            Client activeClient = null;
            try {
                activeClient = (Client) users.getUser(i);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            assert activeClient != null;
            activeClient.addHabit(habits[i-1][0], habits[i-1][1], Integer.parseInt(habits[i-1][2]));
            for (String dateToParse: executions[i-1]) {
                LocalDate date = getDate(dateToParse);
                activeClient.getHabitForIndex(0).getExecutions().add(date);
            }
        }

        for (int i=1; i<users.getUsers().size(); i++) {
            Client activeClient = null;
            try {
                activeClient = (Client) users.getUser(i);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            assert activeClient != null;
            System.out.printf("Пользователь: %s; email: %s; блокировка: %s \n", activeClient.getName(),
                    activeClient.getEmail(), users.isBlocked(activeClient));
            for (Habit habit : activeClient.getHabits()) {
                System.out.printf(" - %s : %s : %s \n", habit.getName(), habit.getDescription(), habit.getFrequency());
                for (LocalDate date : habit.getExecutions()) {
                    System.out.printf(date + " ");
                }
                System.out.println();
            }
        }

        playWithLogin (users);
        playWithProfile(users);
    }
    static void playWithLogin (UsersList users) {
        infoLogin(users);

        for (int i=0; i<persons.length; i++ ) {
            try {
                users.logout(users.getUserId(persons[i][1], persons[i][2]));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        infoLogin(users);
        try {
            users.authenticate("max@mail.com", "123");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        infoLogin(users);
    }

    static void playWithProfile(UsersList users) {
        int clientId = users.getUserId("max@mail.com", "123");

        ClientProfile profile = new ClientProfile(clientId, users);
        String name = "Maxim2";
        String email = "maxim2@mail.com";
        String password = "0099";
        System.out.println("Редактирование профиля");
        System.out.println("Блокировка клиента");
        User user = null;
        try {
            user = users.getUser(clientId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        users.blockUser(user);
        profile.editClientProfile(name, email, password);
        System.out.println("Разблокировка клиента");
        users.unBlockUser(user);
        profile.editClientProfile(name, email, password);

        Client client = (Client) user;

        System.out.printf("Пользователь: %s; email: %s; пароль: %s \n", client.getName(), client.getEmail(),
                client.getPassword());
        try {
            users.logout(clientId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static LocalDate getDate(String dateToParse) {
        LocalDate date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Формат даты

        try {
            date = LocalDate.parse(dateToParse, formatter); // Преобразование строки в дату
            return date;
        } catch (Exception e) {
            System.out.println("Некорректный ввод даты");
            System.out.println("Будет введена текущая дата");
            return LocalDate.now();
        }
    }

    private static LocalDate getRandomDate() {
        // Диапазон дат
        LocalDate startDate = LocalDate.of(2024, 8, 1); // Начальная дата
        LocalDate endDate = LocalDate.of(2024, 10, 10); // Конечная дата

        // Генерация случайной даты в диапазоне
        long start = startDate.toEpochDay(); // Преобразуем начальную дату в дни с начала эпохи
        long end = endDate.toEpochDay(); // Преобразуем конечную дату в дни с начала эпохи
        long randomEpochDay = ThreadLocalRandom.current().nextLong(start, end + 1); // Случайное число между start и end

        return LocalDate.ofEpochDay(randomEpochDay); // Преобразуем обратно в LocalDate и возвращаем
    }
}
