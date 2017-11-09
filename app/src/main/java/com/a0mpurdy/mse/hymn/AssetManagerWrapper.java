package com.a0mpurdy.mse.hymn;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.ObjectInputStream;

public class AssetManagerWrapper implements ObjectStreamer {

    AssetManager inner;

    public AssetManagerWrapper(AssetManager inner) {
        this.inner = inner;
    }

    @Override
    public ObjectInputStream open(String filename) throws IOException {
        return new ObjectInputStream(inner.open(filename));
    }
}
