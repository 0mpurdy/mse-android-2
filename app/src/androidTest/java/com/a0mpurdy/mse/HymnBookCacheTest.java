package com.a0mpurdy.mse;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.test.InstrumentationRegistry;

import com.a0mpurdy.mse.hymn.AssetManagerWrapper;
import com.a0mpurdy.mse.hymn.HymnBookCache;
import com.a0mpurdy.mse_core.data.hymn.HymnBook;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by mj_pu_000 on 08/11/2017.
 */

public class HymnBookCacheTest {

    @Test
    public void getHymnBook_worksWithName() throws Exception {

        // arrange
        Context appContext = InstrumentationRegistry.getTargetContext();

        HymnBookCache cache = new HymnBookCache();
        AssetManager am = appContext.getAssets();

        // act
        HymnBook book = cache.getHymnBook("hymns1962.ser", new AssetManagerWrapper(am));

        // assert
        assertEquals(1, cache.getBooksMap().size());
        assertEquals(1962, book.getId());
        assertEquals(456, book.getNumHymns());
        assertEquals(456, book.getHymns().size());
        assertEquals("hymns1962.ser", book.getSerializedName());
        assertEquals("Hymns (1962)", book.getShortDescription());
    }

    @Test
    public void getHymnBook_worksWithId() throws Exception {

        // arrange
        Context appContext = InstrumentationRegistry.getTargetContext();

        HymnBookCache cache = new HymnBookCache();
        AssetManager am = appContext.getAssets();

        // act
        HymnBook book = cache.getHymnBook(1962, new AssetManagerWrapper(am));

        // assert
        assertEquals(1, cache.getBooksMap().size());
        assertEquals(1962, book.getId());
        assertEquals(456, book.getNumHymns());
        assertEquals(456, book.getHymns().size());
        assertEquals("hymns1962.ser", book.getSerializedName());
        assertEquals("Hymns (1962)", book.getShortDescription());
    }

}
