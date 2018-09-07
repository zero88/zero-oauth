package com.zero.oauth.client.http;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Future;

import com.zero.oauth.core.properties.HeaderProperty;
import com.zero.oauth.core.type.HttpMethod;
import com.zero.oauth.core.utils.Urls;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpClientWrapper implements HttpClient {

    private OkHttpClient httpClient = new OkHttpClient.Builder().build();

    @Override
    public HttpClientConfig getConfig() {
        return null;
    }

    @Override
    public HttpData execute(String url, HttpMethod method, HttpData requestHttpData) {
        Request request = buildRequest(url, method, requestHttpData.getHeaderMap(), requestHttpData.getStrBody(),
                                       requestHttpData.getStrBody());
        try (Response response = httpClient.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
        } catch (IOException ex) {

        }
        return null;
    }

    @Override
    public Future<HttpData> asyncExecute(String url, HttpMethod method, HttpData requestHttpData) {
        Request request = buildRequest(url, method, requestHttpData.getHeaderMap(), requestHttpData.getStrBody(),
                                       requestHttpData.getStrBody());
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
        return null;
    }

    private Request buildRequest(String url, HttpMethod method, Map<String, String> headers, String queries,
                                 String body) {
        RequestBody reqBody = RequestBody.create(MediaType.parse(HeaderProperty.ACCEPT_JSON.getValue().toString()),
                                                 body);
        Headers.Builder builder = new Headers.Builder();
        headers.forEach(builder::add);
        return new Request.Builder().method(method.name(), reqBody).headers(builder.build()).url(
            Urls.buildURL(url, queries)).build();
    }

}
