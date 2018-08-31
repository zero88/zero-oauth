package com.zero.oauth.core.oauth1;

public final class OAuth1RequestProperties extends OAuth1Properties<OAuth1RequestProperty> {

    public OAuth1RequestProperties() {
        super(OAuth1RequestProperty.class);
    }

    @Override
    public OAuth1RequestProperty create(String name) {
        return new OAuth1RequestProperty(name);
    }

}
