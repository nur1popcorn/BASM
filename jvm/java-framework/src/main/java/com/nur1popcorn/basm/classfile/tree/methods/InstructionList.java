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

package com.nur1popcorn.basm.classfile.tree.methods;

import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.MalformedClassFileException;
import com.nur1popcorn.basm.classfile.Opcode;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.IInstructionVisitor;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.factory.InstructionFactory;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.Label;
import com.nur1popcorn.basm.utils.ByteDataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * The {@link InstructionList} class is derived from the abstract {@link AbstractList} class and
 * implements a resizeable array and linked list made up of {@link Instruction}s. These
 * {@link Instruction}s allow for {@link Instruction}s to be recycled and provide iterability.
 * The list can be modified by invoking any of the set(), add() and remove() methods, where removing
 * {@link Instruction}s might result in an {@link InstructionLostException} being thrown.
 * Further more, the list's {@link Instruction}'s can be transversed by invoking the accept()
 * method. Deserialization is handled by a package local constructor, where as serialization is
 * handled by the write() method.
 *
 * @see #write(DataOutputStream)
 * @see #accept(IInstructionVisitor)
 *
 * @see #get(int)
 * @see #set(int, Instruction)
 * @see #add(int, Instruction)
 * @see #remove(int)
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class InstructionList extends AbstractList<Instruction> implements Cloneable {
    /* This constant denotes the average number of instruction per method in the runtime classes that
     * conglomerate the Java core.
     *
     */
    private static final int DEFAULT_SIZE = 37;

    private Instruction first, last;
    private Instruction instructions[];
    private int size;

    /**
     * Constructs a new {@link InstructionList}, reads the {@link Instruction}s from the specified
     * byte code, inserts them into mentioned list and links them together.
     *
     * @param code The serialized {@link Instruction}s which should be read and added to the list.
     * @param constantPool The {@link ConstantPool} referenced by the read {@link Instruction}s.
     *
     * @see InstructionFactory#read(ByteDataInputStream, ConstantPool)
     *
     * @throws IOException If an error occurs during the process of reading from the {@link ByteDataInputStream}.
     * @throws MalformedClassFileException If an {@link Instruction} is unknown or malformed.
     */
    InstructionList(byte code[], ConstantPool constantPool) throws IOException {
        final ByteDataInputStream in = new ByteDataInputStream(code);
        int length = 0;
        for(;in.available() != 0; length++)
            in.skip(Opcode.valueOf(in.readByte()));
        in.reset();

        instructions = new Instruction[length];
        for(int i = 0; i < length; i++)
            add(InstructionFactory.read(in, constantPool));

        final Label labels[] = in.getLabels();
        for(int i = 0, count = 0; i < length; i++) {
            final Instruction instruction = instructions[i + count];
            final Label label = labels[instruction.getOffset()];
            if(label != null)
                add(i + count++, label);
        }
    }

    /**
     * Constructs a new empty {@link InstructionList} instance.
     * @param size The {@link InstructionList}'s initial capacity.
     */
    public InstructionList(int size) {
        instructions = new Instruction[size];
    }

    /**
     * Constructs a new empty {@link InstructionList} instance with default size 37.
     */
    public InstructionList() {
        this(DEFAULT_SIZE);
    }

    /**
     * Writes the {@link InstructionList}'s length and entries to the given {@link DataOutputStream}.
     *
     * @param os The {@link DataOutputStream} the {@link InstructionList} should be written to.
     * @throws IOException If an error occurs during the process of writing to the {@link DataOutputStream}.
     */
    public void write(DataOutputStream os) throws IOException {
        for(Instruction instruction : this)
            instruction.write(os);
    }

    /**
     * Accepts a {@link IInstructionVisitor}, transverses the {@link InstructionList} and calls the
     * for the {@link Instruction} appropriate 'visitXXX()' methods to notify the visitor of
     * what type of {@link Instruction} is being entered.
     *
     * @param visitor The {@link IInstructionVisitor} whose callbacks will be invoked.
     */
    public void accept(IInstructionVisitor visitor) {
        for(Instruction i : this)
            i.accept(visitor);
    }

    /**
     * @return The number of instructions stored in the {@link InstructionList}.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @param index The index of the {@link Instruction} which should be returned.
     * @return The {@link Instruction} at the given index.
     */
    @Override
    public Instruction get(int index) {
        rangeCheck(index);
        return instructions[index];
    }

    /**
     * Replaces the {@link Instruction} at the given index.
     *
     * @param index The index of the {@link Instruction} which is to be replaced.
     * @param element The {@link Instruction} with which the other {@link Instruction}
     *                is to be replaced.
     *
     * @return The {@link Instruction} which previously occupied the given index.
     */
    @Override
    public Instruction set(int index, Instruction element) {
        modCount++;
        rangeCheck(index);
        final Instruction old = instructions[index];
        instructions[index] = element;
        if(old instanceof Label && element instanceof Label) {
            final IInstructionPointer pointers[] = ((Label)old).getPointers();
            for(IInstructionPointer pointer : pointers)
                ((Label)element).addPointer(pointer);
        }
        if((element.prev = old.prev) == null)
            first = element;
        else {
            element.updateOffset();
            element.prev.next = element;
        }
        if((element.next = old.next) == null)
            last = element;
        else {
            element.next.prev = element;
            if(element.getLength() - old.getLength() != 0)
                for(int i = index + 1; i < size; i++)
                    instructions[i].updateOffset();
        }
        return old;
    }

    /**
     * Inserts the {@link Instruction} at the given index.
     *
     * @param index The index at which the {@link Instruction} is to be inserted.
     * @param element The {@link Instruction} which is to be inserted.
     */
    @Override
    public void add(int index, Instruction element) {
        rangeCheckAdd(index);
        final int oldSize = size;
        grow(++size);
        System.arraycopy(
            instructions, index,
            instructions, index + 1, oldSize - index
        );
        instructions[index] = element;
        if(oldSize == 0)
            last = first = element;
        else if(oldSize == index) {
            last = (element.prev = instructions[index - 1])
                .next = element;
            element.updateOffset();
        } else if(index == 0)
            first = (element.next = first)
                .prev = element;
        else {
            (((element.prev = instructions[index - 1])
                .next = element)
                .next = instructions[index + 1])
                .prev = element;
            element.updateOffset();
        }
        if(element.getLength() != 0)
            for(int i = index + 1; i < size; i++)
                instructions[i].updateOffset();
    }

    /**
     * Removes the {@link Instruction} at the specified index from the {@link InstructionList}.
     *
     * @param index The index of the {@link Instruction} which is to be removed.
     * @throws InstructionLostException If the number of {@link IInstructionPointer}s pointing at the to
     *         be removed {@link Instruction} is greater than 0.
     * @return The {@link Instruction} which was removed.
     */
    @Override
    public Instruction remove(int index) {
        modCount++;
        rangeCheck(index);
        final Instruction old = instructions[index];
        if(old instanceof Label) {
            final Label label = (Label)old;
            if(label.hasPointers())
                throw new InstructionLostException(
                    label.getPointers());
        }
        final int moved = size - index - 1;
        if(moved > 0)
            System.arraycopy(
                instructions, index + 1,
                instructions, index, moved
            );
        instructions[--size] = null;
        if(size == 0)
            first = last = null;
        else if(size == index)
            (last = instructions[index - 1])
                .next = null;
        else {
            final Instruction element = instructions[index];
            if ((element.prev = old.prev) == null)
                first = element;
            else
                element.prev.next = element;
            if(old.getLength() != 0)
                for(int i = index; i < size; i++)
                    instructions[i].updateOffset();
        }
        return old;
    }

    /**
     * @return The first {@link Instruction} in the list.
     */
    public Instruction getFirst() {
        return first;
    }

    /**
     * @return The last {@link Instruction} in the list.
     */
    public Instruction getLast() {
        return last;
    }

    /**
     * @param index The index which should be tested.
     * @throws IndexOutOfBoundsException If the index is not in range of the instruction array.
     */
    private void rangeCheck(int index) {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException(
                "index=" + index + ", size=" + size);
    }

    /**
     * @param index The index which should be tested.
     * @throws IndexOutOfBoundsException If the index is not the size of the array or in range of the
     *                                   instruction array.
     */
    private void rangeCheckAdd(int index) {
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException(
                "index=" + index + ", size=" + size);
    }

    /**
     * Grows the instruction array if the given value is bigger than the capacity of said array.
     * @param size The minimum capacity.
     */
    private void grow(int size) {
        modCount++;
        final Instruction old[] = instructions;
        if(old.length < size)
            instructions = Arrays.copyOf(
                old,
                size << 1
            );
    }

    /**
     * @return A clone of this {@link InstructionList}'s instance.
     */
    public Object clone() throws CloneNotSupportedException {
        final InstructionList il = (InstructionList) super.clone();
        il.instructions = Arrays.copyOf(instructions, size);
        il.modCount = 0;
        return il;
    }
}
