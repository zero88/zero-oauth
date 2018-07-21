package com.zero.oauth.client.core;

import java.util.Objects;

import com.zero.oauth.client.core.oauth1.OAuth1RequestParams;
import com.zero.oauth.client.core.oauth2.OAuth2AccessTokenResponse;
import com.zero.oauth.client.core.oauth2.OAuth2RequestParams;
import com.zero.oauth.client.type.OAuthVersion;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * <code>PropertyModel</code> is model to define an OAuth property in HTTP request, HTTP response or HTTP header.
 */
@Getter
@Setter(value = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class PropertyModel implements Cloneable {

    private final OAuthVersion version;
    private final String name;

    private String value;
    private boolean isRequired = false;

    /**
     * Init default property value.
     *
     * @return Current instance
     */
    @SuppressWarnings("unchecked")
    public <T extends PropertyModel> T defaultValue(String value) {
        this.value = value;
        return (T) this;
    }

    /**
     * @param value
     *        Any value but have to implement {@link Object#toString()}
     */
    @SuppressWarnings("unchecked")
    public <T extends PropertyModel> T setValue(Object value) {
        this.value = Objects.isNull(value) ? "" : value.toString();
        return (T) this;
    }

    /**
     * Mark property is required to validate later.
     *
     * @return Current instance
     */
    @SuppressWarnings("unchecked")
    public <T extends PropertyModel> T require() {
        this.isRequired = true;
        return (T) this;
    }

    /**
     * @return a clone of {@link PropertyModel} instance
     */
    public PropertyModel clone() throws CloneNotSupportedException {
        return (PropertyModel) super.clone();
    }

    /**
     * Clone current instance with overriding param value. It is helper method to
     * generate new {@link PropertyModel} instance from builtin {@link PropertyModel}.
     *
     * @param value
     *        Override param value for clone object. Any data but have to implement {@link Object#toString()}
     * @return a clone of {@link PropertyModel} instance
     * @throws CloneNotSupportedException
     * @see OAuth1RequestParams
     * @see OAuth2RequestParams
     * @see OAuth2AccessTokenResponse
     */
    @SuppressWarnings("unchecked")
    public <T extends PropertyModel> T clone(Object value) throws CloneNotSupportedException {
        T instance = (T) this.clone();
        instance.setValue(value);
        return instance;
    }
}
