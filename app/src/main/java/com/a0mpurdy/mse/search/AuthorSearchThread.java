/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.a0mpurdy.mse.search;

//import com.a0mpurdy.mse_core.common.config.Config;
import com.a0mpurdy.mse_core.log.LogLevel;
import com.a0mpurdy.mse_core.log.LogRow;
import com.a0mpurdy.mse_core.data.author.Author;
import com.a0mpurdy.mse.search.criteria.SearchCriteria;
import com.a0mpurdy.mse.search.criteria.SearchScope;
import com.a0mpurdy.mse.search.results.ErrorResult;
import com.a0mpurdy.mse.search.results.IResult;
import com.a0mpurdy.mse.search.results.Result;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * @author Michael Purdy
 *         Thread to search a single author
 */
public class AuthorSearchThread extends SingleSearchThread {

//    private Config cfg;
    private ArrayList<LogRow> searchLog;
    private AuthorSearchCache asc;
    private ArrayList<IResult> authorResults;
    private AtomicInteger progress;
    private ArrayList<String> sentences = new ArrayList<>();
    private SearchCriteria criteria;

//    public AuthorSearchThread(Config cfg, AuthorSearchCache asc, AtomicInteger progress) {
    public AuthorSearchThread(AuthorSearchCache asc, AtomicInteger progress) {
//        this.cfg = cfg;
        this.searchLog = new ArrayList<>();
        this.authorResults = new ArrayList<>();
        this.progress = progress;

        this.asc = asc;
    }

    @Override
    public void run() {
        searchAuthor(authorResults, asc);
    }

    // region search

    /**
     * Search a single author
     * <p>
     * Search all the words to make sure that all the search tokens are in the author's
     * index. log any words that are too frequent, find the least frequent token and
     * record the number of infrequent words
     *
     * @param results List of results to add too
     * @param asc     Author Search Cache
     */
    private void searchAuthor(ArrayList<IResult> results, AuthorSearchCache asc) {

        log(LogLevel.DEBUG, "\tSearching: " + asc.author.getName() + " for \"" + asc.getSearchString() + "\"");

        // setup searchWords, searchTokens and leastFrequentTokens
        asc.setup(searchLog);

        // TODO: replace
        // log(LogLevel.TRACE, "\tSearch strings: " + asc.printableSearchWords());
        // log(LogLevel.TRACE, "\tSearch tokens: " + asc.printableSearchTokens());
        log(LogLevel.TRACE, "\tLeast Frequent Token: " + asc.getLeastFrequentToken());

        if ((asc.getLeastFrequentToken() != null) && !asc.getTokenNotFound()) {
            // at least one searchable token and all tokens in author index

            asc.setupReferences();

            // should be at least two references (volume and page to start)
            if (asc.referencesToSearch.length > 1) {

                // first two references should be volume and page
                asc.getNextPage();

                boolean volumeSuccess = true;

                // for each reference
                while (asc.reference.volNum != 0 && volumeSuccess) {

                    asc.notFoundCurrentHymnBook = true;
                    volumeSuccess = searchVolume(results, asc);

                } // end one frequent token and all tokens found

            } // end had references to search
            else {
                log(LogLevel.LOW, "No overlap in references for " + asc.author.getCode());
            }

        } else {
            String error = "\t\t\t<p class=\"centered\">";
            if (asc.getTokenNotFound()) {
                error += "Not all words were found in index.";

                // TODO: replace
                // searchLog.add(new LogRow(LogLevel.LOW, "\tTokens in " + asc.author.getCode() + " not all found: " + asc.printableSearchTokens()));
            }
            if (asc.getTooFrequentTokens().size() > 0) {
                error += "Some words appeared too frequently: " + asc.getTooFrequentTokensList();
                searchLog.add(new LogRow(LogLevel.LOW, "\tToo frequent tokens in " + asc.author.getCode() + ": " + asc.getTooFrequentTokensList()));
            }
            error += "</p>";
            results.add(new ErrorResult(error));
        }
    }

