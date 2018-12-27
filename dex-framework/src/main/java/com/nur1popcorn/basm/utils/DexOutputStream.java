package com.nur1popcorn.basm.utils;

import java.io.IOException;
import java.io.OutputStream;

public abstract class DexOutputStream extends OutputStream {
    private OutputStream out;
    private long position;

    private DexOutputStream(OutputStream out) {
        this.out = out;
    }

    /**
     * Writes 0x00 until the position is aligned to a multiple of 4.
     */
    public void alignToFourBytesWithZeroFill() throws IOException {
        while ((position & 0x3) != 0)
            write(0);
    }

    public void writeByte(byte b) throws IOException {
        write(b);
    }

    public abstract void writeShort(short n) throws IOException;
    public abstract void writeInt(int n) throws IOException;
    public abstract void writeLong(long n) throws IOException;

    @Override
    public void write(int b) throws IOException {
        position++;
        out.write(b);
    }

    @Override
    public void flush() throws IOException {
        out.flush();
    }

    @Override
    public void close() throws IOException {
        out.close();
    }

    private static final class LittleEndianOutputStream extends DexOutputStream {
        private LittleEndianOutputStream(OutputStream out) {
            super(out);
        }

        @Override
        public void writeShort(short n) throws IOException {
            write(n & 0xff);
            write((n >> 8) & 0xff);
        }

        @Override
        public void writeInt(int n) throws IOException {
            write(n & 0xff);
            write((n >> 8) & 0xff);
            write((n >> 16) & 0xff);
            write((n >> 24) & 0xff);
        }

        @Override
        public void writeLong(long n) throws IOException {
            write((byte) (n & 0xff));
            write((byte) ((n >> 8L) & 0xff));
            write((byte) ((n >> 16L) & 0xff));
            write((byte) ((n >> 24L) & 0xff));
            write((byte) ((n >> 32L) & 0xff));
            write((byte) ((n >> 40L) & 0xff));
            write((byte) ((n >> 48L) & 0xff));
            write((byte) ((n >> 56L) & 0xff));
        }
    }

    private static final class BigEndianOutputStream extends DexOutputStream {
        private BigEndianOutputStream(OutputStream out) {
            super(out);
        }

        @Override
        public void writeShort(short n) throws IOException {
            write((n >> 8) & 0xff);
            write(n & 0xff);
        }

        @Override
        public void writeInt(int n) throws IOException {
            write((n >> 24) & 0xff);
            write((n >> 16) & 0xff);
            write((n >> 8) & 0xff);
            write(n & 0xff);
        }

        @Override
        public void writeLong(long n) throws IOException {
            write((byte) ((n >> 56L) & 0xff));
            write((byte) ((n >> 48L) & 0xff));
            write((byte) ((n >> 40L) & 0xff));
            write((byte) ((n >> 32L) & 0xff));
            write((byte) ((n >> 24L) & 0xff));
            write((byte) ((n >> 16L) & 0xff));
            write((byte) ((n >> 8L) & 0xff));
            write((byte) (n & 0xff));
        }
    }
}
