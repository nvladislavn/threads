package ru.job4j.concurrent.userCache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * UserCache
 *
 * @author Vladislav Nechaev
 * @since 14.09.2020
 */

public class UserCache {

    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }

    public User findById(int id) {
        return User.of(users.get(id).getName());
    }

    /**
     * findAll
     * creates a List of copies of Users.
     *
     * @return - a List of copies of Users.
     */
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        for (User user : users.values()) {
            User copy = User.of(user.getName());
            copy.setId(user.getId());
            list.add(copy);
        }
        return list;
    }
}
