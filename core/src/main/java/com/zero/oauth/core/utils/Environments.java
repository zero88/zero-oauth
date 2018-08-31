package com.zero.oauth.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

import com.zero.oauth.core.LoggerFactory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Environment utilities.
 *
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Environments {

    private static final String OAUTH_PROPERTIES_FILE = "oauth.properties";

    /**
     * Get property value by given getName.
     * <p>
     * The order of searching:
     * <ul>
     * <li>Environment variable</li>
     * <li>System properties</li>
     * <li>Properties file: {@code oauth.properties} in Java classpath</li>
     * </ul>
     *
     * @param name Variable name. Must conforms {@code UPPER_CASE_WITH_UNDERSCORE} if environment variable or {@code
     *             lower.case.with.dot} if property name
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
            LoggerFactory.instance().getLogger().warn("Security error when accessing environment variable", ex);
        }
        String propertyName = envName.toLowerCase(Locale.ENGLISH).replaceAll("_", ".");
        String property = System.getProperty(propertyName);
        if (Strings.isNotBlank(property)) {
            return property;
        }
        return getPropertyByName(propertyName, OAUTH_PROPERTIES_FILE);
    }

    /**
     * For test.
     *
     * @param propertyName Property getName
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
                LoggerFactory.instance().getLogger().warn("Cannot load resource with getName {}", file);
                return null;
            }
            Properties properties = new Properties();
            properties.load(stream);
            return properties.getProperty(propertyName);
        } catch (SecurityException | IOException ex) {
            LoggerFactory.instance().getLogger().warn(ex, "Error when reading property file {}", file);
            return null;
        }
    }

}
