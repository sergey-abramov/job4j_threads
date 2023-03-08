package ru.job4j.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static class Sums {
        private final int rowSum;
        private final int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] rsl = new Sums[]{};
        int colSum = 0;
        int rowSum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
                rsl = new Sums[]{new Sums(rowSum, colSum)};
            }
        }
        return rsl;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        List<Sums> list = new ArrayList<>();
        int row = 0;
        int col = 0;
        for (int i = 0; i < matrix.length; i++) {
            list.add(new Sums(row(matrix, i).get(), col(matrix, i).get()));
        }
        for (Sums sums : list) {
            row += sums.getRowSum();
            col += sums.getColSum();
        }
        return new Sums[]{new Sums(row, col)};
    }

    public static CompletableFuture<Integer> col(int[][] array, int index) {
        return CompletableFuture.supplyAsync(() -> {
            int rsl = 0;
            for (int[] ints : array) {
                rsl += ints[index];
            }
            return rsl;
        });
    }

    public static CompletableFuture<Integer> row(int[][] array, int index) {
        return CompletableFuture.supplyAsync(() -> {
            int rsl = 0;
            for (int i = 0; i < array.length; i++) {
                rsl += array[index][i];
            }
            return rsl;
        });
    }

}
