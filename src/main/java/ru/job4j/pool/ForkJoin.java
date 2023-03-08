package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoin<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T value;
    private final int start;
    private final int finish;

    public ForkJoin(T[] array, T value, int start, int finish) {
        this.array = array;
        this.value = value;
        this.start = start;
        this.finish = finish;
    }

    @Override
    protected Integer compute() {
        if (finish - start < 10) {
            return findIndex();
        }
        int mid = (array.length - 1) / 2;
        ForkJoin<T> first = new ForkJoin<>(array, value, start, mid);
        ForkJoin<T> second = new ForkJoin<>(array, value, mid + 1, array.length - 1);
        first.fork();
        second.fork();
        int left = first.join();
        int right = second.join();
        return Math.max(left, right);
    }

    public int findIndex() {
        int rsl = -1;
        for (int i = start; i <= finish; i++) {
            if (array[i].equals(value)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    public static <T> int find(T[] array, T value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ForkJoin<>(array, value, 0, array.length - 1));
    }
}
