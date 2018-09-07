package com.zero.oauth.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.zero.oauth.core.TestBase;

public class HttpStatusTest extends TestBase {

    @Test
    public void test_OkInstance() {
        HttpStatus status = new HttpStatus(200, "OK");
        assertTrue(status.isOk());
        assertFalse(status.isError());
        assertFalse(status.isClientError());
        assertFalse(status.isServerError());
        assertEquals("OK", status.getMessage());
    }

    @Test
    public void test_ErrorInstance() {
        HttpStatus status = new HttpStatus(400, "Bad Request");
        assertTrue(status.isError());
        assertTrue(status.isClientError());
        assertFalse(status.isOk());
        assertFalse(status.isServerError());
        assertEquals("Bad Request", status.getMessage());
    }

    @Test
    public void test_ServerErrorInstance() {
        HttpStatus status = new HttpStatus(500, "Internal server error");
        assertTrue(status.isError());
        assertTrue(status.isServerError());
        assertFalse(status.isOk());
        assertFalse(status.isClientError());
        assertEquals("Internal server error", status.getMessage());
    }

    @Test
    public void test_Ok() {
        assertTrue(HttpStatus.isOk(200));
        assertFalse(HttpStatus.isOk(100));
        assertFalse(HttpStatus.isOk(300));
        assertFalse(HttpStatus.isOk(400));
        assertFalse(HttpStatus.isOk(500));
    }

    @Test
    public void test_Error() {
        assertTrue(HttpStatus.isError(400));
        assertTrue(HttpStatus.isError(500));
        assertFalse(HttpStatus.isError(100));
        assertFalse(HttpStatus.isError(200));
        assertFalse(HttpStatus.isError(300));
    }

    @Test
    public void test_ClientError() {
        assertTrue(HttpStatus.isClientError(400));
        assertFalse(HttpStatus.isClientError(500));
        assertFalse(HttpStatus.isClientError(100));
        assertFalse(HttpStatus.isClientError(300));
    }

    @Test
    public void test_ServerError() {
        assertTrue(HttpStatus.isServerError(500));
        assertFalse(HttpStatus.isServerError(100));
        assertFalse(HttpStatus.isServerError(300));
        assertFalse(HttpStatus.isServerError(400));
    }

}
