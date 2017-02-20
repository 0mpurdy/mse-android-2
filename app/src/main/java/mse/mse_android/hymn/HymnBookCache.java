package mse.mse_android.hymn;

import android.util.Log;

import java.util.HashMap;

/**
 * Cache for reading in hymn books
 *
 * @author Michael Purdy
 */
public class HymnBookCache {

    private HashMap<String, HymnBook> hymnBooks;

    public HymnBookCache() {
        this.hymnBooks = new HashMap<>();
    }

    public HymnBook getHymnBook(String name) {
        HymnBook hymnBookInCache = hymnBooks.get(name);
        if (hymnBookInCache != null) {
            return hymnBookInCache;
        }
        return cacheNewHymnBookAsset(name);
    }

    private HymnBook cacheNewHymnBookAsset(String name) {
        Log.d("[DEBUG   ]", "cacheNewHymnBookAsset: " + name);
        return null;
    }
}
