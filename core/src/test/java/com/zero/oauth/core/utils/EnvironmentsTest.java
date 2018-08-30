package com.zero.oauth.core.utils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.zero.oauth.core.TestBase;

public class EnvironmentsTest extends TestBase {

    @SuppressWarnings("unchecked")
    private static void setEnv(Map<String, String> newEnv) throws Exception {
        try {
            Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
            Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
            theEnvironmentField.setAccessible(true);
            Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
            env.putAll(newEnv);
            Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField(
                "theCaseInsensitiveEnvironment");
            theCaseInsensitiveEnvironmentField.setAccessible(true);
            Map<String, String> caseInsensitiveEnv = (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
            caseInsensitiveEnv.putAll(newEnv);
        } catch (NoSuchFieldException e) {
            Class<?>[] classes = Collections.class.getDeclaredClasses();
            Map<String, String> env = System.getenv();
            for (Class<?> cl : classes) {
                if ("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
                    Field field = cl.getDeclaredField("m");
                    field.setAccessible(true);
                    Object obj = field.get(env);
                    Map<String, String> map = (Map<String, String>) obj;
                    map.clear();
                    map.putAll(newEnv);
                }
            }
        }
    }

    @Test
    public void getVar_FromEnvironment() throws Exception {
        setEnv(Collections.singletonMap("A_B", "bc"));
        Assert.assertEquals("bc", Environments.getVar("a.b"));
    }

    @Test
    public void getVar_FromSystem() {
        System.setProperty("a.b", "bc");
        Assert.assertEquals("bc", Environments.getVar("a.b"));
    }

    @Test
    public void getVar_FromFile_FileNameBlank() {
        Assert.assertNull(Environments.getPropertyByName("z.oauth.test", ""));
    }

    @Test
    public void getVar_FromFile_FileNotFound() {
        Assert.assertNull(Environments.getPropertyByName("123", "xx.properties"));
    }

    @Test
    public void getVar_FromFile_FileNotProperties() {
        Assert.assertNull(Environments.getPropertyByName("456", "private_key.pem"));
    }

    @Test
    public void getVar_FromFile_PropertyNameBlank() {
        Assert.assertNull(Environments.getPropertyByName(null, "test.properties"));
    }

    @Test
    public void getVar_FromFile() {
        Assert.assertEquals("lalala", Environments.getPropertyByName("z.oauth.test", "test.properties"));
    }

}
