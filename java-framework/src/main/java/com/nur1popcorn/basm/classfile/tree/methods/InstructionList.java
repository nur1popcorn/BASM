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
import com.nur1popcorn.basm.classfile.tree.methods.instructions.IInstructionVisitor;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.Instruction;
import com.nur1popcorn.basm.utils.ByteDataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.AbstractList;
import java.util.Arrays;

/**
 * The {@link InstructionList} class is derived from the abstract {@link AbstractList} class and
 * implements a resizeable array and linked list made up of {@link InstructionHandle}s. These
 * {@link InstructionHandle}s allow for {@link Instruction}s to be recycled and provide iterability.
 * The list can be modified by invoking any of the set(), add() and remove() methods, where removing
 * {@link InstructionHandle}s might result in an {@link InstructionLostException} being thrown.
 * Further more, the list's {@link Instruction}'s can be transversed by invoking the accept()
 * method. Deserialization is handled by a package local constructor, where as serialization is
 * handled by the write() method.
 *
 * @see #write(DataOutputStream)
 * @see #accept(IInstructionVisitor)
 *
 * @see #get(int)
 * @see #set(int, InstructionHandle)
 * @see #add(int, InstructionHandle)
 * @see #remove(int)
 *
 * @author nur1popcorn
 * @since 1.0.0-alpha
 */
public final class InstructionList extends AbstractList<InstructionHandle> implements Cloneable {
    /* This constant denotes the average number of instruction per method in the runtime classes that
     * conglomerate the Java core.
     *
     */
    private static final int DEFAULT_SIZE = 37;

    private InstructionHandle first, last;
    private InstructionHandle instructions[];
    private int size;

    /**
     * Constructs a new {@link InstructionList}, reads the {@link Instruction}s from the specified
     * byte code, inserts them into mentioned list and links them together.
     *
     * @param code The serialized {@link Instruction}s which should be read and added to the list.
     * @param constantPool The {@link ConstantPool} referenced by the read {@link Instruction}s.
     *
     * @see Instruction#read(ByteDataInputStream, ConstantPool)
     *
     * @throws IOException If an error occurs during the process of reading from the {@link ByteDataInputStream}.
     * @throws MalformedClassFileException If an {@link Instruction} is unknown or malformed.
     */
    InstructionList(byte code[], ConstantPool constantPool) throws IOException {
        final ByteDataInputStream in = new ByteDataInputStream(code);
        int length = 0;
        while(in.available() != 0) {
            in.skipInstruction();
            length++;
        }
        instructions = new InstructionHandle[length];
        in.reset();
        for(int i = 0; i < length; i++)
            add(new InstructionHandle(
                Instruction.read(in, constantPool)));
    }

    /**
     * Constructs a new empty {@link InstructionList} instance.
     * @param size The {@link InstructionList}'s initial capacity.
     */
    public InstructionList(int size) {
        instructions = new InstructionHandle[size];
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
        for(InstructionHandle ih : this)
            ih.getHandle()
              .write(os, this);
    }

    /**
     * Accepts a {@link IInstructionVisitor}, transverses the {@link InstructionList} and calls the
     * for the {@link Instruction} appropriate 'visitXXX()' methods to notify the visitor of
     * what type of {@link Instruction} is being entered.
     *
     * @param visitor The {@link IInstructionVisitor} whose callbacks will be invoked.
     */
    public void accept(IInstructionVisitor visitor) {
        for(InstructionHandle ih : this)
            ih.getHandle()
              .accept(visitor);
    }

    /**
     * @return The number of instructions stored in the {@link InstructionList}.
     */
    @Override
    public int size() {
        return size;
    }

    public int getRealSize() {
        if(last != null)
            return last.getOffset() + last.getLength();
        return 0;
    }

    /**
     * @param index The index of the {@link Instruction} which should be returned.
     * @return The {@link Instruction} at the given index.
     */
    @Override
    public InstructionHandle get(int index) {
        rangeCheck(index);
        return instructions[index];
    }

    /**
     * Replaces the {@link InstructionHandle} at the given index.
     *
     * @param index The index of the {@link InstructionHandle} which is to be replaced.
     * @param element The {@link InstructionHandle} with which the other {@link InstructionHandle}
     *                is to be replaced.
     *
     * @return The {@link Instruction} which previously occupied the given index.
     */
    @Override
    public InstructionHandle set(int index, InstructionHandle element) {
        modCount++;
        rangeCheck(index);
        final InstructionHandle old = instructions[index];
        instructions[index] = element;
        final IInstructionPointer pointers[] = old.getPointers();
        for(IInstructionPointer pointer : pointers)
            element.addPointer(pointer);
        if((element.prev = old.prev) == null)
            first = element;
        if((element.next = old.next) == null)
            last = element;
        if(index != 0)
            element.offset = old.getOffset();
        final int diff = element.getLength() - old.getLength();
        if(diff != 0)
            for(int i = index + 1; i < size; i++)
                instructions[i].offset += diff;
        return old;
    }

    /**
     * Inserts the {@link InstructionHandle} at the given index.
     *
     * @param index The index at which the {@link InstructionHandle} is to be inserted.
     * @param element The {@link InstructionHandle} which is to be inserted.
     */
    @Override
    public void add(int index, InstructionHandle element) {
        rangeCheckAdd(index);
        final int oldSize = size;
        grow(++size);
        System.arraycopy(
            instructions, index,
            instructions, index + 1, oldSize - index
        );
        instructions[index] = element;
        if(oldSize == 0) {
            last = first = element;
            element.offset = 0;
        } else if(oldSize == index) {
            last = (element.prev = instructions[index - 1])
                .next = element;
            element.offset = getRealSize();
        } else if(index == 0) {
            first = (element.next = first)
                .prev = element;
            element.offset = 0;
        } else {
            final InstructionHandle prev = instructions[index - 1];
            (((element.prev = prev)
                .next = element)
                    .next = instructions[index + 1])
                        .prev = element;
            element.offset = prev.getOffset() + prev.getLength();
        }
        for(int i = index + 1; i < size; i++)
            instructions[i].offset += element.getLength();
    }

    /**
     * Removes the {@link InstructionHandle} at the specified index from the {@link InstructionList}.
     *
     * @param index The index of the {@link InstructionHandle} which is to be removed.
     * @throws InstructionLostException If the number of {@link IInstructionPointer}s pointing at the to
     *         be removed {@link InstructionHandle} is greater than 0.
     * @return The {@link InstructionHandle} which was removed.
     */
    @Override
    public InstructionHandle remove(int index) {
        modCount++;
        rangeCheck(index);
        final InstructionHandle old = instructions[index];
        final int moved = size - index - 1;
        if(moved > 0)
            System.arraycopy(
                instructions, index + 1,
                instructions, index, moved
            );
        instructions[--size] = null;
        if(size != 0) {
            final InstructionHandle element = instructions[index];
            if((element.prev = old.prev) == null)
                first = element;
            if((element.next = old.next) == null)
                last = element;
            for(int i = index; i < size; i++)
                instructions[i].offset -= old.getLength();
        }
        if(old.hasPointers())
            throw new InstructionLostException(old.getPointers());
        return old;
    }

    /**
     * @return The first {@link InstructionHandle} in the list.
     */
    public InstructionHandle getFirst() {
        return first;
    }

    /**
     * @return The last {@link InstructionHandle} in the list.
     */
    public InstructionHandle getLast() {
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
        final InstructionHandle old[] = instructions;
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
