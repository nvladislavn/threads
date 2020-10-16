package ru.job4j.concurrent.blocking;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * SimpleBlockingQueue
 *
 * @author Vladislav Nechaev
 * @since 10.10.2020
 */

@ThreadSafe
public class SimpleBlockingQueue<T> {

    private final int limit;

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    /**
     * offer
     * adds an item to the end of the queue.
     *
     * @param value - the item to add.
     * @throws InterruptedException -  throws exception.
     */
    public synchronized void offer(T value) throws InterruptedException {
        if (value == null) {
            throw new NullPointerException("The value should not be null.");
        }
        while (queue.size() >= limit) {
            wait();
        }
        queue.offer(value);
        notifyAll();
    }

    /**
     * poll
     * returns the item from the front of the queue with removal.
     *
     * @return - the item from the front of the queue.
     * @throws InterruptedException - throws exception.
     */
    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T value = queue.poll();
        notifyAll();
        return value;
    }

    public synchronized int capacity() {
        return limit;
    }

    public synchronized int size() {
        return queue.size();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
