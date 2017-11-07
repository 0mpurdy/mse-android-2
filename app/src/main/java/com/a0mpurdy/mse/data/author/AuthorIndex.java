package com.a0mpurdy.mse.data.author;

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

}
