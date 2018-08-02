package com.zero.oauth.client.utils;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import com.zero.oauth.client.type.OAuthVersion;

public class ReflectionsTest {

    @Test(expected = NullPointerException.class)
    public void testFindConst_DestClass_Null() {
        Reflections.getConstants(null, String.class);
    }

    @Test(expected = NullPointerException.class)
    public void testFindConst_FindClass_Null() {
        Reflections.getConstants(ReflectionTest.class, null);
    }

    @Test
    public void testFindConstStr() {
        List<String> constants = Reflections.getConstants(ReflectionTest.class, String.class);
        Assert.assertThat(constants, CoreMatchers.hasItems("1"));
        Assert.assertFalse(constants.contains("2"));
        Assert.assertFalse(constants.contains("xxx"));
    }

    @Test
    public void testFindConstInt() {
        List<Integer> constants = Reflections.getConstants(ReflectionTest.class, Integer.class);
        Assert.assertThat(constants, CoreMatchers.hasItems(1, 3));
        Assert.assertTrue(
            constants.stream().noneMatch(i -> Arrays.asList(2, 4, 10, 20, 30, 40, 50).contains(i)));
    }

    @Test
    public void testFindConstEnum() {
        List<OAuthVersion> constants = Reflections.getConstants(ReflectionTest.class, OAuthVersion.class);
        Assert.assertThat(constants, CoreMatchers.hasItems(OAuthVersion.V1));
        Assert.assertFalse(constants.contains(OAuthVersion.V2));
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

}
