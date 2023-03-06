package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CacheTest {

    private final Cache cache = new Cache();

    @Test
    void add() {
        assertThat(true).isEqualTo(cache.add(new Base(1, 1)));
    }

    @Test
    void update() {
        cache.add(new Base(1, 1));
        cache.add(new Base(2, 1));
        assertThat(true).isEqualTo(cache.update(new Base(2, 1)));
    }

    @Test
    void delete() {
        cache.add(new Base(1, 1));
        cache.delete(new Base(1, 1));
        assertThat(true).isEqualTo(cache.add(new Base(1, 1)));
    }
}