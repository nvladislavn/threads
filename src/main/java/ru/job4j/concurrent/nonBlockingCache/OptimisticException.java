package ru.job4j.concurrent.nonBlockingCache;

/**
 * OptimisticException
 *
 * @author Vladislav Nechaev
 * @since 05.11.2020
 */

public class OptimisticException extends RuntimeException {


    public OptimisticException(String message) {
        super(message);
    }


    public OptimisticException(String message, Throwable cause) {
        super(message, cause);
    }
}
