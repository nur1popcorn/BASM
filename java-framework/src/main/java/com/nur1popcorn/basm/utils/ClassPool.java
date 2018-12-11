package com.nur1popcorn.basm.utils;

import com.nur1popcorn.basm.classfile.ClassReader;
import com.nur1popcorn.basm.classfile.tree.ClassFile;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.nur1popcorn.basm.classfile.ClassReader.READ_ALL;

public final class ClassPool {
    private final Set<String> paths = new HashSet<>();
    private final Map<String, ClassFile> cache = new HashMap<>();

    public ClassPool() {
        addPaths(parseSystemProperty("sun.boot.class.path"));
        addChilds(parseSystemProperty("java.ext.dirs"));
        addPaths(parseSystemProperty("java.class.path"));
    }

    private String[] parseSystemProperty(String key) {
        final String property = System.getProperty(key);
        return property.split(File.pathSeparator);
    }

    public void addChilds(String... paths) {
        if (paths == null || paths.length == 0)
            return;
        for (String path : paths) {
            final File child = new File(path);
            addFiles(child.listFiles());
        }
    }

    public void addPath(String path) {
        addFile(new File(path));
    }

    public void addPaths(String... paths) {
        if (paths == null || paths.length == 0)
            return;
        for (String path : paths)
            addPath(path);
    }

    public void addFile(File file) {
        if (file.exists()) {
            final String uri =  file.toURI().toString();
            if (file.isFile() && ensureZip(file))
                paths.add("jar:" + uri + "!/");
            else if (file.isDirectory())
                paths.add(uri);
        }
    }

    public void addFiles(File... files) {
        if (files == null || files.length == 0)
            return;
        for (File file : files)
            addFile(file);
    }

    public ClassFile find(String name) {
        return cache.computeIfAbsent(name, n -> {
            final String fileName = name.replace('.', '/') + ".class";
            for (String path : paths)
                try {
                    final ClassFile classFile = new ClassFile(this);
                    final InputStream in = new URL(path + fileName).openStream();
                    new ClassReader(in)
                        .accept(
                            classFile,
                            READ_ALL
                        );
                    return classFile;
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
