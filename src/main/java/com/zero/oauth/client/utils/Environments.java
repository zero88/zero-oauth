package com.zero.oauth.client.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

import org.apache.logging.log4j.message.ParameterizedMessage;

import lombok.extern.log4j.Log4j2;

/**
 * Environment utilities.
 *
 * @since 1.0.0
 */
@Log4j2
public final class Environments {

    private static final String OAUTH_PROPERTIES_FILE = "oauth.properties";

    /**
     * Get property value by given name.
     * <p>
     * The order of searching:
     * <ul>
     * <li>Environment variable</li>
     * <li>System properties</li>
     * <li>Properties file: {@code oauth.properties} in Java classpath</li>
     * </ul>
     *
     * @param name Variable name
     * @return property value
     */
    public static String getVar(String name) {
        String envName = Strings.requireNotBlank(name);
        try {
            String env = System.getenv(envName.toUpperCase(Locale.ENGLISH).replaceAll("\\.", "_"));
            if (Strings.isNotBlank(env)) {
                return env;
            }
        } catch (SecurityException ex) {
            log.warn("Error when reading access environment variable", ex);
        }
        String property = System.getProperty(envName);
        if (Strings.isNotBlank(property)) {
            return property;
        }
        return getPropertyByName(envName, OAUTH_PROPERTIES_FILE);
    }

    /**
     * For test.
     *
     * @param propertyName Property name
     * @param file         Properties file in classpath
     * @return property value or {@code null} if not found
     */
    static String getPropertyByName(String propertyName, String file) {
        try {
            if (Strings.isBlank(propertyName) || Strings.isBlank(file)) {
                return null;
            }
            InputStream stream = Environments.class.getClassLoader().getResourceAsStream(file);
            if (Objects.isNull(stream)) {
                log.warn("Cannot load resource with name {}", file);
                return null;
            }
            Properties properties = new Properties();
            properties.load(stream);
            return properties.getProperty(propertyName);
        } catch (SecurityException | IOException ex) {
            log.warn(new ParameterizedMessage("Error when reading property file {}", file, ex));
            return null;
        }
    }

}
