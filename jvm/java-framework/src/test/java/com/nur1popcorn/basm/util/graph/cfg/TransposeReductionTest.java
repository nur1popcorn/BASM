package com.nur1popcorn.basm.util.graph.cfg;

import com.nur1popcorn.basm.utils.graph.DirectedGraph;
import com.nur1popcorn.basm.utils.graph.cfg.crossings.BarrierCrossings;
import com.nur1popcorn.basm.utils.graph.cfg.crossings.CrossingMatrix;
import com.nur1popcorn.basm.utils.graph.cfg.crossings.TransposeReduction;
import com.nur1popcorn.basm.utils.graph.model.directed.DirectedAdjacencyListGraph;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class TransposeReductionTest {
    /* How many operations should randomly be performed on the graph. */
    private static final int N_TRIES = 1337;

    private List<Integer> a, b;
    private DirectedGraph<Integer, Boolean> graph;

    @Before
    public void setup() {
        a = new ArrayList<>();
        for(int i = 0; i < 5; i++)
            a.add(i);

        b = new ArrayList<>();
        for(int i = 5; i < 9; i++)
            b.add(i);

        graph = new DirectedAdjacencyListGraph<>();
        graph.addEdge(0, 5, true);
        graph.addEdge(1, 6, true);
        graph.addEdge(2, 6, true);
        graph.addEdge(3, 8, true);
        graph.addEdge(3, 5, true);
        graph.addEdge(4, 8, true);
        graph.addEdge(4, 7, true);
    }

    @Test
    public void test() {
        final CrossingMatrix<Integer, Boolean> matrix = new CrossingMatrix<>();
        matrix.compute(graph, a, b);

        final int expected[][] =
            { { 0, 2, 0, 0 },
              { 2, 0, 0, 0 },
              { 0, 2, 0, 1 },
              { 0, 0, 0, 0 } };
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                assertEquals(expected[i][j], matrix.crossings(i + 5, j + 5));

        assertEquals(3, BarrierCrossings.countCrossings(graph, a, b));
        final TransposeReduction<Integer, Boolean> reduction = new TransposeReduction<>();
        reduction.reduce(graph, List.of(a, b));
        assertEquals(2, BarrierCrossings.countCrossings(graph, a, b));
    }

    @Test
    public void randomTest() {
        final Random random = new Random();
        final Operation operations[] = Operation.values();
        final TransposeReduction<Integer, Boolean> reduction =
            new TransposeReduction<>();
        for(int i = 0; i < N_TRIES; i++) {
            switch(operations[random.nextInt(operations.length)]) {
                case INSERT: {
                    final int v = graph.size();
                    graph.addVertex(v);
                    if(random.nextBoolean())
                        a.add(v);
                    else
                        b.add(v);
                }   break;
                case REMOVE: {
                    Integer v = graph.size();
                    graph.removeVertex(v);
                    a.remove(v);
                    b.remove(v);
                }   break;
                case ADD_EDGE: {
                    final Set<Integer> vertices = graph.getVertices();
                    for(int j = 0; j < random.nextInt(20); j++) {
                        final int v = vertices.stream().skip(random.nextInt(vertices.size()))
                            .findFirst()
                            .get();
                        final int w = vertices.stream().skip(random.nextInt(vertices.size()))
                            .findFirst()
                            .get();
                        graph.addEdge(v, w, true);
                    }
                }   break;
                case REMOVE_EDGE: {
                    final Set<Integer> vertices = graph.getVertices();
                    for(int j = 0; j < random.nextInt(20); j++) {
                        final int v = vertices.stream().skip(random.nextInt(vertices.size()))
                            .findFirst()
                            .get();
                        final int w = vertices.stream().skip(random.nextInt(vertices.size()))
                            .findFirst()
                            .get();
                        graph.removeEdge(v, w);
                    }
                }   break;
                case TRANSPOSE: {
                    final int before = BarrierCrossings.countCrossings(graph, a, b);
                    reduction.reduce(graph, List.of(a, b));
                    assertTrue(before >= BarrierCrossings.countCrossings(graph, a, b));
                }   break;
            }
        }
    }

    private enum Operation {
        INSERT, REMOVE, ADD_EDGE, REMOVE_EDGE, TRANSPOSE
    }
}