    /**
     * Search a single volume
     *
     * @param results List of results to add to
     * @param asc     Author Search Cache
     * @return true if no errors in searching
     */
    private boolean searchVolume(ArrayList<IResult> results, AuthorSearchCache asc) {

        // initialise page and volume number
        int cPageNum = 0;
        final int currentVolumeNumber = asc.reference.volNum;

        // log volume being searched
        searchLog.add(new LogRow(LogLevel.TRACE, "\tVol: " + currentVolumeNumber));

        // open the file

        // TODO: replace
        // String filename = cfg.getResDir() + File.separator + FileHelper.getHtmlFile(asc.author, currentVolumeNumber, File.separator);
        // IFileReader fReader = new HtmlReader(filename, searchLog);

        // read first line

        // TODO: replace
        // asc.line = fReader.readContentLine();

        // set progress
        progress.addAndGet(1000 / asc.author.getNumVols());

        // while still in the same volume keep searching
        while (asc.reference.volNum == currentVolumeNumber) {

            asc.prevLine = "";

            // skip to next page and get the last line of the previous page

            // TODO: replace
            // cPageNum = fReader.findNextAuthorPage(asc);

            // if the page number is 0 log then error and break out
            if (cPageNum == 0) {
                log(LogLevel.HIGH, "Could not find reference " + asc.reference.getShortReadableReference());

                // TODO: replacev
                // fReader.close();
                return false;
            }

            // get the first section

            // TODO: replace
            // asc.currentSectionHeader = fReader.getFirstAuthorSectionHeader(asc);

            // search the page
            if (asc.currentSectionHeader != null) {

                // TODO: replace
                // searchPage(results, asc, fReader);
            } else {
                searchLog.add(new LogRow(LogLevel.HIGH, "NULL line " + asc.reference.getShortReadableReference()));
            }
            asc.line = asc.currentSectionHeader;

            // clear the previousLine
            asc.prevLine = "";

            // get the next reference
            asc.getNextPage();

        } // finished references in volume


        // TODO: replace
        // Reader.close();
        return true;

    }

    private void searchPage(ArrayList<IResult> results, AuthorSearchCache asc) {

        boolean foundToken = false;

        asc.reference.sectionNum = 0;
        asc.reference.sentenceNum = 0;

        // while still on the same page (class != page-number)
        while (isNextSectionSearchable(asc.currentSectionHeader)) {

            // TODO: replace
//            asc.line = htmlReader.getNextAuthorSection(asc);

            String section = asc.line;

            asc.incrementSectionNumber();

            foundToken = searchSection(results, section, asc) || foundToken;

            // TODO: replace
//            asc.brl.setFoundDarby(foundToken);

            // set the current line as the previous line if it is searchable
            if (isNextSectionSearchable(asc.currentSectionHeader)) {
                asc.prevLine = asc.line;
            } else {
                asc.prevLine = "";
            }

            // TODO: replace
//            asc.currentSectionHeader = htmlReader.getNextSectionHeader(asc);
        }

        if (!foundToken) {
            // if exact match only debug level as many references will not have exact matches

            // TODO: replace
            // if (asc.getSearchType() == SearchType.MATCH) {
            searchLog.add(new LogRow(LogLevel.DEBUG, "Did not find token " + asc.reference.getShortReadableReference()));
        } else {
            searchLog.add(new LogRow(LogLevel.LOW, "Did not find token " + asc.reference.getShortReadableReference()));
        }
    }

    private boolean searchSection(ArrayList<IResult> results, String section, AuthorSearchCache asc) {
        switch (asc.author) {
            case BIBLE:
                return searchBibleSection(results, section, asc);
            default:
                return searchMinistrySection(results, section, asc);
        }
    }

    private boolean searchBibleSection(ArrayList<IResult> results, String section, AuthorSearchCache asc) {
        // return true if jnd OR kjv are valid

        boolean validSection = false;

        String[] lines = section.split(Pattern.quote(" <!-> "));

        String jndSection = lines[0];
        String kjvSection = lines[1];

        if (searchMinistrySection(results, jndSection, asc) || searchMinistrySection(results, kjvSection, asc)) {
            Result result = new Result(asc.reference.copy(), section);
            results.add(result);
            asc.incrementResults();
            validSection = true;
        }

        return validSection;
    }

