package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContentNotWith() {
        return getContent(p -> true);
    }

    public String getContentWithoutUnicode() {
        return getContent(p -> p < 0x80);
    }

    public String getContent(Predicate<Integer> predicate) {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new FileInputStream(file)) {
            int data;
            while ((data = i.read()) != -1) {
                if (predicate.test(data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

}
