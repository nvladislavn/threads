package ru.job4j.concurrent.nonBlockingCache;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * NonBlockingCache
 *
 * @author Vladislav Nechaev
 * @since 04.11.2020
 */

public class NonBlockingCache {

    private final Map<Integer, Base> cache = new ConcurrentHashMap<>();

    /**
     * add
     * adds a getting Base (@link ru.job4j.concurrent.nonBlockingCache.Base)
     * in cache (@link ru.job4j.concurrent.nonBlockingCache.NonBlockingCache#cache).
     *
     * @param model - a getting Base.
     * @return - the previous value associated with the specified key.
     */
    public Base add(@NotNull Base model) {
        return cache.putIfAbsent(model.getId(), model);
    }

    /**
     * update
     * If the Base  (@link ru.job4j.concurrent.nonBlockingCache.Base)
     * is present in cache (@link ru.job4j.concurrent.nonBlockingCache.NonBlockingCache#cache),
     * updates a value for name (@link ru.job4j.concurrent.nonBlockingCache.Base#name) and
     * increments for version (@link ru.job4j.concurrent.nonBlockingCache.Base#version)
     *
     * @param model - a Base with updatable data.
     * @return - an updated Base (@link ru.job4j.concurrent.nonBlockingCache.Base).
     */
    public Base update(@NotNull Base model) {
        int key = model.getId();
        return cache.computeIfPresent(key, (k, v) -> {
            model.setVersion(cache.get(key).getVersion() + 1);
            if (model.getVersion() != cache.get(key).getVersion() + 1) {
                throw new OptimisticException("Wrong model version.");
            }
            cache.get(key).setName(model.getName());
            cache.get(key).setVersion(model.getVersion());
            return cache.get(key);
        });
    }

    /**
     * delete
     * deletes an item from cache (@link ru.job4j.concurrent.nonBlockingCache.NonBlockingCache#cache)
     * with specified id (@link ru.job4j.concurrent.nonBlockingCache.Base#id).
     *
     * @param model - a deletable Base (@link ru.job4j.concurrent.nonBlockingCache.Base).
     */
    public void delete(@NotNull Base model) {
        cache.remove(model.getId());
    }

    /**
     * get
     *
     * @param key - key whose mapping is to be returned
     *            from the cache (@link ru.job4j.concurrent.nonBlockingCache.NonBlockingCache#cache).
     * @return - the value to which the specified key
     * from cache (@link ru.job4j.concurrent.nonBlockingCache.NonBlockingCache#cache).
     */
    public Base get(int key) {
        return cache.get(key);
    }
}
