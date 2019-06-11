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
import com.nur1popcorn.basm.classfile.tree.methods.instructions.SwitchInstruction;
import com.nur1popcorn.basm.classfile.tree.methods.instructions.WideInstruction;
import com.nur1popcorn.basm.utils.ByteDataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

import static com.nur1popcorn.basm.Constants.*;
import static com.nur1popcorn.basm.Constants.OPCODE_MNEMONICS;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.Instruction.SWITCH_INS;
import static com.nur1popcorn.basm.classfile.tree.methods.instructions.Instruction.WIDE_INS;

public final class InstructionList extends AbstractSequentialList<Instruction> implements Cloneable {
    private int size;
    private Node head,
                 tail;

    public InstructionList() {
    }

    InstructionList(byte code[], ConstantPool constantPool) throws IOException {
        final ByteDataInputStream in = new ByteDataInputStream(code);
        while(in.available() != 0)
            addBack(Instruction.read(in, constantPool));
    }

    public void write(DataOutputStream os) throws IOException {
        for(Instruction instruction : this)
            instruction.write(os);
    }

    public void accept(IInstructionVisitor visitor) {
        for(Instruction ih : this)
            ih.accept(visitor);
    }

    private void addBack(Instruction value) {
        size++;
        modCount++;
        final Node newNode = new Node(value, tail, null);
        if(head == null)
            head = tail = newNode;
        else
            tail = (tail.next = newNode);
    }

    private void addFront(Instruction value) {
        size++;
        modCount++;
        final Node newNode = new Node(value, null, head);
        if(head == null)
            head = tail = newNode;
        else
            head = (head.prev = newNode);
    }

    private void addBefore(Instruction value, Node node) {
        size++;
        modCount++;
        final Node newNode = new Node(value, node.prev, node);
        if(node == head) {
            head = (head.prev = newNode);
            return;
        }

        node.prev = (node.prev.next = newNode);
    }

    private void addAfter(Instruction value, Node prevNode) {
        size++;
        modCount++;
        final Node newNode = new Node(value, prevNode, prevNode.next);
        Node nextNode = prevNode.next;
        prevNode.next = newNode;
        nextNode.prev = newNode;
    }

    @Override
    public void add(int index, Instruction element) {
        if (index == size)
            addBack(element);
        else
            addBefore(element, find(index));
    }

    @Override
    public Instruction set(int index, Instruction element) {
        final Node x = find(index);
        final Instruction oldVal = x.value;
        x.value = element;
        return oldVal;
    }

    private void checkIndex(int index) {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException(
                "The index=" + index + " cannot be contained in a linked list of size=" + size + ".");
    }

    private Node find(int index) {
        checkIndex(index);
        if(index <= (size / 2)) {
            Node cursor = head;
            for(int i = 0; i < index; i++)
                cursor = cursor.next;
            return cursor;
        } else {
            Node cursor = tail;
            for(int i = size - 1; i > index; i--)
                cursor = cursor.prev;
            return cursor;
        }
    }

    @Override
    public Instruction get(int index) {
        return find(index).value;
    }

    private void unlink(Node node) {
        size--;
        modCount++;
        if(node == head)
            head = node.next;
        else
            node.prev.next = node.next;

        if(node == tail)
            tail = node.prev;
        else
            node.next.prev = node.prev;
    }

    @Override
    public Instruction remove(int index) {
        final Node found = find(index);
        unlink(found);
        return found.value;
    }

    private Node find(Object obj) {
        for(Node node = head; node != null; node = node.next)
            if(node.value.equals(obj))
                return node;
        return null;
    }

    @Override
    public boolean remove(Object obj) {
        final Node found = find(obj);
        if(found != null) {
            unlink(found);
            return true;
        }
        return false;
    }

    public Instruction removeLast() {
        size--;
        modCount++;
        final Node node = tail;
        if(node == null)
            throw new NoSuchElementException("The list is empty.");
        tail = node.prev;
        return node.value;
    }

    @Override
    public boolean contains(Object obj) {
        return find(obj) != null;
    }

