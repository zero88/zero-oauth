package com.zero.oauth.client.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.message.StringFormattedMessage;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReflectionUtils {

    public static <T> List<T> getConstants(Class<T> destClazz) throws IllegalArgumentException {
        return ReflectionUtils.getConstants(destClazz, destClazz);
    }

    @SuppressWarnings("unchecked")
    public static <T, P> List<P> getConstants(Class<T> destClazz, Class<P> findClazz) {
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
            try {
                consts.add((P) f.get(null));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                log.warn(new StringFormattedMessage("Failed to get field constant {}", f.getName()),
                         e);
            }
        }
        return consts;
    }

}
