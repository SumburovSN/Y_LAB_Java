package com.habitApp.usecases;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientProfileTest {

    @Test
    public void testClientProfileConstructor() throws Exception {
        UsersList users = new UsersList();
        users.registerClient("John Doe", "john@example.com", "password123");
        ClientProfile profile = new ClientProfile(0, users);
        assertEquals("John Doe", users.getUser(0).getName());
        assertEquals("john@example.com", users.getUser(0).getEmail());
        assertEquals("password123", users.getUser(0).getPassword());
    }

    @Test
    void editClientProfile() throws Exception {
        UsersList users = new UsersList();
        users.registerClient("John Doe", "john@example.com", "password123");
        ClientProfile profile = new ClientProfile(0, users);
        profile.editClientProfile("John Rambo", "rambo@mail.com", "new_blood");
        assertEquals("John Rambo", users.getUser(0).getName());
        assertEquals("rambo@mail.com", users.getUser(0).getEmail());
        assertEquals("new_blood", users.getUser(0).getPassword());
    }

    @Test
    void changePassword() throws Exception {
        UsersList users = new UsersList();
        users.registerClient("John Doe", "john@example.com", "password123");
        ClientProfile profile = new ClientProfile(0, users);
        Exception thrown = assertThrows(Exception.class, () ->
                profile.changePassword("incorrect", "new_blood"));
        assertEquals("Неверный пароль", thrown.getMessage());
        profile.changePassword("password123", "new_blood");
        assertEquals("new_blood", profile.client.getPassword());
    }

    @Test
    void deleteAccount() throws Exception {
        UsersList users = new UsersList();
        users.registerClient("John Doe", "john@example.com", "password123");
        ClientProfile profile = new ClientProfile(0, users);
        profile.deleteAccount();
        assertEquals(0, users.getUsers().size());
    }

    @Test
    void clientBlocking() throws Exception {
        UsersList users = new UsersList();
        users.registerClient("John Doe", "john@example.com", "password123");
        ClientProfile profile = new ClientProfile(0, users);
        users.blockUser(profile.client);
        Exception thrown = assertThrows(Exception.class, profile::clientBlocking);
        assertEquals("Пользователь заблокирован", thrown.getMessage());
    }
}