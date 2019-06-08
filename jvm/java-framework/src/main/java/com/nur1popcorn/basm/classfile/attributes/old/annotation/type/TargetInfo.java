package com.nur1popcorn.basm.classfile.attributes.annotation.type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class TargetInfo {
    public static TargetInfo read(int targetType, DataInputStream in) throws IOException {
        switch (targetType) {
            case 0x00:
            case 0x01:
                return new TypeParameterTarget(in);
            case 0x10:
                return new SuperTypeTarget(in);
            case 0x11:
            case 0x12:
                return new TypeParameterBoundTarget(in);
            case 0x13:
            case 0x14:
            case 0x15:
                return new EmptyTarget();
            case 0x16:
                return new FormalParameterTarget(in);
            case 0x17:
                return new ThrowsTarget(in);
            case 0x40:
            case 0x41:
                return new LocalvarTarget(in);
            case 0x42:
                return new CatchTarget(in);
            case 0x43:
            case 0x44:
            case 0x45:
            case 0x46:
                return new OffsetTarget(in);
            case 0x47:
            case 0x48:
            case 0x49:
            case 0x4A:
            case 0x4B:
                return new TypeArgumentTarget(in);
            default:
                throw new IllegalArgumentException("Illegal target_type: " + Integer.toHexString(targetType));
        }
    }

    public abstract void write(DataOutputStream os) throws IOException;
}
