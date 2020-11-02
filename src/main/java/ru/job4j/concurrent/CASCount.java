package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * CASСount
 *
 * @author Vladislav Nechaev
 * @since 01.11.2020
 */

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>();

    /**
     * increment
     * increments the count (see @ru.job4j.concurrent.Count#count)
     */
    public void increment() {
        int temp = get();
        boolean isSet;
        do {
            isSet = count.compareAndSet(temp, ++temp);
        } while (!isSet);
    }

    /**
     * get
     *
     * @return - a value of the count (see @ru.job4j.concurrent.Count#count)
     */
    public int get() {
        return count.get();
    }
}
