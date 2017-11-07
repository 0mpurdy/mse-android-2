package com.a0mpurdy.mse.search;

import com.a0mpurdy.mse.search.criteria.SearchCriteria;

/**
 * Created by mj_pu_000 on 07/11/2017.
 */

public class Search {

    private SearchCriteria criteria;

    private int numTotalResults;

    public Search(SearchCriteria criteria) {
        this.criteria = criteria;
        this.numTotalResults = 0;
    }

    public int getTotalSearchResults() {
        return numTotalResults;
    }

    public void addAuthorSearchResults(int authorResults) {
        numTotalResults += authorResults;
    }
}
