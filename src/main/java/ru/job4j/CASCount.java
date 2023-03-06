package ru.job4j;

import java.util.concurrent.atomic.AtomicReference;

public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer integer;
        int increment;
        do {
            integer = count.get();
            increment = integer + 1;
        } while (!count.compareAndSet(integer, increment));
    }

    public int get() {
        return count.get();
    }

}
