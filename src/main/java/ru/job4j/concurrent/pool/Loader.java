package ru.job4j.concurrent.pool;

/**
 * Loader
 *
 * @author Vladislav Nechaev
 * @since 04.12.2020
 */

public class Loader {

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        pool.work(new Job());
        pool.work(new Job());
        pool.work(new Job());
        pool.work(new Job());
        pool.work(new Job());
        pool.shutdown();
    }
}
