package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.apache.logging.log4j.core.util.JsonUtils;

import java.util.stream.IntStream;

/**
 * CountBarrier
 *
 * @author Vladislav Nechaev
 * @since 08.10.2020
 */

@ThreadSafe
public class CountBarrier {

    private final int total;

    @GuardedBy("this")
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    /**
     * count
     * increments the counter (@link ru.job4j.concurrent.CountBarrier#count)
     */
    public synchronized void count() {
        count++;
        notifyAll();
    }

    /**
     * await
     * manages an incoming threads.
     */
    public synchronized void await() {
        while (count != total) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

