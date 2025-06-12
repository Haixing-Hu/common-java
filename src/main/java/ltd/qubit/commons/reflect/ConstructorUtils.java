////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.reflect;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.ObjectStreamConstants;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import org.objenesis.ObjenesisHelper;

import ltd.qubit.commons.io.IoUtils;
import ltd.qubit.commons.lang.ClassUtils;
import ltd.qubit.commons.lang.Equality;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_CLASS_ARRAY;
import static ltd.qubit.commons.lang.ObjectUtils.defaultIfNull;

/**
 * 提供专注于构造函数的实用反射方法，参考了 {@link MethodUtils}。
 *
 * <h2>已知限制</h2>
 * <h2>在默认访问权限的超类中访问公共构造函数</h2>
 *
 * <p>当调用包含在默认访问权限超类中的公共构造函数时存在问题。反射可以正确定位这些构造函数
 * 并正确地将它们分配为公共的。但是，如果调用构造函数，则会抛出 {@code IllegalAccessException}。
 *
 * <p>{@code ConstructorUtils} 包含针对这种情况的解决方法。它将尝试在此构造函数上调用
 * {@code setAccessible}。如果此调用成功，则该方法可以正常调用。此调用只有在应用程序具有
 * 足够的安全权限时才会成功。如果此调用失败，则将记录警告，方法可能会失败。
 *
 * @author 胡海星
 */
@ThreadSafe
public class ConstructorUtils {

  private ConstructorUtils() {}

  /**
   * 缓存类的序列化数据。
   */
  private static final ClassValue<byte[]> SERIALIZED_DATA_CACHE =
      new ClassValue<>() {
        @Override
        protected byte[] computeValue(final Class<?> type) {
          try {
            final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            final DataOutputStream out = new DataOutputStream(bytes);
            out.writeShort(ObjectStreamConstants.STREAM_MAGIC);
            out.writeShort(ObjectStreamConstants.STREAM_VERSION);
            out.writeByte(ObjectStreamConstants.TC_OBJECT);
            out.writeByte(ObjectStreamConstants.TC_CLASSDESC);
            out.writeUTF(type.getName());
            out.writeLong(ObjectStreamClass.lookup(type).getSerialVersionUID());
            out.writeByte(2);  // classDescFlags (2 = Serializable)
            out.writeShort(0); // field count
            out.writeByte(ObjectStreamConstants.TC_ENDBLOCKDATA);
            out.writeByte(ObjectStreamConstants.TC_NULL);
            return bytes.toByteArray();
          } catch (final IOException e) {
            throw new ConstructFailedException(type, e);
          }
        }
      };

  /**
   * 缓存类的所有构造函数。
   */
  private static final ClassValue<List<Constructor<?>>> CONSTRUCTOR_CACHE =
      new ClassValue<>() {
        @Override
        protected List<Constructor<?>> computeValue(final Class<?> type) {
          final List<Constructor<?>> constructors = new ArrayList<>();
          for (final Constructor<?> ctor : type.getDeclaredConstructors()) {
            ctor.setAccessible(true); // FIXME: suppress access checks
            constructors.add(ctor);
          }
          return constructors;
        }
      };

  /**
   * 获取类的所有构造函数。
   *
   * @param <T>
   *     指定类中对象的类型。
   * @param cls
   *     一个类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。默认值可以是 {@link Option#DEFAULT}。
   * @return 所有指定构造函数的数组；如果没有此类构造函数则返回空数组。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  @SuppressWarnings("unchecked")
  public static <T> Constructor<T>[] getAllConstructors(final Class<T> cls,
      final int options) {
    final List<Constructor<?>> constructors = CONSTRUCTOR_CACHE.get(cls);
    final List<Constructor<T>> result = new ArrayList<>();
    for (final Constructor<?> c : constructors) {
      if (Option.satisfy(cls, c, options)) {
        final Constructor<T> ctor = (Constructor<T>) c;
        result.add(ctor);
      }
    }
    return result.toArray((Constructor<T>[]) new Constructor<?>[0]);
  }

  /**
   * 使用Java序列化创建对象的实例。
   *
   * @param cls
   *     要实例化的类。
   * @return 类的新实例。
   */
  @SuppressWarnings("unchecked")
  private static <T> T newInstanceUsingSerialization(final Class<T> cls) {
    try {
      final byte[] data = SERIALIZED_DATA_CACHE.get(cls);
      final ByteArrayInputStream bin = new ByteArrayInputStream(data);
      final ObjectInputStream in = new ObjectInputStream(bin) {
        @Override
        protected Class<?> resolveClass(final ObjectStreamClass desc)
            throws ClassNotFoundException {
          return Class.forName(desc.getName(), false, cls.getClassLoader());
        }
      };
      try {
        return (T) in.readObject();
      } finally {
        IoUtils.closeQuietly(in);
      }
    } catch (final IOException | ClassNotFoundException e) {
      throw new ConstructFailedException(cls, e);
    }
  }

