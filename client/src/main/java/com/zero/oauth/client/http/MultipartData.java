package com.zero.oauth.client.http;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.zero.oauth.core.exceptions.OAuthException;
import com.zero.oauth.core.security.SecurityService;
import com.zero.oauth.core.security.ServiceRegistry;
import com.zero.oauth.core.utils.Constants;
import com.zero.oauth.core.utils.FileUtils;
import com.zero.oauth.core.utils.Strings;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * HTTP Multipart data. It used to upload file.
 *
 * @since 1.0.0
 */
public final class MultipartData {

    @Getter
    private final Map<String, Map<String, InputStream>> files = new HashMap<>();
    @Getter
    private final Map<String, String> forms = new HashMap<>();
    @Setter(value = AccessLevel.PACKAGE)
    private String boundary;

    /**
     * Add text data.
     *
     * @param fieldName Form data name
     * @param value     Form data value
     * @return {@code this}
     * @throws IllegalArgumentException if {@code fieldName} is blank
     * @throws NullPointerException     if {@code value} is null
     */
    public MultipartData addData(String fieldName, String value) {
        this.forms.put(Strings.requireNotBlank(fieldName, "Field name cannot be blank"),
                       Objects.requireNonNull(value, "Field data cannot be null"));
        return this;
    }

    /**
     * Add file data.
     *
     * @param fieldName   Form data name
     * @param fileName    Form data file name
     * @param inputStream Form data file content
     * @return {@code this}
     * @throws IllegalArgumentException if {@code fieldName} or {@code fileName} is blank
     * @throws NullPointerException     if {@code inputStream} is null
     */
    public MultipartData addFile(String fieldName, String fileName, InputStream inputStream) {
        String field = Strings.requireNotBlank(fieldName, "Field name cannot be blank");
        this.files.putIfAbsent(field, new HashMap<>());
        this.files.get(field).put(Strings.requireNotBlank(fileName, "File name cannot be blank"),
                                  Objects.requireNonNull(inputStream, "Input stream cannot be null"));
        return this;
    }

    /**
     * Add file data.
     *
     * @param fieldName Form data name
     * @param fileUrl   File URL in string
     * @return {@code this}
     * @throws IllegalArgumentException if {@code fieldName} or {@code fileUrl} is blank
     * @throws OAuthException           if {@code fileUrl} is not conformed {@code File System Path}
     * @see #addFile(String, File)
     */
    public MultipartData addFile(String fieldName, String fileUrl) {
        return addFile(fieldName, FileUtils.toPath(fileUrl).toFile());
    }

    /**
     * Add file data.
     *
     * @param fieldName Form data name
     * @param fileUrl   File URL
     * @return {@code this}
     * @throws IllegalArgumentException if {@code fieldName} is blank
     * @throws NullPointerException     if {@code fileUrl} is null
     * @throws OAuthException           if {@code fileUrl} is not conformed {@code File System Path} or cannot open
     *                                  input stream
     * @see #addFile(String, String, InputStream)
     */
    public MultipartData addFile(String fieldName, URL fileUrl) {
        return addFile(fieldName, FileUtils.getFileName(fileUrl), FileUtils.toStream(fileUrl));
    }

    /**
     * Add file data.
     *
     * @param fieldName Form data name
     * @param file      Upload file
     * @return {@code this}
     * @throws IllegalArgumentException if {@code fieldName} or {@code fileName} is blank
     * @throws NullPointerException     if {@code file} is null
     * @throws OAuthException           if {@code file} not found
     * @see #addFile(String, String, InputStream)
     */
    public MultipartData addFile(String fieldName, File file) {
        return addFile(fieldName, Objects.requireNonNull(file).getName(), FileUtils.toStream(file));
    }

    String randomBoundary() {
        if (Strings.isNotBlank(boundary)) {
            return boundary;
        }
        SecurityService securityService = ServiceRegistry.getSecurityService(Constants.TIMESTAMP_ALGO);
        return securityService.randomToken();
    }

}
