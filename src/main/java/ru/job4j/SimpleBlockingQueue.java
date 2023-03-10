package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int size;

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() >= size) {
                this.wait();
            }
            queue.add(value);
            this.notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        T rsl;
        synchronized (this) {
            while (queue.isEmpty()) {
                this.wait();
            }
            rsl = queue.poll();
            this.notifyAll();
        }
        return rsl;
    }

    public boolean isEmpty() {
        synchronized (this) {
            return queue.isEmpty();
        }
    }
}
