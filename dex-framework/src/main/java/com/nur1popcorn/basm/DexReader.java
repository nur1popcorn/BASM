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

package com.nur1popcorn.basm;

import com.nur1popcorn.basm.tree.item.*;
import com.nur1popcorn.basm.tree.item.data.annotation_item.AnnotationItem;
import com.nur1popcorn.basm.tree.item.data.annotation_set_item.AnnotationSetItem;
import com.nur1popcorn.basm.tree.item.data.annotation_set_ref_list.AnnotationSetRefList;
import com.nur1popcorn.basm.tree.item.data.annotations_directory_item.AnnotationsDirectoryItem;
import com.nur1popcorn.basm.tree.item.data.debug_info_item.DebugInfoItem;
import com.nur1popcorn.basm.tree.item.data.class_data_item.ClassDataItem;
import com.nur1popcorn.basm.tree.item.data.code_item.CodeItem;
import com.nur1popcorn.basm.tree.item.data.encoded_array_item.EncodedArrayItem;
import com.nur1popcorn.basm.tree.item.data.map_list.MapItem;
import com.nur1popcorn.basm.tree.item.data.map_list.MapList;
import com.nur1popcorn.basm.tree.item.data.string_data_item.StringDataItem;
import com.nur1popcorn.basm.tree.item.data.type_list.TypeList;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class DexReader {
    public static final int READ_HEADER = 0x1;

    public static final int READ_ALL = READ_HEADER;

    public static DexReader instance;

    private final ByteBuffer byteBuffer;

    private HeaderItem header;
    public StringIdItem[] stringIds;
    public TypeIdItem[] typeIds;
    public ProtoIdItem[] protoIds;
    public FieldIdItem[] fieldIds;
    public MethodIdItem[] methodIds;
    private ClassDefIdItem[] classDefs;
    private CallSiteIdItem[] callSiteIds;
    public MethodHandleItem[] methodHandles;

    public DexReader(ByteBuffer byteBuffer) {
        instance = this;
        this.byteBuffer = byteBuffer.asReadOnlyBuffer().order(ByteOrder.LITTLE_ENDIAN);
    }

    private void readHeader() {
        header = new HeaderItem();
        header.read(byteBuffer);

        //TODO: organize all this into new read methods
        stringIds = new StringIdItem[header.stringIdsSize];
        for (int i = 0; i < stringIds.length; i++)
            stringIds[i] = new StringIdItem(byteBuffer);
        typeIds = new TypeIdItem[header.typeIdsSize];
        for (int i = 0; i < typeIds.length; i++)
            typeIds[i] = new TypeIdItem(byteBuffer);
        protoIds = new ProtoIdItem[header.protoIdsSize];
        for (int i = 0; i < protoIds.length; i++)
            protoIds[i] = new ProtoIdItem(byteBuffer);
        fieldIds = new FieldIdItem[header.fieldIdsSize];
        for (int i = 0; i < fieldIds.length; i++)
            fieldIds[i] = new FieldIdItem(byteBuffer);
        methodIds = new MethodIdItem[header.methodIdsSize];
        for (int i = 0; i < methodIds.length; i++)
            methodIds[i] = new MethodIdItem(byteBuffer);
        classDefs = new ClassDefIdItem[header.classDefsSize];
        for (int i = 0; i < classDefs.length; i++)
            classDefs[i] = new ClassDefIdItem(byteBuffer);

        byteBuffer.position(header.mapOff);
        MapList mapList = new MapList(byteBuffer);
        for (MapItem mapItem : mapList.list) {
            byteBuffer.position(mapItem.offset);
            switch (mapItem.type) {
                case MapItem.TYPE_HEADER_ITEM:
                case MapItem.TYPE_STRING_ID_ITEM:
                case MapItem.TYPE_TYPE_ID_ITEM:
                case MapItem.TYPE_PROTO_ID_ITEM:
                case MapItem.TYPE_FIELD_ID_ITEM:
                case MapItem.TYPE_METHOD_ID_ITEM:
                case MapItem.TYPE_CLASS_DEF_ITEM:
                    break;
                case MapItem.TYPE_CALL_SITE_ID_ITEM:
                    callSiteIds = new CallSiteIdItem[mapItem.size];
                    for (int i = 0; i < callSiteIds.length; i++)
                        callSiteIds[i] = new CallSiteIdItem(byteBuffer);
                    break;
                case MapItem.TYPE_METHOD_HANDLE_ITEM:
                    methodHandles = new MethodHandleItem[mapItem.size];
                    for (int i = 0; i < methodHandles.length; i++)
                        methodHandles[i] = new MethodHandleItem(byteBuffer);
                    break;
                case MapItem.TYPE_MAP_LIST:
                    break;
                case MapItem.TYPE_TYPE_LIST:
                    for (int i = 0; i < mapItem.size; i++)
                        new TypeList(byteBuffer);
                    break;
                case MapItem.TYPE_ANNOTATION_SET_REF_LIST:
                    for (int i = 0; i < mapItem.size; i++)
                        new AnnotationSetRefList(byteBuffer);
                    break;
                case MapItem.TYPE_ANNOTATION_SET_ITEM:
                    for (int i = 0; i < mapItem.size; i++)
                        new AnnotationSetItem(byteBuffer);
                    break;
                case MapItem.TYPE_CLASS_DATA_ITEM:
                    for (int i = 0; i < mapItem.size; i++)
                        new ClassDataItem(byteBuffer);
                    break;
                case MapItem.TYPE_CODE_ITEM:
                    for (int i = 0; i < mapItem.size; i++)
                        new CodeItem(byteBuffer);
                    break;
                case MapItem.TYPE_STRING_DATA_ITEM:
                    for (int i = 0; i < mapItem.size; i++)
                        new StringDataItem(byteBuffer);
                    break;
                case MapItem.TYPE_DEBUG_INFO_ITEM:
                    for (int i = 0; i < mapItem.size; i++)
                        new DebugInfoItem(byteBuffer);
                    break;
                case MapItem.TYPE_ANNOTATION_ITEM:
                    for (int i = 0; i < mapItem.size; i++)
                        new AnnotationItem(byteBuffer);
                    break;
                case MapItem.TYPE_ENCODED_ARRAY_ITEM:
                    for (int i = 0; i < mapItem.size; i++)
                        new EncodedArrayItem(byteBuffer);
                    break;
                case MapItem.TYPE_ANNOTATIONS_DIRECTORY_ITEM:
                    for (int i = 0; i < mapItem.size; i++)
                        new AnnotationsDirectoryItem(byteBuffer);
                    break;
                default:
                    throw new RuntimeException();
            }
        }
    }

    public void accept(IDexVisitor visitor, int read) throws IOException {
        if((read & READ_HEADER) != 0) {
            readHeader();
            //visitor.visitHeader();
        } else {

        }
    }
}
