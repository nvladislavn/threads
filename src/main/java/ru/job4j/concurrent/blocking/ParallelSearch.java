package ru.job4j.concurrent.blocking;

/**
 * ParallelSearch
 *
 * @author Vladislav Nechaev
 * @since 14.10.2020
 */

public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        var bQueue = new SimpleBlockingQueue<Integer>(3);
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
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    while (bQueue.size() > 0) {
                        try {
                            System.out.println(bQueue.poll());
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println("Consumer is finished.");
                    Thread.currentThread().interrupt();
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}
