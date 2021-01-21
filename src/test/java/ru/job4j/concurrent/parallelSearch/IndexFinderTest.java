package ru.job4j.concurrent.parallelSearch;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IndexFinderTest {

    private String[] array;

    @Before
    public void prepare() {
        array = new String[]{"a", "b", "c", "d", "e", "f", "g",
                "h", "i", "j", "k", "l", "m", "n"};
    }

    @Test
    public void whenMThenTrue() {
        int actual = new IndexFinder<>(array, "m").search();
        assertEquals(12, actual);
    }

    @Test
    public void whenNThenFalse() {
        int actual = new IndexFinder<>(array, "n").search();
        assertFalse(12 == actual);
    }

    @Test
    public void whenNotContainThenMinusOne() {
        int actual = new IndexFinder<>(array, "z").search();
        assertTrue(-1 == actual);
    }
}