  /**
   * 获取类的默认构造函数。
   *
   * @param <T>
   *     指定类中对象的类型。
   * @param cls
   *     要获取构造函数的类。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  public static <T> Constructor<T> getConstructor(final Class<T> cls) {
    return getConstructor(cls, Option.DEFAULT, null);
  }

  /**
   * 获取类的构造函数。
   *
   * @param <T>
   *     指定类中对象的类型。
   * @param cls
   *     要获取构造函数的类。
   * @param paramType
   *     构造函数参数的类型。
   * @return
   *     指定的构造函数，如果没有此类构造函数则返回 {@code null}。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  @Nullable
  public static <T> Constructor<T> getConstructor(final Class<T> cls,
      final Class<?> paramType) {
    return getConstructor(cls, Option.DEFAULT, new Class<?>[] { paramType });
  }

  /**
   * 获取类的构造函数。
   *
   * @param <T>
   *     指定类中对象的类型。
   * @param cls
   *     要获取构造函数的类。
   * @param paramType1
   *     构造函数第一个参数的类型。
   * @param paramType2
   *     构造函数第二个参数的类型。
   * @return
   *     指定的构造函数，如果没有此类构造函数则返回 {@code null}。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  @Nullable
  public static <T> Constructor<T> getConstructor(final Class<T> cls,
      final Class<?> paramType1, final Class<?> paramType2) {
    return getConstructor(cls, Option.DEFAULT, new Class<?>[] { paramType1, paramType2 });
  }

  /**
   * 获取与给定参数类型匹配的类的构造函数。
   *
   * @param <T>
   *     指定类中对象的类型。
   * @param cls
   *     要获取构造函数的类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。默认值可以是 {@link Option#DEFAULT}。
   * @param paramTypes
   *     要获取的构造函数的参数类型，如果要获取的构造函数没有参数则为空数组。
   * @return
   *     指定的构造函数，如果没有此类构造函数则返回 {@code null}。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  @SuppressWarnings("unchecked")
  @Nullable
  public static <T> Constructor<T> getConstructor(final Class<T> cls,
      final int options, @Nullable final Class<?>[] paramTypes)
      throws ReflectionException {
    final List<Constructor<?>> constructors = CONSTRUCTOR_CACHE.get(cls);
    final Class<?>[] types = defaultIfNull(paramTypes, EMPTY_CLASS_ARRAY);
    Constructor<T> result = null;
    boolean ambiguous = false;
    for (final Constructor<?> c : constructors) {
      final Constructor<T> ctor = (Constructor<T>) c;
      if (Option.satisfy(cls, ctor, options)
          && Equality.equals(types, ctor.getParameterTypes())) {
        if (result == null) {
          result = ctor;
        } else {
          ambiguous = true;
        }
      }
    }
    if (ambiguous) {
      throw new AmbiguousMemberException(cls, result.getName());
    }
    return result;
  }

  /**
   * 获取与兼容参数类型匹配的类的构造函数。
   *
   * @param <T>
   *     指定类中对象的类型。
   * @param cls
   *     要获取构造函数的类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。默认值可以是 {@link Option#DEFAULT}。
   * @param paramTypes
   *     要获取的构造函数的兼容参数的类型，如果要获取的构造函数没有参数则为空数组。
   * @return
   *     指定的构造函数，如果没有此类构造函数则返回 {@code null}。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  @SuppressWarnings("unchecked")
  @Nullable
  public static <T> Constructor<T> getMatchingConstructor(final Class<T> cls,
      final int options, final Class<?>[] paramTypes)
      throws ReflectionException {
    final List<Constructor<?>> constructors = CONSTRUCTOR_CACHE.get(cls);
    final Class<?>[] types = defaultIfNull(paramTypes, EMPTY_CLASS_ARRAY);
    Constructor<T> result = null;
    boolean ambiguous = false;
    for (final Constructor<?> c : constructors) {
      final Constructor<T> ctor = (Constructor<T>) c;
      if (Option.satisfy(cls, ctor, options)
          && ClassUtils.isAssignable(types, ctor.getParameterTypes())) {
        if (result == null) {
          result = ctor;
        } else {
          final int rc = MemberUtils.compareParameterTypes(
              ctor.getParameterTypes(), result.getParameterTypes(), types);
          if (rc < 0) {
            // the parameter types of new constructor is better than the result
            result = ctor;
            ambiguous = false;
          } else if (rc == 0) {
            ambiguous = true;
          }
        }
      }
    }
    if (ambiguous) {
      throw new AmbiguousMemberException(cls, result.getName());
    }
    return result;
  }

  /**
   * 创建指定类的新实例。
   *
   * <p>如何创建此类实例是实现的责任。
   *
   * @param <T>
   *     指定类中对象的类型。
   * @param cls
   *     要实例化的类。
   * @return 类的新实例
   */
  public static <T> T newInstance(final Class<T> cls)
      throws ReflectionException {
    return newInstance(cls, Option.DEFAULT);
  }

