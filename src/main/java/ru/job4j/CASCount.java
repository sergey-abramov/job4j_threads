package ru.job4j;

import java.util.concurrent.atomic.AtomicReference;

public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        Integer integer;
        int increment;
        do {
            integer = count.get();
            increment = integer + 1;
        } while (!count.compareAndSet(integer, increment));
    }

    public int get() {
        Integer integer;
        do {
            integer = count.get();
            if (integer == 0) {
                throw new UnsupportedOperationException("Count is not impl.");
            }
        } while (!count.compareAndSet(integer, 0));
        return integer;
    }

}
