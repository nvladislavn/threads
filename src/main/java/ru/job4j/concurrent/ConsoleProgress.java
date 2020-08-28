package ru.job4j.concurrent;

/**
 * ConsoleProgress
 *
 * @author Vladislav Nechaev
 * @since 24.08.2020
 */

public class ConsoleProgress implements Runnable {

    private static final int SLEEP_DURATION = 250;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\rLoading...|");
            try {
                Thread.sleep(SLEEP_DURATION);
                System.out.print("\rLoading.../");
                Thread.sleep(SLEEP_DURATION);
                System.out.print("\rLoading...--");
                Thread.sleep(SLEEP_DURATION);
                System.out.print("\rLoading...\\");
                Thread.sleep(SLEEP_DURATION);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.interrupt();
    }
}
