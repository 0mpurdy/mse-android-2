package com.a0mpurdy.mse.search;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Helper class to create tokens from search strings and searchable text
 */
public class TokenHelper {

    /**
     * Get a comma separated list of the tokens
     */
    public static String getTokensAsString(String[] searchTokens) {
        StringBuilder tokenString = new StringBuilder();
        int i = 0;
        for (String token : searchTokens) {
            tokenString.append(token);
            if (++i < searchTokens.length) {
                tokenString.append(", ");
            }
        }
        return tokenString.toString();
    }

    /**
     * Process a list of tokens to remove non-alpabetical characters
     *
     * @param tokens tokens to process
     */
    private static String[] tokenizeArray(String[] tokens) {

        ArrayList<String> newTokens = new ArrayList<>();

        // make each token into a word that can be searched
        for (String token : tokens) {
            token = token.toUpperCase();
            if (!isAlpha(token)) {
                token = processString(token);
            }
            if (!token.isEmpty()) {
                newTokens.add(token);
            }
        } // end for each token

        String[] newTokensArray = new String[newTokens.size()];
        newTokensArray = newTokens.toArray(newTokensArray);

        return newTokensArray;
    }

    /**
     * Convert the string into a list of tokens
     *
     * Tokens are uppercase, eg: Adam -> ADAM
     */
    public static String[] tokenizeString(String line) {
        return tokenizeArray(line.split("\\s"));
    }

    /**
     * Check if the string only contains alphabetical characters
     * @param token string to check for non-alphabetical characters
     */
    private static boolean isAlpha(String token) {
        char[] chars = token.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Remove non word characters from a token
     *
     * eg: heav'n -> heavn
     */
    private static String processString(String token) {
        for (char c : token.toCharArray()) {
            if (!Character.isLetter(c)) {
                token = token.replace(Character.toString(c), "");
            }
        }

        return token;
    }
}
