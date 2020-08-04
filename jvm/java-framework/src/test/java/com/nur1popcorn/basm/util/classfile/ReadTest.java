package com.nur1popcorn.basm.util.classfile;

import com.nur1popcorn.basm.classfile.*;
import com.nur1popcorn.basm.classfile.attributes.*;
import com.nur1popcorn.basm.classfile.constants.ConstantInfo;
import com.nur1popcorn.basm.classfile.constants.ConstantPoolPointer;
import com.nur1popcorn.basm.classfile.constants.ConstantVisitor;
import com.nur1popcorn.basm.classfile.tree.Type;
import com.nur1popcorn.basm.classfile.tree.methods.InstructionList;
import org.junit.Test;

import java.io.*;

import static com.nur1popcorn.basm.classfile.ClassReader.READ_ALL;
import static org.junit.Assert.assertTrue;

/**
 * The {@link ReadTest} class attempts to find a difference in the bytecode produced by the class writer
 * and the original bytecode. Once such a difference has been found it will attempt to find the root cause
 * of this difference.
 *
 * @author nur1popcorn
 * @since 1.1.0-alpha
 */
public final class ReadTest {
    /* A set of classes which should be compared. */
    private static final Class<?> CLASSES[] =
        {ReadTest.class, ConstantPool.class, ClassReader.class, Opcode.class,
         AttributeInfo.class, ConstantInfo.class, AccessFlags.class, Type.class,
         InstructionList.class, ClassWriter.class, AttributeCode.class, AttributeStackMapTable.class,
         ClassVisitor.class, AttributeVisitor.class, ConstantPoolPointer.class, ConstantVisitor.class };

    @Test
    public void testBasic() throws IOException {
        boolean flag = true;
        for(Class<?> clazz : CLASSES) {
            System.out.print("Testing ");
            System.out.println(clazz.getSimpleName());
            flag &= read(clazz);
        }
        assertTrue(flag);
    }

    /**
     * Opens an input stream to of the given class file.
     *
     * @param clazz The class which should be opened.
     * @return The input stream of the class.
     */
    private static DataInputStream open(Class<?> clazz) {
        InputStream in = clazz.getResourceAsStream(clazz.getSimpleName() + ".class");
        return new DataInputStream(in);
    }

    /**
     * Reads the class files compares the bytes and dispatches the tracer if something went wrong.
     *
     * @param clazz The class which should be compared.
     * @return True if everything went well and no difference was found false otherwise.
     *
     * @throws IOException if an error occurs during the process of reading.
     */
    private static boolean read(Class<?> clazz) throws IOException {
        DataInputStream din = open(clazz);
        int index = traceDifference(din);
        din.close();
        if(index != -1) {
            din = open(clazz);
            traceCause(din, index);
            din.close();
            return false;
        }
        return true;
    }

    /**
     * Searches for a difference in the produced AOB.
     *
     * @param in The input stream from which the class file is the be read.
     * @return The index at which the difference occurred.
     *
     * @throws IOException if an error occurs during the process of reading.
     */
    private static int traceDifference(DataInputStream in) throws IOException {
        final byte a[] = in.readAllBytes();
        final ClassReader cr = new ClassReader(new ByteArrayInputStream(a));
        final ClassWriter cw = new ClassWriter();
        final TestVisitor visitor = new TestVisitor(cw);
        cr.accept(visitor, READ_ALL);

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        cw.write(bos);
        final byte b[] = bos.toByteArray();

        for(int i = 0; i < a.length && i < b.length; i++)
            if(a[i] != b[i])
                return i;

        if(a.length != b.length)
            return Math.min(a.length, b.length);
        return -1;
    }

    /**
     * Attempts to find the reason that a given index has been corrupted.
     *
     * @param in The input stream from which the reference class file is the read.
     * @param index The corrupt index.
     *
     * @throws IOException if an error occurs during the process of reading.
     */
    private static void traceCause(DataInputStream in, int index) throws IOException {
        final OutputStream out = new OutputStream() {
            private int current;

            @Override
            public synchronized void write(int b) {
                if(current++ == index) {
                    final StackTraceElement elements[] =
                        Thread.currentThread()
                            .getStackTrace();
                    for(StackTraceElement el : elements) {
                        System.out.print("\t");
                        System.out.println(el);
                    }
                    System.out.println();
                }
            }
        };

        final ClassReader cr = new ClassReader(in);
        final ClassWriter cw = new ClassWriter();
        final TestVisitor visitor = new TestVisitor(cw);
        cr.accept(visitor, READ_ALL);
        cw.write(out);
    }

    public static class TestVisitor extends ClassVisitorDecorator {
        private ConstantPool constantPool;

        public TestVisitor(ClassVisitor writer) {
            super(writer);
        }

        @Override
        public void visitHead(int minorVersion, int majorVersion, ConstantPool constantPool) {
            this.constantPool = constantPool;
            super.visitHead(minorVersion, majorVersion, constantPool);
        }

        @Override
        public AttributeVisitor visitMethod(FieldMethodInfo method) {
            return new TestMethodVisitor(super.visitMethod(method), constantPool);
        }
    }

    public static class TestMethodVisitor extends AttributeVisitorDecorator {
        private ConstantPool constantPool;

        public TestMethodVisitor(AttributeVisitor parent, ConstantPool constantPool) {
            super(parent);
            this.constantPool = constantPool;
        }
    }
}
