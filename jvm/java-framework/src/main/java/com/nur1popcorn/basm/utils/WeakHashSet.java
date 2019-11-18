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

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.WeakHashMap;

/**
 * The {@link WeakHashSet} class is derived from the abstract {@link AbstractSet} class and
 * mainly bases it's implementation on a {@link WeakHashMap}. It therefore makes no guarantees
 * on iteration order or the order in which objects are stored. The class is mostly used to
 * cleanup straggler pointers to a specific object.
 *
 * @see com.nur1popcorn.basm.classfile.tree.methods.Instruction
 * @see com.nur1popcorn.basm.classfile.constants.ConstantInfo
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class WeakHashSet<E> extends AbstractSet<E> {
    /* A dummy object used to used as a value for the map backing the WeakHashMap.
     */
    private static final Object DUMMY = new Object();

    private final WeakHashMap<E, Object> map;

    /**
     * Constructs a new empty {@link WeakHashSet} instance with default size 16 and load factor 0.75.
     */
    public WeakHashSet() {
        map = new WeakHashMap<>();
    }

    /**
     * Constructs a new empty {@link WeakHashSet} instance with the default load factor 0.75.
     * @param initialCapacity The {@link WeakHashSet}'s initial capacity.
     */
    public WeakHashSet(int initialCapacity) {
        map = new WeakHashMap<>(initialCapacity);
    }

    /**
     * Constructs a new empty {@link WeakHashSet} instance.
     *
     * @param initialCapacity The {@link WeakHashSet}'s initial capacity.
     * @param loadFactor The {@link WeakHashSet}'s load factor.
     */
    public WeakHashSet(int initialCapacity, float loadFactor) {
        map = new WeakHashMap<>(initialCapacity, loadFactor);
    }

    /**
     * @return The number of objects stored in the {@link WeakHashSet}.
     */
    @Override
    public int size() {
        return map.size();
    }

    /**
     * @return Whether the {@link WeakHashSet} is empty.
     */
    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * @param o The element which should be tested.
     * @return Whether the {@link WeakHashSet} contains the given object.
     */
    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    /**
     * @param e The element which should be added to this {@link WeakHashSet}.
     * @return Whether the element was already contained within the {@link WeakHashSet}.
     */
    @Override
    public boolean add(E e) {
        return map.put(e, DUMMY) == null;
    }

    /**
     * @param o The element which should be removed from this {@link WeakHashSet}.
     * @return Whether the {@link WeakHashSet} contained the given element.
     */
    @Override
    public boolean remove(Object o) {
        return map.remove(o) == DUMMY;
    }

    /**
     * Removes all elements from the {@link WeakHashSet}.
     */
    @Override
    public void clear() {
        map.clear();
    }

    /**
     * @return An iterator over the elements of this {@link WeakHashSet} whose order is not specified.
     */
    @Override
    public Iterator<E> iterator() {
        return map.keySet()
                  .iterator();
    }
}
