package ru.job4j.concurrent.userStorage;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserStorageTest {

    private UserStorage storage;
    private User user1;
    private User user2;
    private User user3;

    @Before
    public void createAndFillUserStorage() {
        user1 = User.of(1, 100);
        user2 = User.of(2, 200);
        user3 = User.of(3, 300);
        storage = new UserStorage();
        storage.add(user1);
        storage.add(user2);
        storage.add(user3);
    }

    /**
     * tests add(@link ru.job4j.concurrent.userStorage.UserStorage#add)
     *
     * @throws InterruptedException  - throws (@link java.lang.Thread#join) methods.
     */
    @Test
    public void shouldReturnListWithThreeItems() throws InterruptedException {
        UserStorage localStorage = new UserStorage();
        Thread thread1 = new Thread(() -> localStorage.add(user1));
        Thread thread2 = new Thread(() -> localStorage.add(user2));
        Thread thread3 = new Thread(() -> localStorage.add(user3));
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        List<String> actual = localStorage.getUsers()
                .stream()
                .map(u -> String.format("%d : %d", u.getId(), u.getAmount()))
                .collect(Collectors.toList());
        List<String> expected = List.of("1 : 100", "2 : 200", "3 : 300");
        assertThat(actual, is(expected));
    }

    /**
     * tests update (@link ru.job4j.concurrent.userStorage.UserStorage#update)
     *
     * @throws InterruptedException  - throws (@link java.lang.Thread#join) methods.
     */
    @Test
    public void whenNewAmount555ThenReturnAmount555() throws InterruptedException {
        int expectedAmount = 555;
        Thread thread = new Thread(
                () -> storage.update(User.of(2, expectedAmount))
        );
        thread.start();
        thread.join();
        int actualAmount = storage.getUsers()
                .stream()
                .filter(u -> u.getId() == 2)
                .map(User::getAmount)
                .findFirst().orElse(-1);
        assertEquals(expectedAmount, actualAmount);
    }

    /**
     * tests update (@link ru.job4j.concurrent.userStorage.UserStorage#update)
     *
     * @throws InterruptedException  - throws (@link java.lang.Thread#join) methods.
     */
    @Test
    public void whenUserExistsThenReturnTrue() throws ExecutionException, InterruptedException {
        FutureTask<Boolean> futureTask = new FutureTask<>(
                () -> storage.update(User.of(2, 1000))
        );
        new Thread(futureTask).start();
        assertTrue(futureTask.get());
    }

    /**
     * tests update (@link ru.job4j.concurrent.userStorage.UserStorage#update)
     *
     * @throws InterruptedException  - throws (@link java.lang.Thread#join) methods.
     * @throws ExecutionException - throws (@link java.util.concurrent.FutureTask#get) method.
     */
    @Test
    public void whenUserNotExistsThenReturnFalse() throws ExecutionException, InterruptedException {
        FutureTask<Boolean> futureTask = new FutureTask<>(
                () -> storage.update(User.of(10, 1000))
        );
        new Thread(futureTask).start();
        assertFalse(futureTask.get());
    }

    /**
     * tests delete (@link ru.job4j.concurrent.userStorage.UserStorage#delete)
     *
     * @throws InterruptedException  - throws (@link java.lang.Thread#join) methods.
     */
    @Test
    public void whenDeleteUserWithId2ThenHimDoesNotFindHim() throws InterruptedException {
        Thread thread = new Thread(
                () -> storage.delete(User.of(2, 1000))
        );
        thread.start();
        thread.join();
        boolean isExists = storage.getUsers()
                .stream()
                .anyMatch(u -> u.getId() == 2);
        assertFalse(isExists);
    }

    /**
     * tests transfer (@link ru.job4j.concurrent.userStorage.UserStorage#transfer)
     *
     * @throws InterruptedException  - throws (@link java.lang.Thread#join) methods.
     */
    @Test
    public void shouldBeUser2AmountIs500User3AmountIs0() throws InterruptedException {
        Thread thread = new Thread(
                () -> storage.transfer(3, 2, 300)
        );
        thread.start();
        thread.join();
        List<User> users = storage.getUsers();
        int amountOfUser3 = users.stream()
                .filter(u -> u.getId() == 3)
                .map(User::getAmount)
                .findFirst()
                .orElse(-1);
        int amountOfUser2 = users.stream()
                .filter(u -> u.getId() == 2)
                .map(User::getAmount)
                .findFirst()
                .orElse(-1);
        int user2Expected = 500;
        int user3Expected = 0;
        assertEquals(user2Expected, amountOfUser2);
        assertEquals(user3Expected, amountOfUser3);
    }
}