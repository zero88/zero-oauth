package com.zero.oauth.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.zero.oauth.core.LoggerFactory;
import com.zero.oauth.core.exceptions.OAuthException;
import com.zero.oauth.core.exceptions.OAuthUrlException;

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
     * @throws OAuthException if error when parsing file path or reading file
     */
    public static String readFileToString(String filePath) {
        Path path = toPath(filePath);
        try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
            return stream.collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new OAuthException("Error when reading file: " + filePath, e);
        }
    }

    public static String getFileName(URL fileUrl) {
        try {
            return Paths.get(Objects.requireNonNull(fileUrl).toURI()).getFileName().toString();
        } catch (FileSystemNotFoundException | URISyntaxException e) {
            throw new OAuthUrlException("File URL is wrong syntax", e);
        }
    }

    /**
     * Convert file path from String to {@link Path}.
     *
     * @param filePath Given file path
     * @return File path object
     * @throws IllegalArgumentException if {@code filePath} is blank
     * @throws OAuthException           if error when parsing file path or reading file
     */
    public static Path toPath(String filePath) {
        String strPath = Strings.requireNotBlank(filePath);
        strPath = strPath.replaceFirst("^(?:file:/)([^/])", "/".equals(File.separator) ? "/$1" : "$1");
        try {
            return Paths.get(URI.create(filePath));
        } catch (IllegalArgumentException | FileSystemNotFoundException | SecurityException ex) {
            LoggerFactory.instance().getLogger().warn(ex, "Invalid parse URI: {0}. Try to parse plain text", strPath);
            try {
                return Paths.get(strPath);
            } catch (InvalidPathException ex1) {
                ex1.addSuppressed(ex);
                throw new OAuthException("Cannot parse file path: " + filePath, ex1);
            }
        }
    }

    /**
     * Open stream from given URL.
     *
     * @param url URL
     * @return input stream
     * @throws NullPointerException if given {@code URL} is null
     * @throws OAuthException       if error when opening stream
     */
    public static InputStream toStream(URL url) {
        try {
            return Objects.requireNonNull(url).openStream();
        } catch (IOException e) {
            throw new OAuthException("Cannot open stream via url: " + url.toString(), e);
        }
    }

    /**
     * Open stream from given file.
     *
     * @param file Given file
     * @return input stream
     * @throws NullPointerException if given {@code URL} is null
     * @throws OAuthException       if file not found
     */
    public static InputStream toStream(File file) {
        try {
            return new FileInputStream(Objects.requireNonNull(file));
        } catch (FileNotFoundException e) {
            throw new OAuthException("File not found: " + file.toString(), e);
        }
    }

    /**
     * Convert {@link InputStream} to {@code byte array}.
     *
     * @param inputStream Given stream
     * @return Bytes array represents for data in input stream
     * @throws OAuthException if error when reading stream
     */
    public static byte[] convertToBytes(InputStream inputStream) {
        return convertToByteArray(inputStream).toByteArray();
    }

    /**
     * Write data from {@link InputStream} to {@link OutputStream}.
     *
     * @param inputStream  Given input stream
     * @param outputStream Given output stream
     * @param <T>          Type of output stream
     * @return Given Output Stream
     * @throws OAuthException if error when reading stream
     */
    public static <T extends OutputStream> T writeToOutputStream(InputStream inputStream, T outputStream) {
        Objects.requireNonNull(outputStream, "Output stream is null");
        Objects.requireNonNull(inputStream, "Input stream is null");
        try {
            int length;
            byte[] buffer = new byte[1024];
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            return outputStream;
        } catch (IOException e) {
            throw new OAuthException("Error when writing stream", e);
        } finally {
            silentClose(inputStream);
        }
    }

    /**
     * Silent close stream.
     *
     * @param stream {@link Closeable}
     */
    public static void silentClose(Closeable stream) {
        if (Objects.isNull(stream)) {
            return;
        }
        try {
            stream.close();
        } catch (IOException e) {
            LoggerFactory.instance().getLogger().trace(e, "Cannot close stream");
        }
    }

    static ByteArrayOutputStream convertToByteArray(InputStream inputStream) {
        return writeToOutputStream(inputStream, new ByteArrayOutputStream());
    }

}
