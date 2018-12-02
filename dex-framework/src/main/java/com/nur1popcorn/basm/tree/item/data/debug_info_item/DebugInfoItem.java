package com.nur1popcorn.basm.tree.item.data.debug_info_item;

import com.nur1popcorn.basm.utils.Leb128;

import java.nio.ByteBuffer;

public class DebugInfoItem {
    private static final int DBG_END_SEQUENCE = 0x00;
    private static final int DBG_ADVANCE_PC = 0x01;
    private static final int DBG_ADVANCE_LINE = 0x02;
    private static final int DBG_START_LOCAL = 0x03;
    private static final int DBG_START_LOCAL_EXTENDED = 0x04;
    private static final int DBG_END_LOCAL = 0x05;
    private static final int DBG_RESTART_LOCAL = 0x06;
    private static final int DBG_SET_PROLOGUE_END = 0x07;
    private static final int DBG_SET_EPILOGUE_BEGIN = 0x08;
    private static final int DBG_SET_FILE = 0x09;

    // the smallest special opcode
    private static final int DBG_FIRST_SPECIAL = 0x0a;
    // the smallest line number increment
    private static final int DBG_LINE_BASE = -4;
    // the number of line increments represented
    private static final int DBG_LINE_RANGE = 15;

    private final int lineStart,
                      parametersSize;
    private final int[] parameterNames;

    public DebugInfoItem(ByteBuffer byteBuffer) {
        lineStart = Leb128.readULeb128i(byteBuffer);
        parametersSize = Leb128.readULeb128i(byteBuffer);
        parameterNames = new int[parametersSize];
        for (int i = 0; i < parameterNames.length; i++)
            parameterNames[i] = Leb128.readULeb128p1(byteBuffer);

        int address = 0;
        int line = lineStart;

        int addrDiff;    // uleb128   address delta.
        int lineDiff;    // sleb128   line delta.
        int registerNum; // uleb128   register number.
        int nameIndex;   // uleb128p1 string index.    Needs indexMap adjustment.
        int typeIndex;   // uleb128p1 type_list index.      Needs indexMap adjustment.
        int sigIndex;    // uleb128p1 string index.    Needs indexMap adjustment.
        while (true) {
            final int opcode = byteBuffer.get() & 0xff;
            switch (opcode) {
                case DBG_END_SEQUENCE:
                    return;
                case DBG_ADVANCE_PC:
                    address += (addrDiff = Leb128.readULeb128i(byteBuffer));
                    break;
                case DBG_ADVANCE_LINE:
                    line += (lineDiff = Leb128.readLeb128i(byteBuffer));
                    break;
                case DBG_START_LOCAL:
                case DBG_START_LOCAL_EXTENDED:
                    registerNum = Leb128.readULeb128i(byteBuffer);
                    nameIndex = Leb128.readULeb128p1(byteBuffer);
                    typeIndex = Leb128.readULeb128p1(byteBuffer);
                    if (opcode == DBG_START_LOCAL_EXTENDED)
                        sigIndex = Leb128.readULeb128p1(byteBuffer);
                    break;
                case DBG_END_LOCAL:
                case DBG_RESTART_LOCAL:
                    registerNum = Leb128.readULeb128i(byteBuffer);
                    break;
                case DBG_SET_PROLOGUE_END:
                case DBG_SET_EPILOGUE_BEGIN:
                    // do nothing
                    break;
                case DBG_SET_FILE:
                    nameIndex = Leb128.readULeb128p1(byteBuffer);
                    break;
                default:
                    if (opcode < DBG_FIRST_SPECIAL)
                        throw new RuntimeException("Invalid extended opcode encountered " + opcode);
                    final int adjustedOpcode = opcode - DBG_FIRST_SPECIAL;

                    line += (lineDiff = DBG_LINE_BASE + (adjustedOpcode % DBG_LINE_RANGE));
                    address += (addrDiff = (adjustedOpcode / DBG_LINE_RANGE));
                    break;
            }
        }
    }
}
