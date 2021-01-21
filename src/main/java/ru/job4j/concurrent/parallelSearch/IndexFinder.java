package ru.job4j.concurrent.parallelSearch;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

/**
 * Finder
 *
 * @author Vladislav Nechaev
 * @since 20.01.2021
 */

public class IndexFinder<T> extends RecursiveTask<Integer> {

    private final int threshold = 10;
    private T[] array;
    private T item;

    public IndexFinder(T[] array, T item) {
        this.array = array;
        this.item = item;
    }

    @Override
    protected Integer compute() {
        if (array.length > threshold) {
            int mid = array.length / 2;
            IndexFinder<T> leftTask = new IndexFinder<>(Arrays.copyOfRange(array, 0, mid), item);
            IndexFinder<T> rightTask = new IndexFinder<>(Arrays.copyOfRange(array, mid, array.length), item);
            int leftRes = leftTask.invoke();
            int rightRes = rightTask.invoke();
            return merge(leftRes, rightRes, mid);
        }
        return linearSearch();
    }

    /**
     * merge
     * combines the obtained results.
     *
     * @param leftRes    - the result of first (left) task.
     * @param rightRes   - the result of second (right) task.
     * @param leftLength - the length of the first task array.
     * @return -  the index of the item found.
     */
    private Integer merge(int leftRes, int rightRes, int leftLength) {
        if (leftRes >= 0) {
            return leftRes;
        }
        if (rightRes >= 0) {
            return rightRes + leftLength;
        }
        return -1;
    }

    /**
     * linearSearch
     * searches in single threaded mode.
     *
     * @return - the index of the search item.
     */
    private int linearSearch() {
        for (int i = 0; i < array.length; i++) {
            T elem = array[i];
            if (elem == null) {
                throw new IllegalArgumentException("The array must no contain null.");
            }
            if (elem.equals(item)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * search
     * starts the search an item in array.
     *
     * @return - an index of the search item.
     */
    public int search() {
        return this.invoke();
    }
}