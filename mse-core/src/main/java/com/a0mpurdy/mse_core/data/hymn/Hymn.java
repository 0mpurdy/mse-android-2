package com.a0mpurdy.mse_core.data.hymn;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Hymn
 *
 * @author MichaelPurdy
 */
public class Hymn implements Serializable {

    private static final long serialVersionUID = 5565913617525938193L;

    private HymnBook parentHymnBook;
    private int number;
    private String author;
    private String meter;
    private ArrayList<HymnVerse> verses;
    private HymnVerse chorus;

    public Hymn(HymnBook parentHymnBook, int number, String author, String meter) {
        this.parentHymnBook = parentHymnBook;
        this.number = number;
        this.author = author;
        this.meter = meter;
        this.verses = new ArrayList<>();
    }

    public HymnBook getParentHymnBook() {
        return parentHymnBook;
    }

    public String getShortDescription() {
        return this.parentHymnBook.getShortDescription() + ":" + number;
    }

    public int getNumber() {
        return number;
    }

    public HymnVerse getVerse(int number) {
        return verses.get(number - 1);
    }

    public String getVerseText() {
        StringBuilder verseText = new StringBuilder();
        for (HymnVerse verse : verses) {
            verseText.append(verse.getVerseText());
            verseText.append('\n');
        }
        return verseText.toString();
    }

    public ArrayList<HymnVerse> getVerses() {
        return verses;
    }
}
