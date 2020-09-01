package ru.job4j.concurrent;

/**
 * DCLSingleton
 *
 * @author Vladislav Nechaev
 * @since 01.09.2020
 */

public class DCLSingleton {

    //added key word volatile.
    private static volatile DCLSingleton inst;

    private DCLSingleton() {
    }

    public static DCLSingleton instOf() {
        if (inst == null) {
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
            }
        }
        return inst;
    }
}
