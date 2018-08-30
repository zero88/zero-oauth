package com.zero.oauth.core.utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.zero.oauth.core.LoggerFactory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * File Utilities.
 *
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileUtils {

    /**
     * Read file to text.
     *
     * @param filePath Given file path
     * @return File content in text
     * @throws RuntimeException if error when parsing file path or reading file
     */
    public static String readFileToString(String filePath) {
        String strPath = Strings.requireNotBlank(filePath);
        strPath = strPath.replaceFirst("^(?:file:/)([^/])", "/".equals(File.separator) ? "/$1" : "$1");
        Path path;
        try {
            path = Paths.get(URI.create(filePath));
        } catch (IllegalArgumentException | FileSystemNotFoundException | SecurityException ex) {
            LoggerFactory.instance().getLogger().warn(ex, "Invalid parse URI: {0}. Try to parse plain text", strPath);
            try {
                path = Paths.get(strPath);
            } catch (InvalidPathException ex1) {
                ex1.addSuppressed(ex);
                throw new RuntimeException("Cannot parse file path: " + filePath, ex1);
            }
        }
        try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
            return stream.collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Error when reading file: " + filePath, e);
        }
    }

}
