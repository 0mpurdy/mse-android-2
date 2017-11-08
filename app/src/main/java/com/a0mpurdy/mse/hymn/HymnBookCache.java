package com.a0mpurdy.mse.hymn;

import android.content.res.AssetManager;
import android.util.Log;

import com.a0mpurdy.mse_core.data.author.Author;
import com.a0mpurdy.mse_core.data.author.AuthorIndex;
import com.a0mpurdy.mse_core.data.hymn.HymnBook;

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
// TODO: possibly make into a service?
public class HymnBookCache implements Serializable {

    transient private AuthorIndex index;
    private HashMap<String, HymnBook> hymnBooks;

    public HymnBookCache() {
        this.hymnBooks = new HashMap<>();
    }

    public HymnBook getHymnBook(String name, AssetManager am) throws IOException, ClassNotFoundException {
        HymnBook hymnBookInCache = hymnBooks.get(name);
        if (hymnBookInCache != null) {
            return hymnBookInCache;
        }
        return cacheNewHymnBookAsset(name, am);
    }

    private HymnBook cacheNewHymnBookAsset(String name, AssetManager am) throws IOException, ClassNotFoundException {
        Log.d("[DEBUG   ]", "cacheNewHymnBookAsset: " + name);
        FileInputStream fis = am.openFd("hymns/" + name).createInputStream();
        ObjectInputStream ois = new ObjectInputStream(fis);
        HymnBook hymnbook = (HymnBook) ois.readObject();
        return hymnbook;
    }

    private AuthorIndex readIndex(AssetManager am) {
        try {
            ObjectInputStream ois = new ObjectInputStream(am.open("hymns/index-hymns.idx"));
            HashMap<String, Integer> tokenCountMap = (HashMap<String, Integer>) ois.readObject();
            HashMap<String, short[]> references = (HashMap<String, short[]>) ois.readObject();
            return new AuthorIndex(Author.HYMNS, tokenCountMap, references);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AuthorIndex getIndex(AssetManager am) {
        if (this.index == null) {
            this.index = this.readIndex(am);
        }
        return this.index;
    }
}
