package com.habitApp.entities;

public class User {
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        // 1. Проверяем равенство ссылок
        if (this == obj) {
            return true;
        }

        // 2. Проверяем, что переданный объект не равен null и является экземпляром класса User
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // 3. Приводим объект к типу User для сравнения полей
        User user = (User) obj;

        // 4. Сравниваем значимые поля
        return email.equals(user.email) && password.equals(user.password);
    }

    // Также нужно перегрузить hashCode() для соблюдения контракта equals() и hashCode()
    @Override
    public int hashCode() {
        int result = password.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
