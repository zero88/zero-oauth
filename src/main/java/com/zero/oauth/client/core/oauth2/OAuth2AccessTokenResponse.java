package com.zero.oauth.client.core.oauth2;

import com.zero.oauth.client.core.PropertyList;
import com.zero.oauth.client.core.ResponsePropModel;

public final class OAuth2AccessTokenResponse extends PropertyList<ResponsePropModel> {

    /**
     * The access token string as issued by the authorization server.
     */
    public static final ResponsePropModel ACCESS_TOKEN = ResponsePropModel.v2("access_token").require();
    /**
     * The type of token this is, typically just the string "bearer".
     */
    public static final ResponsePropModel TOKEN_TYPE = (ResponsePropModel.v2("token_type")).defaultValue("bearer")
                                                                                           .require();
    /**
     * If the access token expires, the server should reply with the duration of time the access
     * token is granted for.
     */
    public static final ResponsePropModel EXPIRES_IN = ResponsePropModel.v2("expires_in").require();
    /**
     * If the access token will expire, then it is useful to return a refresh token which
     * applications can use to obtain another access token. However, tokens issued with the implicit
     * grant cannot be issued a refresh token.
     */
    public static final ResponsePropModel REFRESH_TOKEN = ResponsePropModel.v2("refresh_token");
    /**
     * If the scope the user granted is identical to the scope the app requested, this parameter is
     * optional. If the granted scope is different from the requested scope, such as if the user
     * modified the scope, then this parameter is required.
     */
    public static final ResponsePropModel SCOPE = ResponsePropModel.v2("scope");

    /**
     * The request is missing a parameter so the server can’t proceed with the request. This may
     * also be returned if the request includes an unsupported parameter or repeats a parameter.
     */
    public static final ResponsePropModel INVALID_REQUEST = ResponsePropModel.v2("invalid_request").error();
    /**
     * Client authentication failed, such as if the request contains an invalid client ID or secret.
     * Send an HTTP 401 response in this case.
     */
    public static final ResponsePropModel INVALID_CLIENT = ResponsePropModel.v2("invalid_client").error();
    /**
     * The authorization code (or user’s password for the password grant type) is invalid or
     * expired. This is also the error you would return if the redirect URL given in the
     * authorization grant does not match the URL provided in this access token request.
     */
    public static final ResponsePropModel INVALID_GRANT = ResponsePropModel.v2("invalid_grant").error();
    /**
     * For access token requests that include a scope (password or client_credentials grants), this
     * error indicates an invalid scope value in the request.
     */
    public static final ResponsePropModel INVALID_SCOPE = ResponsePropModel.v2("invalid_scope").error();
    /**
     * This client is not authorized to use the requested grant type. For example, if you restrict
     * which applications can use the Implicit grant, you would return this error for the other
     * apps.
     */
    public static final ResponsePropModel UNAUTHORIZED_CLIENT = ResponsePropModel.v2("unauthorized_client").error();
    /**
     * If a grant type is requested that the authorization server doesn’t recognize, use this code.
     * Note that unknown grant types also use this specific error code rather than using the
     * {@link OAuth2AccessTokenResponse#INVALID_REQUEST} above.
     */
    public static final ResponsePropModel UNSUPPORTED_GRANT_TYPE = (ResponsePropModel.v2("unsupported_grant_type")).error();
    /**
     * A great place to link to your API documentation for information about how to correct the
     * specific error that was encountered.
     */
    public static final ResponsePropModel ERROR_URI = ResponsePropModel.v2("error_uri").error();
    /**
     * A sentence or two at most describing the circumstance of the error
     */
    public static final ResponsePropModel ERROR_DESCRIPTION = ResponsePropModel.v2("error_description").error();

}
