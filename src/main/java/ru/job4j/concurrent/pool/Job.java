package ru.job4j.concurrent.pool;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Job
 *
 * @author Vladislav Nechaev
 * @since 04.12.2020
 */

public class Job implements Runnable {

    private static AtomicInteger jobCounter = new AtomicInteger(0);

    @Override
    public void run() {
        int jobNumber = jobCounter.incrementAndGet();
        System.out.printf("Start job#%d%s", jobNumber, System.lineSeparator());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Finish job#%d%s", jobNumber, System.lineSeparator());
    }
}
