package com.a0mpurdy.mse.search.criteria;

import java.util.ArrayList;

/**
 * Created by mj_pu_000 on 07/11/2017.
 */

public class SearchCriteria {

    private String searchString;
    private boolean wildSearch;
    private SearchType searchType;
    private SearchScope searchScope;

    public SearchCriteria(SearchType searchType, SearchScope searchScope, String searchString) {
        this.searchString = searchString;
        this.searchType = searchType;
        this.searchScope = searchScope;
        setWildSearch();
    }

    private void setWildSearch() {

        ArrayList<Integer> starIndexes = new ArrayList<>();

        if (searchString.contains(" ")) {
            wildSearch = false;
        }

        // get the index of each *
        for (int i = 0; i < searchString.length(); i++) {
            if (searchString.charAt(i) == '*') {
                starIndexes.add(i);
            }
        }

        // the stars can only be at the start and/or end of the search text
        if (starIndexes.size() == 2) {
            wildSearch = (starIndexes.get(0) == 0) && (starIndexes.get(1) == searchString.length() - 1);
        } else
            wildSearch = starIndexes.size() == 1 && ((starIndexes.get(0) == 0) || (starIndexes.get(0) == searchString.length() - 1));

    }

    public boolean getWildSearch() {
        return wildSearch;
    }

    public String getSearchString() {
        return searchString;
    }

    public SearchScope getSearchScope() {
        return searchScope;
    }
}
