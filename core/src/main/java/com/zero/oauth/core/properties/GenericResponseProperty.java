package com.zero.oauth.core.properties;

import com.zero.oauth.core.type.OAuthVersion;

public class GenericResponseProperty extends PropertyModel implements IResponseProperty {

    private boolean error = false;

    public GenericResponseProperty(OAuthVersion version, String name) {
        super(version, name);
    }

    private GenericResponseProperty(GenericResponseProperty property) {
        super(property);
    }

    @SuppressWarnings("unchecked")
    @Override
    public GenericResponseProperty error() {
        this.error = true;
        return this;
    }

    @Override
    public boolean isError() {
        return this.error;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected GenericResponseProperty duplicate() {
        GenericResponseProperty prop = new GenericResponseProperty(this);
        return this.isError() ? prop.error() : prop;
    }

}
