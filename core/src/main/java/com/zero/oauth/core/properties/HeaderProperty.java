package com.zero.oauth.core.properties;

import com.zero.oauth.core.type.HttpPlacement;
import com.zero.oauth.core.type.OAuthVersion;
import com.zero.oauth.core.utils.Constants;
import com.zero.oauth.core.utils.Manifests;

public final class HeaderProperty extends PropertyModel {

    /**
     * HTTP Authorization Header.
     *
     * @see <a href="https://tools.ietf.org/html/rfc5849#section-3.5.1">Header syntax</a>
     */
    public static final HeaderProperty AUTHORIZATION = new HeaderProperty("Authorization");

    /**
     * WWW-Authenticate HTTP header.
     */
    public static final HeaderProperty WWW_AUTHENTICATE = new HeaderProperty("WWW-Authenticate");

    /**
     * HTTP Accept Header for {@code json}.
     */
    public static final HeaderProperty ACCEPT_JSON = new HeaderProperty("Accept").setValue("application/json");
    /**
     * On the client side, you can advertise a list of compression schemes that will be sent along in an HTTP request.
     * The Accept-Encoding header is used for negotiating content encoding.
     */
    public static final HeaderProperty ACCEPT_ENCODING = new HeaderProperty("Accept-Encoding").setValue(
        "gzip, compress, deflate, identity, br");

    /**
     * HTTP User-Agent Header.
     */
    public static final HeaderProperty USER_AGENT = new HeaderProperty("User-Agent").setValue(
        Manifests.generateAppInfo(Constants.CLIENT_ARTIFACT));

    /**
     * Content type header.
     */
    public static final HeaderProperty CONTENT_TYPE = new HeaderProperty("Content-Type").setValue(
        "application/x-www-form-urlencoded;charset=UTF-8");
    /**
     * Content length.
     */
    public static final HeaderProperty CONTENT_LENGTH = new HeaderProperty("Content-Length");
    /**
     * The server responds with the scheme used by the Content-Encoding response header.
     */
    public static final HeaderProperty CONTENT_ENCODING = new HeaderProperty("Content-Encoding");
    public static final HeaderProperty CONTENT_DISPOSITION = new HeaderProperty("Content-Disposition");
    public static final HeaderProperty CONTENT_TRANSFER_ENCODING = new HeaderProperty("Content-Transfer-Encoding");

    public HeaderProperty(String name) {
        super(OAuthVersion.ALL, name);
        this.registerPlacements(HttpPlacement.HEADER);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected HeaderProperty duplicate() {
        return new HeaderProperty(this.getName());
    }

}
