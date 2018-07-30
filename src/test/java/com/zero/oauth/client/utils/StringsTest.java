package com.zero.oauth.client.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringsTest {

    @Test
    public void testBlank() {
        assertTrue(Strings.isBlank(""));
        assertTrue(Strings.isBlank(null));
        assertTrue(Strings.isBlank(" "));
        assertTrue(Strings.isBlank("    "));
        assertFalse(Strings.isBlank("a"));
    }

    @Test
    public void testNotBlank() {
        assertTrue(Strings.isNotBlank("a"));
        assertTrue(Strings.isNotBlank(" a"));
        assertTrue(Strings.isNotBlank("a "));
        assertTrue(Strings.isNotBlank(" a "));
        assertFalse(Strings.isNotBlank(" "));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRequireNotBlank_shouldFailed() {
        Strings.requireNotBlank("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRequireNotBlankWithMessage_shouldFailed() {
        try {
            Strings.requireNotBlank("", "Cannot blank");
        } catch (IllegalArgumentException ex) {
            assertEquals("Cannot blank", ex.getMessage());
            throw ex;
        }
    }

    @Test
    public void testRequireNotBlank_shouldSuccess() {
        assertEquals("abc", Strings.requireNotBlank(" abc "));
    }
}
