package com.niekgigengack.generator;

import java.util.UUID;

public class SyncBag {
    private String id;
    private String source;
    private SyncItem syncItem;

    {
        this.id = UUID.randomUUID().toString();
        this.source = UUID.randomUUID().toString();
    }

    public SyncBag() {
        this.syncItem = new SyncItem();
    }

    public String getId() {
        return this.id;
    }

    public String getSource() {
        return this.source;
    }

    public SyncItem getSyncItem() {
        return this.syncItem;
    }
}