////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nullable;

import ltd.qubit.commons.io.IoUtils;

/**
 * {@code ClassLoader}不像普通类加载器那样<i>总是</i>委托给父加载器。
 * <p>
 * 例如，这使得可以强制在重写的ClassLoader中进行增强，或者在"临时"类加载行为中，
 * 选定的应用程序类被临时加载到重写的{@code ClassLoader}中用于自省目的，
 * 然后最终在给定的父{@code ClassLoader}中加载类的增强版本。
 * <p>
 * 此类是{@code org.springframework.core.OverridingClassLoader}的副本，
 * 经过轻微修改。用于避免对Spring框架的依赖。
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author 胡海星
 */
public class OverridingClassLoader extends DecoratingClassLoader {

  /**
   * 默认排除的包。
   */
  public static final String[] DEFAULT_EXCLUDED_PACKAGES = new String[]
      {"java.", "javax.", "sun.", "oracle.", "javassist.", "org.aspectj.", "net.sf.cglib."};

  private static final String CLASS_FILE_SUFFIX = ".class";

  static {
    ClassLoader.registerAsParallelCapable();
  }

  @Nullable
  private final ClassLoader overrideDelegate;

  /**
   * 为给定的ClassLoader创建一个新的OverridingClassLoader。
   *
   * @param parent
   *     要为其构建重写ClassLoader的ClassLoader
   */
  public OverridingClassLoader(@Nullable final ClassLoader parent) {
    this(parent, null);
  }

  /**
   * 为给定的ClassLoader创建一个新的OverridingClassLoader。
   *
   * @param parent
   *     要为其构建重写ClassLoader的ClassLoader
   * @param overrideDelegate
   *     用于重写委托的ClassLoader
   */
  public OverridingClassLoader(@Nullable final ClassLoader parent,
      @Nullable final ClassLoader overrideDelegate) {
    super(parent);
    this.overrideDelegate = overrideDelegate;
    for (final String packageName : DEFAULT_EXCLUDED_PACKAGES) {
      excludePackage(packageName);
    }
  }

  @Override
  public Class<?> loadClass(final String name) throws ClassNotFoundException {
    if (this.overrideDelegate != null && isEligibleForOverriding(name)) {
      return this.overrideDelegate.loadClass(name);
    }
    return super.loadClass(name);
  }

  @Override
  protected Class<?> loadClass(final String name, final boolean resolve)
      throws ClassNotFoundException {
    if (isEligibleForOverriding(name)) {
      final Class<?> result = loadClassForOverriding(name);
      if (result != null) {
        if (resolve) {
          resolveClass(result);
        }
        return result;
      }
    }
    return super.loadClass(name, resolve);
  }

  /**
   * 确定指定的类是否符合被此类加载器重写的条件。
   *
   * @param className
   *     要检查的类名
   * @return 指定的类是否符合条件
   * @see #isExcluded
   */
  protected boolean isEligibleForOverriding(final String className) {
    return !isExcluded(className);
  }

  /**
   * 在此ClassLoader中加载指定的类以进行重写。
   * <p>默认实现委托给{@link #findLoadedClass}、
   * {@link #loadBytesForClass}和{@link #defineClass}。
   *
   * @param name
   *     类的名称
   * @return Class对象，如果没有为该名称定义类则返回{@code null}
   * @throws ClassNotFoundException
   *     如果无法加载给定名称的类
   */
  @Nullable
  protected Class<?> loadClassForOverriding(final String name)
      throws ClassNotFoundException {
    Class<?> result = findLoadedClass(name);
    if (result == null) {
      final byte[] bytes = loadBytesForClass(name);
      if (bytes != null) {
        result = defineClass(name, bytes, 0, bytes.length);
      }
    }
    return result;
  }

  /**
   * 加载给定类的定义字节，通过{@link #defineClass}调用转换为Class对象。
   * <p>
   * 默认实现委托给{@link #openStreamForClass}和
   * {@link #transformIfNecessary}。
   *
   * @param name
   *     类的名称
   * @return 字节内容（已应用转换器），如果没有为该名称定义类则返回{@code null}
   * @throws ClassNotFoundException
   *     如果无法加载给定名称的类
   */
  @Nullable
  protected byte[] loadBytesForClass(final String name)
      throws ClassNotFoundException {
    final InputStream is = openStreamForClass(name);
    if (is == null) {
      return null;
    }
    try (is) {
      // Load the raw bytes.
      final byte[] bytes = IoUtils.toByteArray(is);
      // Transform if necessary and use the potentially transformed bytes.
      return transformIfNecessary(name, bytes);
    } catch (final IOException ex) {
      throw new ClassNotFoundException("Cannot load resource for class [" + name + "]", ex);
    }
  }

  /**
   * 为指定的类打开InputStream。
   * <p>
   * 默认实现通过父ClassLoader的{@code getResourceAsStream}方法
   * 加载标准类文件。
   *
   * @param name
   *     类的名称
   * @return 包含指定类字节码的InputStream
   */
  @Nullable
  protected InputStream openStreamForClass(final String name) {
    final String internalName = name.replace('.', '/') + CLASS_FILE_SUFFIX;
    return getParent().getResourceAsStream(internalName);
  }

  /**
   * 由子类实现的转换钩子。
   * <p>
   * 默认实现简单地按原样返回给定的字节。
   *
   * @param name
   *     被转换类的完全限定名称
   * @param bytes
   *     类的原始字节
   * @return 转换后的字节（永远不会是{@code null}；如果转换没有产生任何更改，
   *     则与输入字节相同）
   */
  protected byte[] transformIfNecessary(final String name, final byte[] bytes) {
    return bytes;
  }
}