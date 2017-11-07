package com.a0mpurdy.mse.data.author;

public enum Author implements IAuthor {

    // region authors

    // Some not searchable
//    BIBLE(0, "Bible", "Bible", "bible", 66, false, true, true),
//    HYMNS(1, "Hymns", "Hymns", "hymns", 5, false, true, true),
//    TUNES(2, "Tunes", "Hymn Tunes", "tunes", 100, false, false, true),
//    JND(3, "JND", "J.N.Darby", "jnd", 52, true, true, true),
//    JBS(4, "JBS", "J.B.Stoney", "jbs", 17, true, true, false),
//    CHM(5, "CHM", "C.H.Mackintosh", "chm", 18, true, false, false),
//    FER(6, "FER", "F.E.Raven", "fer", 21, true, true, false),
//    CAC(7, "CAC", "C.A.Coates", "cac", 37, true, true, false),
//    JT(8, "JT", "J.Taylor Snr", "jt", 103, true, false, false),
//    GRC(9, "GRC", "G.R.Cowell", "grc", 88, true, false, false),
//    AJG(10, "AJG", "A.J.Gardiner", "ajg", 11, true, false, false),
//    SMC(11, "SMC", "S.McCallum", "smc", 10, true, false, false),
//    WJH(12, "WJH", "W.J.House", "wjh", 23, true, false, false),
//    Misc(13, "Misc", "Various Authors", "misc", 26, true, false, false);

    // all searchable (apart from tunes)
    BIBLE(0, "Bible", "Bible", "bible", 66, false, true, true),
    HYMNS(1, "Hymns", "Hymns", "hymns", 5, false, true, true),
    TUNES(2, "Tunes", "Hymn Tunes", "tunes", 100, false, false, true),
    JND(3, "JND", "J.N.Darby", "jnd", 52, true, true, true),
    JBS(4, "JBS", "J.B.Stoney", "jbs", 17, true, true, false),
    CHM(5, "CHM", "C.H.Mackintosh", "chm", 18, true, false, false),
    FER(6, "FER", "F.E.Raven", "fer", 21, true, true, false),
    CAC(7, "CAC", "C.A.Coates", "cac", 37, true, true, false),
    JT(8, "JT", "J.Taylor Snr", "jt", 103, true, true, false),
    GRC(9, "GRC", "G.R.Cowell", "grc", 88, true, true, false),
    AJG(10, "AJG", "A.J.Gardiner", "ajg", 11, true, true, false),
    SMC(11, "SMC", "S.McCallum", "smc", 10, true, true, false),
    WJH(12, "WJH", "W.J.House", "wjh", 23, true, true, false),
    Misc(13, "Misc", "Various Authors", "misc", 26, true, true, false);

    // endregion

    private final int index;
    private final String code;
    private final String name;
    private final String folder;
    private final int numVols;
    private final boolean isMinistry;
    private final boolean searchable;
    private final boolean asset;

    Author(int index, String code, String name, String folder, int numVols, boolean isMinistry, boolean searchable, boolean asset) {
        this.index = index;
        this.code = code;
        this.name = name;
        this.folder = folder;
        this.numVols = numVols;
        this.isMinistry = isMinistry;
        this.searchable = searchable;
        this.asset = asset;
    }

    public String getCode() {
        return code;
    }

    public String getFolder() {
        return folder;
    }

    public String getName() {
        return name;
    }

    public String getTargetName(int volumeNumber) {
        return folder + volumeNumber + ".html";
    }

    public int getNumVols() {
        return numVols;
    }

    @Override
    public AuthorType getType() {
        switch (this) {
            case BIBLE:
                return AuthorType.BIBLE;
            case HYMNS:
                return AuthorType.HYMNS;
            case TUNES:
                return AuthorType.TUNES;
            default:
                return AuthorType.MINISTRY;
        }
    }

    public String getSourceName(int volumeNumber) {
        return folder + volumeNumber + ".txt";
    }

    public boolean isMinistry() {
        return isMinistry;
    }

    @Override
    public boolean isSearchable() {
        return this.searchable;
    }

    @Override
    public boolean isAsset() {
        return asset;
    }

    /**
     * Check if the string matches the code or name of any author (ignores case)
     *
     * @param authorString String to check for author match
     * @return Author matching the string or null if none
     */
    public static Author getFromString(String authorString) {

        // go through each author and check if the name or the code matches
        for (Author nextAuthor : values()) {
            if (authorString.equalsIgnoreCase(nextAuthor.code) ||
                    authorString.equalsIgnoreCase(nextAuthor.name))
                return nextAuthor;
        }

        // potentially include switch statement for other string matches here

        // if no authors matched return null
        return null;

    }
}
