package ru.job4j.concurrent;

/**
 * Node
 *
 * @author Vladislav Nechaev
 * @since 03.09.2020
 */

public final class Node<T> {

    private final Node next;
    private final T value;

    public Node(Node next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }
}
