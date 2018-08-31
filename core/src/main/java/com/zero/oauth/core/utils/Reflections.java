package com.zero.oauth.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.zero.oauth.core.LoggerFactory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Reflections Utilities.
 *
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Reflections {

    /**
     * Get Constants with same type with specified class.
     *
     * @param destClazz Class to find constants
     * @param <T>       Type of class
     * @return list of constants
     */
    public static <T> List<T> getConstants(Class<T> destClazz) {
        return Reflections.getConstants(destClazz, destClazz);
    }

    /**
     * Get Constants with given type from specified class.
     *
     * @param destClazz Class to find constants
     * @param findClazz Class of constant
     * @param <T>       Type of find class
     * @param <P>       Type of constants class
     * @return list of constants value
     */
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
                LoggerFactory.instance().getLogger().warn(e, "Failed to get field constant {0}", f.getName());
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
            LoggerFactory.instance().getLogger().trace(e, "Try casting primitive class from class {0}",
                                                       findClazz.getName());
        }
        return null;
    }

    /**
     * Find Class by class name and parent class.
     *
     * @param className   Class name to find. Must be full qualified name
     * @param parentClass Parent class to check a finding class is sub class of given one
     * @param <T>         Type of parent class
     * @return Class object
     * @throws IllegalArgumentException if {@code className} is {@code blank}
     * @throws NullPointerException     if {@code parentClass} is {@code null}
     * @throws ClassNotFoundException   if class not found by {@code className}
     * @throws RuntimeException         if class found but not child of by {@code parentClass}
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> findClass(String className, Class<T> parentClass) throws ClassNotFoundException {
        Objects.requireNonNull(parentClass);
        String clazzName = Strings.requireNotBlank(className);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = Reflections.class.getClassLoader();
        }
        Class<?> clazz = classLoader.loadClass(clazzName);
        if (parentClass.isAssignableFrom(clazz)) {
            return (Class<T>) clazz;
        }
        throw new RuntimeException("Class " + clazz.getName() + " is not child of class " + parentClass.getName());
    }

    /**
     * Get instance of class name.
     *
     * @param className   Class name to find. Must be full qualified name
     * @param parentClass Parent class to check a finding class is sub class of given one
     * @param <T>         Type of parent class
     * @return Instance object, {@code null} if not found a class by name or failed to init an instance
     */
    public static <T> T getClassInstance(String className, Class<T> parentClass) {
        try {
            Class<T> clazz = findClass(className, parentClass);
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | RuntimeException e) {
            LoggerFactory.instance().getLogger().warn(e, "Failed to init instance of class name {0}", className);
            return null;
        }
    }

}
