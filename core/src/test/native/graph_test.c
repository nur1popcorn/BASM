/*
 * Copyright (C) Keanu Poeschko - All Rights Reserved
 * Unauthorized copying of this file is strictly prohibited
 *
 * Created by Keanu Poeschko <nur1popcorn@gmail.com>, August 2017
 * This file is part of {BASM}.
 *
 * Do not copy or distribute files of {BASM} without permission of {Keanu Poeschko}
 *
 * Permission to use, copy, modify, and distribute my software for
 * educational, and research purposes, without a signed licensing agreement
 * and for free, is hereby granted, provided that the above copyright notice
 * and this paragraph appear in all copies, modifications, and distributions.
 *
 * {BASM} is based on this document: https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html
 *
 */

#include "graph.h"

#include <assert.h>
#include <stdlib.h>

#include <stdio.h>

int main(int argc, char **argv) {
    {
        struct Graph *graph = Graph_new(1337);

        for (int i = 0; i < 1337; i++)
            Graph_add_edge(graph, i, (i + 1) % 1337);

        for (int i = 0; i < 1337; i++)
            assert(Graph_is_connected(graph, i, (i + 1) % 1337));
        Graph_delete(graph);
    }

    {
        struct Graph *graph = Graph_new(3);
        Graph_add_edge(graph, 0, 1);
        Graph_add_edge(graph, 1, 2);
        Graph_add_edge(graph, 2, 0);

        int *colors = Graph_greedy_coloring(graph);

        for(int i = 0; i < 3; i++)
            assert(colors[i] == i);

        free(colors);
        Graph_delete(graph);
    }

    {
        struct Graph *graph = Graph_new(8);
        Graph_add_edge(graph, 0, 3);
        Graph_add_edge(graph, 0, 5);
        Graph_add_edge(graph, 0, 7);

        Graph_add_edge(graph, 2, 1);
        Graph_add_edge(graph, 2, 5);
        Graph_add_edge(graph, 2, 7);

        Graph_add_edge(graph, 4, 1);
        Graph_add_edge(graph, 4, 3);
        Graph_add_edge(graph, 4, 7);

        Graph_add_edge(graph, 6, 1);
        Graph_add_edge(graph, 6, 3);
        Graph_add_edge(graph, 6, 5);

        int *colors = Graph_greedy_coloring(graph);

        for(int i = 0; i < 8; i += 2)
            assert(colors[i] == colors[i + 1]);

        free(colors);
        Graph_delete(graph);
    }
}
