////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * Provides utility reflection methods focused on constructors, modeled after
 * {@link MethodUtils}.
 *
 * <h2>Known Limitations</h2>
 * <h2>Accessing Public Constructors In A Default Access Superclass</h2>
 *
 * <p>There is an issue when invoking public constructors contained in a
 * default access superclass. Reflection locates these constructors fine and
 * correctly assigns them as public. However, an {@code IllegalAccessException}
 * is thrown if the constructors is invoked.
 *
 * <p>{@code ConstructorUtils} contains a workaround for this situation. It
 * will attempt to call {@code setAccessible} on this constructor. If this call
 * succeeds, then the method can be invoked as normal. This call will only
 * succeed when the application has sufficient security privileges. If this call
 * fails then a warning will be logged and the method may fail.
 *
 * @author Haixing Hu
 * @since 1.0.0
 */
@ThreadSafe
public class ConstructorUtils {

  private ConstructorUtils() {}

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
   * Gets all constructors of a class.
   *
   * @param <T>
   *     the type of the objects in the specified class.
   * @param cls
   *     a class.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @return the array of all specified constructors; or an empty array if no
   *     such constructor.
   * @throws ReflectionException
   *     if any error occurred.
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
   * Creates an instance of a object using the Java serialization.
   *
   * @param cls
   *     The class to instantiate.
   * @return a new instance of the class.
   * @since 1.0.0
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
   * Gets a constructor of a class that matches the given parameter types.
   *
   * @param <T>
   *     the type of the objects in the specified class.
   * @param cls
   *     The class on which to get the constructor.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @param paramTypes
   *     The types of the parameters of the constructor to be get, or an empty
   *     array if the constructor to be get has no parameter.
   * @return the specified constructor, or {@code null} if no such field.
   * @throws ReflectionException
   *     if any error occurred.
   */
  @SuppressWarnings("unchecked")
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
   * Gets a constructor of a class that matches the compatible parameter types.
   *
   * @param <T>
   *     the type of the objects in the specified class.
   * @param cls
   *     The class on which to get the constructor.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @param paramTypes
   *     The types of the compatible parameters of the constructor to be get, or
   *     an empty array if the constructor to be get has no parameter.
   * @return the specified constructor, or {@code null} if no such field.
   * @throws ReflectionException
   *     if any error occurred.
   */
  @SuppressWarnings("unchecked")
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
   * Creates a new instance of the specified class.
   *
   * <p>It is in the responsibility of the implementation how such an instance
   * is created.
   *
   * @param <T>
   *     the type of the objects in the specified class.
   * @param cls
   *     The class to instantiate.
   * @return a new instance of the class
   * @since 1.0.0
   */
  public static <T> T newInstance(final Class<T> cls)
      throws ReflectionException {
    return newInstance(cls, Option.DEFAULT);
  }

  /**
   * Creates a new instance of the specified class.
   *
   * <p>It is in the responsibility of the implementation how such an instance
   * is created.
   *
   * @param <T>
   *     the type of the objects in the specified class.
   * @param cls
   *     The class to instantiate.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @return a new instance of the class
   * @since 1.0.0
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
   * Creates a new instance of the specified class inferring the right
   * constructor from the types of the arguments.
   *
   * <p>This locates and calls a constructor. The constructor signature must
   * match the argument types by assignment compatibility.
   *
   * @param <T>
   *     The type to be constructed
   * @param cls
   *     The class to be constructed.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @param arguments
   *     The array of arguments. If the constructor has no arguments, it could
   *     be {@code null} or empty.
   * @return A new instance of {@code cls}, which will never be {@code null}.
   * @throws ReflectionException
   *     if any error occurred.
   * @since 1.0.0
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
   * Creates a new instance of the specified class inferring the right
   * constructor from the specified parameter types.
   *
   * <p>This locates and calls a constructor. The constructor signature must
   * match the parameter types by assignment compatibility.
   *
   * @param <T>
   *     The type to be constructed
   * @param cls
   *     The class to be constructed.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @param paramTypes
   *     The array of classes of the parameters for the constructor to be
   *     invoked. If the constructor has no arguments, it could be {@code null}
   *     or empty.
   * @param arguments
   *     The array of arguments. If the constructor has no arguments, it could
   *     be {@code null} or empty.
   * @return A new instance of {@code cls}, which will never be {@code null}.
   * @throws ReflectionException
   *     if any error occurred.
   * @since 1.0.0
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
   * Creates a new instance of the specified class choosing the right
   * constructor from the exact types of the arguments.
   *
   * <p>This function locates and calls a constructor. The constructor
   * signature must exactly match the specified argument types.
   *
   * @param <T>
   *     The type to be constructed
   * @param cls
   *     The class to be constructed.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @param arguments
   *     The array of arguments. If the constructor has no arguments, it could
   *     be {@code null} or empty.
   * @return A new instance of {@code cls}, which will never be {@code null}.
   * @throws ReflectionException
   *     if any error occurred.
   * @since 1.0.0
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
   * Creates a new instance of the specified class choosing the right
   * constructor from the given parameter types.
   *
   * <p>This function locates and calls a constructor. The constructor
   * signature must exactly match the specified parameter types.
   *
   * @param <T>
   *     The type to be constructed
   * @param cls
   *     The class to be constructed.
   * @param options
   *     A bitwise combination of reflection options defined in the {@link
   *     Option} class. The default value could be {@link Option#DEFAULT}.
   * @param paramTypes
   *     The array of classes of the parameters for the constructor to be
   *     invoked. If the constructor has no arguments, it could be {@code null}
   *     or empty.
   * @param arguments
   *     The array of arguments. If the constructor has no arguments, it could
   *     be {@code null} or empty.
   * @return A new instance of {@code cls}, which will never be {@code null}.
   * @throws ReflectionException
   *     if any error occurred.
   * @since 1.0.0
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
   * Creates a new instance of the specified class WITHOUT calling its
   * constructor.
   *
   * <p>The implementation use the {@code objenesis} to bypass the security
   * checks of different JVMs.</p>
   *
   * @param <T>
   *     the type of the objects in the specified class.
   * @param cls
   *     The class to instantiate.
   * @return a new instance of the class.
   * @since 4.1.0
   */
  public static <T> T newInstanceWithoutConstructor(final Class<T> cls) {
    try {
      return ObjenesisHelper.newInstance(cls);
    } catch (final Exception e) {
      throw new ConstructFailedException(cls, e);
    }
  }

  /**
   * Creates a new instance of the specified class with its constructor.
   *
   * @param <T>
   *     the type of the objects in the specified class.
   * @param ctor
   *     The constructor to be invoked.
   * @param arguments
   *     The array of arguments. If the constructor has no arguments, it could
   *     be {@code null} or empty.
   * @return
   *     A new instance of the class.
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
