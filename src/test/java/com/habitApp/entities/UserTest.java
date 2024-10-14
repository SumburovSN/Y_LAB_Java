package com.habitApp.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void testUserConstructor() {
        User user = new User("John Doe", "john@example.com", "password123");
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
    }

    @Test
    void getName() {
        User user = new User("Jane Doe", "jane@example.com", "password123");
        assertEquals("Jane Doe", user.getName());
    }

    @Test
    void setName() {
        User user = new User("Jane Doe", "jane@example.com", "password123");
        user.setName("Jane Smith");
        assertEquals("Jane Smith", user.getName());
    }

    @Test
    void getEmail() {
        User user = new User("John Doe", "john@example.com", "password123");
        assertEquals("john@example.com", user.getEmail());
    }

    @Test
    void setEmail() {
        User user = new User("John Doe", "john@example.com", "password123");
        user.setEmail("john.doe@example.com");
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    void getPassword() {
        User user = new User("John Doe", "john@example.com", "password123");
        assertEquals("password123", user.getPassword());
    }

    @Test
    void setPassword() {
        User user = new User("John Doe", "john@example.com", "password123");
        user.setPassword("newpassword456");
        assertEquals("newpassword456", user.getPassword());
    }

    @Test
    void testEquals() {
        User user = new User("John Doe", "john@example.com", "password123");
        User anotherUser = new User("Sam Nemo", "john@example.com", "password123");
        assertEquals(true, user.equals(anotherUser));
    }

    @Test
    void testHashCode() {
        User user = new User("John Doe", "john@example.com", "password123");
        int result = user.getPassword().hashCode();
        result = 31 * result + user.getEmail().hashCode();
        assertEquals(result, user.hashCode());
    }
}