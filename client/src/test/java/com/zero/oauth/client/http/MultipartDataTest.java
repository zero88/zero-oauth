package com.zero.oauth.client.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.InvalidPathException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.zero.oauth.core.TestBase;
import com.zero.oauth.core.exceptions.OAuthException;

public class MultipartDataTest extends TestBase {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    private MultipartData multipartData;

    @Before
    public void setUp() {
        multipartData = new MultipartData();
    }

    @Test(expected = NullPointerException.class)
    public void test_add_data_null() {
        multipartData.addData("test", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_add_data_fieldName_blank() {
        multipartData.addData("", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_add_file_blank_string_url() {
        multipartData.addFile("test", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_add_file_null_url_string() {
        String url = null;
        multipartData.addFile("test", url);
    }

    @Test(expected = OAuthException.class)
    public void test_add_file_url_not_valid() {
        multipartData.addFile("test", "hehe");
    }

    @Test(expected = NullPointerException.class)
    public void test_add_file_null_url() {
        URL url = null;
        multipartData.addFile("test", url);
    }

    @Test(expected = FileNotFoundException.class)
    public void test_add_file_url_not_found() throws Throwable {
        try {
            multipartData.addFile("", "file://tmp/xx");
        } catch (OAuthException ex) {
            throw ex.getCause();
        }
    }

    @Test
    public void test_add_file_url_wrong_syntax() {
        try {
            multipartData.addFile("", "https://postman-echo.com/post");
        } catch (OAuthException ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(isWin() ? cause instanceof InvalidPathException : cause instanceof FileNotFoundException);
        }
    }

    @Test(expected = NullPointerException.class)
    public void test_add_file_null_file() {
        File file = null;
        multipartData.addFile("test", file);
    }

    @Test(expected = FileNotFoundException.class)
    public void test_add_file_not_found() throws Throwable {
        try {
            multipartData.addFile("", new File("/tmp/xx"));
        } catch (OAuthException ex) {
            throw ex.getCause();
        }
    }

    @Test(expected = NullPointerException.class)
    public void test_add_file_null_inputStream() {
        multipartData.addFile("test", "file1", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_add_file_blank_fileName() {
        multipartData.addFile("test", "", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_add_file_blank_field() {
        multipartData.addFile("", "", null);
    }

    @Test
    public void test_add_file_inputStream() {
        multipartData.addFile("a1", "f1.txt",
                              this.getClass().getClassLoader().getResourceAsStream("none_private_key.txt"));
        Assert.assertNotNull(multipartData.getFiles().get("a1").get("f1.txt"));
    }

    @Test
    public void test_add_file_url_string() throws IOException {
        File file = tempFolder.newFile();
        multipartData.addFile("test", file.getAbsolutePath());
        Assert.assertNotNull(multipartData.getFiles().get("test").get(file.getName()));
    }

    @Test
    public void test_add_file() throws IOException {
        File file = tempFolder.newFile();
        multipartData.addFile("test", file);
        Assert.assertNotNull(multipartData.getFiles().get("test").get(file.getName()));
    }

    @Test
    public void test_add_file_url() {
        multipartData.addFile("a1", this.getClass().getClassLoader().getResource("none_private_key.txt"));
        Assert.assertNotNull(multipartData.getFiles().get("a1").get("none_private_key.txt"));
    }

}
