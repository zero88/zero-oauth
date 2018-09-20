package com.zero.oauth.client.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.zero.oauth.core.LoggerFactory;
import com.zero.oauth.core.exceptions.OAuthHttpException;
import com.zero.oauth.core.properties.HeaderProperty;
import com.zero.oauth.core.type.HttpMethod;
import com.zero.oauth.core.type.HttpPlacement;
import com.zero.oauth.core.utils.Constants;
import com.zero.oauth.core.utils.FileUtils;
import com.zero.oauth.core.utils.Strings;
import com.zero.oauth.core.utils.Urls;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Plain Java HTTP Client Config.
 *
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor
public class JdkHttpClient implements HttpClient {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(10);
    private final JdkHttpClientConfig config;

    public JdkHttpClient() {
        this.config = new JdkHttpClientConfig();
    }

    @Override
    public HttpData execute(String url, HttpMethod method, HttpData requestData) {
        return doExecute(url, method, requestData);
    }

    @Override
    public Future<HttpData> asyncExecute(String url, HttpMethod method, HttpData requestData) {
        return EXECUTOR_SERVICE.submit(() -> this.doExecute(url, method, requestData));
    }

    @Override
    public void asyncExecute(String url, HttpMethod method, HttpData requestData, HttpCallback callback) {
        Objects.requireNonNull(callback, "HTTP Callback must be not null");
        EXECUTOR_SERVICE.submit(() -> {
            try {
                callback.onSuccess(this.doExecute(url, method, requestData));
            } catch (OAuthHttpException e) {
                callback.onFailed(e);
            }
        });
    }

    @Override
    public CompletableFuture<HttpData> asyncExecute(String url, HttpMethod method, HttpData requestData,
                                                    boolean resultNullable) {
        CompletableFuture<HttpData> future = CompletableFuture.supplyAsync(
            () -> this.doExecute(url, method, requestData), EXECUTOR_SERVICE);
        if (resultNullable) {
            return future.exceptionally(throwable -> {
                LoggerFactory.logger().error(throwable, "Error when making HTTP request");
                return null;
            });
        }
        return future;
    }

    private HttpData doExecute(String url, HttpMethod method, HttpData requestData) {
        try {
            URL urlConn = new URL(Urls.buildURL(url, requestData.getQuery()));
            HttpURLConnection conn = (HttpURLConnection) (Objects.isNull(this.config.getProxy())
                                                          ? urlConn.openConnection()
                                                          : urlConn.openConnection(this.config.getProxy()));
            conn.setRequestMethod(method.name());
            conn.setInstanceFollowRedirects(this.getConfig().isFollowRedirect());
            conn.setConnectTimeout(this.getConfig().getConnectTimeout() * 1000);
            conn.setReadTimeout(this.getConfig().getReadTimeout() * 1000);
            addHeaders(requestData, conn);
            addBody(method, requestData, conn);
            return connect(conn);
        } catch (IOException e) {
            throw new OAuthHttpException("Failed when connect host", e);
        }
    }

    private HttpData connect(HttpURLConnection conn) throws IOException {
        try {
            conn.connect();
            HttpStatus httpStatus = new HttpStatus(conn.getResponseCode(), conn.getResponseMessage());
            InputStream content = httpStatus.isError() ? conn.getErrorStream() : conn.getInputStream();
            return HttpData.builder().streamBody(content).headerMap(parseResponseHeaders(conn)).status(httpStatus)
                           .build();
        } catch (UnknownHostException e) {
            throw new OAuthHttpException("The IP address of a host could not be determined.", e);
        }
    }

    private void addHeaders(HttpData requestData, HttpURLConnection conn) {
        Map<String, String> headerMap = requestData.getHeaderMap();
        if (Objects.nonNull(headerMap)) {
            headerMap.forEach(conn::setRequestProperty);
        }
        if (Strings.isNotBlank(getConfig().getUserAgent())) {
            conn.setRequestProperty(HeaderProperty.USER_AGENT.getName(), getConfig().getUserAgent());
        }
    }

