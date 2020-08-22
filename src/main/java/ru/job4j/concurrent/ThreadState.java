package ru.job4j.concurrent;

public class ThreadState {

    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();
        int count = 0;
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            count++;
        }
        System.out.printf("%n%s and %s is terminated.%n", first.getName(), second.getName());
    }
}
