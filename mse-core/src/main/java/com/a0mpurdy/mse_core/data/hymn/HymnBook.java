package com.a0mpurdy.mse_core.data.hymn;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Hymn book
 *
 * @author MichaelPurdy
 */
public class HymnBook implements Serializable, IHymnBook {

    private static final long serialVersionUID = 1L;

    private String title;
    private String filename;
    private String code;
    private ArrayList<Hymn> hymns;

    public HymnBook(String title, String filename, String code) {
        this.title = title;
        this.filename = filename;
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return this.title;
    }

    public String getSerializedName() {
        return filename + ".ser";
    }

    @Override
    public int getNumHymns() {
        return hymns.size();
    }

    public Hymn getHymn(int number) throws IndexOutOfBoundsException {
        return hymns.get(number - 1);
    }

    public int getId() {
        return 1962;
    }

    public ArrayList<Hymn> getHymns() {
        return hymns;
    }
}
