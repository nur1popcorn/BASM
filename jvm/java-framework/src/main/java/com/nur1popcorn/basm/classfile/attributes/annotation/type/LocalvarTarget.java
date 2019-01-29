package com.nur1popcorn.basm.classfile.attributes.annotation.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LocalvarTarget extends TargetInfo {
    private final Entry[] table;

    public LocalvarTarget(DataInputStream in) throws IOException  {
        table = new Entry[in.readUnsignedShort()];
        for (int i = 0; i < table.length; i++)
            table[i] = new Entry(in);
    }

    public void write(DataOutputStream os) throws IOException {
        os.writeShort(table.length);
        for (Entry entry : table)
            entry.write(os);
    }

    public static final class Entry {
        private final int startPC,
                          length,
                          index;

        public Entry(DataInputStream in) throws IOException {
            startPC = in.readUnsignedShort();
            length = in.readUnsignedShort();
            index = in.readUnsignedShort();
        }

        public void write(DataOutputStream os) throws IOException {
            os.writeShort(startPC);
            os.writeShort(length);
            os.writeShort(index);
        }
    }
}
