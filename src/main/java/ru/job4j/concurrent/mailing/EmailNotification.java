package ru.job4j.concurrent.mailing;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * EmailNotification
 *
 * @author Vladislav Nechaev
 * @since 23.12.2020
 */

public class EmailNotification {

    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private ExecutorService executor;

    public EmailNotification() {
        executor = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS);
    }

    public void emailTo(@NotNull User user) {
        String subject = String.format("Notification %s to email %s", user.getUsername(), user.getEmail());
        String body = String.format("Add a new event to %s", user.getUsername());
        executor.submit(
                () -> send(subject, body, user.getEmail())
        );
    }

    public void close() {
        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {

    }
}
