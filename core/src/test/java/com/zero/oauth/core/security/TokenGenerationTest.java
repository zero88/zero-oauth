package com.zero.oauth.core.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.zero.oauth.core.TestBase;

public class TokenGenerationTest extends TestBase {

    @Test
    public void test_generate_by_empty_value() {
        TokenGeneration tokenGeneration = new TokenGeneration();
        String token = tokenGeneration.apply(null);
        assertNotNull(token);
        assertEquals(token, tokenGeneration.apply(null));
    }

    @Test
    public void test_generate_by_specific_value() {
        TokenGeneration tokenGeneration = new TokenGeneration();
        String token = tokenGeneration.apply("xyz");
        assertEquals("xyz", token);
    }

}
