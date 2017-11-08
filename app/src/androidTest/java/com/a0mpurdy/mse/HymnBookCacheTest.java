package com.a0mpurdy.mse;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.test.InstrumentationRegistry;

import com.a0mpurdy.mse.hymn.HymnBookCache;
import com.a0mpurdy.mse_core.data.hymn.HymnBook;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by mj_pu_000 on 08/11/2017.
 */

public class HymnBookCacheTest {

    @Test
    public void getHymnBook_works() throws Exception {

        // arrange
        Context appContext = InstrumentationRegistry.getTargetContext();

        HymnBookCache cache = new HymnBookCache();
        AssetManager am = appContext.getAssets();

        // act
        HymnBook book = cache.getHymnBook("hymns1962.ser", am);

        // assert
        assertEquals(book.getId(), 1962);
        assertEquals(book.getNumHymns(), 456);
        assertEquals(book.getHymns().size(), 456);
        assertEquals(book.getSerializedName(), "hymns1962.ser");
        assertEquals(book.getShortDescription(), "Hymns (1962)");
    }

}
