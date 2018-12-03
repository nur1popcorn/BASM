package com.nur1popcorn.basm.tree.item.data.class_data_item;

import com.nur1popcorn.basm.utils.Leb128;

import java.nio.ByteBuffer;

public class ClassDataItem {
    private final int staticFieldsSize,
                     instanceFieldsSize,
                     directMethodsSize,
                     virtualMethodsSize;
    private final EncodedField[] staticFields,
                                 instanceFields;
    private final EncodedMethod[] directMethods,
                                  virtualMethods;

    public ClassDataItem(ByteBuffer byteBuffer) {
        staticFieldsSize = Leb128.readULeb128i(byteBuffer);
        instanceFieldsSize = Leb128.readULeb128i(byteBuffer);
        directMethodsSize = Leb128.readULeb128i(byteBuffer);
        virtualMethodsSize = Leb128.readULeb128i(byteBuffer);

        staticFields = new EncodedField[staticFieldsSize];
        for (int i = 0; i < staticFields.length; i++)
            staticFields[i] = new EncodedField(byteBuffer);

        instanceFields = new EncodedField[instanceFieldsSize];
        for (int i = 0; i < instanceFields.length; i++)
            instanceFields[i] = new EncodedField(byteBuffer);

        directMethods = new EncodedMethod[directMethodsSize];
        for (int i = 0; i < directMethods.length; i++)
            directMethods[i] = new EncodedMethod(byteBuffer);

        virtualMethods = new EncodedMethod[virtualMethodsSize];
        for (int i = 0; i < virtualMethods.length; i++)
            virtualMethods[i] = new EncodedMethod(byteBuffer);
    }
}
