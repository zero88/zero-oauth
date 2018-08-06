package com.zero.oauth.client.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TokenGenerationTest {

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
