package ru.job4j.concurrent.ConcurrentList;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;

/**
 * SingleLockList
 *
 * @author Vladislav Nechaev
 * @since 06.10.2020
 */
@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    private static final int DEFAULT_CAPACITY = 16;

    @GuardedBy("this")
    private DynamicList<T> list = new DynamicList<>(DEFAULT_CAPACITY);

    /**
     * add
     *
     * @param value - the item to add.
     */
    public synchronized void add(T value) {
        list.add(value);
    }

    /**
     * get
     *
     * @param index - an index of the getting item.
     * @return - the specified item.
     */
    public synchronized T get(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("The index cannot be less than zero.");
        }
        return list.get(index);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }

    /**
     * copy
     *
     * @param list - the list to copy.
     * @return - copy of the list passed in the parameter.
     */
    private Iterable<T> copy(DynamicList<T> list) {
        DynamicList<T> rsl = new DynamicList<>(list.length());
        list.forEach(rsl::add);
        return rsl;
    }
}
