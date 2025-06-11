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
 * 该类提供对 {@link Class} 对象的操作。
 *
 * <p>该类尽量优雅地处理 {@code null} 输入。对于 {@code null} 输入不会抛出异常。
 * 每个方法在其文档中详细说明了其行为。
 *
 * <p>该类还处理从 {@link Class} 对象到常见类型的转换。
 *
 * @author 胡海星
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
   // 使用 ClassKey 作为 Map 的键，而不是直接使用 Class 对象。
   // 这样可以避免在 Web 容器热部署环境中因为保留对类加载器的引用而导致的内存泄漏问题。
   // 详细说明请参考 ClassKey 类的 javadoc。
  private static final Map<ClassKey, Class<?>> PRIMITIVE_WRAPPER_MAP =
      Map.ofEntries(
          entry(new ClassKey(boolean.class), Boolean.class),
          entry(new ClassKey(byte.class), Byte.class),
          entry(new ClassKey(char.class), Character.class),
          entry(new ClassKey(short.class), Short.class),
          entry(new ClassKey(int.class), Integer.class),
          entry(new ClassKey(long.class), Long.class),
          entry(new ClassKey(double.class), Double.class),
          entry(new ClassKey(float.class), Float.class),
          entry(new ClassKey(void.class), Void.class)
      );

  /**
   * Maps wrapper {@code Class}es to their corresponding primitive types.
   *
   * @since 1.0.0
   */
   // 使用 ClassKey 作为 Map 的键，而不是直接使用 Class 对象。
   // 这样可以避免在 Web 容器热部署环境中因为保留对类加载器的引用而导致的内存泄漏问题。
   // 详细说明请参考 ClassKey 类的 javadoc。
  private static final Map<ClassKey, Class<?>> WRAPPER_PRIMITIVE_MAP =
      Map.ofEntries(
          entry(new ClassKey(Boolean.class), boolean.class),
          entry(new ClassKey(Byte.class), byte.class),
          entry(new ClassKey(Character.class), char.class),
          entry(new ClassKey(Short.class), short.class),
          entry(new ClassKey(Integer.class), int.class),
          entry(new ClassKey(Long.class), long.class),
          entry(new ClassKey(Double.class), double.class),
          entry(new ClassKey(Float.class), float.class),
          entry(new ClassKey(Void.class), void.class)
      );

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
   * 检查一个 Classes 数组是否可以赋值给另一个 Classes 数组。
   *
   * <p>该方法对输入数组中的每个 Class 对调用 {@link #isAssignable(Class, Class) isAssignable}。
   * 它可以用于检查一组参数（第一个参数）是否与一组方法参数类型（第二个参数）适当兼容。
   *
   * <p>与 {@link Class#isAssignableFrom(Class)} 方法不同，该方法考虑了基本类的
   * 拓宽和 {@code null}。
   *
   * <p>基本类型拓宽允许将 int 赋值给 {@code long}、{@code float} 或 {@code double}。
   * 该方法对这些情况返回正确的结果。
   *
   * <p>{@code Null} 可以赋值给任何引用类型。如果传入 {@code null} 且 toClass 是
   * 非基本类型，该方法将返回 {@code true}。
   *
   * <p>具体来说，该方法测试指定 {@code Class} 参数表示的类型是否可以通过恒等转换、
   * 拓宽基本类型或拓宽引用转换转换为此 {@code Class} 对象表示的类型。
   *
   * <p>详细信息请参见 <em><a href="http://java.sun.com/docs/books/jls/">The Java Language
   * Specification</a></em> 第 5.1.1、5.1.2 和 5.1.4 节。
   *
   * @param classArray
   *     要检查的 Classes 数组，可以为 {@code null}。
   * @param toClassArray
   *     要尝试赋值到的 Classes 数组，可以为 {@code null}。
   * @return 如果可以赋值则返回 {@code true}。
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
   * 检查一个 {@code Class} 是否可以赋值给另一个 {@code Class} 的变量。
   *
   * <p>与 {@link Class#isAssignableFrom(Class)} 方法不同，该方法考虑了基本类的
   * 拓宽和 {@code null}。
   *
   * <p>基本类型拓宽允许将 int 赋值给 {@code long}、{@code float} 或 {@code double}。
   * 该方法对这些情况返回正确的结果。
   *
   * <p>{@code Null} 可以赋值给任何引用类型。如果传入 {@code null} 且 toClass 是
   * 非基本类型，该方法将返回 {@code true}。
   *
   * <p>具体来说，该方法测试指定 {@code cls} 参数表示的类型是否可以通过恒等转换、
   * 拓宽基本类型或拓宽引用转换转换为 {@code toClass} 参数表示的类型。
   *
   * <p>详细信息请参见 <em><a href="http://java.sun.com/docs/books/jls/">The Java Language
   * Specification</a></em> 第 5.1.1、5.1.2 和 5.1.4 节。
   *
   * @param cls
   *     要检查的 Class，可以为 null。
   * @param toClass
   *     要尝试赋值到的 Class，如果为 null 则返回 false。
   * @return 如果可以赋值则返回 {@code true}。
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
   * 测试指定的类是否为内部类或静态嵌套类。
   *
   * @param cls
   *     要检查的类，可以为 null。
   * @return 如果类是内部类或静态嵌套类则返回 {@code true}，否则或为 {@code null} 时返回 false。
   * @since 1.0.0
   */
  public static boolean isInnerClass(final Class<?> cls) {
    if (cls == null) {
      return false;
    }
    return cls.getName().indexOf(INNER_CLASS_SEPARATOR_CHAR) >= 0;
  }

  /**
   * 给定类名的 {@code List}，该方法将它们转换为类。
   *
   * <p>返回一个新的 {@code List}。如果找不到类名，在 {@code List} 中存储 {@code null}。
   * 如果 {@code List} 中的类名为 {@code null}，在输出 {@code List} 中存储 {@code null}。
   *
   * @param classNames
   *     要转换的类名。
   * @return 对应于类名的 Class 对象的 {@code List}，如果输入为 null 则返回 {@code null}。
   * @throws ClassCastException
   *     如果 classNames 包含非 String 条目。
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
   * 给定 {@code Class} 对象的 {@code List}，该方法将它们转换为类名。
   *
   * <p>返回一个新的 {@code List}。{@code null} 对象将作为 {@code null} 复制到返回的列表中。
   *
   * @param classes
   *     要转换的类。
   * @return 对应于 Class 对象的类名的 {@code List}，如果输入为 null 则返回 {@code null}。
   * @throws ClassCastException
   *     如果 {@code classes} 包含非 {@code Class} 条目。
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
   * 获取指定包装类对应的基本类型。
   *
   * @param cls
   *     要转换的包装类，可以为 null
   * @return 对应的基本类型，如果 cls 为 null 或不是包装类则返回 null
   * @since 1.0.0
   */
  public static Class<?> wrapperToPrimitive(final Class<?> cls) {
    return (cls == null ? null : WRAPPER_PRIMITIVE_MAP.get(new ClassKey(cls)));
  }

  /**
   * 获取指定基本类型对应的包装类。
   *
   * @param cls
   *     要转换的基本类型，可以为 null
   * @return 对应的包装类，如果 cls 为 null 或不是基本类型则返回 null
   * @since 1.0.0
   */
  public static Class<?> primitiveToWrapper(final Class<?> cls) {
    Class<?> convertedClass = cls;
    if (cls != null && cls.isPrimitive()) {
      convertedClass = PRIMITIVE_WRAPPER_MAP.get(new ClassKey(cls));
    }
    return convertedClass;
  }

  /**
   * 将指定的包装 Class 对象数组转换为其对应的基本 Class 对象数组。
   *
   * <p>该方法对传入数组的每个元素调用 {@code wrapperToPrimitive()}。
   *
   * @param classes
   *     要转换的类数组，可以为 null 或空。
   * @return 对于给定数组中的每个类，包含对应基本类或 <b>null</b>（如果原始类不是包装类）的数组。
   *     如果输入为 null 则返回 {@code null}。如果传入空数组则返回空数组。
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
   * 将指定的基本 Class 对象数组转换为其对应的包装 Class 对象数组。
   *
   * @param classes
   *     要转换的类数组，可以为 null 或空。
   * @return 对于给定数组中的每个类，包含对应包装类或原始类（如果类不是基本类型）的数组。
   *     如果输入为 null 则返回 {@code null}。如果传入空数组则返回空数组。
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
   * 将 {@code Object} 数组转换为 {@code Class} 对象数组。
   *
   * <p>该方法对 {@code null} 输入数组返回 {@code null}。
   *
   * @param array
   *     {@code Object} 数组。
   * @return {@code Class} 数组，如果输入数组为 null 则返回 {@code null}。
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
   * 使用 {@code classLoader} 获取 {@code className} 表示的类。
   *
   * <p>该实现支持诸如 " {@code java.lang.String[]} " 以及 " {@code [Ljava.lang.String;}"
   * 这样的名称。
   *
   * @param classLoader
   *     用于加载类的类加载器。
   * @param className
   *     类名。
   * @param initialize
   *     类是否必须被初始化。
   * @return 使用 {@code classLoader} 获取的 {@code className} 表示的类。
   * @throws NullPointerException
   *     如果任何参数为 null。
   * @throws ClassNotFoundException
   *     如果找不到类。
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
   * 使用 {@code classLoader} 获取 {@code className} 表示的（已初始化的）类。
   *
   * <p>该实现支持诸如 " {@code java.lang.String[]} " 以及 " {@code [Ljava.lang.String;}"
   * 这样的名称。
   *
   * @param classLoader
   *     用于加载类的类加载器。
   * @param className
   *     类名。
   * @return 使用 {@code classLoader} 获取的 {@code className} 表示的类。
   * @throws NullPointerException
   *     如果任何参数为 null。
   * @throws ClassNotFoundException
   *     如果找不到类。
   * @since 1.0.0
   */
  public static Class<?> getClass(final ClassLoader classLoader,
      final String className) throws ClassNotFoundException {
    return getClass(classLoader, className, true);
  }

  /**
   * 使用当前线程的上下文类加载器获取 {@code className} 表示的（已初始化的）类。
   *
   * <p>该实现支持诸如 "{@code java.lang.String[]} " 以及 " {@code [Ljava.lang.String;}"
   * 这样的名称。
   *
   * @param className
   *     类名。
   * @return 使用当前线程的上下文类加载器获取的 {@code className} 表示的类。
   * @throws NullPointerException
   *     如果任何参数为 null。
   * @throws ClassNotFoundException
   *     如果找不到类。
   * @since 1.0.0
   */
  public static Class<?> getClass(final String className)
      throws ClassNotFoundException {
    return getClass(className, true);
  }

  /**
   * 使用当前线程的上下文类加载器获取 {@code className} 表示的类。
   *
   * <p>该实现支持诸如 " {@code java.lang.String[]} " 以及 " {@code [Ljava.lang.String;}"
   * 这样的名称。
   *
   * @param className
   *     类名。
   * @param initialize
   *     类是否必须被初始化。
   * @return 使用当前线程的上下文类加载器获取的 {@code className} 表示的类。
   * @throws NullPointerException
   *     如果任何参数为 null。
   * @throws ClassNotFoundException
   *     如果找不到类。
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
   * 获取 {@code Object} 的类名，不包含包名。
   *
   * @param object
   *     要获取短名称的类，可以为 null。
   * @param valueIfNull
   *     为 null 时返回的值。
   * @return 对象的类名（不包含包名），或 null 值。
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
   * 从 {@code Class} 获取类名，不包含包名。
   *
   * @param cls
   *     要获取短名称的类。
   * @return 不包含包名的类名或空字符串。
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
   * 从字符串获取类名，不包含包名。
   *
   * <p>传入的字符串假定为类名 - 不会进行检查。
   *
   * @param className
   *     要获取短名称的类名。
   * @return 不包含包名的类的类名或空字符串。
   * @since 1.0.0
   */
  public static String getShortClassName(final String className) {
    if ((className == null) || className.isEmpty()) {
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
   * 获取 {@code Object} 的包名。
   *
   * @param object
   *     要获取包名的类，可以为 null。
   * @param valueIfNull
   *     为 null 时返回的值。
   * @return 对象的包名，或 null 值。
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
   * 获取 {@code Class} 的包名。
   *
   * @param cls
   *     要获取包名的类，可以为 {@code null}。
   * @return 包名或空字符串。
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
   * 从 {@code String} 获取包名。
   *
   * <p>传入的字符串假定为类名 - 不会进行检查。
   *
   * <p>如果类未打包，返回空字符串。
   *
   * @param className
   *     要获取包名的类名，可以为 {@code null}。
   * @return 包名或空字符串。
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
   * 获取 {@code Object} 类的完整规范名称。
   *
   * @param object
   *     要获取短名称的类，可以为 null。
   * @param valueIfNull
   *     为 null 时返回的值。
   * @return 对象类的完整规范名称，或 {@code null}。
   */
  public static String getFullCanonicalName(final Object object,
      final String valueIfNull) {
    if (object == null) {
      return valueIfNull;
    }
    return getCanonicalNameImpl(object.getClass().getName());
  }

  /**
   * 获取 {@code Class} 的完整规范名称。
   *
   * @param cls
   *     要获取短名称的类。
   * @return 类的完整规范名称，或空字符串。
   */
  public static String getFullCanonicalName(final Class<?> cls) {
    if (cls == null) {
      return StringUtils.EMPTY;
    }
    return getCanonicalNameImpl(cls.getName());
  }

  /**
   * 获取类名的完整规范名称。
   *
   * <p>传入的字符串假定为规范名称 - 不会进行检查。
   *
   * @param canonicalName
   *     要获取短名称的类名。
   * @return 类的完整规范名称或空字符串。
   * @since 1.0.0
   */
  public static String getFullCanonicalName(final String canonicalName) {
    return getCanonicalNameImpl(canonicalName);
  }

  /**
   * 获取 {@code Object} 的规范名称，不包含包名。
   *
   * @param object
   *     要获取短名称的类，可以为 null。
   * @param valueIfNull
   *     为 null 时返回的值。
   * @return 对象的规范名称（不包含包名），或 null 值。
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
   * 从 {@code Class} 获取规范名称，不包含包名。
   *
   * @param cls
   *     要获取短名称的类。
   * @return 不包含包名的规范名称或空字符串。
   * @since 1.0.0
   */
  public static String getShortCanonicalName(final Class<?> cls) {
    if (cls == null) {
      return StringUtils.EMPTY;
    }
    return getShortCanonicalName(cls.getName());
  }

  /**
   * 从字符串获取规范名称，不包含包名。
   *
   * <p>传入的字符串假定为规范名称 - 不会进行检查。
   *
   * @param canonicalName
   *     要获取短名称的类名。
   * @return 不包含包名的类的规范名称或空字符串。
   * @since 1.0.0
   */
  public static String getShortCanonicalName(final String canonicalName) {
    return getShortClassName(getCanonicalNameImpl(canonicalName));
  }

  /**
   * 从 {@code Object} 的规范名称获取包名。
   *
   * @param object
   *     要获取包名的类，可以为 null。
   * @param valueIfNull
   *     为 null 时返回的值。
   * @return 对象的包名，或 null 值。
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
   * 从 {@code Class} 的规范名称获取包名。
   *
   * @param cls
   *     要获取包名的类，可以为 {@code null}。
   * @return 包名或空字符串。
   * @since 1.0.0
   */
  public static String getPackageCanonicalName(final Class<?> cls) {
    if (cls == null) {
      return StringUtils.EMPTY;
    }
    return getPackageCanonicalName(cls.getName());
  }

  /**
   * 从规范名称获取包名。
   *
   * <p>传入的字符串假定为规范名称 - 不会进行检查。
   *
   * <p>如果类未打包，返回空字符串。
   *
   * @param canonicalName
   *     要获取包名的规范名称，可以为 {@code null}。
   * @return 包名或空字符串。
   * @since 1.0.0
   */
  public static String getPackageCanonicalName(final String canonicalName) {
    return getPackageName(getCanonicalNameImpl(canonicalName));
  }

  /**
   * 获取给定类的超类的 {@code List}。
   *
   * @param cls
   *     要查找的类，可以为 {@code null}。
   * @return 从该类向上排列的超类的 {@code List}，如果输入为 null 则返回 {@code null}。
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
   * 获取给定类及其超类实现的所有接口的 {@code List}。
   * <p>
   * 顺序由按照源文件中声明的顺序依次查看每个接口并跟随其层次结构向上确定。
   * 然后以同样的方式考虑每个超类。后面的重复项被忽略，因此保持顺序。
   *
   * @param cls
   *     要查找的类，可以为 {@code null}。
   * @return 按顺序排列的接口的 {@code List}，如果输入为 null 则返回 {@code null}。
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
   * 获取所需的 Method，很像 {@code Class.getMethod}，但它确保返回的 Method 来自
   * 公共类或接口，而不是来自匿名内部类。这意味着 Method 是可调用的，并且不会遇到
   * Java bug <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4071957">4071957</a>。
   *
   * <pre>
   * Set set = Collections.unmodifiableSet(...);
   * Method method = ClassUtils.getPublicMethod(set.getClass(), "isEmpty", new Class[0]);
   * Object result = method.invoke(set, new Object[]);
   * </pre>
   *
   * @param cls
   *     要检查的类，不能为 null
   * @param methodName
   *     方法的名称
   * @param parameterTypes
   *     参数列表
   * @return 方法
   * @throws NullPointerException
   *     如果类为 null
   * @throws SecurityException
   *     如果发生安全违规。
   * @throws NoSuchMethodException
   *     如果在给定类中找不到方法，或者方法不符合要求。
   * @deprecated 使用 {@link MethodUtils#getMethod(Class, int, String, Class[])} 代替。
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
          if (!name.isEmpty()) {
            name = REVERSE_ABBREVIATION_MAP.get(name.substring(0, 1));
          }
        }
        return name + "[]".repeat(dim);
      }
    }
  }

  /**
   * 获取指定类型的默认值。
   *
   * <p>如果指定的类型是基本类型，默认值取决于其实际类型；否则，非基本类型的默认值
   * 总是 {@code null}。</p>
   *
   * @param type
   *     指定类型的类对象。
   * @return 指定类型的默认值对象。
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
   * 检查一个类型是否为接口。
   *
   * @param type
   *     要检查的类型。
   * @return 如果类型是接口则返回 true，否则返回 false。
   */
  public static boolean isInterface(final Class<?> type) {
    return type.isInterface();
  }

  /**
   * 检查一个类型是否为抽象的（接口或抽象类）。
   *
   * @param type
   *     要检查的类型。
   * @param <T>
   *     要检查的实际类型。
   * @return 如果类型是抽象的则返回 true，否则返回 false。
   */
  public static <T> boolean isAbstract(final Class<T> type) {
    return Modifier.isAbstract(type.getModifiers());
  }

  /**
   * 检查一个类型是否为公共的。
   *
   * @param type
   *     要检查的类型。
   * @param <T>
   *     要检查的实际类型。
   * @return 如果类型是公共的则返回 true，否则返回 false。
   */
  public static <T> boolean isPublic(final Class<T> type) {
    return Modifier.isPublic(type.getModifiers());
  }

  /**
   * 检查一个类型是否为受保护的。
   *
   * @param type
   *     要检查的类型。
   * @param <T>
   *     要检查的实际类型。
   * @return 如果类型是受保护的则返回 true，否则返回 false。
   */
  public static <T> boolean isProtected(final Class<T> type) {
    return Modifier.isProtected(type.getModifiers());
  }

  /**
   * 检查一个类型是否为私有的。
   *
   * @param type
   *     要检查的类型。
   * @param <T>
   *     要检查的实际类型。
   * @return 如果类型是私有的则返回 true，否则返回 false。
   */
  public static <T> boolean isPrivate(final Class<T> type) {
    return Modifier.isPrivate(type.getModifiers());
  }

  /**
   * 检查一个类对象是否为代理类。
   *
   * @param type
   *     要检查的类对象。
   * @return 如果类对象是代理类则返回 true；否则返回 false。
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

  /**
   * 检查类名是否匹配已知的代理类名称模式。
   *
   * @param className
   *     要检查的类名。
   * @return 如果类名匹配已知的代理类名称模式则返回 true。
   */
  static boolean matchesWellKnownProxyClassNamePattern(final String className) {
    return className.contains(BYTE_BUDDY_CLASS_SEPARATOR)
        || className.contains(CGLIB_JAVASSIST_CLASS_SEPARATOR)
        || className.contains(HIBERNATE_PROXY_CLASS_SEPARATOR);
  }

  private static final String CGLIB_JAVASSIST_CLASS_SEPARATOR = "$$";
  private static final String BYTE_BUDDY_CLASS_SEPARATOR = "$ByteBuddy$";
  private static final String HIBERNATE_PROXY_CLASS_SEPARATOR = "$HibernateProxy$";

  /**
   * 检查一个类型是否为基本类型。
   *
   * @param type
   *     要检查的类型。
   * @return 如果类型是基本类型则返回 true，否则返回 false。
   */
  public static boolean isPrimitiveType(final Class<?> type) {
    return type.isPrimitive();
  }

  /**
   * 检查一个类型是否为数组类型。
   *
   * @param type
   *     要检查的类型。
   * @return 如果类型是数组类型则返回 true，否则返回 false。
   */
  public static boolean isArrayType(final Class<?> type) {
    return type.isArray();
  }

  /**
   * 检测指定的类型是否是个枚举类型。
   * <p>
   * <b>注意：</b>不能直接使用{@link Class#isEnum()}判定枚举类型，因为{@code Enum.class.isEnum()}
   * 会返回{@code false}。而对于此函数，{@code isEnumType(Enum.class)}返回{@code true}。
   *
   * @param type
   *     待检测的类型的类对象。
   * @return 若指定的类型是枚举类型，返回{@code true}；否则返回{@code false}。
   */
  public static boolean isEnumType(final Class<?> type) {
    return type.isEnum() || Enum.class.isAssignableFrom(type);
  }

  /**
   * 检查指定的类型是否是记录类。
   *
   * @param type
   *     待检测的类型的类对象。
   * @return 若指定的类型是记录类，返回 {@code true}；否则返回 {@code false}。
   */
  public static boolean isRecordType(final Class<?> type) {
    return type.isRecord();
  }

  /**
   * 检查一个类型是否为集合类型。
   *
   * @param type
   *     要检查的类型。
   * @return 如果类型是集合类型则返回 true，否则返回 false。
   */
  public static boolean isCollectionType(final Class<?> type) {
    return Collection.class.isAssignableFrom(type);
  }

  /**
   * 检查一个类型是否为集合类型。
   *
   * @param type
   *     要检查的类型。
   * @return 如果类型是集合类型则返回 true，否则返回 false。
   */
  public static boolean isCollectionType(final java.lang.reflect.Type type) {
    return isParameterizedType(type)
        && isCollectionType((Class<?>) ((ParameterizedType) type).getRawType());
  }

  /**
   * 检查一个类型是否为列表类型。
   *
   * @param type
   *     要检查的类型。
   * @return 如果类型是列表类型则返回 true，否则返回 false。
   */
  public static boolean isListType(final Class<?> type) {
    return List.class.isAssignableFrom(type);
  }

  /**
   * 检查一个类型是否为列表类型。
   *
   * @param type
   *     要检查的类型。
   * @return 如果类型是列表类型则返回 true，否则返回 false。
   */
  public static boolean isListType(final java.lang.reflect.Type type) {
    return isParameterizedType(type)
        && isListType((Class<?>) ((ParameterizedType) type).getRawType());
  }

  /**
   * 检查一个类型是否为集合类型。
   *
   * @param type
   *     要检查的类型。
   * @return 如果类型是集合类型则返回 true，否则返回 false。
   */
  public static boolean isSetType(final Class<?> type) {
    return Set.class.isAssignableFrom(type);
  }

  /**
   * 检查一个类型是否为集合类型。
   *
   * @param type
   *     要检查的类型。
   * @return 如果类型是集合类型则返回 true，否则返回 false。
   */
  public static boolean isSetType(final java.lang.reflect.Type type) {
    return isParameterizedType(type)
        && isSetType((Class<?>) ((ParameterizedType) type).getRawType());
  }

  /**
   * 检查一个类型是否为映射类型。
   *
   * @param type
   *     要检查的类型。
   * @return 如果类型是映射类型则返回 true，否则返回 false。
   */
  public static boolean isMapType(final Class<?> type) {
    return Map.class.isAssignableFrom(type);
  }

  /**
   * 检查一个类型是否为映射类型。
   *
   * @param type
   *     要检查的类型。
   * @return 如果类型是映射类型则返回 true，否则返回 false。
   */
  public static boolean isMapType(final java.lang.reflect.Type type) {
    return isParameterizedType(type)
        && isMapType((Class<?>) ((ParameterizedType) type).getRawType());
  }

  /**
   * 检查一个类型是否为 JDK 内置类。
   *
   * @param type
   *     要检查的类型。
   * @return 如果类型是内置类则返回 true，否则返回 false。
   */
  public static boolean isJdkBuiltIn(final Class<?> type) {
    final String name = type.getName();
    return name.startsWith("java.") || name.startsWith("javax.");
  }

  /**
   * 检查一个类型是否为基本类型的包装类型。
   *
   * @param type
   *     要检查的类型的类对象。
   * @return 如果类型是基本类型的包装类型则返回 {@code true}，否则返回 {@code false}。
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
   * 检查一个类型是否表示权限。
   *
   * @param type
   *     要检查的类型的类对象。
   * @return 如果类型表示权限（即 {@link java.security.Permission} 的子类）则返回 {@code true}；
   *     否则返回 {@code false}。
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
   * 检查一个类型是否为不可变对象的类型。
   *
   * @param type
   *     要检查的类型的类对象。
   * @return 如果类型是不可变对象的类型则返回 {@code true}，否则返回 {@code false}。
   */
  public static boolean isImmutableType(final Class<?> type) {
    return IMMUTABLE_JDK_CLASSES.contains(type)
        || isEnumType(type)
        || isPermissionType(type)
        || type.isAnnotationPresent(Immutable.class);
  }

  /**
   * 检查一个类型是否为可变对象的类型。
   *
   * @param type
   *     要检查的类型的类对象。
   * @return 如果类型是可变对象的类型则返回 {@code true}，否则返回 {@code false}。
   */
  public static boolean isMutableType(final Class<?> type) {
    return !isImmutableType(type);
  }

  /**
   * 检查一个类型是否应该被内省其内部字段。
   * <p>
   * 注意：此方法仅由 common-random 使用，应该移动到 common-random 库中。
   *
   * @param type
   *     要检查的类型。
   * @return 如果类型应该被内省则返回 true，否则返回 false。
   */
  public static boolean isIntrospectable(final Class<?> type) {
    return !isEnumType(type)
        && !isArrayType(type)
        && !(isCollectionType(type) && isJdkBuiltIn(type))
        && !(isMapType(type) && isJdkBuiltIn(type));
  }

  /**
   * 检查一个类型是否可填充。
   * <p>
   * 注意：此方法仅由 common-random 使用，应该移动到 common-random 库中。
   *
   * @param type
   *     要检查的类型。
   * @return 如果类型可填充则返回 true，否则返回 false。
   */
  public static boolean isPopulatable(final java.lang.reflect.Type type) {
    return !isWildcardType(type)
        && !isTypeVariable(type)
        && !isCollectionType(type)
        && !isParameterizedType(type);
  }

  /**
   * 检查一个类型是否为参数化类型。
   *
   * @param type
   *     要检查的类型。
   * @return 如果类型是参数化的则返回 true，否则返回 false。
   */
  public static boolean isParameterizedType(final java.lang.reflect.Type type) {
    return (type instanceof ParameterizedType)
        && ((ParameterizedType) type).getActualTypeArguments().length > 0;
  }

  /**
   * 检查一个类型是否为通配符类型。
   *
   * @param type
   *     要检查的类型。
   * @return 如果类型是通配符类型则返回 true，否则返回 false。
   */
  public static boolean isWildcardType(final java.lang.reflect.Type type) {
    return (type instanceof WildcardType);
  }

  /**
   * 检查一个类型是否为类型变量。
   *
   * @param type
   *     要检查的类型。
   * @return 如果类型是类型变量则返回 true，否则返回 false。
   */
  public static boolean isTypeVariable(final Type type) {
    return (type instanceof TypeVariable<?>);
  }

  /**
   * 搜索类的指定接口。
   *
   * @param type
   *     指定的类。
   * @param simpleName
   *     要搜索的接口的简单名称。
   * @return 具有指定简单名称的类的接口；如果没有这样的接口则返回 {@code null}。
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
   * 获取可能的代理类对象的真实类对象。
   *
   * @param <T>
   *     类对象的类型参数。
   * @param clazz
   *     类对象，可能是代理类对象。
   * @return 指定的可能代理类对象的真实类对象。
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
   * 获取可能的代理对象的真实类对象。
   *
   * @param <T>
   *     对象的类型。
   * @param object
   *     对象，可能是代理对象。
   * @return 指定的可能代理对象的真实类对象。
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
