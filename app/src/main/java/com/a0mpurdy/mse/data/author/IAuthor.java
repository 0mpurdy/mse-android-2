package com.a0mpurdy.mse.data.author;

/**
 * @author 0mpurdy
 */
public interface IAuthor {

    /**
     * Get the folder name that contains the files for the author
     * @return String Folder name
     */
    String getFolder();

    /**
     * Get the short string code that represents the author
     * @return String Code
     */
    String getCode();

    /**
     * Get the source filename for a specific volume number
     * @param volumeNumber Volume number of the source file
     * @return String Source file name
     */
    String getSourceName(int volumeNumber);

    /**
     * Get the target filename for a specific volume number
     * @param volumeNumber Volume number of the source file
     * @return String Target file name
     */
    String getTargetName(int volumeNumber);

    /**
     * Get the type of the author (eg: BIBLE or MINISTRY)
     * @return AuthorType Type of author
     */
    AuthorType getType();

    /**
     * Get if the author is searchable (eg: Tunes is false)
     * @return Boolean True if the author can be searched
     */
    boolean isSearchable();

    /**
     * Get if the author is an Android asset
     * @return Boolean True if the author is an Android asset
     */
    boolean isAsset();
}
