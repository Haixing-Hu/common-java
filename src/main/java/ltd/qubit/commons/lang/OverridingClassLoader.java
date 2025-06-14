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
 * {@code ClassLoader} that does <i>not</i> always delegate to the parent loader
 * as normal class loaders do.
 * <p>
 * This enables, for example, instrumentation to be
 * forced in the overriding ClassLoader, or a "throwaway" class loading behavior
 * where selected application classes are temporarily loaded in the overriding
 * {@code ClassLoader} for introspection purposes before eventually loading an
 * instrumented version of the class in the given parent {@code ClassLoader}.
 * <p>
 * This class is a copy of {@code org.springframework.core.OverridingClassLoader}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Haixing Hu
 */
public class OverridingClassLoader extends DecoratingClassLoader {

  /**
   *  Packages that are excluded by default.
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
   * Create a new OverridingClassLoader for the given ClassLoader.
   *
   * @param parent
   *     the ClassLoader to build an overriding ClassLoader for
   */
  public OverridingClassLoader(@Nullable final ClassLoader parent) {
    this(parent, null);
  }

  /**
   * Create a new OverridingClassLoader for the given ClassLoader.
   *
   * @param parent
   *     the ClassLoader to build an overriding ClassLoader for
   * @param overrideDelegate
   *     the ClassLoader to delegate to for overriding
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
   * Determine whether the specified class is eligible for overriding by this
   * class loader.
   *
   * @param className
   *     the class name to check
   * @return whether the specified class is eligible
   * @see #isExcluded
   */
  protected boolean isEligibleForOverriding(final String className) {
    return !isExcluded(className);
  }

  /**
   * Load the specified class for overriding purposes in this ClassLoader.
   * <p>The default implementation delegates to {@link #findLoadedClass},
   * {@link #loadBytesForClass} and {@link #defineClass}.
   *
   * @param name
   *     the name of the class
   * @return the Class object, or {@code null} if no class defined for that name
   * @throws ClassNotFoundException
   *     if the class for the given name couldn't be loaded
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
   * Load the defining bytes for the given class, to be turned into a Class
   * object through a {@link #defineClass} call.
   * <p>
   * The default implementation delegates to {@link #openStreamForClass} and
   * {@link #transformIfNecessary}.
   *
   * @param name
   *     the name of the class
   * @return the byte content (with transformers already applied), or
   *     {@code null} if no class defined for that name
   * @throws ClassNotFoundException
   *     if the class for the given name couldn't be loaded
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
   * Open an InputStream for the specified class.
   * <p>
   * The default implementation loads a standard class file through the parent
   * ClassLoader's {@code getResourceAsStream} method.
   *
   * @param name
   *     the name of the class
   * @return the InputStream containing the byte code for the specified class
   */
  @Nullable
  protected InputStream openStreamForClass(final String name) {
    final String internalName = name.replace('.', '/') + CLASS_FILE_SUFFIX;
    return getParent().getResourceAsStream(internalName);
  }

  /**
   * Transformation hook to be implemented by subclasses.
   * <p>
   * The default implementation simply returns the given bytes as-is.
   *
   * @param name
   *     the fully-qualified name of the class being transformed
   * @param bytes
   *     the raw bytes of the class
   * @return the transformed bytes (never {@code null}; same as the input bytes
   *     if the transformation produced no changes)
   */
  protected byte[] transformIfNecessary(final String name, final byte[] bytes) {
    return bytes;
  }
}