    @Override
    public int indexOf(Object obj) {
        Node cursor = head;
        for(int i = 0; i < size; i++) {
            if(cursor.equals(obj))
                return i;
            cursor = cursor.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object obj) {
        Node cursor = tail;
        for(int i = size; i < 0; i--) {
            if(cursor.equals(obj))
                return i;
            cursor = cursor.prev;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        final InstructionList clone = (InstructionList) super.clone();
        clone.head = clone.tail = null;
        clone.size = 0;
        clone.modCount = 0;
        for (Node x = head; x != null; x = x.next)
            clone.add(x.value);
        return clone;
    }

    private static int getLength(Instruction handle, int offset) {
        final byte opcode = handle.getOpcode();
        switch(handle.getType()) {
            case SWITCH_INS: {
                final SwitchInstruction instruction = (SwitchInstruction) handle;
                return opcode == TABLESWITCH ?
                    ((-1 - offset) & 0x3) + 13 + (instruction.getCount() << 2) :
                    ((-1 - offset) & 0x3) + 9 + (instruction.getCount() << 3);
            }
            case WIDE_INS:
                return ((WideInstruction) handle)
                    .getOpcodeParameter() == IINC ? 6 : 4;
            default: {
                final int parameters = OPCODE_PARAMETERS[opcode & 0xff];
                if(parameters == UNKNOWN_PARAMETERS)
                    throw new MalformedClassFileException(
                        "The opcode=" + OPCODE_MNEMONICS[opcode & 0xff] + " is invalid."
                    );
                return parameters + 1;
            }
        }
    }

    @Override
    public OffsetListIterator listIterator(int index) {
        return new OffsetListIterator() {
            private Node lastReturned;
            private Node next = next = (index == size) ? null : find(index);
            private int nextIndex = index, nextOffset;
            private int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public Instruction next() {
                checkForComodification();
                if (!hasNext())
                    throw new NoSuchElementException();

                lastReturned = next;
                next = next.next;
                nextIndex++;
                nextOffset += getLength(lastReturned.value, nextOffset);
                return lastReturned.value;
            }

            @Override
            public boolean hasPrevious() {
                return nextIndex > 0 && nextOffset > 0;
            }

            @Override
            public Instruction previous() {
                checkForComodification();
                if (!hasPrevious())
                    throw new NoSuchElementException();

                lastReturned = next = (next == null) ? tail : next.prev;
                nextIndex--;
                nextOffset -= getLength(lastReturned.value, nextOffset);
                return lastReturned.value;
            }

            @Override
            public int nextIndex() {
                return nextIndex;
            }

            @Override
            public int previousIndex() {
                return nextIndex - 1;
            }

            @Override
            public int nextOffset() {
                return nextOffset;
            }

            @Override
            public int previousOffset() {
                if(lastReturned == null)
                    return -1;
                return nextOffset - getLength(lastReturned.value, nextOffset);
            }

            @Override
            public void remove() {
                checkForComodification();
                if (lastReturned == null)
                    throw new IllegalStateException();

                Node lastNext = lastReturned.next;
                unlink(lastReturned);
                if (next == lastReturned)
                    next = lastNext;
                else {
                    nextIndex--;
                    nextOffset -= getLength(lastReturned.value, nextOffset);
                }
                lastReturned = null;
                expectedModCount++;
            }

            @Override
            public void set(Instruction e) {
                if (lastReturned == null)
                    throw new IllegalStateException();
                checkForComodification();
                lastReturned.value = e;
            }

            @Override
            public void add(Instruction e) {
                checkForComodification();
                lastReturned = null;
                if (next == null)
                    addBack(e);
                else
                    addBefore(e, next);
                nextIndex++;
                nextOffset += getLength(e, nextOffset);
                expectedModCount++;
            }

            @Override
            public void forEachRemaining(Consumer<? super Instruction> action) {
                Objects.requireNonNull(action);
                while (modCount == expectedModCount && next != null) {
                    action.accept(next.value);
                    lastReturned = next;
                    next = next.next;
                    nextIndex++;
                    nextOffset += getLength(lastReturned.value, nextOffset);
                }
                checkForComodification();
            }

            private void checkForComodification() {
                if (modCount != expectedModCount)
                    throw new ConcurrentModificationException();
            }
        };
    }

    private static final class Node {
        private Instruction value;
        private Node prev,
            next;

        private Node(Instruction value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
