package com.zero.oauth.core.security;

public class MockSecurityService implements SecurityService {

    @Override
    public String randomToken() {
        return "xx";
    }

    public static class InnerMockSecurityService implements SecurityService {

        @Override
        public String randomToken() {
            return "yyy";
        }

    }

}
