package com.zero.oauth.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import com.zero.oauth.core.LoggerFactory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Manifest utilities to lookup {@code jar} or {@code war} information that stored in {@code META-INF/MANIFEST.MF}
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Manifests {

    private static final String BUNDLE_VERSION = "Bundle-Version";

    /**
     * Generate application info by combining {@code application id} and {@code application version} in runtime. The
     * attribute name to lookup application id is {@link Attributes.Name#IMPLEMENTATION_TITLE}
     *
     * @param appId Application identifier
     * @return application info in format {@code ${AppId}/${AppVersion}}
     */
    public static String generateAppInfo(String appId) {
        return generateAppInfo(Attributes.Name.IMPLEMENTATION_TITLE.toString(), appId);
    }

    /**
     * Generate application info by combining {@code application id} and {@code application version} in runtime.
     *
     * @param attributeName The attribute name to lookup {@code application id}
     * @param appId         Application identifier
     * @return application info in format {@code ${AppId}/${AppVersion}}
     */
    public static String generateAppInfo(String attributeName, String appId) {
        Strings.requireNotBlank(appId);
        try {
            Enumeration resources = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
            while (resources.hasMoreElements()) {
                String appInfo = generateAppInfo(attributeName, appId, (URL) resources.nextElement());
                if (Strings.isNotBlank(appInfo)) {
                    return appInfo;
                }
            }
        } catch (IOException e) {
            LoggerFactory.logger().debug(e, "Cannot load resources");
        }
        return null;
    }

    private static String generateAppInfo(String attributeName, String appId, URL url) {
        try (InputStream is = url.openStream()) {
            return generateAppInfo(attributeName, appId, is);
        } catch (IOException e) {
            LoggerFactory.logger().trace(e, "Cannot read manifest {0}", url);
        }
        return null;
    }

    private static String generateAppInfo(String attributeName, String appId, InputStream is) throws IOException {
        if (is == null) {
            return null;
        }
        Manifest manifest = new Manifest(is);
        Attributes attributes = manifest.getMainAttributes();
        if (!appId.equals(attributes.getValue(attributeName))) {
            return null;
        }
        return MessageFormat.format("{0}/{1}", appId, resolveVersion(attributes));
    }

    private static String resolveVersion(Attributes attributes) {
        String specVersion = attributes.getValue(Attributes.Name.SPECIFICATION_VERSION);
        String implVersion = attributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
        String bundleVersion = attributes.getValue(BUNDLE_VERSION);
        if (Strings.isNotBlank(bundleVersion)) {
            return bundleVersion;
        }
        if (Strings.isBlank(implVersion) || Strings.isBlank(specVersion) || implVersion.equals(specVersion)) {
            return Strings.isBlank(implVersion) ? specVersion : implVersion;
        }
        return MessageFormat.format("{0}-{1}", specVersion, implVersion);
    }

}
