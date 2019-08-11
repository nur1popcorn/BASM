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

package com.nur1popcorn.basm.utils;

import java.util.Map;
import java.util.TreeMap;

public final class BKTree<T> {
    private Node<T> root;
    private final EditDistanceStrategy<? super T> strategy;

    public BKTree(EditDistanceStrategy<? super T> strategy) {
        this.strategy = strategy;
    }

    public void add(T element) {

    }

    public EditDistanceStrategy<? super T> getStrategy() {
        return strategy;
    }

    private static final class Node<T> {
        private final T value;
        private final Map<Integer, Node<T>> children = new TreeMap<>();

        public Node(T value) {
            this.value = value;
        }
    }
}

