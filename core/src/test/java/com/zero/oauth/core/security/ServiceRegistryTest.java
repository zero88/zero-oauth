package com.zero.oauth.core.security;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.zero.oauth.core.TestBase;
import com.zero.oauth.core.exceptions.OAuthSecurityException;

public class ServiceRegistryTest extends TestBase {

    @Test(expected = OAuthSecurityException.class)
    public void test_get_SecurityService_ByDefaultMethod() {
        ServiceRegistry.getSecurityService(MockSecurityService.class.getName());
    }

    @Test
    public void test_get_DefaultSecurityService() {
        assertTrue(ServiceRegistry.getSecurityService(null) instanceof PlainTextSecurityService);
        assertTrue(ServiceRegistry.getSecurityService("") instanceof PlainTextSecurityService);
        assertTrue(ServiceRegistry.getSecurityService("text") instanceof PlainTextSecurityService);
        assertTrue(ServiceRegistry.getSecurityService("uuid") instanceof UUIDSecurityService);
        assertTrue(ServiceRegistry.getSecurityService("timestamp") instanceof TimestampSecurityService);
    }

    @Test
    public void test_get_RollbackSecurityService_ByEnv() {
        System.setProperty("z.oauth.sec.algo.random.token", "xxx");
        assertTrue(ServiceRegistry.getSecurityService() instanceof PlainTextSecurityService);
    }

    @Test
    public void test_get_NewClass_SecurityService_ByEnv() {
        System.setProperty("z.oauth.sec.algo.random.token", MockSecurityService.class.getName());
        assertTrue(ServiceRegistry.getSecurityService() instanceof MockSecurityService);
    }

    @Test
    public void test_get_NewInnerClass_SecurityService_ByEnv() {
        System.setProperty("z.oauth.sec.algo.random.token",
                           MockSecurityService.InnerMockSecurityService.class.getName());
        assertTrue(ServiceRegistry.getSecurityService() instanceof MockSecurityService.InnerMockSecurityService);
    }

}
