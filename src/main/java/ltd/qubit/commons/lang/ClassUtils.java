////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.reflect.MethodUtils;
import ltd.qubit.commons.text.Remover;
import ltd.qubit.commons.util.filter.character.WhitespaceCharFilter;

import static java.util.Map.entry;

import static ltd.qubit.commons.datastructure.map.MapUtils.invertAsUnmodifiable;
import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_CLASS_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.isSameLength;
import static ltd.qubit.commons.lang.ObjectUtils.defaultIfNull;

/**
 * This class provides operations on {@link Class} objects.
 *
 * <p>This class tries to handle {@code null} input gracefully. An exception
 * will not be thrown for a {@code null} input. Each method documents its
 * behavior in more detail.
 *
 * <p>This class also handle the conversion from {@link Class} objects to
 * common types.
 *
 * @author Haixing Hu
 * @since 1.0.0
 */
public class ClassUtils {

  /**
   * The default {@link Class} value used when necessary.
   *
   * @since 1.0.0
   */
  public static final Class<?> DEFAULT = null;

  /**
   * The package separator character: {@code '&#x2e;' == {@value}}.
   *
   * @since 1.0.0
   */
  public static final char PACKAGE_SEPARATOR_CHAR = '.';

  /**
   * The package separator String: {@code "&#x2e;"}.
   *
   * @since 1.0.0
   */
  public static final String PACKAGE_SEPARATOR = ".";

  /**
   * The inner class separator character: {@code '$' == {@value}}.
   *
   * @since 1.0.0
   */
  public static final char INNER_CLASS_SEPARATOR_CHAR = '$';

  /**
   * The inner class separator String: {@code "$"}.
   *
   * @since 1.0.0
   */
  public static final String INNER_CLASS_SEPARATOR = "$";

  /**
   * Maps primitive {@code Class}es to their corresponding wrapper {@code
   * Class}.
   *
   * @since 1.0.0
   */
  private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_MAP =
      Map.ofEntries(
          entry(boolean.class, Boolean.class),
          entry(byte.class, Byte.class),
          entry(char.class, Character.class),
          entry(short.class, Short.class),
          entry(int.class, Integer.class),
          entry(long.class, Long.class),
          entry(double.class, Double.class),
          entry(float.class, Float.class),
          entry(void.class, Void.class)
      );

  /**
   * Maps wrapper {@code Class}es to their corresponding primitive types.
   *
   * @since 1.0.0
   */
  private static final Map<Class<?>, Class<?>> WRAPPER_PRIMITIVE_MAP =
      invertAsUnmodifiable(PRIMITIVE_WRAPPER_MAP);

  /**
   * Maps a primitive class name to its corresponding abbreviation used in array
   * class names.
   *
   * @since 1.0.0
   */
  private static final Map<String, String> ABBREVIATION_MAP =
      Map.ofEntries(
          entry("boolean", "Z"),
          entry("char", "C"),
          entry("int", "I"),
          entry("long", "J"),
          entry("short", "S"),
          entry("byte", "B"),
          entry("float", "F"),
          entry("double", "D")
      );

  /**
   * Maps an abbreviation used in array class names to corresponding primitive
   * class name.
   *
   * @since 1.0.0
   */
  private static final Map<String, String> REVERSE_ABBREVIATION_MAP =
      invertAsUnmodifiable(ABBREVIATION_MAP);

  private static final float AUTOBOXING_JDK_VERSION = 1.5f;

