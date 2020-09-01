package ru.job4j.concurrent;

/**
 * Cache
 *
 * @author Vladislav Nechaev
 * @since 01.09.2020
 */

public class Cache {

    private static Cache cache;

    //Added key word synchronized.
    public synchronized static Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
