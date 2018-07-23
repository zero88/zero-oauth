package com.zero.oauth.client.core;

public interface IResponsePropModel {

    <T extends IResponsePropModel> T error();

    boolean isError();
}
