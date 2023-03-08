package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class RolColSumTest {

    @Test
    void sum() {
        int[][] array = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] rol = RolColSum.sum(array);
        RolColSum.Sums rsl = new RolColSum.Sums(rol[0].getRowSum(), rol[0].getColSum());
        assertThat(rsl.getColSum()).isEqualTo(45);
        assertThat(rsl.getRowSum()).isEqualTo(45);
    }

    @Test
    void asyncSum() throws ExecutionException, InterruptedException {
        int[][] array = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum.Sums[] rol = RolColSum.asyncSum(array);
        RolColSum.Sums rsl = new RolColSum.Sums(rol[0].getRowSum(), rol[0].getColSum());
        assertThat(rsl.getColSum()).isEqualTo(45);
        assertThat(rsl.getRowSum()).isEqualTo(45);
    }
}