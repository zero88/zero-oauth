package com.zero.oauth.client.http;

import java.io.InputStream;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import com.zero.oauth.core.TestBase;

public class HttpDataTest extends TestBase {

    @Test
    public void test_Default() {
        HttpData httpData = HttpData.builder().build();
        Assert.assertTrue(httpData.getHeaderMap().isEmpty());
        Assert.assertNull(httpData.getStreamBody());
        Assert.assertNull(httpData.getStrBody());
        Assert.assertNull(httpData.getBytes());
        Assert.assertNull(httpData.getBytesBody());
        Assert.assertNull(httpData.getStatus());
        Assert.assertNull(httpData.getMultipartData());
        Assert.assertNull(httpData.getQuery());
        Assert.assertFalse(httpData.isMultipart());
        Assert.assertFalse(httpData.isResponse());
    }

    @Test
    public void test_Header_String() {
        HttpData httpData = HttpData.builder().header(
            "Content-Type: application/x-www-form-urlencoded\r\nx-forwarded-port: 443").build();
        Assert.assertEquals(2, httpData.getHeaderMap().size());
        Assert.assertEquals("application/x-www-form-urlencoded", httpData.getHeaderMap().get("Content-Type"));
        Assert.assertEquals("443", httpData.getHeaderMap().get("x-forwarded-port"));
    }

    @Test
    public void test_Header_Map() {
        HttpData httpData = HttpData.builder().headerMap(Collections.singletonMap("Content-Type", "application/json"))
                                    .build();
        Assert.assertEquals(1, httpData.getHeaderMap().size());
        Assert.assertEquals("application/json", httpData.getHeaderMap().get("Content-Type"));
    }

    @Test
    public void test_Header_Add() {
        HttpData httpData = HttpData.builder().addHeader("Content-Type", "application/json").addHeader(
            "x-forwarded-port", "443").build();
        Assert.assertEquals(2, httpData.getHeaderMap().size());
        Assert.assertEquals("application/json", httpData.getHeaderMap().get("Content-Type"));
        Assert.assertEquals("443", httpData.getHeaderMap().get("x-forwarded-port"));
    }

    @Test
    public void test_Add_String_Body() {
        HttpData httpData = HttpData.builder().strBody("hello").build();
        Assert.assertEquals("hello", httpData.getStrBody());
        Assert.assertArrayEquals(new byte[] {104, 101, 108, 108, 111}, httpData.getBytes());
    }

    @Test
    public void test_Add_Stream_Body() {
        InputStream inputStream = HttpDataTest.class.getClassLoader().getResourceAsStream("hello.txt");
        HttpData httpData = HttpData.builder().streamBody(inputStream).build();
        Assert.assertNotNull(httpData.getStreamBody());
        Assert.assertArrayEquals(new byte[] {104, 101, 108, 108, 111, 10}, httpData.getBytes());
    }

    @Test
    public void test_Add_Byte_Body() {
        HttpData httpData = HttpData.builder().bytesBody(new byte[] {0, 1, 2, 3}).build();
        Assert.assertArrayEquals(new byte[] {0, 1, 2, 3}, httpData.getBytes());
    }

    @Test
    public void test_Add_Query() {
        HttpData httpData = HttpData.builder().query("heyhey").build();
        Assert.assertEquals("heyhey", httpData.getQuery());
    }

    @Test
    public void test_Add_Multipart() {
        HttpData httpData = HttpData.builder().multipartData(new MultipartData().addData("user", "test")).build();
        Assert.assertTrue(httpData.isMultipart());
    }

    @Test
    public void test_Create_ResponseData() {
        HttpData httpData = HttpData.builder().status(new HttpStatus(200)).build();
        Assert.assertTrue(httpData.isResponse());
    }

}
