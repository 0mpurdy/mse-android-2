package com.a0mpurdy.mse.hymn;

import android.content.res.AssetManager;
import android.util.Log;

import com.a0mpurdy.mse.data.hymn.Hymn;
import com.a0mpurdy.mse.data.hymn.HymnBook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Cache for reading in hymn books
 *
 * @author Michael Purdy
 */
public class HymnBookCache implements Serializable {

    private HashMap<String, HymnBook> hymnBooks;

    public HymnBookCache() {
        this.hymnBooks = new HashMap<>();
    }

    public HymnBook getHymnBook(String name, AssetManager am) {
        HymnBook hymnBookInCache = hymnBooks.get(name);
        if (hymnBookInCache != null) {
            return hymnBookInCache;
        }
        try {
            return cacheNewHymnBookAsset(name, am);
        } catch (IOException e) {
            Log.e("[ERROR]", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private HymnBook cacheNewHymnBookAsset(String name, AssetManager am) throws IOException {
        Log.d("[DEBUG   ]", "cacheNewHymnBookAsset: " + name);
        FileInputStream fis = am.openFd("hymns/" + name).createInputStream();
        ObjectInputStream ois = new ObjectInputStream(fis);
        try {
            HymnBook hymnbook = (HymnBook) ois.readObject();
            return hymnbook;
        } catch (ClassNotFoundException e) {
            Log.e("[ERROR]", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
