package ru.job4j.concurrent.pool;

import ru.job4j.concurrent.blocking.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * ThreadPool
 *
 * @author Vladislav Nechaev
 * @since 07.12.2020
 */

public class ThreadPool {

    private final static int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Thread> tasks;

    public ThreadPool() {
        tasks = new SimpleBlockingQueue<>(NUMBER_OF_THREADS * 2);
        initThreads();
    }

    /**
     * initThreads
     * creates the threads pool (@link ru.job4j.concurrent.pool.ThreadPool#threads).
     */
    private void initThreads() {
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads.add(new WorkThread(tasks));
        }
    }

    /**
     * work
     * adds a task to tasks (@link ru.job4j.concurrent.pool.ThreadPool#tasks).
     *
     * @param job - the task (@link ru.job4j.concurrent.pool.Job).
     * @throws InterruptedException - throws InterruptedException.
     */
    public void work(Runnable job) throws InterruptedException {
        if (job == null) {
            throw new IllegalArgumentException("The job can't be null.");
        }
        tasks.offer(new Thread(job));
        notifyAll();
    }

    /**
     * shutdown
     * starts the task execution (@link ru.job4j.concurrent.pool.WorkThread).
     */
    public  void shutdown() {
        threads.forEach(Thread::start);
    }
}
