package ru.job4j.concurrent;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    /**
     * tests the work of the SimpleBlockingQueue (@link ru.job4j.concurrent.SimpleBlockingQueue) in multi-threaded mode.
     *
     * @throws InterruptedException -  throws exception.
     */
    @Test
    public void setsShouldBeEqual() throws InterruptedException {
        SimpleBlockingQueue<String> bq = new SimpleBlockingQueue<>(3);
        Set<String> actual = new HashSet<>();
        Thread producer = new Thread(
                () -> {
                    int count = 0;
                    for (int i = 0; i < 9; i++) {
                        try {
                            bq.offer("Str_" + ++count);
                            System.out.println("Added Str_" + count);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    for (int i = 0; i < 9; i++) {
                        try {
                            String ex = bq.poll();
                            System.out.println("Extract " + ex);
                            actual.add(ex);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        Set<String> expected = Set.of("Str_1", "Str_2", "Str_3", "Str_4", "Str_5", "Str_6", "Str_7", "Str_8", "Str_9");
        assertEquals(actual, expected);
    }

    /**
     * tests offer (@link ru.job4j.concurrent.SimpleBlockingQueue#offer)
     *
     * @throws InterruptedException -  throws exception.
     */
    @Test(expected = NullPointerException.class)
    public void shouldReturnException() throws InterruptedException {
        SimpleBlockingQueue<String> bq = new SimpleBlockingQueue<>(3);
        bq.offer(null);
    }
}