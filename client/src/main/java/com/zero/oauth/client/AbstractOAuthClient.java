package com.zero.oauth.client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.zero.oauth.client.http.HttpClient;
import com.zero.oauth.client.http.HttpData;
import com.zero.oauth.core.converter.HttpHeaderConverter;
import com.zero.oauth.core.converter.HttpQueryConverter;
import com.zero.oauth.core.converter.JsonConverter;
import com.zero.oauth.core.exceptions.OAuthException;
import com.zero.oauth.core.properties.GenericResponseStore;
import com.zero.oauth.core.properties.IPropertyModel;
import com.zero.oauth.core.properties.IRequestProperties;
import com.zero.oauth.core.type.FlowStep;
import com.zero.oauth.core.type.HttpMethod;
import com.zero.oauth.core.utils.Strings;

import lombok.Getter;

/**
 * A mixed OAuth client that carry out {@code OAuth v1 API} and {@code OAuth v2 API}, helps to execute HTTP request to
 * server then receiving and processing HTTP response.
 *
 * @see OAuthApi
 * @since 1.0.0
 */
@Getter
abstract class AbstractOAuthClient implements OAuthClient, OAuthHttp {

    private final OAuthApi api;
    private final boolean strict;
    private final ICallbackHandler callback;
    private final HttpClient httpClientExecutor;
    private final ThreadLocal<Boolean> async;
    protected ThreadLocal<IRequestProperties> requestProperties;

    AbstractOAuthClient(OAuthApi api, boolean strict, ICallbackHandler callback, HttpClient httpClientExecutor,
                        boolean async) {
        this.api = api;
        this.strict = strict;
        this.callback = callback;
        this.httpClientExecutor = httpClientExecutor;
        this.async = ThreadLocal.withInitial(() -> async);
        this.requestProperties = ThreadLocal.withInitial(this.api::getRequestProperties);
    }

    @Override
    public void turnOnAsync() {
        this.async.set(true);
    }

    @Override
    public void turnOffAsync() {
        this.async.set(false);
    }

    /**
     * Issue request to the {@code Resource server}.
     *
     * @param <R>               The type of request property model
     * @param url               Destination full URL or path(if register specific {@code apiBaseUrl}) for issue
     *                          particular request
     * @param method            Http method
     * @param requestProperties Request properties, maybe located in {@code HTTP Headers}, {@code URI query} or {@code
     *                          HTTP request body}
     * @return Response properties store
     * @see HttpMethod
     * @see IRequestProperties
     * @see GenericResponseStore
     */
    public final <R extends IPropertyModel> GenericResponseStore request(String url, HttpMethod method,
                                                                         IRequestProperties<R> requestProperties) {
        return this.request(url, method, FlowStep.ACCESS_RESOURCE, requestProperties);
    }

    /**
     * @return Response properties store
     */
    public abstract Future<GenericResponseStore> authorize();

    public void analyzeAuthorizationStep(GenericResponseStore authorizationResponse) {
        if (Strings.isNotBlank(Strings.toString(authorizationResponse.get("error").getValue()))) {
            throw new OAuthException("");
        }
        Object responseState = authorizationResponse.get("state").getValue();
        //        Object requestState = this.api.getRequestProperties().get("state").getValue();
        //        if (!requestState.equals(responseState)) {
        //            throw new OAuthSecurityException("The state is not match");
        //        }
        this.exchangeToken();
    }

    /**
     * TODO
     *
     * @return Response properties store
     */
    public abstract Future<GenericResponseStore> exchangeToken();

    protected abstract void updateRedirectUrlIntoApi(String callbackUrl);

    protected final <R extends IPropertyModel> GenericResponseStore request(String url, HttpMethod method,
                                                                            FlowStep flowStep,
                                                                            IRequestProperties<R> requestProperties) {
        HttpData requestData = HttpData.builder().header(
            new HttpHeaderConverter().serialize(requestProperties, flowStep)).strBody(
            new JsonConverter().serialize(requestProperties, flowStep)).query(
            new HttpQueryConverter().serialize(requestProperties, flowStep)).build();
        HttpData responseData;
        if (this.async.get()) {
            try {
                responseData = (HttpData) this.httpClientExecutor.asyncExecute(url, method, requestData).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new OAuthException(e);
            }
        } else {
            responseData = this.httpClientExecutor.execute(url, method, requestData);
        }
        return null;
    }

}
