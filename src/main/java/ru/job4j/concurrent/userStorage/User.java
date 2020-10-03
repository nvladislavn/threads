package ru.job4j.concurrent.userStorage;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * User
 *
 * @author Vladislav Nechaev
 * @since 20.09.2020
 */

public class User {

    private final int id;
    private int amount;

    private User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public static User of(int id, int amount) throws IllegalArgumentException {
        if (id < 0 || amount < 0) {
            throw new IllegalArgumentException();
        }
        return new User(id, amount);
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
