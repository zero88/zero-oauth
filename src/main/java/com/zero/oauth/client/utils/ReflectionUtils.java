package com.zero.oauth.client.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReflectionUtils {

    public static <T> List<T> getConstants(Class<T> destClazz) throws IllegalArgumentException {
        return ReflectionUtils.getConstants(destClazz, destClazz);
    }

    @SuppressWarnings("unchecked")
    public static <T, P> List<P> getConstants(Class<T> destClazz, Class<P> findClazz) throws IllegalArgumentException {
        try {
            List<P> consts = new ArrayList<>();
            for (Field f : destClazz.getFields()) {
                if (!Modifier.isPublic(f.getModifiers())) {
                    continue;
                }
                if (!Modifier.isStatic(f.getModifiers())) {
                    continue;
                }
                if (f.getType() != findClazz) {
                    continue;
                }
                consts.add((P) f.get(null));
            }
            return consts;
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
}
