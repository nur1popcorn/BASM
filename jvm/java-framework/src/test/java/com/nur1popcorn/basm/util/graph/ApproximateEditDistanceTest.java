package com.nur1popcorn.basm.util.graph;

import com.nur1popcorn.basm.utils.graph.AdjacencyMatrixGraph;
import com.nur1popcorn.basm.utils.graph.ApproximateEditDistance;
import com.nur1popcorn.basm.utils.graph.SimpleEdge;
import com.nur1popcorn.basm.utils.graph.SimpleGraph;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public final class ApproximateEditDistanceTest {
    private final ApproximateEditDistance<Integer, SimpleEdge<Integer>> ged = new ApproximateEditDistance<>();
    private SimpleGraph<Integer, SimpleEdge<Integer>> g1, g2;

    @Before
    public void setup() {
        g1 = new AdjacencyMatrixGraph<>();
        g2 = new AdjacencyMatrixGraph<>();
    }

    @Test
    public void testSimple() {
        g1.addVertex(0);
        g1.addVertex(1);

        g1.addEdge(0, 1);

        g2.addVertex(0);
        g2.addVertex(1);
        g2.addVertex(2);

        g2.addEdge(0, 1);

        assertEquals(1, ged.distance(g1, g2));
        assertEquals(1, ged.distance(g2, g1));
    }

    @Test
    public void testEF() {
        /* x-x
         * |
         * x-x
         * |
         * x
         */
        for(int i = 0; i < 5; i++)
            g1.addVertex(i);
        for(int i = 0; i < 4; i += 2)
            g1.addEdge(i, i + 1);
        for(int i = 0; i < 4; i += 2)
            g1.addEdge(i, i + 2);

        /* x-x
         * |
         * x-x
         * |
         * x-x
         */
        for(int i = 0; i < 6; i++)
            g2.addVertex(i);
        for(int i = 0; i < 6; i += 2)
            g2.addEdge(i, i + 1);
        for(int i = 0; i < 4; i += 2)
            g2.addEdge(i, i + 2);

        assertEquals(2, ged.distance(g1, g2));
        assertEquals(2, ged.distance(g2, g1));
    }
}
