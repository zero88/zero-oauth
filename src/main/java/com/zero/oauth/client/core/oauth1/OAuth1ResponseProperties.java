package com.zero.oauth.client.core.oauth1;

import java.util.List;

import com.zero.oauth.client.core.properties.ResponseProperties;

public final class OAuth1ResponseProperties extends OAuth1Properties<OAuth1ResponseProperty>
    implements ResponseProperties<OAuth1ResponseProperty> {

    public OAuth1ResponseProperties() {
        super(OAuth1ResponseProperty.class);
    }

    @Override
    public boolean isError() {
        return false;
    }

    @Override
    public List<OAuth1ResponseProperty> errors() {
        return null;
    }

}