  /**
   * Checks if an array of Classes can be assigned to another array of Classes.
   *
   * <p>This method calls {@link #isAssignable(Class, Class) isAssignable} for
   * each Class pair in the input arrays. It can be used to check if a set of
   * arguments (the first parameter) are suitably compatible with a set of
   * method parameter types (the second parameter).
   *
   * <p>Unlike the {@link Class#isAssignableFrom(Class)} method, this
   * method takes into account widenings of primitive classes and {@code null}s.
   *
   * <p>Primitive widenings allow an int to be assigned to a {@code long},
   * {@code float} or {@code double}. This method returns the correct result for
   * these cases.
   *
   * <p>{@code Null} may be assigned to any reference type. This method will
   * return {@code true} if {@code null} is passed in and the toClass is
   * non-primitive.
   *
   * <p>Specifically, this method tests whether the type represented by the
   * specified {@code Class} parameter can be converted to the type represented
   * by this {@code Class} object via an identity conversion widening primitive
   * or widening reference conversion.
   *
   * <p>See <em><a href="http://java.sun.com/docs/books/jls/">The Java Language
   * Specification</a></em>, sections 5.1.1, 5.1.2 and 5.1.4 for details.
   *
   * @param classArray
   *     the array of Classes to check, may be {@code null}.
   * @param toClassArray
   *     the array of Classes to try to assign into, may be {@code null}.
   * @return {@code true} if assignment possible.
   * @since 1.0.0
   */
  public static boolean isAssignable(final Class<?>[] classArray,
      final Class<?>[] toClassArray) {
    if (! isSameLength(classArray, toClassArray)) {
      return false;
    }
    final Class<?>[] from = defaultIfNull(classArray, EMPTY_CLASS_ARRAY);
    final Class<?>[] to = defaultIfNull(toClassArray, EMPTY_CLASS_ARRAY);
    for (int i = 0; i < from.length; i++) {
      if (!isAssignable(from[i], to[i])) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if one {@code Class} can be assigned to a variable of another {@code
   * Class}.
   *
   * <p>Unlike the {@link Class#isAssignableFrom(Class)} method, this
   * method takes into account widening of primitive classes and {@code null}s.
   *
   * <p>Primitive widening allow an int to be assigned to a long, float or
   * double.
   * This method returns the correct result for these cases.
   *
   * <p>{@code Null} may be assigned to any reference type. This method will
   * return {@code true} if {@code null} is passed in and the toClass is
   * non-primitive.
   *
   * <p>Specifically, this method tests whether the type represented by the
   * specified {@code cls} parameter can be converted to the type represented by
   * the {@code toClass} parameter via an identity conversion widening primitive
   * or widening reference conversion.
   *
   * <p>See <em><a href="http://java.sun.com/docs/books/jls/">The Java Language
   * Specification</a></em>, sections 5.1.1, 5.1.2 and 5.1.4 for details.
   *
   * @param cls
   *     the Class to check, may be null.
   * @param toClass
   *     the Class to try to assign into, returns false if null.
   * @return {@code true} if assignment possible.
   * @since 1.0.0
   */
  public static boolean isAssignable(final Class<?> cls,
      final Class<?> toClass) {
    if (toClass == null) {
      return false;
    }
    // have to check for null, as isAssignableFrom doesn't
    if (cls == null) {
      return (!toClass.isPrimitive());
    }
    Class<?> from = cls;
    // autoboxing for JDK >= 1.5
    if (SystemUtils.isJavaVersionAtLeast(AUTOBOXING_JDK_VERSION)) {
      if (from.isPrimitive() && (!toClass.isPrimitive())) {
        from = primitiveToWrapper(from);
        if (from == null) {
          return false;
        }
      }
      if (toClass.isPrimitive() && (!from.isPrimitive())) {
        from = wrapperToPrimitive(from);
        if (from == null) {
          return false;
        }
      }
    }
    if (from.equals(toClass)) {
      return true;
    }
    if (from.isPrimitive()) {
      if (!toClass.isPrimitive()) {
        return false;
      }
      if (Integer.TYPE.equals(from)) {
        return Long.TYPE.equals(toClass)
            || Float.TYPE.equals(toClass)
            || Double.TYPE.equals(toClass);
      }
      if (Long.TYPE.equals(from)) {
        return Float.TYPE.equals(toClass)
            || Double.TYPE.equals(toClass);
      }
      if (Boolean.TYPE.equals(from)) {
        return false;
      }
      if (Double.TYPE.equals(from)) {
        return false;
      }
      if (Float.TYPE.equals(from)) {
        return Double.TYPE.equals(toClass);
      }
      if (Character.TYPE.equals(from) || Short.TYPE.equals(from)) {
        return Integer.TYPE.equals(toClass)
            || Long.TYPE.equals(toClass)
            || Float.TYPE.equals(toClass)
            || Double.TYPE.equals(toClass);
      }
      if (Byte.TYPE.equals(from)) {
        return Short.TYPE.equals(toClass)
            || Integer.TYPE.equals(toClass)
            || Long.TYPE.equals(toClass)
            || Float.TYPE.equals(toClass)
            || Double.TYPE.equals(toClass);
      }
      // should never get here
      return false;
    }
    return toClass.isAssignableFrom(from);
  }

  /**
   * Tests whether the specified class an inner class or static nested class.
   *
   * @param cls
   *     the class to check, may be null.
   * @return {@code true} if the class is an inner or static nested class, false
   *     if not or {@code null}.
   * @since 1.0.0
   */
  public static boolean isInnerClass(final Class<?> cls) {
    if (cls == null) {
      return false;
    }
    return cls.getName().indexOf(INNER_CLASS_SEPARATOR_CHAR) >= 0;
  }

  /**
   * Given a {@code List} of class names, this method converts them into
   * classes.
   *
   * <p>A new {@code List} is returned. If the class name cannot be found,
   * {@code null} is stored in the {@code List}. If the class name in the {@code
   * List} is {@code null}, {@code null} is stored in the output {@code List}.
   *
   * @param classNames
   *     the classNames to change.
   * @return a {@code List} of Class objects corresponding to the class names,
   *     {@code null} if null input.
   * @throws ClassCastException
   *     if classNames contains a non String entry.
   * @since 1.0.0
   */
  public static List<Class<?>> namesToClasses(final List<String> classNames) {
    if (classNames == null) {
      return null;
    }
    final List<Class<?>> classes = new ArrayList<>(classNames.size());
    for (final String className : classNames) {
      try {
        classes.add(Class.forName(className));
      } catch (final ClassNotFoundException ex) {
        classes.add(null);
      }
    }
    return classes;
  }

  /**
   * Given a {@code List} of {@code Class} objects, this method converts them
   * into class names.
   *
   * <p>A new {@code List} is returned. {@code null} objects will be
   * copied into the returned list as {@code null}.
   *
   * @param classes
   *     the classes to change.
   * @return a {@code List} of class names corresponding to the Class objects,
   *     {@code null} if null input.
   * @throws ClassCastException
   *     if {@code classes} contains a non-{@code Class} entry.
   * @since 1.0.0
   */
  public static List<String> classesToNames(final List<Class<?>> classes) {
    if (classes == null) {
      return null;
    }
    final List<String> classNames = new ArrayList<>(classes.size());
    for (final Class<?> cls : classes) {
      if (cls == null) {
        classNames.add(null);
      } else {
        classNames.add(cls.getName());
      }
    }
    return classNames;
  }

  /**
   * Converts the specified wrapper class to its corresponding primitive class.
   *
   * <p>This method is the counter part of {@code primitiveToWrapper()}. If
   * the passed in class is a wrapper class for a primitive type, this primitive
   * type will be returned (e.g. {@code Integer.TYPE} for {@code
   * Integer.class}). For other classes, or if the parameter is
   * <b>null</b>, the return value is <b>null</b>.
   *
   * @param cls
   *     the class to convert, may be <b>null</b>.
   * @return the corresponding primitive type if {@code cls} is a wrapper class,
   *     <b>null</b> otherwise.
   * @see #primitiveToWrapper(Class)
   * @since 1.0.0
   */
  public static Class<?> wrapperToPrimitive(final Class<?> cls) {
    return (cls == null ? null : WRAPPER_PRIMITIVE_MAP.get(cls));
  }

  /**
   * Converts the specified primitive Class object to its corresponding wrapper
   * Class object.
   *
   * @param cls
   *     the class to convert, may be null.
   * @return the wrapper class for {@code cls} or {@code cls} if {@code cls} is
   *     not a primitive. {@code null} if null input.
   * @since 1.0.0
   */
  public static Class<?> primitiveToWrapper(final Class<?> cls) {
    if ((cls != null) && cls.isPrimitive()) {
      return PRIMITIVE_WRAPPER_MAP.get(cls);
    } else {
      return cls;
    }
  }

  /**
   * Converts the specified array of wrapper Class objects to an array of its
   * corresponding primitive Class objects.
   *
   * <p>This method invokes {@code wrapperToPrimitive()} for each element of
   * the passed in array.
   *
   * @param classes
   *     the class array to convert, may be null or empty
   * @return an array which contains for each given class, the primitive class
   *     or <b>null</b> if the original class is not a wrapper class. {@code
   *     null} if null input. Empty array if an empty array passed in.
   * @see #wrapperToPrimitive(Class)
   * @since 1.0.0
   */
  public static Class<?>[] wrappersToPrimitives(final Class<?>[] classes) {
    if (classes == null) {
      return null;
    }
    if (classes.length == 0) {
      return classes;
    }
    final Class<?>[] convertedClasses = new Class<?>[classes.length];
    for (int i = 0; i < classes.length; i++) {
      convertedClasses[i] = wrapperToPrimitive(classes[i]);
    }
    return convertedClasses;
  }

  /**
   * Converts the specified array of primitive Class objects to an array of its
   * corresponding wrapper Class objects.
   *
   * @param classes
   *     the class array to convert, may be null or empty
   * @return an array which contains for each given class, the wrapper class or
   *     the original class if class is not a primitive. {@code null} if null
   *     input. Empty array if an empty array passed in.
   * @since 1.0.0
   */
  public static Class<?>[] primitivesToWrappers(final Class<?>[] classes) {
    if (classes == null) {
      return null;
    }
    if (classes.length == 0) {
      return classes;
    }
    final Class<?>[] convertedClasses = new Class<?>[classes.length];
    for (int i = 0; i < classes.length; i++) {
      convertedClasses[i] = primitiveToWrapper(classes[i]);
    }
    return convertedClasses;
  }

  /**
   * Converts an array of {@code Object} in to an array of {@code Class}
   * objects.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array
   *     an {@code Object} array.
   * @return a {@code Class} array, {@code null} if null array input.
   * @since 1.0.0
   */
  public static Class<?>[] toClass(final Object[] array) {
    if (array == null) {
      return null;
    } else if (array.length == 0) {
      return EMPTY_CLASS_ARRAY;
    }
    final Class<?>[] classes = new Class<?>[array.length];
    for (int i = 0; i < array.length; i++) {
      classes[i] = array[i].getClass();
    }
    return classes;
  }

  /**
   * Gets the class represented by {@code className} using the {@code
   * classLoader}.
   *
   * <p>This implementation supports names like " {@code java.lang.String[]}
   * " as well as " {@code [Ljava.lang.String;}".
   *
   * @param classLoader
   *     the class loader to use to load the class
   * @param className
   *     the class name
   * @param initialize
   *     whether the class must be initialized
   * @return the class represented by {@code className} using the {@code
   *     classLoader}
   * @throws NullPointerException
   *     if any argument is null.
   * @throws ClassNotFoundException
   *     if the class is not found.
   * @since 1.0.0
   */
  public static Class<?> getClass(final ClassLoader classLoader,
      final String className, final boolean initialize)
      throws ClassNotFoundException {
    requireNonNull("className", className);
    if (ABBREVIATION_MAP.containsKey(className)) {
      final String clsName = "[" + ABBREVIATION_MAP.get(className);
      return Class.forName(clsName, initialize, classLoader)
                   .getComponentType();
    } else {
      return Class.forName(toCanonicalName(className), initialize, classLoader);
    }
  }

  /**
   * Gets the (initialized) class represented by {@code className} using the
   * {@code classLoader}.
   *
   * <p>This implementation supports names like " {@code java.lang.String[]}
   * " as well as " {@code [Ljava.lang.String;}".
   *
   * @param classLoader
   *     the class loader to use to load the class.
   * @param className
   *     the class name.
   * @return the class represented by {@code className} using the {@code
   *     classLoader}.
   * @throws NullPointerException
   *     if any argument is null.
   * @throws ClassNotFoundException
   *     if the class is not found.
   * @since 1.0.0
   */
  public static Class<?> getClass(final ClassLoader classLoader,
      final String className) throws ClassNotFoundException {
    return getClass(classLoader, className, true);
  }

  /**
   * Gets the (initialized )class represented by {@code className} using the
   * current thread's context class loader.
   *
   * <p>This implementation supports names like "{@code java.lang.String[]}
   * " as well as " {@code [Ljava.lang.String;}".
   *
   * @param className
   *     the class name.
   * @return the class represented by {@code className} using the current
   *     thread's context class loader.
   * @throws NullPointerException
   *     if any argument is null.
   * @throws ClassNotFoundException
   *     if the class is not found.
   * @since 1.0.0
   */
  public static Class<?> getClass(final String className)
      throws ClassNotFoundException {
    return getClass(className, true);
  }

  /**
   * Gets the class represented by {@code className} using the current thread's
   * context class loader.
   *
   * <p>This implementation supports names like " {@code java.lang.String[]}
   * " as well as " {@code [Ljava.lang.String;}".
   *
   * @param className
   *     the class name.
   * @param initialize
   *     whether the class must be initialized.
   * @return the class represented by {@code className} using the current
   *     thread's context class loader.
   * @throws NullPointerException
   *     if any argument is null.
   * @throws ClassNotFoundException
   *     if the class is not found.
   * @since 1.0.0
   */
  public static Class<?> getClass(final String className,
      final boolean initialize) throws ClassNotFoundException {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    if (loader == null) {
      loader = ClassUtils.class.getClassLoader();
    }
    return getClass(loader, className, initialize);
  }

  /**
   * Gets the class name minus the package name for an {@code Object}.
   *
   * @param object
   *     the class to get the short name for, may be null.
   * @param valueIfNull
   *     the value to return if null.
   * @return the class name of the object without the package name, or the null
   *     value.
   * @since 1.0.0
   */
  public static String getShortClassName(final Object object,
      final String valueIfNull) {
    if (object == null) {
      return valueIfNull;
    } else {
      return getShortClassName(object.getClass().getName());
    }
  }

  /**
   * Gets the class name minus the package name from a {@code Class}.
   *
   * @param cls
   *     the class to get the short name for.
   * @return the class name without the package name or an empty string.
   * @since 1.0.0
   */
  public static String getShortClassName(final Class<?> cls) {
    if (cls == null) {
      return StringUtils.EMPTY;
    } else {
      return getShortClassName(cls.getName());
    }
  }

  /**
   * Gets the class name minus the package name from a String.
   *
   * <p>The string passed in is assumed to be a class name - it is not checked.
   *
   * @param className
   *     the className to get the short name for.
   * @return the class name of the class without the package name or an empty
   *     string.
   * @since 1.0.0
   */
  public static String getShortClassName(final String className) {
    if ((className == null) || (className.length() == 0)) {
      return StringUtils.EMPTY;
    }
    final int lastDotIdx = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);
    final int innerIdx = className.indexOf(INNER_CLASS_SEPARATOR_CHAR,
        lastDotIdx == -1 ? 0 : lastDotIdx + 1);
    String out = className.substring(lastDotIdx + 1);
    if (innerIdx != -1) {
      out = out.replace(INNER_CLASS_SEPARATOR_CHAR, PACKAGE_SEPARATOR_CHAR);
    }
    return out;
  }

  /**
   * Gets the package name of an {@code Object}.
   *
   * @param object
   *     the class to get the package name for, may be null.
   * @param valueIfNull
   *     the value to return if null.
   * @return the package name of the object, or the null value.
   * @since 1.0.0
   */
  public static String getPackageName(final Object object,
      final String valueIfNull) {
    if (object == null) {
      return valueIfNull;
    } else {
      return getPackageName(object.getClass().getName());
    }
  }

  /**
   * Gets the package name of a {@code Class}.
   *
   * @param cls
   *     the class to get the package name for, may be {@code null}.
   * @return the package name or an empty string.
   * @since 1.0.0
   */
  public static String getPackageName(final Class<?> cls) {
    if (cls == null) {
      return StringUtils.EMPTY;
    } else {
      return getPackageName(cls.getName());
    }
  }

  /**
   * Gets the package name from a {@code String}.
   *
   * <p>The string passed in is assumed to be a class name - it is not checked.
   *
   * <p>If the class is unpackaged, return an empty string.
   *
   * @param className
   *     the className to get the package name for, may be {@code null}.
   * @return the package name or an empty string.
   * @since 1.0.0
   */
  public static String getPackageName(final String className) {
    if (className == null) {
      return StringUtils.EMPTY;
    }
    final int pos = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);
    if (pos < 0) {
      return StringUtils.EMPTY;
    }
    return className.substring(0, pos);
  }

  /**
   * Gets the full canonical name of the class of an {@code Object}.
   *
   * @param object
   *     the class to get the short name for, may be null.
   * @param valueIfNull
   *     the value to return if null.
   * @return the full canonical name of the class of the object, or {@code null}.
   */
  public static String getFullCanonicalName(final Object object,
      final String valueIfNull) {
    if (object == null) {
      return valueIfNull;
    }
    return getCanonicalNameImpl(object.getClass().getName());
  }

  /**
   * Gets the full canonical name of a {@code Class}.
   *
   * @param cls
   *     the class to get the short name for.
   * @return the full canonical name of the class, or an empty string.
   */
  public static String getFullCanonicalName(final Class<?> cls) {
    if (cls == null) {
      return StringUtils.EMPTY;
    }
    return getCanonicalNameImpl(cls.getName());
  }

  /**
   * Gets the full canonical name of a class name.
   *
   * <p>The string passed in is assumed to be a canonical name - it is not
   * checked.
   *
   * @param canonicalName
   *     the class name to get the short name for.
   * @return the full canonical name of the class or an empty string.
   * @since 1.0.0
   */
  public static String getFullCanonicalName(final String canonicalName) {
    return getCanonicalNameImpl(canonicalName);
  }

  /**
   * Gets the canonical name minus the package name for an {@code Object}.
   *
   * @param object
   *     the class to get the short name for, may be null.
   * @param valueIfNull
   *     the value to return if null.
   * @return the canonical name of the object without the package name, or the
   *     null value.
   * @since 1.0.0
   */
  public static String getShortCanonicalName(final Object object,
      final String valueIfNull) {
    if (object == null) {
      return valueIfNull;
    }
    return getShortCanonicalName(object.getClass().getName());
  }

  /**
   * Gets the canonical name minus the package name from a {@code Class}.
   *
   * @param cls
   *     the class to get the short name for.
   * @return the canonical name without the package name or an empty string.
   * @since 1.0.0
   */
  public static String getShortCanonicalName(final Class<?> cls) {
    if (cls == null) {
      return StringUtils.EMPTY;
    }
    return getShortCanonicalName(cls.getName());
  }

  /**
   * Gets the canonical name minus the package name from a String.
   *
   * <p>The string passed in is assumed to be a canonical name - it is not
   * checked.
   *
   * @param canonicalName
   *     the class name to get the short name for.
   * @return the canonical name of the class without the package name or an
   *     empty string.
   * @since 1.0.0
   */
  public static String getShortCanonicalName(final String canonicalName) {
    return getShortClassName(getCanonicalNameImpl(canonicalName));
  }

  /**
   * Gets the package name from the canonical name of an {@code Object}.
   *
   * @param object
   *     the class to get the package name for, may be null.
   * @param valueIfNull
   *     the value to return if null.
   * @return the package name of the object, or the null value.
   * @since 1.0.0
   */
  public static String getPackageCanonicalName(final Object object,
      final String valueIfNull) {
    if (object == null) {
      return valueIfNull;
    }
    return getPackageCanonicalName(object.getClass().getName());
  }

  /**
   * Gets the package name from the canonical name of a {@code Class}.
   *
   * @param cls
   *     the class to get the package name for, may be {@code null}.
   * @return the package name or an empty string.
   * @since 1.0.0
   */
  public static String getPackageCanonicalName(final Class<?> cls) {
    if (cls == null) {
      return StringUtils.EMPTY;
    }
    return getPackageCanonicalName(cls.getName());
  }

  /**
   * Gets the package name from the canonical name.
   *
   * <p>The string passed in is assumed to be a canonical name - it is not
   * checked.
   *
   * <p>If the class is unpackaged, return an empty string.
   *
   * @param canonicalName
   *     the canonical name to get the package name for, may be {@code null}.
   * @return the package name or an empty string.
   * @since 1.0.0
   */
  public static String getPackageCanonicalName(final String canonicalName) {
    return getPackageName(getCanonicalNameImpl(canonicalName));
  }

  /**
   * Gets a {@code List} of super-classes for the given class.
   *
   * @param cls
   *     the class to look up, may be {@code null}.
   * @return the {@code List} of super-classes in order going up from this one
   *     {@code null} if null input.
   * @since 1.0.0
   */
  public static List<Class<?>> getAllSuperclasses(final Class<?> cls) {
    if (cls == null) {
      return null;
    }
    final List<Class<?>> result = new ArrayList<>();
    Class<?> superclass = cls.getSuperclass();
    while (superclass != null) {
      result.add(superclass);
      superclass = superclass.getSuperclass();
    }
    return result;
  }

  /**
   * Gets a {@code List} of all interfaces implemented by the given class and
   * its super-classes.
   * <p>
   * The order is determined by looking through each interface in turn as
   * declared in the source file and following its hierarchy up. Then each
   * superclass is considered in the same way. Later duplicates are ignored, so
   * the order is maintained.
   *
   * @param cls
   *     the class to look up, may be {@code null}.
   * @return
   *     the {@code List} of interfaces in order, {@code null} if null input.
   */
  public static List<Class<?>> getAllInterfaces(final Class<?> cls) {
    if (cls == null) {
      return null;
    }
    final List<Class<?>> result = new ArrayList<>();
    Class<?> current = cls;
    while (current != null) {
      final Class<?>[] interfaces = current.getInterfaces();
      for (final Class<?> i : interfaces) {
        if (!result.contains(i)) {
          result.add(i);
        }
        final List<Class<?>> superInterfaces = getAllInterfaces(i);
        for (final Class<?> j : superInterfaces) {
          if (!result.contains(j)) {
            result.add(j);
          }
        }
      }
      current = current.getSuperclass();
    }
    return result;
  }

  /**
   * Gets the desired Method much like {@code Class.getMethod}, however it
   * ensures that the returned Method is from a public class or interface and
   * not from an anonymous inner class. This means that the Method is
   * invokable and doesn't fall foul of Java bug
   * <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4071957">4071957</a>).
   *
   * <pre>
   * Set set = Collections.unmodifiableSet(...);
   * Method method = ClassUtils.getPublicMethod(set.getClass(), "isEmpty", new Class[0]);
   * Object result = method.invoke(set, new Object[]);
   * </pre>
   *
   * @param cls
   *     the class to check, not null
   * @param methodName
   *     the name of the method
   * @param parameterTypes
   *     the list of parameters
   * @return the method
   * @throws NullPointerException
   *     if the class is null
   * @throws SecurityException
   *     if a a security violation occurred.
   * @throws NoSuchMethodException
   *     if the method is not found in the given class or if the method doesn't
   *     conform with the requirements.
   * @deprecated use {@link MethodUtils#getMethod(Class, int, String, Class[])} instead.
   */
  @Deprecated
  public static Method getPublicMethod(final Class<?> cls,
      final String methodName, final Class<?>[] parameterTypes)
      throws SecurityException, NoSuchMethodException {
    final Method declaredMethod = cls.getMethod(methodName, parameterTypes);
    if (Modifier.isPublic(declaredMethod.getDeclaringClass().getModifiers())) {
      return declaredMethod;
    }
    final List<Class<?>> candidateClasses = new ArrayList<>();
    candidateClasses.addAll(getAllInterfaces(cls));
    candidateClasses.addAll(getAllSuperclasses(cls));

    for (final Class<?> candidateClass : candidateClasses) {
      if (!Modifier.isPublic(candidateClass.getModifiers())) {
        continue;
      }
      final Method candidateMethod;
      try {
        candidateMethod = candidateClass.getMethod(methodName, parameterTypes);
      } catch (final NoSuchMethodException ex) {
        continue;
      }
      if (Modifier.isPublic(candidateMethod.getDeclaringClass().getModifiers())) {
        return candidateMethod;
      }
    }
    throw new NoSuchMethodException("Can't find a public method for "
        + methodName + " " + ArrayUtils.toString(parameterTypes));
  }

  /**
   * Converts a class name to a JLS style class name.
   *
   * @param className
   *     the class name.
   * @return the converted name.
   * @since 1.0.0
   */
  private static String toCanonicalName(final String className) {
    requireNonNull("className", className);
    String name = new Remover()
        .forCharsSatisfy(WhitespaceCharFilter.INSTANCE)
        .removeFrom(className);
    if (name.endsWith("[]")) {
      final StringBuilder builder = new StringBuilder();
      while (name.endsWith("[]")) {
        name = name.substring(0, name.length() - 2);
        builder.append('[');
      }
      final String abbreviation = ABBREVIATION_MAP.get(name);
      if (abbreviation != null) {
        builder.append(abbreviation);
      } else {
        builder.append('L').append(name).append(';');
      }
      name = builder.toString();
    }
    return name;
  }

  /**
   * Converts a given name of class into canonical format. If name of class is
   * not a name of array class it returns unchanged name.
   *
   * <p>Example:
   * <ul>
   * <li>{@code getCanonicalName("[I") = "int[]"}</li>
   * <li>
   * {@code getCanonicalName("[Ljava.lang.String;") = "java.lang.String[]"}
   * </li>
   * <li>{@code getCanonicalName("java.lang.String") = "java.lang.String"}</li>
   * </ul>
   *
   * @param className
   *     the name of class.
   * @return canonical form of class name.
   */
  private static String getCanonicalNameImpl(final String className) {
    String name = new Remover()
        .forCharsSatisfy(WhitespaceCharFilter.INSTANCE)
        .removeFrom(className);
    if (name == null) {
      return null;
    } else {
      int dim = 0;
      while (name.startsWith("[")) {
        dim++;
        name = name.substring(1);
      }
      if (dim < 1) {
        return name;
      } else {
        if (name.startsWith("L")) {
          int len = name.length();
          if (name.endsWith(";")) {
            --len;
          }
          name = name.substring(1, len);
        } else {
          if (name.length() > 0) {
            name = REVERSE_ABBREVIATION_MAP.get(name.substring(0, 1));
          }
        }
        final StringBuilder builder = new StringBuilder();
        builder.append(name);
        for (int i = 0; i < dim; ++i) {
          builder.append("[]");
        }
        return builder.toString();
      }
    }
  }

  /**
   * Gets the default value of a specified type.
   *
   * <p>If the specified type is a primitive type, the default value depends on
   * its actual type; otherwise, the default value of a non-primitive type is
   * always {@code null}.</p>
   *
   * @param type
   *     the class object of the specified type.
   * @return
   *     the default value object of the specified type.
   */
  public static Object getDefaultValueObject(final Class<?> type) {
    if (type.isPrimitive()) {
      if (type.equals(byte.class)) {
        return (byte) 0;
      } else if (type.equals(char.class)) {
        return '\0';
      } else if (type.equals(short.class)) {
        return (short) 0;
      } else if (type.equals(int.class)) {
        return 0;
      } else if (type.equals(long.class)) {
        return 0L;
      } else if (type.equals(float.class)) {
        return 0.0F;
      } else if (type.equals(double.class)) {
        return 0.0;
      } else if (type.equals(boolean.class)) {
        return Boolean.FALSE;
      } else if (type.equals(void.class)) {
        return null;
      } else {
        throw new IllegalArgumentException("Unhandled primitive type: " + type);
      }
    }
    return null;
  }


  /**
   * Check if a type is an interface.
   *
   * @param type
   *     the type to check
   * @return true if the type is an interface, false otherwise
   */
  public static boolean isInterface(final Class<?> type) {
    return type.isInterface();
  }

  /**
   * Check if the type is abstract (either an interface or an abstract class).
   *
   * @param type
   *     the type to check
   * @param <T>
   *     the actual type to check
   * @return true if the type is abstract, false otherwise
   */
  public static <T> boolean isAbstract(final Class<T> type) {
    return Modifier.isAbstract(type.getModifiers());
  }

  /**
   * Check if the type is public.
   *
   * @param type
   *     the type to check
   * @param <T>
   *     the actual type to check
   * @return true if the type is public, false otherwise
   */
  public static <T> boolean isPublic(final Class<T> type) {
    return Modifier.isPublic(type.getModifiers());
  }

  /**
   * Check if the type is protected.
   *
   * @param type
   *     the type to check
   * @param <T>
   *     the actual type to check
   * @return true if the type is protected, false otherwise
   */
  public static <T> boolean isProtected(final Class<T> type) {
    return Modifier.isProtected(type.getModifiers());
  }

  /**
   * Check if the type is private.
   *
   * @param type
   *     the type to check
   * @param <T>
   *     the actual type to check
   * @return true if the type is private, false otherwise
   */
  public static <T> boolean isPrivate(final Class<T> type) {
    return Modifier.isPrivate(type.getModifiers());
  }

  /**
   * Check whether a class object is a proxy class.
   *
   * @param type
   *     the class object be checked.
   * @return
   *     true if the class object is a proxy class; false otherwise.
   */
  public static boolean isProxy(final Class<?> type) {
    if (type == null) {
      return false;
    } else if (Proxy.isProxyClass(type)) {
      return true;
    } else {
      return matchesWellKnownProxyClassNamePattern(type.getName());
    }
  }

  static boolean matchesWellKnownProxyClassNamePattern(final String className) {
    return className.contains(BYTE_BUDDY_CLASS_SEPARATOR)
        || className.contains(CGLIB_JAVASSIST_CLASS_SEPARATOR)
        || className.contains(HIBERNATE_PROXY_CLASS_SEPARATOR);
  }

  private static final String CGLIB_JAVASSIST_CLASS_SEPARATOR = "$$";
  private static final String BYTE_BUDDY_CLASS_SEPARATOR = "$ByteBuddy$";
  private static final String HIBERNATE_PROXY_CLASS_SEPARATOR = "$HibernateProxy$";

  /**
   * Check if a type is a primitive type.
   *
   * @param type
   *     the type to check.
   * @return true if the type is a primitive type, false otherwise.
   */
  public static boolean isPrimitiveType(final Class<?> type) {
    return type.isPrimitive();
  }

  /**
   * Check if a type is an array type.
   *
   * @param type
   *     the type to check.
   * @return true if the type is an array type, false otherwise.
   */
  public static boolean isArrayType(final Class<?> type) {
    return type.isArray();
  }

  /**
   * 检测指定的类型是否是个枚举类行。
   * <p>
   * <b>注意：</b>不能直接使用{@link Class#isEnum()}判定枚举类型，因为{@code Enum.class.isEnum()}
   * 会返回{@code false}。而对于此函数，{@code isEnumType(Enum.class)}返回{@code true}。
   *
   * @param type
   *     待检测的类型的类对象。
   * @return
   *     若指定的类型是枚举类型，返回{@code true}；否则返回{@code false}。
   */
  public static boolean isEnumType(final Class<?> type) {
    return type.isEnum() || Enum.class.isAssignableFrom(type);
  }

  /**
   * 检查指定的类型是否是个记录类。
   *
   * @param type
   *     待检测的类型的类对象。
   * @return
   *     若指定的类型是记录类，返回{@code true}；否则返回{@code false}。
   */
  public static boolean isRecordType(final Class<?> type) {
    return type.isRecord();
  }

  /**
   * Check if a type is a collection type.
   *
   * @param type
   *     the type to check.
   * @return true if the type is a collection type, false otherwise
   */
  public static boolean isCollectionType(final Class<?> type) {
    return Collection.class.isAssignableFrom(type);
  }

  /**
   * Check if a type is a collection type.
   *
   * @param type
   *     the type to check.
   * @return true if the type is a collection type, false otherwise
   */
  public static boolean isCollectionType(final java.lang.reflect.Type type) {
    return isParameterizedType(type)
        && isCollectionType((Class<?>) ((ParameterizedType) type).getRawType());
  }

  /**
   * Check if a type is a list type.
   *
   * @param type
   *     the type to check.
   * @return true if the type is a list type, false otherwise
   */
  public static boolean isListType(final Class<?> type) {
    return List.class.isAssignableFrom(type);
  }

  /**
   * Check if a type is a list type.
   *
   * @param type
   *     the type to check.
   * @return true if the type is a list type, false otherwise
   */
  public static boolean isListType(final java.lang.reflect.Type type) {
    return isParameterizedType(type)
        && isListType((Class<?>) ((ParameterizedType) type).getRawType());
  }

  /**
   * Check if a type is a set type.
   *
   * @param type
   *     the type to check.
   * @return true if the type is a set type, false otherwise
   */
  public static boolean isSetType(final Class<?> type) {
    return Set.class.isAssignableFrom(type);
  }

  /**
   * Check if a type is a set type.
   *
   * @param type
   *     the type to check.
   * @return true if the type is a set type, false otherwise
   */
  public static boolean isSetType(final java.lang.reflect.Type type) {
    return isParameterizedType(type)
        && isSetType((Class<?>) ((ParameterizedType) type).getRawType());
  }

  /**
   * Check if a type is a map type.
   *
   * @param type
   *     the type to check
   * @return true if the type is a map type, false otherwise.
   */
  public static boolean isMapType(final Class<?> type) {
    return Map.class.isAssignableFrom(type);
  }

  /**
   * Check if a type is a map type.
   *
   * @param type
   *     the type to check.
   * @return true if the type is a map type, false otherwise
   */
  public static boolean isMapType(final java.lang.reflect.Type type) {
    return isParameterizedType(type)
        && isMapType((Class<?>) ((ParameterizedType) type).getRawType());
  }

  /**
   * Check if a type is a JDK built-in class.
   *
   * @param type
   *     the type to check
   * @return true if the type is a built-in class, false otherwise.
   */
  public static boolean isJdkBuiltIn(final Class<?> type) {
    final String name = type.getName();
    return name.startsWith("java.") || name.startsWith("javax.");
  }

  /**
   * Check whether a type is the boxing type of primitive type.
   *
   * @param type
   *     the class object of the type to check.
   * @return
   *     {@code true} if the type is the boxing type of primitive type,
   *     {@code false} otherwise.
   */
  public static boolean isBoxingType(final Class<?> type) {
    return (type == Character.class)
        || (type == Boolean.class)
        || (type == Byte.class)
        || (type == Short.class)
        || (type == Integer.class)
        || (type == Long.class)
        || (type == Float.class)
        || (type == Double.class);
  }

  /**
   * Check whether a type is the type representing a permission.
   *
   * @param type
   *     the class object of the type to check.
   * @return
   *     {@code true} if the type is the type representing a permission, i.e.,
   *     the subclass of {@link java.security.Permission}; {@code false}
   *     otherwise.
   */
  public static boolean isPermissionType(final Class<?> type) {
    return java.security.Permission.class.isAssignableFrom(type);
  }

  private static final Set<Class<?>> IMMUTABLE_JDK_CLASSES = new HashSet<>();

  static {
    IMMUTABLE_JDK_CLASSES.add(Character.class);
    IMMUTABLE_JDK_CLASSES.add(Boolean.class);
    IMMUTABLE_JDK_CLASSES.add(Byte.class);
    IMMUTABLE_JDK_CLASSES.add(Short.class);
    IMMUTABLE_JDK_CLASSES.add(Integer.class);
    IMMUTABLE_JDK_CLASSES.add(Long.class);
    IMMUTABLE_JDK_CLASSES.add(Float.class);
    IMMUTABLE_JDK_CLASSES.add(Double.class);
    IMMUTABLE_JDK_CLASSES.add(Void.class);
    IMMUTABLE_JDK_CLASSES.add(BigInteger.class);
    IMMUTABLE_JDK_CLASSES.add(BigDecimal.class);
    IMMUTABLE_JDK_CLASSES.add(String.class);
    IMMUTABLE_JDK_CLASSES.add(Class.class);
    IMMUTABLE_JDK_CLASSES.add(StackTraceElement.class);
    IMMUTABLE_JDK_CLASSES.add(java.net.IDN.class);
    IMMUTABLE_JDK_CLASSES.add(java.net.Inet4Address.class);
    IMMUTABLE_JDK_CLASSES.add(java.net.Inet6Address.class);
    IMMUTABLE_JDK_CLASSES.add(java.net.InetSocketAddress.class);
    IMMUTABLE_JDK_CLASSES.add(java.net.URL.class);
    IMMUTABLE_JDK_CLASSES.add(java.net.URI.class);
    IMMUTABLE_JDK_CLASSES.add(java.security.Permission.class);
    IMMUTABLE_JDK_CLASSES.add(java.io.File.class);
    // 2024-03-15 starfish: Remove the following AWT related classes since they do not exist in Android environment
    // IMMUTABLE_JDK_CLASSES.add(java.awt.Font.class);
    // IMMUTABLE_JDK_CLASSES.add(java.awt.BasicStroke.class);
    // IMMUTABLE_JDK_CLASSES.add(java.awt.Color.class);
    // IMMUTABLE_JDK_CLASSES.add(java.awt.GradientPaint.class);
    // IMMUTABLE_JDK_CLASSES.add(java.awt.LinearGradientPaint.class);
    // IMMUTABLE_JDK_CLASSES.add(java.awt.RadialGradientPaint.class);
    // IMMUTABLE_JDK_CLASSES.add(java.awt.Cursor.class);
    IMMUTABLE_JDK_CLASSES.add(java.util.Locale.class);
    IMMUTABLE_JDK_CLASSES.add(java.util.UUID.class);
    IMMUTABLE_JDK_CLASSES.add(java.util.Currency.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.Clock.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.Duration.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.Instant.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.LocalDate.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.LocalDateTime.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.LocalTime.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.MonthDay.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.OffsetDateTime.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.OffsetTime.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.Period.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.Year.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.YearMonth.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.ZonedDateTime.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.ZoneId.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.ZoneOffset.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.chrono.HijrahChronology.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.chrono.HijrahDate.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.chrono.IsoChronology.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.chrono.JapaneseChronology.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.chrono.JapaneseDate.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.chrono.JapaneseEra.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.chrono.MinguoChronology.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.chrono.MinguoDate.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.chrono.ThaiBuddhistChronology.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.chrono.ThaiBuddhistDate.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.format.DateTimeFormatter.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.format.DateTimeFormatterBuilder.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.format.DecimalStyle.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.temporal.IsoFields.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.temporal.JulianFields.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.temporal.TemporalAdjusters.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.temporal.TemporalQueries.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.temporal.ValueRange.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.temporal.WeekFields.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.zone.ZoneOffsetTransition.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.zone.ZoneOffsetTransitionRule.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.zone.ZoneRules.class);
    IMMUTABLE_JDK_CLASSES.add(java.time.zone.ZoneRulesProvider.class);
  }

  /**
   * Check whether a type is a type of immutable objects.
   *
   * @param type
   *     the class object of the type to check.
   * @return
   *     {@code true} if the type is a type of immutable objects, {@code false}
   *     otherwise.
   */
  public static boolean isImmutableType(final Class<?> type) {
    return IMMUTABLE_JDK_CLASSES.contains(type)
        || isEnumType(type)
        || isPermissionType(type)
        || type.isAnnotationPresent(Immutable.class);
  }

  /**
   * Check whether a type is a type of mutable objects.
   *
   * @param type
   *     the class object of the type to check.
   * @return
   *     {@code true} if the type is a type of mutable objects, {@code false}
   *     otherwise.
   */
  public static boolean isMutableType(final Class<?> type) {
    return !isImmutableType(type);
  }

  /**
   * Check if a type should be introspected for internal fields.
   * <p>
   * FIXME: this method is only used by common-random, and it should be moved to
   *   the common-random library.
   *
   * @param type
   *     the type to check
   * @return true if the type should be introspected, false otherwise
   */
  public static boolean isIntrospectable(final Class<?> type) {
    return !isEnumType(type)
        && !isArrayType(type)
        && !(isCollectionType(type) && isJdkBuiltIn(type))
        && !(isMapType(type) && isJdkBuiltIn(type));
  }

  /**
   * Check if a type is populatable.
   * <p>
   * FIXME: this method is only used by common-random, and it should be moved to
   *   the common-random library.
   *
   * @param type
   *     the type to check
   * @return true if the type is populatable, false otherwise
   */
  public static boolean isPopulatable(final java.lang.reflect.Type type) {
    return !isWildcardType(type)
        && !isTypeVariable(type)
        && !isCollectionType(type)
        && !isParameterizedType(type);
  }

  /**
   * Check if a type is a parameterized type.
   *
   * @param type
   *     the type to check
   * @return true if the type is parameterized, false otherwise
   */
  public static boolean isParameterizedType(final java.lang.reflect.Type type) {
    return (type instanceof ParameterizedType)
        && ((ParameterizedType) type).getActualTypeArguments().length > 0;
  }

  /**
   * Check if a type is a wildcard type.
   *
   * @param type
   *     the type to check
   * @return true if the type is a wildcard type, false otherwise
   */
  public static boolean isWildcardType(final java.lang.reflect.Type type) {
    return (type instanceof WildcardType);
  }

  /**
   * Check if a type is a type variable.
   *
   * @param type
   *     the type to check
   * @return true if the type is a type variable, false otherwise
   */
  public static boolean isTypeVariable(final Type type) {
    return (type instanceof TypeVariable<?>);
  }

  /**
   * Searches the specified interface of a class.
   *
   * @param type
   *     the specified class.
   * @param simpleName
   *     the simple name of the interface to be searched.
   * @return the interface of the class with the specified simple name; or
   *     {@code null} if no such interface.
   */
  public static Class<?> getInterface(final Class<?> type, final String simpleName) {
    for (final Class<?> cls : type.getInterfaces()) {
      if (cls.getSimpleName().equals(simpleName)) {
        return cls;
      }
    }
    return null;
  }

  /**
   * Gets the real class object of a possible proxy class object.
   *
   * @param <T>
   *     the type parameter of the class object.
   * @param clazz
   *     a class object, possibly a proxy class object.
   * @return
   *     the real class object of the specified possible proxy class object.
   */
  @SuppressWarnings("unchecked")
  public static <T> Class<T> getRealClass(final Class<T> clazz) {
    if (clazz == null) {
      return null;
    } else if (Proxy.isProxyClass(clazz)) {
      final Class<?>[] interfaces = clazz.getInterfaces();
      if (interfaces.length != 1) {
        throw new IllegalArgumentException("Unexpected number of interfaces: "
            + interfaces.length);
      }
      return (Class<T>) interfaces[0];
    } else if (matchesWellKnownProxyClassNamePattern(clazz.getName())) {
      final Class<T> superclass = (Class<T>) clazz.getSuperclass();
      return getRealClass(superclass);
    } else {
      return clazz;
    }
  }

  /**
   * Gets the real class object of a possible proxy object.
   *
   * @param <T>
   *     the type of the object.
   * @param object
   *     the object, possibly a proxy object.
   * @return
   *     the real class object of the specified possible proxy object.
   */
  public static <T> Class<T> getRealClass(final T object) {
    if (object instanceof Class<?>) {
      throw new IllegalArgumentException("The provided object is already a class: "
          + object + ". " + "You probably want to call ClassUtils.getRealClass(Class) instead.");
    }
    @SuppressWarnings("unchecked")
    final Class<T> entityClass = (Class<T>) object.getClass();
    return getRealClass(entityClass);
  }
}