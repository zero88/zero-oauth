package com.zero.oauth.client.core;

import com.zero.oauth.client.type.OAuthVersion;

import lombok.Getter;

@Getter
public class ResponsePropModel extends PropertyModel {

    private boolean isError = false;

    public static ResponsePropModel v1(String name) {
        return new ResponsePropModel(OAuthVersion.V1, name);
    }

    public static ResponsePropModel v2(String name) {
        return new ResponsePropModel(OAuthVersion.V2, name);
    }

    protected ResponsePropModel(OAuthVersion version, String name) {
        super(version, name);
    }

    public ResponsePropModel error() {
        this.isError = true;
        return this;
    }
}
