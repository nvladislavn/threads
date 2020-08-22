package ru.job4j.concurrent;

/**
 * ThreadSleep
 *
 * @author Vladislav Nechaev
 * @since 22.08.2020
 */

public class Wget {

    private static final int DURATION = 1000;

    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    for (int i = 0; i < 100; i++) {
                        try {
                            Thread.sleep(DURATION);
                            System.out.print("\rLoading: " + i + "%");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        thread.start();
    }
}
