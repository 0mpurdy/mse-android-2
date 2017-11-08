package com.a0mpurdy.mse;

import com.a0mpurdy.mse.search.TokenHelper;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by mj_pu_000 on 08/11/2017.
 */

public class TokenHelperTests {

    @Test
    public void tokenizeString_Single() throws Exception {
        assertArrayEquals(TokenHelper.tokenizeString("One"), new String[]{"ONE"});
    }

    @Test
    public void tokenizeString_Multiple() throws Exception {
        assertArrayEquals(TokenHelper.tokenizeString("Two tokens"), new String[]{"TWO", "TOKENS"});
    }

    @Test
    public void tokenizeString_NonStandard() throws Exception {
        assertArrayEquals(TokenHelper.tokenizeString("heav'n"), new String[]{"HEAVN"});
    }

    @Test
    public void tokenizeString_splitLine() throws Exception {
        assertArrayEquals(TokenHelper.tokenizeString("Two\ntokens"), new String[]{"TWO", "TOKENS"});
    }

    @Test
    public void tokenizeString_withEmptySpaces() throws Exception {
        assertArrayEquals(TokenHelper.tokenizeString("Two     tokens"), new String[]{"TWO", "TOKENS"});
    }

    @Test
    public void getTokensAsString_single() throws Exception {
        assertEquals(TokenHelper.getTokensAsString(new String[]{"ONE"}), "ONE");
    }

    @Test
    public void getTokensAsString_multiple() throws Exception {
        assertEquals(TokenHelper.getTokensAsString(new String[]{"TWO", "TOKENS"}), "TWO, TOKENS");
    }

}
