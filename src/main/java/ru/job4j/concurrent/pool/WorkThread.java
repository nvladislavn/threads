package ru.job4j.concurrent.pool;

import ru.job4j.concurrent.blocking.SimpleBlockingQueue;

/**
 * WorkThread
 *
 * @author Vladislav Nechaev
 * @since 03.12.2020
 */

public class WorkThread extends Thread {

    private SimpleBlockingQueue<Thread> tasks;

    public WorkThread(SimpleBlockingQueue<Thread> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void run() {
        try {
            Runnable task = tasks.poll();
            task.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