    private void addBody(HttpMethod method, HttpData requestData, HttpURLConnection conn) throws IOException {
        if (!method.getAvailablePlaces().contains(HttpPlacement.BODY)) {
            return;
        }
        conn.setDoOutput(true);
        if (requestData.isMultipart()) {
            addMultipartBody(requestData, conn);
        } else {
            addNormalBody(requestData, conn);
        }
    }

    private void addNormalBody(HttpData requestData, HttpURLConnection conn) throws IOException {
        byte[] body = requestData.getBytes();
        if (body == null || body.length == 0) {
            return;
        }
        conn.setRequestProperty(HeaderProperty.CONTENT_LENGTH.getName(), String.valueOf(body.length));
        conn.getOutputStream().write(requestData.getBytes());
    }

    private void addMultipartBody(HttpData requestData, HttpURLConnection conn) throws IOException {
        MultipartData multipartData = requestData.getMultipartData();
        String boundary = multipartData.randomBoundary();
        conn.setRequestProperty(HeaderProperty.CONTENT_TYPE.getName(), "multipart/form-data; boundary=" + boundary);
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
            addFileParts(writer, conn.getOutputStream(), multipartData, boundary);
            addTextParts(writer, multipartData, boundary);
            writer.append("--").append(boundary).append("--").append(Constants.CARRIAGE_RETURN);
            writer.flush();
        }
    }

    private void addTextParts(Writer writer, MultipartData multipartData, String boundary) {
        multipartData.getForms().forEach((fieldName, value) -> addTextPart(writer, boundary, fieldName, value));
    }

    private void addTextPart(Writer writer, String boundary, String fieldName, String value) {
        try {
            writer.append("--").append(boundary).append(Constants.CARRIAGE_RETURN);
            writer.append("Content-Disposition: form-data; name=\"").append(fieldName);
            writer.append(Constants.CARRIAGE_RETURN).append(Constants.CARRIAGE_RETURN);
            writer.append(value);
            writer.append(Constants.CARRIAGE_RETURN);
            writer.flush();
        } catch (IOException e) {
            throw new OAuthHttpException("Cannot write data into HTTP request", e);
        }
    }

    private void addFileParts(Writer writer, OutputStream outStream, MultipartData multipartData, String boundary) {
        multipartData.getFiles().forEach(
            (fieldName, fileParts) -> this.addFileParts(writer, outStream, boundary, fieldName, fileParts));
    }

    private void addFileParts(Writer writer, OutputStream outStream, String boundary, String fieldName,
                              Map<String, InputStream> fileParts) {
        fileParts.forEach((fileName, is) -> this.addFilePart(writer, outStream, boundary, fieldName, fileName, is));
    }

    private void addFilePart(Writer writer, OutputStream outStream, String boundary, String fieldName, String fileName,
                             InputStream inputStream) {
        try {
            writer.append("--").append(boundary).append(Constants.CARRIAGE_RETURN);
            writer.append("Content-Disposition: form-data; name=\"").append(fieldName).append("\"; filename=\"").append(
                fileName).append("\"");
            writer.append(Constants.CARRIAGE_RETURN);
            writer.append("Content-Type: ").append(URLConnection.guessContentTypeFromName(fileName));
            writer.append(Constants.CARRIAGE_RETURN).append(Constants.CARRIAGE_RETURN);
            writer.flush();
            FileUtils.writeToOutputStream(inputStream, outStream);
            writer.append(Constants.CARRIAGE_RETURN);
            writer.flush();
        } catch (IOException e) {
            throw new OAuthHttpException("Cannot write data into HTTP request", e);
        }
    }

    private Map<String, String> parseResponseHeaders(HttpURLConnection conn) {
        return Stream.of(conn.getHeaderFields()).map(Map::entrySet).flatMap(Collection::stream).filter(
            entry -> Objects.nonNull(entry.getValue()) && !entry.getValue().isEmpty()).collect(
            Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().get(0)));
    }

}
