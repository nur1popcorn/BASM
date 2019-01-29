package com.nur1popcorn.basm.classfile.tree.methods;

import com.nur1popcorn.basm.Constants;
import com.nur1popcorn.basm.TestBase;
import com.nur1popcorn.basm.classfile.constants.ConstantInfo;
import com.nur1popcorn.basm.classfile.constants.ConstantUTF8;
import com.nur1popcorn.basm.classfile.tree.ConstantPoolGenerator;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.InstructionFactory;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class InstructionListTest extends TestBase {
    @Test
    public void testAdd() {
        final InstructionList il = new InstructionList();
        for(int i = 0; i < 38; i++)
            il.add(new InstructionHandle(InstructionFactory.NOP));
        assertEquals(il.size(), 38);
        il.add(0, new InstructionHandle(InstructionFactory.ACONST_NULL));
        il.add(4, new InstructionHandle(InstructionFactory.ICONST_M1));
        final InstructionHandle iha = il.getFirst();
        assertEquals(iha.getHandle().getOpcode(), Constants.ACONST_NULL);
        final InstructionHandle ihb = il.get(4);
        assertEquals(ihb.getHandle().getOpcode(), Constants.ICONST_M1);
    }

    @Test
    public void testSet() {
        final InstructionList il = new InstructionList();
        for(int i = 0; i < 16; i++)
            il.add(new InstructionHandle(InstructionFactory.NOP));
        il.set(0, new InstructionHandle(InstructionFactory.ACONST_NULL));
        il.set(7, new InstructionHandle(InstructionFactory.ICONST_M1));
        il.set(15, new InstructionHandle(InstructionFactory.ICONST_0));
        assertEquals(il.getFirst().getHandle().getOpcode(), Constants.ACONST_NULL);
        assertEquals(il.get(7).getHandle().getOpcode(), Constants.ICONST_M1);
        assertEquals(il.getLast().getHandle().getOpcode(), Constants.ICONST_0);
    }

    @Test
    public void testRemove() {
        final InstructionList il = new InstructionList();
        for(int i = 0; i < 16; i++)
            il.add(new InstructionHandle(InstructionFactory.NOP));

    }

    @Test
    public void testPointers() {

    }

    @Test
    public void testRead() throws IOException {
        final ConstantPoolGenerator cp =
            new ConstantPoolGenerator(new ConstantInfo[] {
                null,
                new ConstantUTF8("Test")
            });
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();

        {
            final DataOutputStream dos = new DataOutputStream(bos);
            dos.writeByte(Constants.NOP);
            dos.writeByte(Constants.NOP);
            dos.writeByte(Constants.NOP);
            //dos.writeByte(Constants.LDC);
            //dos.writeByte(cp.findUTF8("Test"));
        }

        final InstructionList control = new InstructionList();
        control.add(new InstructionHandle(InstructionFactory.NOP));
        control.add(new InstructionHandle(InstructionFactory.NOP));
        control.add(new InstructionHandle(InstructionFactory.NOP));
        // TODO: replace with getter call.
        final InstructionFactory factory = new InstructionFactory(cp);//control.getFactory();
        //control.add(new InstructionHandle(factory.createPush("Test")));

        final InstructionList il = new InstructionList(bos.toByteArray(), cp);
        assertEquals(il.size(), control.size());
        for(int i = 0; i < control.size(); i++)
            assertEquals(control.get(i).getHandle(), il.get(i).getHandle());
    }

    public void testOffset() {
        final InstructionList il = new InstructionList();
        il.add(new InstructionHandle(InstructionFactory.NOP));
        il.add(new InstructionHandle(InstructionFactory.createJump(Constants.GOTO, 2)));
        il.add(new InstructionHandle(InstructionFactory.NOP));
        il.add(new InstructionHandle(InstructionFactory.NOP));
        il.add(new InstructionHandle(InstructionFactory.NOP));
        il.set(1, new InstructionHandle(InstructionFactory.createJump(Constants.GOTO, 2)));
        final int array[] = { 0, 1, 4, 5, 6 };
        for(int i = 0; i < array.length; i++)
            assertEquals(il.get(i).offset, array[i]);
        il.add(3, new InstructionHandle(InstructionFactory.createJump(Constants.GOTO, 4)));
        final int array2[] = { 0, 1, 4, 5, 8, 9 };
        for(int i = 0; i < array2.length; i++)
            assertEquals(il.get(i).offset, array2[i]);
    }
}
