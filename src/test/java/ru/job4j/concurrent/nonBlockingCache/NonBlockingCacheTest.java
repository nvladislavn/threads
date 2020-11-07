package ru.job4j.concurrent.nonBlockingCache;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

public class NonBlockingCacheTest {

    private NonBlockingCache cache;
    private Base base1;
    private Base base2;
    private Base base3;

    @Before
    public void fillCache() {
        base1 = new Base(1);
        base1.setName("base1");
        base2 = new Base(2);
        base1.setName("base2");
        base3 = new Base(3);
        base1.setName("base3");
        cache = new NonBlockingCache();
        cache.add(base1);
        cache.add(base2);
        cache.add(base3);
    }

    @Test
    public void theNameShouldBeReplBase2() throws InterruptedException {
        Base replBase2 = new Base(2);
        String expName = "Replaced base 2";
        Thread thread = new Thread(() -> {
            replBase2.setName(expName);
            cache.update(replBase2);
        });
        thread.start();
        thread.join();
        Base actualBase = cache.get(2);
        assertEquals(expName, actualBase.getName());
    }

    @Test(expected = OptimisticException.class)
    public void test() throws InterruptedException {
        AtomicReference<Exception> exception = new AtomicReference<>();
        Base replBase2 = new Base(2);
        String expName = "Replaced base 2";
        Thread thread = new Thread(() -> {
            replBase2.setName(expName);
            try {
                cache.update(replBase2);
            } catch (OptimisticException oe) {
                exception.set(oe);
            }
        });
        Base replBase22 = new Base(2);
        String expName22 = "Replaced base 22";
        Thread thread2 = new Thread(() -> {
            replBase22.setName(expName22);
            try {
                cache.update(replBase22);
            } catch (OptimisticException oe) {
                exception.set(oe);
            }
        });
        thread.start();
        thread2.start();
        thread.join();
        thread2.join();
    }
}