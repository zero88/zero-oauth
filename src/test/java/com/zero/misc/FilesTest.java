package com.zero.misc;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FilesTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private Path parseFilePath(String filePath) {
        System.out.println("File: " + filePath);
        String optimizePath =
            filePath.replaceFirst("(?:file:/)([^/])", "/".equals(File.separator) ? "/$1" : "$1");
        try {
            return printInfo(Paths.get(optimizePath));
        } catch (InvalidPathException ex) {
            System.out.println("Try to parse by URI");
            System.out.println("----");
            return printInfo(Paths.get(URI.create(optimizePath)));
        } finally {
            System.out.println("-----------------------------------------");
            System.out.println("\n");
        }
    }

    private Path printInfo(Path path) {
        System.out.println("Path: " + path);
        System.out.println("Is existed: " + path.toFile().exists());
        System.out.println("Is absolute: " + path.isAbsolute());
        System.out.println("Normalize: " + path.normalize());
        System.out.println("To URI: " + path.toUri());
        System.out.println("To Absolute: " + path.toAbsolutePath());
        return path;
    }

    @Test
    public void test_file_path() {
        System.out.println("URL TO STRING");
        System.out.println("----");
        Path path = parseFilePath(this.getClass().getClassLoader().getResource("rsa_key.pk8").toString());
        Assert.assertTrue(path.toFile().exists());
    }

    @Test
    public void test_file_path_1() throws IOException {
        File file = temporaryFolder.newFile("test1.txt");
        Assert.assertTrue(parseFilePath(file.getAbsolutePath()).toFile().exists());
    }

    @Test
    public void test_file_path_2() throws IOException {
        File file = temporaryFolder.newFile("test2.txt");
        Assert.assertTrue(parseFilePath(file.toURI().toString()).toFile().exists());
    }

    @Test
    public void test_file_path_3() {
        parseFilePath("file:///tmp/xxx/test2.txt");
    }

    @Test
    public void test_file_path_4() {
        parseFilePath("file://tmp/yyy/test2.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_file_path_5() {
        parseFilePath(":xxx//tmp<>.|]\u0000/test2.txt");
    }

}
