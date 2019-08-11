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

import com.nur1popcorn.basm.classfile.tree.ClassFile;

import java.io.*;
import java.net.URL;
import java.util.*;

public final class ClassPool {
    private final List<String> paths = new LinkedList<>();
    private final Map<String, ClassFile> cache = new HashMap<>();

    public ClassPool() {
        addPaths(
            System.getProperty("sun.boot.class.path")
                .split(File.pathSeparator));
        addPaths(
            System.getProperty("java.class.path")
                .split(File.pathSeparator));
        addChildren(
            System.getProperty("java.ext.dirs")
                .split(File.pathSeparator));
    }

    public void addChildren(String... paths) {
        for(String path : paths) {
            final File[] files = new File(path)
                .listFiles();
            if(files == null)
                continue;
            addFiles(files);
        }
    }

    public void addPaths(String... paths) {
        for(String path : paths)
            addFiles(new File(path));
    }

    public void addFiles(File... files) {
        for(File file : files)
            if(file.exists()) {
                final String uri = file.toURI().toString();
                if(file.isFile() && ensureZip(file))
                    paths.add("jar:" + uri + "!/");
                else if(file.isDirectory())
                    paths.add(uri);
            }
    }

    public ClassFile find(String name) {
        final String fileName = name.replace('.', '/') + ".class";
        return cache.computeIfAbsent(fileName, n -> {
            for(String path : paths)
                try {
                    return new ClassFile(new URL(path + fileName).openStream());
                } catch (IOException ignored) { }
            throw new IllegalArgumentException(
                "Could not find fileName=" + fileName + ".");
        });
    }

    private static boolean ensureZip(File file) {
        try(DataInputStream in = new DataInputStream(new FileInputStream(file))) {
            return in.readInt() == 0x504b0304;
        } catch (IOException e) {
            return false;
        }
    }
}
