////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

/**
 * Base class for decorating ClassLoaders such as {@link OverridingClassLoader}
 * and {@link ShadowingClassLoader}, providing common handling of excluded
 * packages and classes.
 * <p>
 * This class is a copy of {@code org.springframework.core.DecoratingClassLoader}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Rod Johnson
 * @author Haixing Hu
 */
public abstract class DecoratingClassLoader extends ClassLoader {

  static {
    ClassLoader.registerAsParallelCapable();
  }

  private final Set<String> excludedPackages =
      Collections.newSetFromMap(new ConcurrentHashMap<>(8));

  private final Set<String> excludedClasses =
      Collections.newSetFromMap(new ConcurrentHashMap<>(8));

  /**
   * Create a new DecoratingClassLoader with no parent ClassLoader.
   */
  public DecoratingClassLoader() {}

  /**
   * Create a new DecoratingClassLoader using the given parent ClassLoader for
   * delegation.
   */
  public DecoratingClassLoader(@Nullable final ClassLoader parent) {
    super(parent);
  }

  /**
   * Add a package name to exclude from decoration (e.g. overriding).
   * <p>Any class whose fully-qualified name starts with the name registered
   * here will be handled by the parent ClassLoader in the usual fashion.
   *
   * @param packageName
   *     the package name to exclude
   */
  public void excludePackage(final String packageName) {
    if (packageName == null) {
      throw new IllegalArgumentException("Package name must not be null");
    }
    this.excludedPackages.add(packageName);
  }

  /**
   * Add a class name to exclude from decoration (e.g. overriding).
   * <p>Any class name registered here will be handled by the parent
   * ClassLoader in the usual fashion.
   *
   * @param className
   *     the class name to exclude
   */
  public void excludeClass(final String className) {
    if (className == null) {
      throw new IllegalArgumentException("Class name must not be null");
    }
    this.excludedClasses.add(className);
  }

  /**
   * Determine whether the specified class is excluded from decoration by this
   * class loader.
   * <p>
   * The default implementation checks against excluded packages and classes.
   *
   * @param className
   *     the class name to check
   * @return whether the specified class is eligible
   * @see #excludePackage
   * @see #excludeClass
   */
  protected boolean isExcluded(final String className) {
    if (this.excludedClasses.contains(className)) {
      return true;
    }
    for (final String packageName : this.excludedPackages) {
      if (className.startsWith(packageName)) {
        return true;
      }
    }
    return false;
  }
}
