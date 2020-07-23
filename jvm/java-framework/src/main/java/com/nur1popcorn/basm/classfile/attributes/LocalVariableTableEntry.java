package com.nur1popcorn.basm.classfile.attributes;

import com.nur1popcorn.basm.Constants;
import com.nur1popcorn.basm.classfile.ConstantPool;
import com.nur1popcorn.basm.classfile.constants.ConstantUTF8;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * The {@link LocalVariableTableEntry} holds information about an individual variable in the local variable table.
 *
 * @see AttributeLocalVariableTable
 *
 * @author Ben Kinney
 * @since 1.1.0-alpha
 */
public final class LocalVariableTableEntry {
    /* The index in the code array where the variable starts
     */
    private int startPc, /* u2 */

    /* The length the local variable is available. Bound is [startpc, startpc + length).
     */
                length, /* u2 */

    /* The index in the constant pool of the name of the variable.
     */
                nameIndex, /* u2 */

    /* The index in the constant pool of the descriptor for the variable.
     */
                descriptorIndex, /* u2 */

    /* The index which the variable occupies in the frame.
     * If the variable is of type double or long, it occupies index and index + 1.
     */
                index; /* u2 */

    public LocalVariableTableEntry(int startPc, int length, int nameIndex, int descriptorIndex, int index) {
        this.startPc = startPc;
        this.length = length;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.index = index;
    }

    /**
     * Write the local variable table entry to an output stream.
     * @param os The {@link DataOutputStream} to write to.
     * @throws IOException
     */
    public void write(DataOutputStream os) throws IOException {
        os.writeShort(startPc);
        os.writeShort(length);
        os.writeShort(nameIndex);
        os.writeShort(descriptorIndex);
        os.writeShort(index);
    }

    /**
     * Get the {@link ConstantUTF8} which represents the name of the variable.
     * @param pool The {@link ConstantPool}.
     * @return A {@link ConstantUTF8}.
     */
    public ConstantUTF8 indexName(ConstantPool pool) {
        return pool.getEntry(nameIndex, Constants.CONSTANT_UTF8);
    }

    /**
     * Get the {@link ConstantUTF8} which represents the descriptor of the variable.
     * @param pool The {@link ConstantPool}.
     * @return A {@link ConstantUTF8}.
     */
    public ConstantUTF8 indexDescriptor(ConstantPool pool) {
        return pool.getEntry(descriptorIndex, Constants.CONSTANT_UTF8);
    }

    /**
     * Get the offset in the code array where the variable starts.
     * @return The offset.
     */
    public int getStartPc() {
        return startPc;
    }

    /**
     * Get the length in the code array the variable occupies.
     * @return The length.
     */
    public int getLength() {
        return length;
    }

    /**
     * Convenience method which gets the index in the code array where the variable ends.
     * @return The offset.
     */
    public int getEndPc() {
        return startPc + length;
    }

    /**
     * Get the index in the constant pool for the name of the variable.
     * @return The name's index.
     */
    public int getNameIndex() {
        return nameIndex;
    }

    /**
     * Get the index in the constant pool for the descriptor of the variable.
     * @return The descriptor's index.
     */
    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    /**
     * Get the index in the frame the variable occupies.
     * If the variable is of type double or long, it occupies index and index + 1.
     * @return The index.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Set the offset in the code array where the variable starts.
     * @param startPc The offset.
     */
    public void setStartPc(int startPc) {
        this.startPc = startPc;
    }

    /**
     * Set the length in the code array the variable occupies.
     * @param length The length.
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Set the index in the constant pool of the name for the variable.
     * @param nameIndex The index.
     */
    public void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    /**
     * Set the index in the constant pool of the descriptor for the variable.
     * @param descriptorIndex The index.
     */
    public void setDescriptorIndex(int descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
    }

    /**
     * Set the index in the frame the variable occupies.
     * @param index The index.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        LocalVariableTableEntry that = (LocalVariableTableEntry) o;
        return startPc == that.startPc &&
               length == that.length &&
               nameIndex == that.nameIndex &&
               descriptorIndex == that.descriptorIndex &&
               index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPc, length, nameIndex, descriptorIndex, index);
    }
}
