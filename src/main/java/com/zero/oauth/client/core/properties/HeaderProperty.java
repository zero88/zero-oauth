package com.zero.oauth.client.core.properties;

import com.zero.oauth.client.type.HttpPlacement;
import com.zero.oauth.client.type.OAuthVersion;

public class HeaderProperty extends PropertyModel {

    /**
     * HTTP Authorization Header.
     */
    public static final HeaderProperty AUTHORIZATION = new HeaderProperty("Authorization");

    /**
     * WWW-Authenticate HTTP header.
     */
    public static final HeaderProperty WWW_AUTHENTICATE = new HeaderProperty("WWW-Authenticate");

    /**
     * HTTP Accept Header for {@code json}.
     */
    public static final HeaderProperty JSON_ACCEPT =
        new HeaderProperty("Accept").setValue("application/json");

    /**
     * HTTP User-Agent Header.
     */
    public static final HeaderProperty USER_AGENT = new HeaderProperty("User-Agent").setValue("{}/{} (+{})");

    /**
     * Content type header.
     */
    public static final HeaderProperty CONTENT_TYPE =
        new HeaderProperty("Content-Type").setValue("application/x-www-form-urlencoded;charset=UTF-8");

    public HeaderProperty(String name) {
        super(OAuthVersion.ALL, name);
        this.registerPlacements(HttpPlacement.HEADER);
    }

}
