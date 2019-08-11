package com.nur1popcorn.basm.util;

import com.nur1popcorn.basm.utils.WeakHashSet;
import org.junit.Test;

import java.lang.ref.WeakReference;

import static org.junit.Assert.*;

public final class TestWeakHashSet {
    @Test
    public void testAdd() {
        final WeakHashSet<Object> set = new WeakHashSet<>();
        final Object object = new Object();
        set.add(object);
        assertFalse(set.isEmpty());
    }

    @Test
    public void testContains() {
        final WeakHashSet<Object> set = new WeakHashSet<>();
        final Object object = new Object();
        set.add(object);
        assertTrue(set.contains(object));
    }

    @Test
    public void testGC() throws InterruptedException {
        final WeakHashSet<Object> set = new WeakHashSet<>();
        Object object = new Object();
        set.add(object);
        assertFalse(set.isEmpty());
        final WeakReference<Object> reference = new WeakReference<>(object);
        object = null;
        while(reference.get() != null) {
            System.gc();
            System.runFinalization();
        }
        Thread.yield();
        Thread.sleep(250);
        assertTrue(set.isEmpty());
    }

    @Test
    public void testRemove() {
        final WeakHashSet<Object> set = new WeakHashSet<>();
        final Object object = new Object();
        set.add(object);
        assertFalse(set.isEmpty());
        set.remove(object);
        assertTrue(set.isEmpty());
    }
}
