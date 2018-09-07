package com.zero.oauth.client.http;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.zero.oauth.core.converter.HttpHeaderConverter;
import com.zero.oauth.core.utils.FileUtils;
import com.zero.oauth.core.utils.Strings;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * HTTP Data. It may represents for {@code HTTP Request data} or {@code HTTP Response data}.
 *
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public final class HttpData {

    private final Map<String, String> headerMap;
    private final String query;
    private final String strBody;
    private final byte[] bytesBody;
    private final MultipartData multipartData;
    private final InputStream streamBody;
    private final HttpStatus status;

    public static HttpDataBuilder builder() {return new HttpDataBuilder();}

    public byte[] getBytes() {
        if (Objects.nonNull(this.bytesBody)) {
            return this.bytesBody;
        }
        if (Objects.nonNull(this.strBody)) {
            return this.strBody.getBytes(StandardCharsets.UTF_8);
        }
        if (Objects.nonNull(this.streamBody)) {
            return FileUtils.convertToBytes(this.streamBody);
        }
        return null;
    }

    /**
     * Check HTTP request is marked as multipart.
     *
     * @return {@code True} if request is multipart, else otherwise
     */
    public boolean isMultipart() {
        return Objects.nonNull(this.multipartData);
    }

    /**
     * Check this instance is {@code HTTP Response data}.
     *
     * @return {@code True} if it is {@code HTTP Response data}
     */
    public boolean isResponse() {
        return Objects.nonNull(this.status);
    }

    public static class HttpDataBuilder {

        private final Map<String, String> headerMap = new HashMap<>();
        private MultipartData multipartData;
        private String header;
        private String query;
        private String strBody;
        private byte[] bytesBody;
        private InputStream streamBody;
        private HttpStatus status;

        HttpDataBuilder() {}

        public HttpDataBuilder headerMap(Map<String, String> headerMap) {
            this.headerMap.putAll(headerMap);
            return this;
        }

        public HttpDataBuilder addHeader(String key, String value) {
            this.headerMap.put(key, value);
            return this;
        }

        public HttpDataBuilder header(String header) {
            this.header = header;
            return this;
        }

        public HttpDataBuilder multipartData(MultipartData multipartData) {
            this.multipartData = multipartData;
            return this;
        }

        public HttpDataBuilder query(String query) {
            this.query = query;
            return this;
        }

        public HttpDataBuilder strBody(String strBody) {
            this.strBody = strBody;
            return this;
        }

        public HttpDataBuilder bytesBody(byte[] bytesBody) {
            this.bytesBody = bytesBody;
            return this;
        }

        public HttpDataBuilder streamBody(InputStream streamBody) {
            this.streamBody = streamBody;
            return this;
        }

        public HttpDataBuilder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public HttpData build() {
            if (Strings.isNotBlank(header)) {
                new HttpHeaderConverter().deserialize(header).forEach(
                    (key, value) -> this.headerMap.put(key, value.toString()));
            }
            return new HttpData(headerMap, query, strBody, bytesBody, multipartData, streamBody, status);
        }

    }

}
