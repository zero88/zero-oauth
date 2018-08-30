package com.zero.oauth.core.oauth1;

import com.zero.oauth.core.properties.IResponseProperties;

import lombok.Getter;

@Getter
public final class OAuth1ResponseProperties extends OAuth1Properties<OAuth1ResponseProperty>
    implements IResponseProperties<OAuth1ResponseProperty> {

    private boolean error;

    public OAuth1ResponseProperties() {
        this(false);
    }

    private OAuth1ResponseProperties(boolean error) {
        super(OAuth1ResponseProperty.class);
        this.error = error;
    }

    @Override
    public void markToError() {
        this.error = true;
    }

    @Override
    public OAuth1ResponseProperty create(String name) {
        return new OAuth1ResponseProperty(name);
    }

}
