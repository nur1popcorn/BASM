package com.nur1popcorn.basm.root.items.data.map_list;

import com.nur1popcorn.basm.root.items.Item;
import com.nur1popcorn.basm.utils.DexInputStream;

import java.io.IOException;

public class MapList extends Item {
    private int size;
    public MapItem[] list;

    @Override
    public void read(DexInputStream in) throws IOException {
        super.read(in);
        size = in.readInt();
        list = new MapItem[size];
        for (int i = 0; i < list.length; i++) {
            final MapItem item = new MapItem();
            item.read(in);
            list[i] = item;
        }
    }
}
