package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {
    private final SimpleBlockingQueue<Integer> simple = new SimpleBlockingQueue<>(5);

    @Test
    void offerAndPoll() throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        Thread first = new Thread(
                () -> {
                    for (int i = 1; i <= 7; i++) {
                        try {
                            simple.offer(i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    for (int i = 1; i <= 7; i++) {
                        try {
                            list.add(simple.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.interrupt();
        second.join();
        assertThat(list).isEqualTo(List.of(1, 2, 3, 4, 5, 6, 7));
    }

    @Test
    public void whenFetchAllThenGetIt2() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        Thread producer = new Thread(
                () -> {
                    for (int i = 1; i <= 7; i++) {
                        try {
                            simple.offer(i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!simple.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(simple.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).isEqualTo(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
    }
}