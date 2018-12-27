package com.nur1popcorn.basm.root.items;

import com.nur1popcorn.basm.utils.DexInputStream;

import java.io.IOException;

public class ClassDefIdItem extends Item {
    private int classIdx,
                accessFlags,
                superclassIdx,
                interfacesOff,
                sourceFileIdx,
                annotationsOff,
                classDataOff,
                staticValuesOff;

    @Override
    public void read(DexInputStream in) throws IOException {
        super.read(in);
        classIdx = in.readInt();
        accessFlags = in.readInt();
        superclassIdx = in.readInt();
        interfacesOff = in.readInt();
        sourceFileIdx = in.readInt();
        annotationsOff = in.readInt();
        classDataOff = in.readInt();
        staticValuesOff = in.readInt();
    }
}
