////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import ltd.qubit.commons.text.Remover;
import ltd.qubit.commons.util.filter.character.WhitespaceCharFilter;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.concurrent.ThreadSafe;

import static ltd.qubit.commons.datastructure.map.MapUtils.invertAsUnmodifiable;
import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_CLASS_ARRAY;
import static ltd.qubit.commons.lang.ArrayUtils.isSameLength;
import static ltd.qubit.commons.lang.ObjectUtils.defaultIfNull;

import static java.util.Map.entry;

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
@ThreadSafe
public final class ClassUtils {

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

  private ClassUtils() {}

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
    return getShortClassName(getCanonicalName(canonicalName));
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
    return getPackageName(getCanonicalName(canonicalName));
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
    final List<Class<?>> classes = new ArrayList<>();
    Class<?> superclass = cls.getSuperclass();
    while (superclass != null) {
      classes.add(superclass);
      superclass = superclass.getSuperclass();
    }
    return classes;
  }

  /**
   * Gets a {@code List} of all interfaces implemented by the given class and
   * its super-classes.
   *
   * <p>The order is determined by looking through each interface in turn as
   * declared in the source file and following its hierarchy up. Then each
   * superclass is considered in the same way. Later duplicates are ignored, so
   * the order is maintained.
   *
   * @param cls
   *     the class to look up, may be {@code null}.
   * @return the {@code List} of interfaces in order, {@code null} if null
   *     input.
   * @since 1.0.0
   */
  public static List<Class<?>> getAllInterfaces(final Class<?> cls) {
    if (cls == null) {
      return null;
    }
    final List<Class<?>> list = new ArrayList<>();
    Class<?> type = cls;
    while (type != null) {
      final Class<?>[] interfaces = type.getInterfaces();
      for (final Class<?> i : interfaces) {
        if (!list.contains(i)) {
          list.add(i);
        }
        final List<Class<?>> superInterfaces = getAllInterfaces(i);
        for (final Class<?> j : superInterfaces) {
          if (!list.contains(j)) {
            list.add(j);
          }
        }
      }
      type = type.getSuperclass();
    }
    return list;
  }

  /**
   * Gets the desired Method much like {@code Class.getMethod}, however it
   * ensures that the returned Method is from a public class or interface and
   * not from an anonymous inner class. ,p This means that the Method is
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
   *     if the method is not found in the given class or if the method doen't
   *     conform with the requirements.
   * @since 1.0.0
   */
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
   * @since 1.0.0
   */
  private static String getCanonicalName(final String className) {
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
}
