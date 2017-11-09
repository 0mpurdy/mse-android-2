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
        String[] result = TokenHelper.tokenizeString("One");
        assertArrayEquals(new String[]{"ONE"}, result);
    }

    @Test
    public void tokenizeString_Multiple() throws Exception {
        String[] result = TokenHelper.tokenizeString("Two tokens");
        assertArrayEquals(new String[]{"TWO", "TOKENS"}, result);
    }

    @Test
    public void tokenizeString_NonStandard() throws Exception {
        String[] result = TokenHelper.tokenizeString("heav'n");
        assertArrayEquals(new String[]{"HEAVN"}, result);
    }

    @Test
    public void tokenizeString_splitLine() throws Exception {
        String[] result = TokenHelper.tokenizeString("Two\ntokens");
        assertArrayEquals(new String[]{"TWO", "TOKENS"}, result);
    }

    @Test
    public void tokenizeString_withEmptySpaces() throws Exception {
        String[] result = TokenHelper.tokenizeString("Two     tokens");
        assertArrayEquals(new String[]{"TWO", "TOKENS"}, result);
    }

    @Test
    public void getTokensAsString_single() throws Exception {
        String result = TokenHelper.getTokensAsString(new String[]{"ONE"});
        assertEquals("ONE", result);
    }

    @Test
    public void getTokensAsString_multiple() throws Exception {
        String result = TokenHelper.getTokensAsString(new String[]{"TWO", "TOKENS"});
        assertEquals("TWO, TOKENS", result);
    }

}
