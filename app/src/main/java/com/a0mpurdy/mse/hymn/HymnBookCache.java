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

    // TODO: there must be a better way to share this? Currently using public method on activity
//    private static HymnBookCache staticCache;
//    public static HymnBookCache getPublicCache() {
//        if (staticCache == null) {
//            staticCache = new HymnBookCache();
//        }
//        return staticCache;
//    }

    transient private AuthorIndex index;
    transient private HashMap<String, HymnBook> hymnBooks;

    public HymnBookCache() {
        this.hymnBooks = new HashMap<>();
    }

    public HymnBook getHymnBook(String name, ObjectStreamer os) throws IOException, ClassNotFoundException {
        HymnBook hymnBookInCache = hymnBooks.get(name);
        if (hymnBookInCache != null) {
            return hymnBookInCache;
        }
        return cacheNewHymnBookAsset(name, os);
    }

    public HymnBook getHymnBook(int volumeNumber, ObjectStreamer os) throws IOException, ClassNotFoundException {
        // TODO: replace hardcoded name
        return getHymnBook("hymns1962.ser", os);
    }

    private HymnBook cacheNewHymnBookAsset(String name, ObjectStreamer os) throws IOException, ClassNotFoundException {
        Log.d("[DEBUG   ]", "cacheNewHymnBookAsset: " + name);
        ObjectInputStream ois = os.open("hymns/" + name);
        HymnBook hymnbook = (HymnBook) ois.readObject();
        this.hymnBooks.put(name, hymnbook);
        return hymnbook;
    }

    private AuthorIndex readIndex(ObjectStreamer os) {
        try {
            ObjectInputStream ois = os.open("hymns/index-hymns.idx");
            HashMap<String, Integer> tokenCountMap = (HashMap<String, Integer>) ois.readObject();
            HashMap<String, short[]> references = (HashMap<String, short[]>) ois.readObject();
            return new AuthorIndex(Author.HYMNS, tokenCountMap, references);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AuthorIndex getIndex(ObjectStreamer os) {
        if (this.index == null) {
            this.index = this.readIndex(os);
        }
        return this.index;
    }

    public HashMap<String, HymnBook> getBooksMap() {
        return hymnBooks;
    }
}
