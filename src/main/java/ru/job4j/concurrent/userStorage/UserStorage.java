package ru.job4j.concurrent.userStorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * UserStorage
 *
 * @author Vladislav Nechaev
 * @since 20.09.2020
 */

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private Map<Integer, User> users = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        boolean res = false;
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), User.of(user.getId(), user.getAmount()));
            res = true;
        }
        return res;
    }

    public synchronized boolean update(User user) {
        boolean res = false;
        if (users.containsKey(user.getId())) {
            User updatedUser = users.get(user.getId());
            updatedUser.setAmount(user.getAmount());
            res = true;
        }
        return res;
    }

    public synchronized boolean delete(User user) {
        boolean res = false;
        if (users.containsKey(user.getId())) {
            users.remove(user.getId());
            res = true;
        }
        return res;
    }

    public synchronized void transfer(int fromId, int toId, int amount) throws IllegalArgumentException {
        if (!users.containsKey(fromId)
                || !users.containsKey(toId)
                || (users.get(fromId).getAmount() - amount) < 0) {
            throw new IllegalArgumentException();
        }
        User from = users.get(fromId);
        User to = users.get(toId);
        from.setAmount(from.getAmount() - amount);
        to.setAmount(to.getAmount() + amount);
    }

    public synchronized List<User> getUsers() {
        return users.values().stream()
                .map(u -> User.of(u.getId(), u.getAmount()))
                .collect(Collectors.toList());
    }
}
