package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class SimpleBlockingQueueTest {
    private final SimpleBlockingQueue<Integer> simple = new SimpleBlockingQueue<>(5);

    @Test
    void offerAndPoll() {
        List<Integer> list = new ArrayList<>();
        Thread first = new Thread(
                () -> {
                    for (int i = 1; i <= 7; i++) {
                        simple.offer(i);
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 1; i <= 7; i++) {
                        list.add(simple.poll());
                    }
                }
        );
        first.start();
        second.start();
        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertThat(list).isEqualTo(List.of(1, 2, 3, 4, 5, 6, 7));
    }
}