package com.zero.oauth.client.http;

import java.net.HttpURLConnection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Defines HTTP Status of HTTP response after making a HTTP request.
 *
 * @since 1.0.0
 */
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public final class HttpStatus {

    private final int code;
    private String message;

    public static boolean isOk(int statusCode) {
        return HttpURLConnection.HTTP_MULT_CHOICE > statusCode && statusCode >= HttpURLConnection.HTTP_OK;
    }

    public static boolean isClientError(int statusCode) {
        return HttpURLConnection.HTTP_INTERNAL_ERROR > statusCode && statusCode >= HttpURLConnection.HTTP_BAD_REQUEST;
    }

    public static boolean isServerError(int statusCode) {
        return statusCode >= HttpURLConnection.HTTP_INTERNAL_ERROR;
    }

    public static boolean isError(int statusCode) {
        return isClientError(statusCode) || isServerError(statusCode);
    }

    public boolean isOk() {
        return isOk(this.code);
    }

    public boolean isError() {
        return isError(this.code);
    }

    public boolean isServerError() {
        return isServerError(this.code);
    }

    public boolean isClientError() {
        return isClientError(this.code);
    }

}
