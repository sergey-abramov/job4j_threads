package ru.job4j.pool;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ForkJoin<T> extends RecursiveTask<Integer> {

    private final List<T> list;
    private final T value;
    private final int start;
    private final int finish;

    public ForkJoin(List<T> list, T value, int start, int finish) {
        this.list = list;
        this.value = value;
        this.start = start;
        this.finish = finish;
    }

    @Override
    protected Integer compute() {
        if (finish - start < 10) {
            return findIndex();
        }
        int mid = list.size() / 2;
        ForkJoin<T> first = new ForkJoin<>(list, value, start, mid);
        ForkJoin<T> second = new ForkJoin<>(list, value, mid + 1, list.size());
        first.fork();
        second.fork();
        int left = first.join();
        int right = second.join();
        return left == -1 ? right : left;
    }

    public int findIndex() {
        int rsl = -1;
        if (!list.contains(value)) {
            return rsl;
        }
        for (int i = start; i < finish; i++) {
            if (list.get(i).equals(value)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

}
