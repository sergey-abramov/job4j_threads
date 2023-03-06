package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {

    private final CASCount count = new CASCount();

    @Test
    void get() throws InterruptedException {
        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        count.increment();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    while (count.get() != 8) {
                        count.increment();
                    }
                });
        first.start();
        second.start();
        first.join();
        second.interrupt();
        second.join();
        assertThat(count.get()).isEqualTo(8);
    }
}