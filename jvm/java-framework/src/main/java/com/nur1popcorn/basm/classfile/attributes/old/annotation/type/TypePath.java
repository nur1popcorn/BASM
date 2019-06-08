package com.nur1popcorn.basm.classfile.attributes.annotation.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TypePath {
    private final Path[] paths;

    public TypePath(DataInputStream in) throws IOException {
        paths = new Path[in.readUnsignedByte()];
        for (int i = 0; i < paths.length; i++)
            paths[i] = new Path(in);
    }

    public void write(DataOutputStream os) throws IOException {
        os.writeByte(paths.length);
        for (Path path : paths)
            path.write(os);
    }

    public static final class Path {
        private final int typePathKind,
                          typeArgumentIndex;

        public Path(DataInputStream in) throws IOException {
            typePathKind = in.readUnsignedByte();
            typeArgumentIndex = in.readUnsignedByte();
        }

        public void write(DataOutputStream os) throws IOException {
            os.writeByte(typePathKind);
            os.writeByte(typeArgumentIndex);
        }
    }
}
