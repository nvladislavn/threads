package ru.job4j.concurrent.mailing;

import java.awt.*;

/**
 * User
 *
 * @author Vladislav Nechaev
 * @since 23.12.2020
 */

public class User {

    private String username;
    private String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(String username) {
        this(username, null);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
