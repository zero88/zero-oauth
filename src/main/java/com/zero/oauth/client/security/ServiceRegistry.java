package com.zero.oauth.client.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.zero.oauth.client.exceptions.OAuthSecurityException;
import com.zero.oauth.client.utils.Constants;
import com.zero.oauth.client.utils.Environments;
import com.zero.oauth.client.utils.Reflections;
import com.zero.oauth.client.utils.Strings;

import lombok.extern.log4j.Log4j2;

@Log4j2
public final class ServiceRegistry {

    private static final Map<String, SecurityService> SECURITY_REGISTRY = initialize();
    private static final Map<String, SignatureService> SIGNATURE_REGISTRY = initialize_signature();

    private static Map<String, SignatureService> initialize_signature() {
        return new HashMap<>();
    }

    private static Map<String, SecurityService> initialize() {
        Map<String, SecurityService> registries = new HashMap<>();
        registries.put(Constants.TEXT_ALGO, PlainTextSecurityService.INSTANCE);
        registries.put(Constants.TIMESTAMP_ALGO, TimestampSecurityService.INSTANCE);
        registries.put(Constants.UUID_ALGO, UUIDSecurityService.INSTANCE);
        return registries;
    }

    /**
     * For testing.
     *
     * @param algorithm algorithm name
     * @return security service
     */
    static SecurityService getSecurityService(String algorithm) {
        SecurityService securityService =
            SECURITY_REGISTRY.get(Strings.isBlank(algorithm) ? Constants.TEXT_ALGO : algorithm);
        if (Objects.nonNull(securityService)) {
            return securityService;
        }
        throw new OAuthSecurityException("Not found algorithm");
    }

    /**
     * Load security service based on environment variable {@code Z_OAUTH_SEC_ALGO_RANDOM_TOKEN} or from
     * configuration with property name {@code z.oauth.sec.algo.random_token}.
     *
     * @return security service
     * @see Constants#OAUTH_RANDOM_TOKEN_PROPERTY
     */
    public static SecurityService getSecurityService() {
        String algo = Strings.requireNotBlank(Environments.getVar(Constants.OAUTH_RANDOM_TOKEN_PROPERTY));
        SecurityService securityService = SECURITY_REGISTRY.get(algo);
        if (Objects.nonNull(securityService)) {
            return securityService;
        }
        SecurityService instance = Reflections.getClassInstance(algo, SecurityService.class);
        if (Objects.isNull(instance)) {
            log.warn("Not found algorithm. Rollback to default algorithm: `text`");
            return getSecurityService("text");
        }
        SECURITY_REGISTRY.put(algo, instance);
        return instance;
    }

}
