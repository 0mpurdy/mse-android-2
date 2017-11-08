package com.a0mpurdy.mse_core.data.author;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mj_pu_000 on 09/09/2015.
 */
public class AuthorIndex {

    private Author author;
    private HashMap<String, Integer> tokenCountMap;
    private HashMap<String, short[]> references;

    public AuthorIndex(Author author) {
        this.author = author;
        tokenCountMap = new HashMap<>();
        references = new HashMap<>();
    }

    public AuthorIndex(Author author, HashMap<String, Integer> tokenCountMap, HashMap<String, short[]> references) {
        this(author);
        this.tokenCountMap = tokenCountMap;
        this.references = references;
    }

    public Author getAuthor() {
        return author;
    }

    public String getAuthorName() {
        return author.getName();
    }

    public HashMap<String, Integer> getTokenCountMap() {
        return tokenCountMap;
    }

    public int getTokenCount(String token) {
        if (tokenCountMap.get(token) != null) {
            return tokenCountMap.get(token);
        } else {
            return 0;
        }
    }

    public HashMap<String, short[]> getReferencesMap() {
        return references;
    }

    public short[] getReferences(String key) {
        return references.get(key);
    }

    /**
     * Get a list of all references where the keys have matching references
     *
     * @param keys                keys to check
     * @param infrequentThreshold number of references below which it is deemed to be "infrequent"
     * @return
     */
    public short[] getOverlappingReferences(String[] keys, int infrequentThreshold) {
        int[] freq = getKeyFrequencies(keys);
        ArrayList<Integer> infrequent = new ArrayList<>();
        // TODO: check that keys.length > 0
        int leastFrequent = 0;
        for (int i = 0; i < keys.length; i++) {
            if (freq[i] < freq[leastFrequent]) {
                leastFrequent = i;
            }
            if (freq[i] < infrequentThreshold) {
                infrequent.add(i);
            }
        }

        short[] validReferences = getReferences(keys[leastFrequent]);

        for (int infreqentIndex : infrequent) {
            if (infreqentIndex != leastFrequent) {
                validReferences = refineOverlapping(validReferences, getReferences(keys[infreqentIndex]));
            }
        }

        return validReferences;
    }

    /**
     * Return a list of references which are present in both lists
     *
     * @param original   original reference list
     * @param comparison list of references to compare to
     * @return
     */
    short[] refineOverlapping(short[] original, short[] comparison) {
        return original;
    }

    /**
     * Get the frequency of each token in the search criteria
     *
     * @param keys tokens to check the frequency of
     */
    int[] getKeyFrequencies(String[] keys) {
        int[] freq = new int[keys.length];
        for (int i = 0; i < keys.length; i++) {
            freq[i] = getTokenCount(keys[i]);
        }
        return freq;
    }

}
