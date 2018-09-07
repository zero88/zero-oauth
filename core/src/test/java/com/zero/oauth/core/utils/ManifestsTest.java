package com.zero.oauth.core.utils;

import org.junit.Assert;
import org.junit.Test;

import com.zero.oauth.core.TestBase;

public class ManifestsTest extends TestBase {

    @Test(expected = IllegalArgumentException.class)
    public void test_getManifestInfo_ByNull() {
        Manifests.generateAppInfo(null);
    }

    @Test
    public void test_getManifestInfo_NotFound() {
        Assert.assertNull(Manifests.generateAppInfo("hey"));
    }

    @Test
    public void test_getManifestInfo_Junit() {
        Assert.assertEquals("JUnit/4.12", Manifests.generateAppInfo("JUnit"));
    }

    @Test
    public void test_getManifestInfo_Gson() {
        Assert.assertEquals("Gson/2.8.5", Manifests.generateAppInfo("Bundle-Name", "Gson"));
    }

}