  /**
   * 创建指定类的新实例。
   *
   * <p>如何创建此类实例是实现的责任。
   *
   * @param <T>
   *     指定类中对象的类型。
   * @param cls
   *     要实例化的类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。默认值可以是 {@link Option#DEFAULT}。
   * @return 类的新实例
   */
  public static <T> T newInstance(final Class<T> cls, final int options)
      throws ReflectionException {
    requireNonNull("cls", cls);
    final Constructor<T> ctor = getConstructor(cls, options, null);
    if (ctor != null) {
      try {
        ctor.setAccessible(true);
        return ctor.newInstance();
      } catch (final InstantiationException
          | IllegalAccessException
          | InvocationTargetException e) {
        throw new ConstructFailedException(cls, e);
      }
    } else if (((options & Option.SERIALIZATION) != 0)
          && Serializable.class.isAssignableFrom(cls)) {
      return newInstanceUsingSerialization(cls);
    } else {
      return newInstanceWithoutConstructor(cls);
    }
  }

  /**
   * 通过推断参数类型来创建指定类的新实例，从而选择正确的构造函数。
   *
   * <p>这会定位并调用构造函数。构造函数签名必须通过赋值兼容性与参数类型匹配。
   *
   * @param <T>
   *     要构造的类型
   * @param cls
   *     要构造的类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。默认值可以是 {@link Option#DEFAULT}。
   * @param arguments
   *     参数数组。如果构造函数没有参数，可以为 {@code null} 或空。
   * @return {@code cls} 的新实例，永远不会为 {@code null}。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  public static <T> T newInstance(final Class<T> cls, final int options,
      @Nullable final Object... arguments) {
    if (arguments == null) {
      return newInstance(cls, options, null, null);
    } else {
      final Class<?>[] paramTypes = new Class<?>[arguments.length];
      for (int i = 0; i < arguments.length; ++i) {
        paramTypes[i] = arguments[i].getClass();
      }
      return newInstance(cls, options, paramTypes, arguments);
    }
  }

  /**
   * 通过指定的参数类型推断正确的构造函数来创建指定类的新实例。
   *
   * <p>这会定位并调用构造函数。构造函数签名必须通过赋值兼容性与参数类型匹配。
   *
   * @param <T>
   *     要构造的类型
   * @param cls
   *     要构造的类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。默认值可以是 {@link Option#DEFAULT}。
   * @param paramTypes
   *     要调用的构造函数的参数类的数组。如果构造函数没有参数，可以为 {@code null} 或空。
   * @param arguments
   *     参数数组。如果构造函数没有参数，可以为 {@code null} 或空。
   * @return {@code cls} 的新实例，永远不会为 {@code null}。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  public static <T> T newInstance(final Class<T> cls, final int options,
      @Nullable final Class<?>[] paramTypes, @Nullable final Object[] arguments) {
    requireNonNull("cls", cls);
    final Class<?>[] types = defaultIfNull(paramTypes, EMPTY_CLASS_ARRAY);
    final Constructor<T> ctor = getMatchingConstructor(cls, options, types);
    if (ctor != null) {
      try {
        ctor.setAccessible(true);
        return ctor.newInstance(arguments);
      } catch (final IllegalArgumentException
          | InstantiationException
          | IllegalAccessException
          | InvocationTargetException e) {
        throw new ConstructFailedException(cls, e);
      }
    } else {
      throw new ConstructorNotExistException(cls, options, types);
    }
  }

  /**
   * 通过参数的精确类型选择正确的构造函数来创建指定类的新实例。
   *
   * <p>此函数定位并调用构造函数。构造函数签名必须与指定的参数类型完全匹配。
   *
   * @param <T>
   *     要构造的类型
   * @param cls
   *     要构造的类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。默认值可以是 {@link Option#DEFAULT}。
   * @param arguments
   *     参数数组。如果构造函数没有参数，可以为 {@code null} 或空。
   * @return {@code cls} 的新实例，永远不会为 {@code null}。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  public static <T> T newInstanceExactly(final Class<T> cls, final int options,
      @Nullable final Object... arguments) {
    if (arguments == null) {
      return newInstanceExactly(cls, options, null, null);
    } else {
      final Class<?>[] paramTypes = new Class<?>[arguments.length];
      for (int i = 0; i < arguments.length; ++i) {
        paramTypes[i] = arguments[i].getClass();
      }
      return newInstanceExactly(cls, options, paramTypes, arguments);
    }
  }

  /**
   * 通过给定的参数类型选择正确的构造函数来创建指定类的新实例。
   *
   * <p>此函数定位并调用构造函数。构造函数签名必须与指定的参数类型完全匹配。
   *
   * @param <T>
   *     要构造的类型
   * @param cls
   *     要构造的类。
   * @param options
   *     在 {@link Option} 类中定义的反射选项的按位组合。默认值可以是 {@link Option#DEFAULT}。
   * @param paramTypes
   *     要调用的构造函数的参数类的数组。如果构造函数没有参数，可以为 {@code null} 或空。
   * @param arguments
   *     参数数组。如果构造函数没有参数，可以为 {@code null} 或空。
   * @return {@code cls} 的新实例，永远不会为 {@code null}。
   * @throws ReflectionException
   *     如果发生任何错误。
   */
  public static <T> T newInstanceExactly(final Class<T> cls, final int options,
      @Nullable final Class<?>[] paramTypes, @Nullable final Object[] arguments) {
    requireNonNull("cls", cls);
    final Class<?>[] types = defaultIfNull(paramTypes, EMPTY_CLASS_ARRAY);
    final Constructor<T> ctor = getConstructor(cls, options, types);
    if (ctor != null) {
      try {
        ctor.setAccessible(true);
        return ctor.newInstance(arguments);
      } catch (final IllegalArgumentException
              | InstantiationException
              | InvocationTargetException
              | IllegalAccessException e) {
        throw new ConstructFailedException(cls, e);
      }
    } else {
      throw new ConstructorNotExistException(cls, options, types);
    }
  }

  /**
   * 创建指定类的新实例而不调用其构造函数。
   *
   * <p>实现使用 {@code objenesis} 来绕过不同JVM的安全检查。</p>
   *
   * @param <T>
   *     指定类中对象的类型。
   * @param cls
   *     要实例化的类。
   * @return 类的新实例。
   */
  public static <T> T newInstanceWithoutConstructor(final Class<T> cls) {
    try {
      return ObjenesisHelper.newInstance(cls);
    } catch (final Exception e) {
      throw new ConstructFailedException(cls, e);
    }
  }

  /**
   * 使用其构造函数创建指定类的新实例。
   *
   * @param <T>
   *     指定类中对象的类型。
   * @param ctor
   *     要调用的构造函数。
   * @param arguments
   *     参数数组。如果构造函数没有参数，可以为 {@code null} 或空。
   * @return
   *     类的新实例。
   */
  public static <T> T newInstance(final Constructor<T> ctor, final Object ... arguments) {
    try {
      ctor.setAccessible(true);
      return ctor.newInstance(arguments);
    } catch (final IllegalArgumentException
        | InstantiationException
        | IllegalAccessException
        | InvocationTargetException e) {
      throw new ConstructFailedException(ctor.getDeclaringClass(), e);
    }
  }
}