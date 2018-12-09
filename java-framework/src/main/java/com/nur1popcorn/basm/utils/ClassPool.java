package com.nur1popcorn.basm.utils;

import com.nur1popcorn.basm.classfile.tree.ClassFile;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class ClassPool {
    private final Set<String> paths = new HashSet<>();
    private final Map<String, ClassFile> cache = new HashMap<>();

    public ClassPool() {
        final String classPath = System.getProperty("java.class.path");
        addPaths(classPath);
    }

    public void addPaths(String... paths) {
        if (paths == null || paths.length == 0)
            return;
        for (String path : paths)
            if (path.contains(File.pathSeparator))
                for (String next : path.split(File.pathSeparator))
                    addPath(next);
            else
                addPath(path);
    }

    public void addPath(String path) {
        final File file = new File(path);
        if (file.isFile() && ensureZip(file))
            paths.add("jar:file:" + path + "!/");
        else if (file.isDirectory()) {
            if (!path.endsWith(File.separator))
                path += File.separator;
            paths.add("file:" + path);
        }
    }

    public ClassFile find(String name) {
        return cache.computeIfAbsent(name, n -> {
            final String fileName = name.replace('.', '/') + ".class";
            for (String path : paths)
                try {
                    return new ClassFile(
                        this,
                        new URL(path + fileName)
                            .openStream()
                    );
                } catch (IOException ignored) { }
            throw new RuntimeException("could not find file: " + fileName);
        });
    }

    private boolean ensureZip(File file) {
        try (InputStream in = new FileInputStream(file);
             DataInputStream din = new DataInputStream(in)) {
            return din.readInt() == 0x504b0304;
        } catch (IOException e) {
            return false;
        }
    }
}
