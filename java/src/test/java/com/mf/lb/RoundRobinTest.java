package com.mf.lb;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RoundRobinTest {

    @Test
    void testRoundRobin() {
        var algo = new RoundRobin(List.of("a","b","c"));
        assertEquals(algo.choseNode(null), Optional.of("a"));
        assertEquals(algo.choseNode(null), Optional.of("b"));
        assertEquals(algo.choseNode(null), Optional.of("c"));
        assertEquals(algo.choseNode(null), Optional.of("a"));
    }

    @Test
    void testRoundRobin_no_config_fails() {
        assertThrows(java.lang.AssertionError.class,()->  new RoundRobin(List.of()));
    }

}