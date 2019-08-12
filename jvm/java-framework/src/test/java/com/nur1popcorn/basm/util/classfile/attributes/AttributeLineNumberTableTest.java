package com.nur1popcorn.basm.util.classfile.attributes;

import com.nur1popcorn.basm.classfile.attributes.AttributeLineNumberTable;
import com.nur1popcorn.basm.classfile.attributes.LineNumberTableEntry;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class AttributeLineNumberTableTest {
    @Test
    public void testGetLineNumber() {
        final AttributeLineNumberTable table = new AttributeLineNumberTable(
            0, 0, new LineNumberTableEntry[] { new LineNumberTableEntry(0, 16),
                                               new LineNumberTableEntry(4, 17),
                                               new LineNumberTableEntry(7, 18),
                                               new LineNumberTableEntry(10, 19),
                                               new LineNumberTableEntry(12, 20),
                                               new LineNumberTableEntry(14, 21),
                                               new LineNumberTableEntry(15, 22),
                                               new LineNumberTableEntry(17, 23),
                                               new LineNumberTableEntry(19, 24) });

        final int correct[] = { 16, 16, 16, 16,
                                17, 17, 17, 18,
                                18, 18, 19, 19,
                                20, 20, 21, 22,
                                22, 23, 23, 24,
                                24, 24, 24, 24 };

        for(int i = 0; i < 24; i++)
            assertEquals(correct[i], table.getLineNumber(i));
    }
}
