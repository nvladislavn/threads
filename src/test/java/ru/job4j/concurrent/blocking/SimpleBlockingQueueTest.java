package ru.job4j.concurrent.blocking;

import org.junit.Test;
import ru.job4j.concurrent.blocking.SimpleBlockingQueue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    /**
     * tests the work of the SimpleBlockingQueue (@link ru.job4j.concurrent.blocking.SimpleBlockingQueue) in multi-threaded mode.
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
     * tests offer (@link ru.job4j.concurrent.blocking.SimpleBlockingQueue#offer)
     *
     * @throws InterruptedException -  throws exception.
     */
    @Test(expected = NullPointerException.class)
    public void shouldReturnException() throws InterruptedException {
        SimpleBlockingQueue<String> bq = new SimpleBlockingQueue<>(3);
        bq.offer(null);
    }

    /**
     * tests work of SimpleBlockingQueue (@link ru.job4j.concurrent.blocking.SimpleBlockingQueue) in multi_threaded mode.
     */
    @Test
    public void fetchListShouldBeEqualsExpectedList() {
        var bQueue = new SimpleBlockingQueue<Integer>(3);
        var actual = new CopyOnWriteArrayList<Integer>();
        Thread producer = new Thread(
                () -> {
                    for (int i = 1; i <= bQueue.capacity(); i++) {
                        try {
                            bQueue.offer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    while (!bQueue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            actual.add(bQueue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        try {
            producer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        consumer.interrupt();
        try {
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Integer> expected = List.of(1, 2, 3);
        assertThat(actual, is(expected));
    }
}