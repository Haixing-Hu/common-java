////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.reflect.ConstructorUtils;

/**
 * This class provides operations on {@code Object}.
 *
 * <p>This class tries to handle {@code null} input gracefully. An exception
 * will generally not be thrown for a {@code null} input. Each method documents
 * its behavior in more detail.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class ObjectUtils {

  /**
   * Singleton used as a {@code null} place holder where {@code null} has
   * another meaning.
   *
   * <p>For example, in a {@code HashMap} the
   * {@link java.util.HashMap#get(Object)} method returns {@code null} if the
   * {@code Map} contains {@code null} or if there is no matching key. The
   * {@code Null} placeholder can be used to distinguish between these two
   * cases.
   *
   * <p>Another example is {@code Hashtable}, where {@code null} cannot
   * be stored.
   *
   * <p>This instance is Serializable.
   */
  public static final Null NULL = new Null();

  private ObjectUtils() {}

  /**
   * Returns a default value if the object passed is {@code null}.
   *
   * <pre>
   * ObjectUtils.defaultIfNull(null, null)      = null
   * ObjectUtils.defaultIfNull(null, "")        = ""
   * ObjectUtils.defaultIfNull(null, "zz")      = "zz"
   * ObjectUtils.defaultIfNull("abc", *)        = "abc"
   * ObjectUtils.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
   * </pre>
   *
   * @param <T>
   *      the type of the object.
   * @param obj
   *      the {@code Object} to test, may be {@code null}
   * @param defaultValue
   *      the default value to return, may be {@code null}
   * @return
   *      {@code obj} if it is not {@code null}, {@code defaultValue} otherwise.
   */
  public static <T> T defaultIfNull(@Nullable final T obj, final T defaultValue) {
    return (obj != null ? obj : defaultValue);
  }

  /**
   * Returns a default collection if the collection passed is {@code null} or empty.
   *
   * @param <T>
   *      the type of the object in the collection.
   * @param col
   *      the {@code Collection} to test, may be {@code null} or empty/
   * @param defaultValue
   *      the default collection to return, may be {@code null}.
   * @return
   *      {@code col} if it is not {@code null} nor empty, {@code defaultValue}
   *      otherwise.
   */
  public static <T> Collection<T> defaultIfEmpty(@Nullable final Collection<T> col,
      final Collection<T> defaultValue) {
    return ((col == null || col.isEmpty()) ? defaultValue : col);
  }

  /**
   * Returns a default string if the string passed is {@code null} or empty.
   *
   * @param str
   *      the string to test, may be {@code null} or empty.
   * @param defaultValue
   *      the default string to return, may be {@code null}.
   * @return
   *      {@code str} if it is not {@code null} nor empty, {@code defaultValue}
   *      otherwise.
   */
  public static String defaultIfEmpty(@Nullable final String str,
      final String defaultValue) {
    return ((str == null || str.isEmpty()) ? defaultValue : str);
  }

  /**
   * Returns a new instance if the object passed is {@code null}.
   *
   * <pre>
   * ObjectUtils.createIfNull(null, null)               = null
   * ObjectUtils.createIfNull(null, String.class)       = ""
   * ObjectUtils.createIfNull(null, Foo.class)          = new Foo()
   * ObjectUtils.createIfNull("abc", String.class)      = "abc"
   * ObjectUtils.createIfNull(new Foo(123), Foo.class)  = new Foo(123)
   * </pre>
   *
   * @param <T>
   *      the type of the object.
   * @param obj
   *      the {@code Object} to test, may be {@code null}
   * @param cls
   *      the class object used to create the new instance.
   * @return
   *     {@code obj} if it is not {@code null}, a new instance otherwise.
   */
  public static <T> T createIfNull(@Nullable final T obj, final Class<T> cls) {
    return (obj != null ? obj : (cls == null ? null : ConstructorUtils.newInstance(cls)));
  }

  /**
   * Gets the hash code of an object.
   *
   * <pre>
   * ObjectUtils.hashCode(null)   = 0
   * ObjectUtils.hashCode(array)  = ArrayUtils.hashCode(array)
   * ObjectUtils.hashCode(obj)    = obj.hashCode()
   * </pre>
   *
   * @param obj
   *     the object to obtain the hash code of, may be {@code null}
   * @return
   *     the hash code of the object, or zero if null. If the object is an array,
   *     this method will delegate to {@link ArrayUtils#hashCode(Object)}.
   */
  public static int hashCode(final Object obj) {
    if (obj == null) {
      return 0;
    } else if (obj.getClass().isArray()) {
      return ArrayUtils.hashCode(obj);
    } else {
      return obj.hashCode();
    }
  }

  /**
   * Gets the toString that would be produced by {@code Object} if a class did
   * not override toString itself. {@code null} will return {@code null}.
   *
   * <pre>
   * ObjectUtils.identityToString(null)         = null
   * ObjectUtils.identityToString("")           = "java.lang.String@1e23"
   * ObjectUtils.identityToString(Boolean.TRUE) = "java.lang.Boolean@7fa"
   * </pre>
   *
   * @param object
   *     the object to create a toString for, may be {@code null}
   * @return the default toString text, or {@code null} if {@code null} passed
   *     in
   */
  public static String identityToString(final Object object) {
    if (object == null) {
      return null;
    }
    final StringBuilder builder = new StringBuilder();
    identityToString(builder, object);
    return builder.toString();
  }

  /**
   * Appends the toString that would be produced by {@code Object} if a class
   * did not override toString itself. {@code null} will throw a
   * NullPointerException for either of the two parameters.
   *
   * <pre>
   * ObjectUtils.identityToString(buf, "")            = buf.append("java.lang.String@1e23"
   * ObjectUtils.identityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa"
   * ObjectUtils.identityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa")
   * </pre>
   *
   * @param builder
   *     the builder to append to
   * @param object
   *     the object to create a toString for
   */
  public static void identityToString(final StringBuilder builder,
      final Object object) {
    Argument.requireNonNull("object", object);
    builder.append(object.getClass().getName())
           .append('@')
           .append(Integer.toHexString(System.identityHashCode(object)));
  }

  /**
   * Gets the {@code toString} of an {@code Object} returning null for
   * {@code null} input.
   *
   * <pre>
   * ObjectUtils.toString(null)         = null
   * ObjectUtils.toString("")           = ""
   * ObjectUtils.toString("bat")        = "bat"
   * ObjectUtils.toString(Boolean.TRUE) = "true"
   * ObjectUtils.toString(new int[]{1, 2, 3})  = "{1, 2, 3}"
   * </pre>
   *
   * @param obj
   *     the Object to {@code toString}, may be null
   * @return
   *     the passed in Object's toString, or {@code null} if {@code obj} is
   *     {@code null}.
   * @see String#valueOf(Object)
   */
  public static String toString(@Nullable final Object obj) {
    if (obj == null) {
      return null;
    }
    if (obj instanceof String) {
      return (String) obj;
    } else if (obj instanceof Character) {
      return String.valueOf(obj);
    } else if (obj instanceof Enum) {
      return ((Enum<?>) obj).name();
    } else if (ArrayUtils.isArray(obj)) {
      return ArrayUtils.toString(obj);
    } else {
      return obj.toString();
    }
  }

  /**
   * Gets the {@code toString} of an {@code Object} returning a specified text
   * if {@code null} input.
   *
   * <pre>
   * ObjectUtils.toString(null, null)                  = null
   * ObjectUtils.toString(null, "null")                = "null"
   * ObjectUtils.toString("", "null")                  = ""
   * ObjectUtils.toString("bat", "null")               = "bat"
   * ObjectUtils.toString(Boolean.TRUE, "null")        = "true"
   * ObjectUtils.toString(new int[]{1, 2, 3}, "null")  = "{1, 2, 3}"
   * </pre>
   *
   * @param obj
   *     the Object to {@code toString}, may be null
   * @param nullStr
   *     the String to return if {@code null} input, may be null
   * @return the passed in Object's toString, or nullStr if {@code null} input.
   * @see String#valueOf(Object)
   */
  public static String toString(final Object obj, final String nullStr) {
    if (obj == null) {
      return nullStr;
    } else if (ArrayUtils.isArray(obj)) {
      return ArrayUtils.toString(obj);
    } else {
      return obj.toString();
    }
  }

  /**
   * Class used as a null place holder where {@code null} has another meaning.
   *
   * <p>For example, in a {@code HashMap} the {@link java.util.HashMap#get(Object)}
   * method returns {@code null} if the {@code Map} contains {@code null} or if
   * there is no matching key. The {@code Null} place holder can be used to
   * distinguish between these two cases.
   *
   * <p>Another example is {@code Hashtable}, where {@code null} cannot be
   * stored.
   */
  public static class Null implements Serializable {

    private static final long serialVersionUID = 661457446116895424L;

    Null() {}

    /**
     * Ensure singleton.
     *
     * @return the singleton value
     */
    private Object readResolve() {
      return NULL;
    }
  }

}