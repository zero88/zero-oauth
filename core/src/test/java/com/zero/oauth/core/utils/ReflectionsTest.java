package com.zero.oauth.core.utils;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.zero.oauth.core.TestBase;
import com.zero.oauth.core.type.OAuthVersion;

public class ReflectionsTest extends TestBase {

    @Test(expected = NullPointerException.class)
    public void test_FindConst_DestClass_Null() {
        Reflections.getConstants(null, String.class);
    }

    @Test(expected = NullPointerException.class)
    public void test_FindConst_FindClass_Null() {
        Reflections.getConstants(ReflectionTest.class, null);
    }

    @Test
    public void test_FindConst_String() {
        List<String> constants = Reflections.getConstants(ReflectionTest.class, String.class);
        Assert.assertThat(constants, CoreMatchers.hasItems("1"));
        Assert.assertFalse(constants.contains("2"));
        Assert.assertFalse(constants.contains("xxx"));
    }

    @Test
    public void test_FindConst_Int() {
        List<Integer> constants = Reflections.getConstants(ReflectionTest.class, Integer.class);
        Assert.assertThat(constants, CoreMatchers.hasItems(1, 3));
        Assert.assertTrue(constants.stream().noneMatch(i -> Arrays.asList(2, 4, 10, 20, 30, 40, 50).contains(i)));
    }

    @Test
    public void test_FindConst_Enum() {
        List<OAuthVersion> constants = Reflections.getConstants(ReflectionTest.class, OAuthVersion.class);
        Assert.assertThat(constants, CoreMatchers.hasItems(OAuthVersion.V1));
        Assert.assertFalse(constants.contains(OAuthVersion.V2));
    }

    @Test(expected = RuntimeException.class)
    public void test_FindClass_NotSub() throws ClassNotFoundException {
        Reflections.findClass(ReflectionTest.class.getName(), OAuthVersion.class);
    }

    @Test(expected = ClassNotFoundException.class)
    public void test_FindClass_NotFoundClassName() throws ClassNotFoundException {
        Reflections.findClass("xxx", OAuthVersion.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_FindClass_ClassName_Null() throws ClassNotFoundException {
        Reflections.findClass(null, OAuthVersion.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_FindClass_ClassName_Empty() throws ClassNotFoundException {
        Reflections.findClass("     ", OAuthVersion.class);
    }

    @Test(expected = NullPointerException.class)
    public void test_FindClass_ParentClass_Null() throws ClassNotFoundException {
        Reflections.findClass("xxx", null);
    }

    @Test
    public void test_FindClass_Success() throws ClassNotFoundException {
        Reflections.findClass(ReflectionTest.class.getName(), ReflectionTest.class);
    }

    @Test
    public void test_GetClassInstance_NotHaveConstrutorNoArgs() {
        Assert.assertNull(Reflections.getClassInstance(NotHaveContrustorWithNoArgs.class.getName(),
                                                       NotHaveContrustorWithNoArgs.class));
    }

    private static class ReflectionTest {

        public static final String CONST_STR = "1";
        public static final int CONST_INT = 1;
        public static final Integer CONST_INTEGER = 3;
        public static final OAuthVersion V1 = OAuthVersion.V1;
        private static final String CONST_STR_PRIVATE = "2";
        private static final int CONST_INT_PRIVATE = 2;
        private static final Integer CONST_INTEGER_PRIVATE = 4;
        public final OAuthVersion version = OAuthVersion.V2;
        private final int finalInt = 10;
        private final String finalStr = "xxx";
        public int protectInt = 30;
        public int publicInt = 40;
        int packageInt = 50;
        private int privateInt = 20;

    }


    public static class NotHaveContrustorWithNoArgs {

        private final String test;

        public NotHaveContrustorWithNoArgs(String test) {
            this.test = test;
        }

    }

}
