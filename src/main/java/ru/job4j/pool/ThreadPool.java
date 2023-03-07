package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(1);

    private final int size = Runtime.getRuntime().availableProcessors();

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(
                    () -> {
                        while (!tasks.isEmpty() || !Thread.currentThread().isInterrupted()) {
                            try {
                                tasks.poll().run();
                                System.out.println("Thread run");
                            } catch (InterruptedException e) {
                                System.out.println("Thread interrupt");
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
            ));
        }
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        threads.forEach(thread -> {
            thread.interrupt();
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool();
        threadPool.work(() -> System.out.println("task 1"));
        threadPool.work(() -> System.out.println("task 2"));
        threadPool.work(() -> System.out.println("task 3"));
        threadPool.shutdown();
    }
}
