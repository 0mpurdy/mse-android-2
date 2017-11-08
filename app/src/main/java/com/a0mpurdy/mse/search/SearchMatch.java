package com.a0mpurdy.mse.search;

import com.a0mpurdy.mse.search.criteria.SearchType;

public class SearchMatch {

    /**
     * Search the scope tokens using the search tokens with a specific search type
     *
     * @param type         type of search to perform
     * @param scopeTokens  the tokens to search through
     * @param searchTokens the tokens to search with
     * @return
     */
    public static boolean search(SearchType type, String[] scopeTokens, String[] searchTokens) {
        boolean valid = false;

        switch (type) {
            case EXACT:
                valid = clauseSearch(scopeTokens, searchTokens);
                break;
            case ORDER:
                valid = scopeWordsInOrder(scopeTokens, searchTokens);
                break;
            case CONTAINS:
                valid = wordSearch(scopeTokens, searchTokens);
                break;
        }

        return valid;
    }

    /**
     * Matches if any tokens in the search tokens match the current line tokens
     *
     * @param scopeTokens  tokens in the current scope
     * @param searchTokens tokens to search for
     * @return true if any matches
     */
    public static boolean wildSearch(String[] scopeTokens, String[] searchTokens) {
        for (String nextSearchToken : searchTokens) {
            for (String nextLineToken : scopeTokens) {
                if (nextSearchToken.equals(nextLineToken)) {
                    return true;
                }
            }
        }

        // if it reaches this point as a wild search then no tokens were found
        return false;
    }

    /**
     * All the search tokens are in the current scope tokens
     *
     * @param scopeTokens  tokens in the current scope
     * @param searchTokens tokens to search for
     * @return true if all search tokens in scope tokens
     */
    private static boolean wordSearch(String[] scopeTokens, String[] searchTokens) {

        for (String nextSearchToken : searchTokens) {
            boolean foundCurrentSearchToken = false;
            for (String nextLineToken : scopeTokens) {
                if (nextSearchToken.equals(nextLineToken)) {
                    foundCurrentSearchToken = true;
                }
            }
            if (!foundCurrentSearchToken) return false;
        }

        // if it reaches this point all tokens were found
        return true;
    }

    /**
     * Check if all the search tokens are in the scope tokens
     * AND in order ADJACENT to each other
     *
     * @param scopeTokens  tokens in the current scope
     * @param searchTokens tokens to search for
     * @return
     */
    private static boolean clauseSearch(String[] scopeTokens, String[] searchTokens) {

        // read through the clause finding each word in order
        // return false if reached the end without finding every word

        // true if the next word should be next word in the search tokens
        boolean currentWordIsSearchToken;

        // position of the next token to find in the search tokens array
        int j = 0;

        //
        for (int i = 0; i < scopeTokens.length; i++) {

            if (scopeTokens[i].equals("")) continue;

            currentWordIsSearchToken = false;
            if (scopeTokens[i].equalsIgnoreCase(searchTokens[j])) {
                j++;
                currentWordIsSearchToken = true;
            }

            if (j > 0) {

                // if all words found in order return true
                if (j == searchTokens.length) return true;

                // if current word wasn't a search token reset j
                if (!currentWordIsSearchToken) j = 0;
            }
        }

        return false;

    }

    /**
     * Check if all the search tokens are in the scope tokens
     * AND in order
     *
     * @param scopeTokens  tokens in the current scope
     * @param searchTokens tokens to search for
     * @return
     */
    private static boolean scopeWordsInOrder(String[] scopeTokens, String[] searchTokens) {
        int indexNextLineToken = 0;
        int indexNextSearchToken = 0;

        while (indexNextLineToken < scopeTokens.length) {
            if (scopeTokens[indexNextLineToken].toUpperCase().equals(searchTokens[indexNextSearchToken])) {
                indexNextSearchToken++;

                // if no more tokens to find
                if (indexNextSearchToken == searchTokens.length) return true;
            }
            indexNextLineToken++;
        }

        // if gone through all the line and not found all the tokens in the order
        return false;
    }

}
