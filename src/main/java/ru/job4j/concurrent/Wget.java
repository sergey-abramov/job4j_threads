package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;

public class Wget implements Runnable {

    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(saveFile(url))) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            Timestamp first = new Timestamp(System.currentTimeMillis());
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                downloadData += bytesRead;
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                Timestamp second = new Timestamp(System.currentTimeMillis());
                long timeInterval = second.getTime() - first.getTime();
                if (downloadData >= speed && timeInterval < 1000) {
                    sleep(1000 - timeInterval);
                    downloadData = 0;
                    first = new Timestamp(System.currentTimeMillis());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String saveFile(String url) {
        String[] s = url.split("//")[1].split("/");
        return s[s.length - 1];
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 1) {
            throw new IllegalArgumentException("Нужно указать аргументы: ссылку и скорость");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
