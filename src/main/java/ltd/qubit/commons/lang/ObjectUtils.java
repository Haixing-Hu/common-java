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
 * 此类提供对{@code Object}的操作。
 *
 * <p>此类尝试优雅地处理{@code null}输入。通常不会对{@code null}输入抛出异常。
 * 每个方法都会详细记录其行为。
 *
 * @author 胡海星
 */
@ThreadSafe
public final class ObjectUtils {

  /**
   * 用作{@code null}占位符的单例对象，当{@code null}有其他含义时使用。
   *
   * <p>例如，在{@code HashMap}中，{@link java.util.HashMap#get(Object)}方法
   * 如果{@code Map}包含{@code null}或者没有匹配的键时都会返回{@code null}。
   * {@code Null}占位符可以用来区分这两种情况。
   *
   * <p>另一个例子是{@code Hashtable}，其中不能存储{@code null}。
   *
   * <p>此实例是可序列化的。
   */
  public static final Null NULL = new Null();

  private ObjectUtils() {}

  /**
   * 如果传入的对象为{@code null}，则返回默认值。
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
   *      对象的类型。
   * @param obj
   *      要测试的{@code Object}，可以为{@code null}
   * @param defaultValue
   *      要返回的默认值，可以为{@code null}
   * @return
   *      如果{@code obj}不为{@code null}则返回{@code obj}，否则返回{@code defaultValue}。
   */
  public static <T> T defaultIfNull(@Nullable final T obj, final T defaultValue) {
    return (obj != null ? obj : defaultValue);
  }

  /**
   * 如果传入的集合为{@code null}或空，则返回默认集合。
   *
   * @param <T>
   *      集合中对象的类型。
   * @param col
   *      要测试的{@code Collection}，可以为{@code null}或空
   * @param defaultValue
   *      要返回的默认集合，可以为{@code null}。
   * @return
   *      如果{@code col}不为{@code null}且非空则返回{@code col}，否则返回{@code defaultValue}。
   */
  public static <T> Collection<T> defaultIfEmpty(@Nullable final Collection<T> col,
      final Collection<T> defaultValue) {
    return ((col == null || col.isEmpty()) ? defaultValue : col);
  }

  /**
   * 如果传入的字符串为{@code null}或空，则返回默认字符串。
   *
   * @param str
   *      要测试的字符串，可以为{@code null}或空。
   * @param defaultValue
   *      要返回的默认字符串，可以为{@code null}。
   * @return
   *      如果{@code str}不为{@code null}且非空则返回{@code str}，否则返回{@code defaultValue}。
   */
  public static String defaultIfEmpty(@Nullable final String str,
      final String defaultValue) {
    return ((str == null || str.isEmpty()) ? defaultValue : str);
  }

  /**
   * 如果传入的对象为{@code null}，则返回新实例。
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
   *      对象的类型。
   * @param obj
   *      要测试的{@code Object}，可以为{@code null}
   * @param cls
   *      用于创建新实例的类对象。
   * @return
   *     如果{@code obj}不为{@code null}则返回{@code obj}，否则返回新实例。
   */
  public static <T> T createIfNull(@Nullable final T obj, final Class<T> cls) {
    return (obj != null ? obj : (cls == null ? null : ConstructorUtils.newInstance(cls)));
  }

  /**
   * 获取对象的哈希码。
   *
   * <pre>
   * ObjectUtils.hashCode(null)   = 0
   * ObjectUtils.hashCode(array)  = ArrayUtils.hashCode(array)
   * ObjectUtils.hashCode(obj)    = obj.hashCode()
   * </pre>
   *
   * @param obj
   *     要获取哈希码的对象，可以为{@code null}
   * @return
   *     对象的哈希码，如果为null则返回零。如果对象是数组，
   *     此方法将委托给{@link ArrayUtils#hashCode(Object)}。
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
   * 获取如果类没有重写toString方法时{@code Object}会产生的toString。
   * {@code null}将返回{@code null}。
   *
   * <pre>
   * ObjectUtils.identityToString(null)         = null
   * ObjectUtils.identityToString("")           = "java.lang.String@1e23"
   * ObjectUtils.identityToString(Boolean.TRUE) = "java.lang.Boolean@7fa"
   * </pre>
   *
   * @param object
   *     要为其创建toString的对象，可以为{@code null}
   * @return 默认的toString文本，如果传入{@code null}则返回{@code null}
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
   * 追加如果类没有重写toString方法时{@code Object}会产生的toString。
   * 对于两个参数中的任何一个为{@code null}都会抛出NullPointerException。
   *
   * <pre>
   * ObjectUtils.identityToString(buf, "")            = buf.append("java.lang.String@1e23"
   * ObjectUtils.identityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa"
   * ObjectUtils.identityToString(buf, Boolean.TRUE)  = buf.append("java.lang.Boolean@7fa")
   * </pre>
   *
   * @param builder
   *     要追加到的构建器
   * @param object
   *     要为其创建toString的对象
   */
  public static void identityToString(final StringBuilder builder,
      final Object object) {
    Argument.requireNonNull("object", object);
    builder.append(object.getClass().getName())
           .append('@')
           .append(Integer.toHexString(System.identityHashCode(object)));
  }

  /**
   * 获取{@code Object}的{@code toString}，对于{@code null}输入返回null。
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
   *     要{@code toString}的Object，可以为null
   * @return
   *     传入的Object的toString，或者如果{@code obj}为{@code null}则返回{@code null}。
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
   * 获取{@code Object}的{@code toString}，如果{@code null}输入则返回指定的文本。
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
   *     要{@code toString}的Object，可以为null
   * @param nullStr
   *     如果{@code null}输入时要返回的String，可以为null
   * @return 传入的Object的toString，或者如果{@code null}输入则返回nullStr。
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
   * 用作null占位符的类，当{@code null}有其他含义时使用。
   *
   * <p>例如，在{@code HashMap}中，{@link java.util.HashMap#get(Object)}
   * 方法如果{@code Map}包含{@code null}或者没有匹配的键时都会返回{@code null}。
   * {@code Null}占位符可以用来区分这两种情况。
   *
   * <p>另一个例子是{@code Hashtable}，其中不能存储{@code null}。
   */
  public static class Null implements Serializable {

    private static final long serialVersionUID = 661457446116895424L;

    Null() {}

    /**
     * 确保单例。
     *
     * @return 单例值
     */
    private Object readResolve() {
      return NULL;
    }
  }

}