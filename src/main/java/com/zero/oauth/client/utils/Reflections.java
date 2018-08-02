package com.zero.oauth.client.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.message.StringFormattedMessage;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Reflections {

    public static <T> List<T> getConstants(Class<T> destClazz) {
        return Reflections.getConstants(destClazz, destClazz);
    }

    @SuppressWarnings("unchecked")
    public static <T, P> List<P> getConstants(Class<T> destClazz, Class<P> findClazz) {
        List<P> consts = new ArrayList<>();
        Class<?> primitiveClass = getPrimitiveClass(Objects.requireNonNull(findClazz));
        for (Field f : Objects.requireNonNull(destClazz).getDeclaredFields()) {
            if (!Modifier.isPublic(f.getModifiers()) || !Modifier.isStatic(f.getModifiers())) {
                continue;
            }
            boolean primitive = f.getType().isPrimitive();
            if (primitive ? f.getType() != primitiveClass : f.getType() != findClazz) {
                continue;
            }
            try {
                consts.add((P) f.get(null));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                log.warn(new StringFormattedMessage("Failed to get field constant {}", f.getName()), e);
            }
        }
        return consts;
    }

    private static <P> Class<?> getPrimitiveClass(Class<P> findClazz) {
        try {
            Field t = findClazz.getField("TYPE");
            if (!Modifier.isPublic(t.getModifiers()) || !Modifier.isStatic(t.getModifiers())) {
                return null;
            }
            Object primitiveClazz = t.get(null);
            if (primitiveClazz instanceof Class) {
                return (Class<?>) primitiveClazz;
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            log.debug(new StringFormattedMessage("Try to get TYPE value of {} if {} can represents primitive",
                                                 findClazz.getName()), e);
        }
        return null;
    }

}
