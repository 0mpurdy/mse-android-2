package com.a0mpurdy.mse.search.results;

import com.a0mpurdy.mse.data.author.Author;
import com.a0mpurdy.mse.search.source.Reference;

import java.util.regex.Pattern;

/**
 * Created by michaelpurdy on 30/12/2015.
 */
public class Result implements IResult {

    Author author;
    Reference reference;

    String[] searchWords;

    String text;

    private boolean newHymnBook;
    private String hymnBookLink;

    // region constructors

    public Result(Author author, Reference reference, String text, String[] searchWords) {
        this.author = author;
        this.reference = reference;
        this.searchWords = searchWords;
        this.text = text;
        this.newHymnBook = false;
    }

    public Result(Author author, Reference reference, String text, String[] searchWords, String hymnBookLink) {
        this.author = author;
        this.reference = reference;
        this.searchWords = searchWords;
        this.text = text;
        this.newHymnBook = true;
        this.hymnBookLink = hymnBookLink;
    }

    // endregion

    // region blockConstructors

//    public Result(Author author, String resultBlock, String[] searchWords) {
//        // this extracts a result from a result block
//        this.author = author;
//        this.searchWords = searchWords;
//
//        switch (author) {
//            case BIBLE:
//                constructBibleFromBlock(resultBlock);
//                break;
//            case HYMNS:
//                constructHymnFromBlock(resultBlock);
//                break;
//            default:
//                constructMinistryFromBlock(resultBlock);
//        }
//    }

    private int getMinistryBookNumber(String bookName) {
        StringBuilder sb = new StringBuilder(bookName);
        int charPos = 0;
        while (charPos < sb.length() && !(Character.isDigit(sb.charAt(charPos)))) charPos++;
        return Integer.parseInt(sb.replace(0, charPos, "").toString());
    }

    // endregion

    // region refine

    // TODO: replace
//    public boolean refine(boolean contains, String[] refineWords) {
//
//        String[] lineTokens = HtmlHelper.tokenizeLine(text);
//
//        if (contains) {
//            return SearchType.SENTENCE.search(lineTokens, refineWords);
//        } else {
//            return !SearchType.SENTENCE.search(lineTokens, refineWords);
//        }
//
//    }

    // endregion

    // region output

    /**
     * Print the result on the console (for debugging)
     */
    public void print() {
        System.out.println(author.getCode() + ": " + reference.getReadableReference());
        System.out.println(text);
    }

    /**
     * Print just the result block on the console (for debugging)
     */
    public void printBlock() {
        System.out.println(getBlock());
    }

    /**
     * Get the result block HTML
     *
     * @return The result in HTML format
     */
    @Override
    public String getBlock() {
        // TODO: replace
//        switch (author) {
//            case BIBLE:
//                return getBibleBlock();
//            case HYMNS:
//                return getHymnsBlock();
//            default:
//                return getMinistryBlock();
//        }
        return null;
    }

    // endregion

}
