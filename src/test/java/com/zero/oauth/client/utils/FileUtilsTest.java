package com.zero.oauth.client.utils;

import java.io.IOException;
import java.nio.file.FileSystemException;

import org.junit.Assert;
import org.junit.Test;

public class FileUtilsTest {

    @Test(expected = FileSystemException.class)
    public void test_FileURINotFound() throws Throwable {
        readErrorFile("file://tmp/xx");
    }

    private void readErrorFile(String filePath) throws Throwable {
        try {
            FileUtils.readFileToString(filePath);
        } catch (RuntimeException ex) {
            throw ex.getCause();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_URISyntaxError() throws Throwable {
        readErrorFile("f/:/xx");
    }

    @Test(expected = IOException.class)
    public void test_FileNotFound() throws Throwable {
        readErrorFile("/tmp/xx");
    }

    @Test
    public void test_ReadFile_Success() {
        String filePath = this.getClass().getClassLoader().getResource("none_private_key.txt").toString();
        Assert.assertEquals("hello", FileUtils.readFileToString(filePath));
    }

}
