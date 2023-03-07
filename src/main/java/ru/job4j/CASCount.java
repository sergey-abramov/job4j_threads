package ru.job4j;

import java.util.concurrent.atomic.AtomicInteger;

public class CASCount {

    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int increment;
        int integer;
        do {
            integer = count.get();
            increment = integer + 1;
        } while (!count.compareAndSet(integer, increment));
    }

    public int get() {
        return count.get();
    }

}