    private boolean searchMinistrySection(ArrayList<IResult> results, String section, AuthorSearchCache asc) {

        ArrayList<String> scopes = getSearchScopes(section, asc.prevLine);

        boolean validSection = false;

        for (String scope : scopes) {

            if (isSentenceScope()) asc.reference.sentenceNum++;

            boolean validScope;

            if (asc.getWildSearch()) {

                // TODO: replace
                // validScope = SearchType.wildSearch(HtmlHelper.tokenizeLine(scope), asc.getSearchTokens());
            } else {

                // TODO: replace
                // String[] tokenizedLine = HtmlHelper.tokenizeLine(scope);
//                 validScope = asc.getSearchType().search(tokenizedLine, asc.getSearchTokens());
            }

            // TODO: replace
            // if the current scope contains all search terms mark them and print it out (or one if it is a wildcard search)
//            if (validScope) {
//
//                validSection = true;
//
//                if (!(asc.author == Author.BIBLE)) {
//
//                    String markedLine = HtmlHelper.markLine(asc.author, new StringBuilder(scope), asc.getSearchWords(), "mse-mark");
//
//                    Result result;
//                    if (asc.author.equals(Author.HYMNS) && asc.notFoundCurrentHymnBook) {
//                        asc.notFoundCurrentHymnBook = false;
//                        result = new Result(asc.author, asc.reference.copy(), markedLine, asc.getSearchWords(),
//                                HtmlHelper.getFormattedHymnbookLink(asc));
//                    } else {
//                        result = new Result(asc.author, asc.reference.copy(), markedLine, asc.getSearchWords());
//                    }
//                    results.add(result);
//
//                    asc.incrementResults();
//                }
//            }
        }

        return validSection;

    }

    private ArrayList<String> getSearchScopes(String section, String previousLine) {
        ArrayList<String> scopes = new ArrayList<>();
        // get the search lines based on the search scope
        if (isSentenceScope()) {
            scopes = convertLineIntoSentences(section, getTrailingIncompleteSentence(previousLine));
        } else {
            scopes.add(section);
        }
        return scopes;
    }

    private boolean isSentenceScope() {
        return criteria.getSearchScope() == SearchScope.SENTENCE;
    }

    private boolean isNextSectionSearchable(String sectionHeader) {

        if (asc.author.isMinistry()) {
            return sectionHeader.contains("class=\"heading\"") || sectionHeader.contains("class=\"paragraph\"") || sectionHeader.contains("class=\"footnote\"");

            // TODO: replace
            // } else if (asc.author.equals(Author.BIBLE)) {
//            return sectionHeader.contains("<tr");
//        } else if (asc.author.equals(Author.HYMNS)) {
//            return sectionHeader.contains("class=\"verse-number\"");
        } else {
            return false;
        }
    }

    // endregion

    // region processSection

    private String getTrailingIncompleteSentence(String line) {

        if (asc.author.equals(Author.BIBLE)) return line;

        if (line.isEmpty()) return "";

        int startOfLastSentencePos = line.lastIndexOf("<a name=");

        // no sentence break in line
        if (startOfLastSentencePos < 0) return line;

        startOfLastSentencePos = line.indexOf('>', startOfLastSentencePos) + 1;
        startOfLastSentencePos = line.indexOf('>', startOfLastSentencePos) + 1;

        if (startOfLastSentencePos < line.length()) return line.substring(startOfLastSentencePos);
        return "";
    }

    private ArrayList<String> convertLineIntoSentences(String line, String trailingIncompleteSentence) {

        sentences.clear();
        int startOfSentencePos = 0;
        int endOfSentencePos = 0;

        if (!line.contains("<a name=")) {
            // if there are no fullstops return the whole line
            sentences.add(line);
            return sentences;
        }

        while (endOfSentencePos >= 0 && endOfSentencePos < line.length()) {

            endOfSentencePos = line.indexOf("<a name=", startOfSentencePos) - 1;

            // if there are no fullstops return the whole line
            if (endOfSentencePos < 0) {
                sentences.add(line.substring(startOfSentencePos, line.length()));
                return sentences;
            }

            sentences.add(line.substring(startOfSentencePos, endOfSentencePos));

            // skip the a tags
            startOfSentencePos = line.indexOf('>', endOfSentencePos) + 1;
            startOfSentencePos = line.indexOf('>', startOfSentencePos) + 1;
            endOfSentencePos = startOfSentencePos;

        }

        prependTrailingIncompleteSentence(sentences, trailingIncompleteSentence);

        return sentences;
    }

