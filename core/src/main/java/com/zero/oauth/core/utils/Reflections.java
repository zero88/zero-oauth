package com.zero.oauth.core.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.zero.oauth.core.LoggerFactory;
import com.zero.oauth.core.exceptions.OAuthException;

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

    @SuppressWarnings("unchecked")
    public static <T> T getConstantByName(Class<?> destClazz, String fieldName) {
        try {
            Field field = destClazz.getDeclaredField("methods");
            int modifiers = field.getModifiers();
            if (!Modifier.isPublic(modifiers) || !Modifier.isStatic(modifiers) || !Modifier.isFinal(modifiers)) {
                return null;
            }
            return (T) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new OAuthException(
                MessageFormat.format("Failed to get field constant {0} of {1}", fieldName, destClazz.getName()), e);
        }
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
            int modifiers = f.getModifiers();
            if (!Modifier.isPublic(modifiers) || !Modifier.isStatic(modifiers) || !Modifier.isFinal(modifiers)) {
                continue;
            }
            boolean primitive = f.getType().isPrimitive();
            if (primitive ? f.getType() != primitiveClass : f.getType() != findClazz) {
                continue;
            }
            try {
                consts.add((P) f.get(null));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                LoggerFactory.logger().debug(e, "Failed to get field constant {0}", f.getName());
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
            LoggerFactory.logger().trace(e, "Try casting primitive class from class {0}",
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
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = Reflections.class.getClassLoader();
        }
        Class<?> clazz = classLoader.loadClass(Strings.requireNotBlank(className));
        if (parentClass.isAssignableFrom(clazz)) {
            return (Class<T>) clazz;
        }
        throw new OAuthException("Class " + clazz.getName() + " is not child of class " + parentClass.getName());
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
            LoggerFactory.logger().warn(e, "Failed when init instance of class {0}", className);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T initInstance(Class<T> clazz, Class[] argClasses, Object[] args) {
        try {
            Constructor constructor = Objects.requireNonNull(clazz).getDeclaredConstructor(argClasses);
            constructor.setAccessible(true);
            return (T) constructor.newInstance(args);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new OAuthException("Cannot init instance", e);
        }
    }

    public static <T> void updateConstants(Class<?> destClazz, String fieldName, T value) {
        try {
            Field methodsField = destClazz.getDeclaredField(fieldName);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);
            methodsField.setAccessible(true);
            methodsField.set(null, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new OAuthException(
                MessageFormat.format("Update failed on field {0} of class {1}", fieldName, destClazz.getName()), e);
        }
    }

}
