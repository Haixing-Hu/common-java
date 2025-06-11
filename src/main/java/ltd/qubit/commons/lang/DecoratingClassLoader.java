////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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
 * 用于装饰 ClassLoader 的基类，如 {@link OverridingClassLoader} 和 {@link ShadowingClassLoader}，
 * 提供对排除包和类的通用处理。
 * <p>
 * 此类是 {@code org.springframework.core.DecoratingClassLoader} 的副本，略有修改。
 * 用于避免对 Spring Framework 的依赖。
 *
 * @author Juergen Hoeller
 * @author Rod Johnson
 * @author 胡海星
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
   * 创建一个没有父 ClassLoader 的新 DecoratingClassLoader。
   */
  public DecoratingClassLoader() {}

  /**
   * 使用给定的父 ClassLoader 创建一个新的 DecoratingClassLoader 进行委托。
   */
  public DecoratingClassLoader(@Nullable final ClassLoader parent) {
    super(parent);
  }

  /**
   * 添加要从装饰（例如覆盖）中排除的包名。
   * <p>任何完全限定名以此处注册的名称开头的类都将以通常的方式由父 ClassLoader 处理。
   *
   * @param packageName
   *     要排除的包名
   */
  public void excludePackage(final String packageName) {
    if (packageName == null) {
      throw new IllegalArgumentException("Package name must not be null");
    }
    this.excludedPackages.add(packageName);
  }

  /**
   * 添加要从装饰（例如覆盖）中排除的类名。
   * <p>此处注册的任何类名都将以通常的方式由父 ClassLoader 处理。
   *
   * @param className
   *     要排除的类名
   */
  public void excludeClass(final String className) {
    if (className == null) {
      throw new IllegalArgumentException("Class name must not be null");
    }
    this.excludedClasses.add(className);
  }

  /**
   * 确定指定的类是否被此类加载器排除在装饰之外。
   * <p>
   * 默认实现检查排除的包和类。
   *
   * @param className
   *     要检查的类名
   * @return 指定的类是否符合条件
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