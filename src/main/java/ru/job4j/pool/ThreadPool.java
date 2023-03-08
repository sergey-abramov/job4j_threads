package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final int size = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(
                    () -> {
                        while (!tasks.isEmpty() || !Thread.currentThread().isInterrupted()) {
                            try {
                                tasks.poll().run();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            ));
        }
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool();
        try {
            threadPool.work(() -> System.out.println("task 1"));
            threadPool.work(() -> System.out.println("task 2"));
            threadPool.work(() -> System.out.println("task 3"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        threadPool.shutdown();
    }
}
