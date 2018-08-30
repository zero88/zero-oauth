package com.zero.oauth.client.http;

import java.io.IOException;

import com.zero.oauth.core.properties.GenericResponseStore;
import com.zero.oauth.core.properties.HeaderProperty;
import com.zero.oauth.core.type.HttpMethod;
import com.zero.oauth.core.utils.Urls;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpClientExecutor implements HttpClientExecutor {

    private okhttp3.OkHttpClient httpClient = new okhttp3.OkHttpClient.Builder().build();

    @Override
    public GenericResponseStore execute(String url, HttpMethod method, String headers, String queries, String body) {
        Request request = buildRequest(url, method, headers, queries, body);
        try (Response response = httpClient.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
        } catch (IOException ex) {

        }
        return null;
    }

    @Override
    public GenericResponseStore asyncExecute(String url, HttpMethod method, String headers, String queries,
                                             String body) {
        Request request = buildRequest(url, method, headers, queries, body);
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

    private Request buildRequest(String url, HttpMethod method, String headers, String queries, String body) {
        RequestBody reqBody = RequestBody.create(MediaType.parse(HeaderProperty.JSON_ACCEPT.getValue().toString()),
                                                 body);
        Headers reqHeaders = new Headers.Builder().add(headers).build();
        return new Request.Builder().method(method.name(), reqBody).headers(reqHeaders).url(Urls.buildURL(url, queries))
                                    .build();
    }

}
