package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        try {
            var process = new char[] {'-', '\'', '|', '/'};
            while (!Thread.currentThread().isInterrupted()) {
                for (char c : process) {
                    System.out.print("\rLoading ... " + c);
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        progress.run();
        progress.interrupt();
    }
}