    private ArrayList<String> prependTrailingIncompleteSentence(ArrayList<String> scopes, String trailingIncompleteSentence) {
        if (scopes.size() > 0) {
            scopes.set(0, trailingIncompleteSentence + scopes.get(0));
        }
        return scopes;
    }

    private String getBasicWords(String strIn, boolean dropPunctuation, boolean dropTableTags) {
        String inString = strIn;
        String outString = "";
        char currentChar;
        int currentPosition = 0;
        int numSpaces = 0;
        int numCols = 0;
        int lengthOfInString = inString.length();

        //remove html tags & page numbers
        while (currentPosition < lengthOfInString) {
            currentChar = inString.charAt(currentPosition);
            if ((currentChar == 13) || (currentChar == 10)) {
                //ignore carriage returns & line feeds

            } else if (currentChar == '<') {

                // get the position of an html starting tag
                int keepCurrPos = currentPosition;

                // find the positon of an html end tag
                while ((currentPosition < inString.length()) && (inString.charAt(currentPosition) != '>')) {
                    currentPosition++;
                }

                // TODO -I find out why you do this
                // if you haven't reached the end of the string to remove tags from and you don't have to drop table tags
                if ((currentPosition < inString.length()) && (!dropTableTags)) {

                    // get the contents of the tag
                    String htmlTag = inString.substring(keepCurrPos, currentPosition);

                    if ((htmlTag.equals("</td")) && (numCols < 2)) {
                        outString += "</td><td>";
                        numCols++;
                    } else if (htmlTag.equals("</tr")) {
                        outString += ("</td></tr><tr><td>");
                        numCols = 0;
                    }
                }

                // TODO -I find out what this does
                if ((inString.length() - currentPosition) > 10) {//possibly new page
                    String strNextBit = inString.toString().substring(currentPosition + 1, currentPosition + 7);
                    if (strNextBit.equals("[Page ")) {//throw away word 'page'
                        while ((currentPosition < inString.length()) && (inString.charAt(currentPosition) != ']')) {
                            currentPosition++;
                        }
                    }
                }

                // TODO -I find out why
                // if there are no spaces, add one on the end
                if (numSpaces == 0) {
                    outString += " ";
                    numSpaces++;
                }

                // TODO -I could this be else if
            } else {
                if (dropPunctuation) {

                    // if punctuation is to be dropped
                    if (Character.isLetter(currentChar) || Character.isDigit(currentChar)) {

                        // if the character is a letter or a digit
                        // add the current character the the output
                        outString += currentChar;
                        numSpaces = 0;

                    } else if (currentChar == '-' || currentChar == '\'') {
                        if ((Character.isLetter(outString.charAt(outString.length() - 1))) && (numSpaces == 0)) {
                            // if the last character in the output so far is a letter and the number of spaces is zero
                            outString += currentChar;
                        } else if (numSpaces == 0) {
                            outString += " ";
                            numSpaces++;
                        }
                    } else if (numSpaces == 0) {
                        outString += " ";
                        numSpaces++;
                    }
                } else {
                    // if punctation is kept just add the current character to the output
                    outString += currentChar;
                    numSpaces = 0;
                }
            }
            currentPosition++;
        }
        return outString;
    }

    // endregion

    // region logging

    private void log(LogLevel logLevel, String message) {
        searchLog.add(new LogRow(logLevel, message));
    }

    // endregion

    // region singleSearchThreadMethods

    @Override
    public ArrayList<LogRow> getLog() {
        return searchLog;
    }

    @Override
    public ArrayList<IResult> getResults() {
        return authorResults;
    }

    @Override
    public int getNumberOfResults() {
        return asc.numAuthorResults;
    }

    public AuthorSearchCache getAsc() {
        return asc;
    }

    // endregion

}
