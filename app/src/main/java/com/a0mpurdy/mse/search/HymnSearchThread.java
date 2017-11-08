package com.a0mpurdy.mse.search;

import android.content.res.AssetManager;
import android.util.Log;

import com.a0mpurdy.mse_core.log.LogLevel;
import com.a0mpurdy.mse_core.log.LogRow;
import com.a0mpurdy.mse_core.data.author.Author;
import com.a0mpurdy.mse_core.data.author.AuthorIndex;
import com.a0mpurdy.mse_core.data.hymn.Hymn;
import com.a0mpurdy.mse_core.data.hymn.HymnBook;
import com.a0mpurdy.mse_core.data.hymn.HymnVerse;
import com.a0mpurdy.mse.hymn.HymnBookCache;
import com.a0mpurdy.mse.search.criteria.SearchCriteria;
import com.a0mpurdy.mse.search.criteria.SearchType;
import com.a0mpurdy.mse.search.results.IResult;
import com.a0mpurdy.mse.search.results.Result;
import com.a0mpurdy.mse.search.source.Reference;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mj_pu_000 on 07/11/2017.
 */
public class HymnSearchThread extends SingleSearchThread {

    // TODO: replace with config
    static int TOO_FREQUENT = 10000;

    SearchCriteria criteria;
    AuthorIndex authorIndex;
    ArrayList<IResult> results;
    ArrayList<LogRow> logRows;
    HymnBookCache cache;
    AssetManager am;

    public HymnSearchThread(SearchCriteria criteria, AuthorIndex authorIndex, HymnBookCache cache, AssetManager am) {
        this.criteria = criteria;
        this.authorIndex = authorIndex;
        logRows = new ArrayList<>();
        results = new ArrayList<>();
        this.cache = cache;
        this.am = am;
    }

    @Override
    public void run() {

        Log.d("SEARCH", TokenHelper.getTokensAsString(criteria.getTokens()));

        try {
            HymnBook book = cache.getHymnBook("hymns1962.ser", am);
            searchVerse(book.getHymn(7).getVerse(1));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        short[] refs = authorIndex.getOverlappingReferences(criteria.getTokens(), TOO_FREQUENT);
    }

    /**
     * Check if the tokens are valid for searching
     *
     * @param tokenFrequencies frequency of each token being searched
     */
    boolean hasValidTokens(int[] tokenFrequencies) {
        for (int freq : tokenFrequencies) {
            if (freq == 0) {
                logRows.add(new LogRow(LogLevel.LOW, "Token doesn't appear"));
            } else if (freq > TOO_FREQUENT) {
                logRows.add(new LogRow(LogLevel.LOW, "Token too frequent"));
            } else {
                // TODO: take in to account difference scopes and types
                return true;
            }
        }
        return false;
    }

    /**
     * Get the results from a verse
     * @param verse
     */
    void searchVerse(HymnVerse verse) {

        String[] verseTokens = TokenHelper.tokenizeString(verse.getVerseText());

        Log.d("TEST", TokenHelper.getTokensAsString(verseTokens));

        Log.d("TEST result", SearchMatch.search(SearchType.CONTAINS, verseTokens, criteria.getTokens()) + "");

        if(SearchMatch.search(SearchType.CONTAINS, verseTokens, criteria.getTokens())) {
            Reference ref = new Reference(Author.HYMNS, verse.getParentHymn().getParentHymnBook().getId(), verse.getParentHymn().getNumber(), verse.getNumber(), 0);

            results.add(new Result(ref, verse.getVerseText()));
        }

    }

    @Override
    ArrayList<LogRow> getLog() {
        return null;
    }

    @Override
    ArrayList<IResult> getResults() {
        return results;
    }

    @Override
    int getNumberOfResults() {
        return 0;
    }
}
