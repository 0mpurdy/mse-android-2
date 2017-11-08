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
    String[] searchTokens;

    public SearchCriteria(SearchType searchType, SearchScope searchScope, String searchString) {
        this.searchString = searchString;
        this.searchType = searchType;
        this.searchScope = searchScope;
        checkWildCard();
        makeTokens();
    }

    /**
     * Check if the current search is a wildcard search
     */
    private void checkWildCard() {

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

    /**
     * Convert the search string into a list of search tokens
     *
     * Tokens are uppercase, eg: Adam -> ADAM
     */
    private void makeTokens() {
        searchTokens = searchString.toUpperCase().split(" ");
        // TODO: handle non letter characters
        // TODO: handle wildcard search (create list of matching words)
    }

    /**
     * Get a list of all the search tokens for this criteria
     */
    public String[] getTokens() {
        return searchTokens;
    }

    /**
     * Get if the current search is a wildcard search
     */
    public boolean getWildSearch() {
        return wildSearch;
    }

    /**
     * Get the search string of this search
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * Get the scope of this search
     */
    public SearchScope getSearchScope() {
        return searchScope;
    }
}
