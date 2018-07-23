package com.zero.oauth.client.core;

public interface IPropertyModel {

    public enum Constraint {
        OPTIONAL, REQUIRED, RECOMMENDATION
    }

    String getName();

    Object getValue();

}
