package com.a0mpurdy.mse.data.hymn;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mj_pu_000 on 07/11/2017.
 */

public class HymnVerse implements Serializable {

    private static final long serialVersionUID = 8458991589149417489L;

    private Hymn parentHymn;
    private int verseNumber;
    private ArrayList<String> lines;

    public HymnVerse(Hymn parentHymn, int verseNumber) {
        this.parentHymn = parentHymn;
        this.verseNumber = verseNumber;
        this.lines = new ArrayList<>();
    }

    public String getShortDescription() {
        return this.parentHymn.getShortDescription() + ":" + verseNumber;
    }

    public Hymn getParentHymn() {
        return parentHymn;
    }

    public String getVerseText() {
        StringBuilder verse = new StringBuilder();
        for (String line : lines) {
            verse.append(line);
            verse.append('\n');
        }
        return verse.toString();
    }

    public int getNumber() {
        return verseNumber;
    }
}
