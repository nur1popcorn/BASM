package com.nur1popcorn.basm.utils.graph;

import java.util.Set;

public class DirectedGraphAdapter<V, E> implements DirectedGraph<V, E> {
    private final SimpleGraph<V, E> graph;

    public DirectedGraphAdapter(SimpleGraph<V, E> graph) {
        this.graph = graph;
    }

    @Override
    public void addVertex(V v) {
        graph.addVertex(v);
    }

    @Override
    public void removeVertex(V v) {
        graph.removeVertex(v);
    }

    @Override
    public boolean hasVertex(V v) {
        return graph.hasVertex(v);
    }

    @Override
    public void addEdge(V v, V w) {
        graph.addEdge(v, w);
    }

    @Override
    public void removeEdge(V v, V w) {
        graph.removeEdge(v, w);
    }

    @Override
    public boolean hasEdge(V v, V w) {
        return graph.hasEdge(v, w);
    }

    @Override
    public E getEdge(V v, V w) {
        return graph.getEdge(v, w);
    }

    @Override
    public int degree(V v) {
        return graph.degree(v);
    }

    @Override
    public int size() {
        return graph.size();
    }

    @Override
    public Set<V> getNeighbours(V v) {
        return graph.getNeighbours(v);
    }

    @Override
    public Set<V> getVertices() {
        return graph.getVertices();
    }

    @Override
    public int getEdgeCount() {
        return graph.getEdgeCount();
    }

    @Override
    public int getInDegree(V v) {
        return graph.degree(v);
    }

    @Override
    public int getOutDegree(V v) {
        return graph.degree(v);
    }

    @Override
    public Set<V> getInNeighbours(V v) {
        return graph.getNeighbours(v);
    }

    @Override
    public Set<V> getOutNeighbours(V v) {
        return graph.getNeighbours(v);
    }
}
