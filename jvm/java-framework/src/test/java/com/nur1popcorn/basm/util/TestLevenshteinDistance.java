package com.nur1popcorn.basm.util;

import com.nur1popcorn.basm.utils.LevenshteinDistance;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class TestLevenshteinDistance {
    private final LevenshteinDistance algo = new LevenshteinDistance();

    @Test
    public void testInsert() {
        assertEquals(0, algo.distance("ac dc", "ac dc"));
        assertEquals(1, algo.distance("ac dc", "aca dc"));
        assertEquals(2, algo.distance("ac dc", "aca dac"));
        assertEquals(3, algo.distance("ac dc", "aca daca"));
    }

    @Test
    public void testDelete() {
        assertEquals(0, algo.distance("hello there", "hello there"));
        assertEquals(1, algo.distance("hello there", "hello ther"));
        assertEquals(2, algo.distance("hello there", "hello the"));
        assertEquals(3, algo.distance("hello there", "hello th"));
        assertEquals(4, algo.distance("hello there", "hello t"));
        assertEquals(5, algo.distance("hello there", "hello "));
        assertEquals(6, algo.distance("hello there", "hello"));
    }

    @Test
    public void testEdit() {
        assertEquals(0, algo.distance("g'day", "g'day"));
        assertEquals(1, algo.distance("g'day", "h'day"));
        assertEquals(2, algo.distance("g'day", "heday"));
        assertEquals(3, algo.distance("g'day", "helay"));
        assertEquals(4, algo.distance("g'day", "helly"));
        assertEquals(5, algo.distance("g'day", "hello"));
    }

    @Test
    public void testCombined() {
        assertEquals(3, algo.distance("kitten", "sitting"));
        assertEquals(7, algo.distance("hitting", "bob"));
    }
}
