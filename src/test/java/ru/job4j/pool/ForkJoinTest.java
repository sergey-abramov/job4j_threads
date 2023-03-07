package ru.job4j.pool;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class ForkJoinTest {

    @Test
    void findIndexfFor9elements() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        ForkJoin<Integer> forkJoin = new ForkJoin<>(list, 6, 0, list.size());
        int rsl = forkJoin.compute();
        assertThat(rsl).isEqualTo(5);
    }

    @Test
    void findIndexfFor15elements() {
        List<Integer> list = Arrays.asList(
                1, 2, 3, 4, 5,
                6, 7, 8, 9, 10,
                11, 12, 13, 14, 15);
        ForkJoin<Integer> forkJoin = new ForkJoin<>(list, 11, 0, list.size());
        int rsl = forkJoin.compute();
        assertThat(rsl).isEqualTo(10);
    }

    @Test
    void findIndexfNotFound() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        ForkJoin<Integer> forkJoin = new ForkJoin<>(list, 10, 0, list.size());
        int rsl = forkJoin.compute();
        assertThat(rsl).isEqualTo(-1);
    }

    @Test
    void findIndexfFor12elements() {
        List<String> list = Arrays.asList(
                "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10",
                "11", "12");
        ForkJoin<String> forkJoin = new ForkJoin<>(list, "11", 0, list.size());
        int rsl = forkJoin.compute();
        assertThat(rsl).isEqualTo(10);
    }
}