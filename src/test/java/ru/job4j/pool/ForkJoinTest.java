package ru.job4j.pool;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ForkJoinTest {

    @Test
    void findIndexfFor9elements() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertThat(ForkJoin.find(array, 6)).isEqualTo(5);
    }

    @Test
    void findIndexfFor15elements() {
        Integer[] array = new Integer[]{
                1, 2, 3, 4, 5,
                6, 7, 8, 9, 10,
                11, 12, 13, 14, 15};
        assertThat(ForkJoin.find(array, 11)).isEqualTo(10);
    }

    @Test
    void findIndexfNotFound() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertThat(ForkJoin.find(array, 10)).isEqualTo(-1);
    }

    @Test
    void findIndexfFor12elements() {
        String[] array = new String[]{
                "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10",
                "11", "12"};
        assertThat(ForkJoin.find(array, "11")).isEqualTo(10);
    }
}