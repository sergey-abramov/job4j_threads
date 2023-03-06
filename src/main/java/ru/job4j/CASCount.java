package ru.job4j;

import java.util.concurrent.atomic.AtomicInteger;

public class CASCount {

    private final AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet();
    }

    public int get() {
        return count.get();
    }

}
