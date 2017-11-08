package com.a0mpurdy.mse.search;

import android.util.Log;

import com.a0mpurdy.mse.common.log.LogLevel;
import com.a0mpurdy.mse.common.log.LogRow;
import com.a0mpurdy.mse.data.author.AuthorIndex;
import com.a0mpurdy.mse.data.hymn.HymnBook;
import com.a0mpurdy.mse.search.criteria.SearchCriteria;
import com.a0mpurdy.mse.search.results.IResult;

import java.util.ArrayList;

/**
 * Created by mj_pu_000 on 07/11/2017.
 */
public class HymnSearchThread extends SingleSearchThread {

    // TODO: replace with config
    static int TOO_FREQUENT = 10000;

    SearchCriteria criteria;
    AuthorIndex authorIndex;
    ArrayList<LogRow> logRows;

    public HymnSearchThread(SearchCriteria criteria, AuthorIndex authorIndex) {
        this.criteria = criteria;
        this.authorIndex = authorIndex;
        logRows = new ArrayList<>();
    }

    @Override
    public void run() {

        Log.d("SEARCH", criteria.getSearchString());

    }

    /**
     * Get the frequency of each token in the search criteria
     *
     * @param searchTokens tokens to check the frequency of
     */
    int[] getTokenFrequencies(String[] searchTokens) {
        int[] freq = new int[searchTokens.length];
        for (int i = 0; i < searchTokens.length; i++) {
            freq[i] = authorIndex.getTokenCount(searchTokens[i]);
        }
        return freq;
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

    @Override
    ArrayList<LogRow> getLog() {
        return null;
    }

    @Override
    ArrayList<IResult> getResults() {
        return null;
    }

    @Override
    int getNumberOfResults() {
        return 0;
    }
}
