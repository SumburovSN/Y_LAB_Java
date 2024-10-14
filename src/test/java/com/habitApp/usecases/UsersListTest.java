package com.habitApp.usecases;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsersListTest {

    @Test
    public void testUsersListConstructor() throws Exception {
        UsersList users = new UsersList();
        users.registerClient("John Doe", "john@example.com", "password123");
        ClientProfile profile = new ClientProfile(0, users);
        assertEquals("John Doe", users.getUser(0).getName());
        assertEquals("john@example.com", users.getUser(0).getEmail());
        assertEquals("password123", users.getUser(0).getPassword());
    }

    @Test
    void getUsers() {
    }

    @Test
    void registerClient() {
    }

    @Test
    void isClientProfileValid() {
    }

    @Test
    void isPasswordNotValid() {
    }

    @Test
    void authenticate() {
    }

    @Test
    void isBlocked() {
    }

    @Test
    void isAdmin() {
    }

    @Test
    void logout() {
    }

    @Test
    void getUser() {
    }

    @Test
    void testGetUser() {
    }

    @Test
    void getUserId() {
    }

    @Test
    void addAdmin() {
    }

    @Test
    void blockUser() {
    }

    @Test
    void unBlockUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getLoginUsers() {
    }

    @Test
    void getBlockedUsers() {
    }
}