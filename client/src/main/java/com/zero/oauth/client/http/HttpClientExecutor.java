package com.zero.oauth.client.http;

import com.zero.oauth.core.properties.GenericResponseStore;
import com.zero.oauth.core.type.HttpMethod;

public interface HttpClientExecutor {

    GenericResponseStore execute(String url, HttpMethod method, String headers, String queries, String body);

    GenericResponseStore asyncExecute(String url, HttpMethod method, String headers, String queries, String body);

}
