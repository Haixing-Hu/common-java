////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.concurrent;

import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nullable;

/**
 * 一个基于 double-checked-locking 设计模式实现的懒加载对象。
 *
 * @param <T>
 *      待加载对象的类型。
 * @author 胡海星
 */
public class Lazy<T> {
  /**
   * 懒加载对象的创建器。
   */
  private final Supplier<T> supplier;

  /**
   * 懒加载对象的资源释放器。
   */
  @Nullable
  private final Consumer<T> releaser;

  /**
   * 标记懒加载对象是否已经被初始化。
   */
  private volatile boolean initialized;

  /**
   * 懒加载对象的引用。
   */
  @Nullable
  private volatile T value;

  /**
   * 懒加载对象的构造器。
   *
   * @param supplier
   *     懒加载对象的创建器。
   * @param releaser
   *     懒加载对象的资源释放器。
   */
  private Lazy(final Supplier<T> supplier, @Nullable final Consumer<T> releaser) {
    this.supplier = supplier;
    this.releaser = releaser;
    this.initialized = false;
    this.value = null;
  }

  /**
   * 创建一个新的懒加载对象的工厂方法。
   *
   * @param <T>
   *     懒加载对象的类型。
   * @param supplier
   *     懒加载对象的创建器。
   * @return
   *     新创建的懒加载对象。
   */
  public static <T> Lazy<T> of(final Supplier<T> supplier) {
    if (supplier == null) {
      throw new IllegalArgumentException("The supplier is null.");
    }
    return new Lazy<>(supplier, null);
  }

  /**
   * 创建一个新的懒加载对象的工厂方法。
   *
   * @param <T>
   *     懒加载对象的类型。
   * @param supplier
   *     懒加载对象的创建器。
   * @param releaser
   *     懒加载对象的资源释放器。
   * @return
   *     新创建的懒加载对象。
   */
  public static <T> Lazy<T> of(final Supplier<T> supplier, final Consumer<T> releaser) {
    if (supplier == null) {
      throw new IllegalArgumentException("The supplier is null.");
    }
    if (releaser == null) {
      throw new IllegalArgumentException("The releaser is null.");
    }
    return new Lazy<>(supplier, releaser);
  }

  /**
   * 获取懒加载对象的实例。
   *
   * @return
   *     懒加载对象的实例，注意有可能是{@code null}，若其创建器返回{@code null}。
   */
  public T get() {
    // use double-checked locking trick to ensure the value is initialized only once.
    if (! initialized) {
      synchronized (this) {
        if (! initialized) {
          value = supplier.get();
          initialized = true;
        }
      }
    }
    return value;
  }

  /**
   * 设置懒加载对象的实例。
   *
   * @param value
   *     懒加载对象的实例，允许为{@code null}。
   */
  public void set(@Nullable final T value) {
    synchronized (this) {
      this.value = value;
      this.initialized = true;
    }
  }

  /**
   * 刷新懒加载对象的实例。
   * <p>
   * 如果懒加载对象的实例已经被初始化，则重新评估创建器以获取新的值。
   * <p>
   * 如果值尚未初始化，则此方法不会执行任何操作。
   */
  public void refresh() {
    if (initialized) {
      synchronized (this) {
        if (initialized) {
          value = supplier.get();
        }
      }
    }
  }

  /**
   * 释放懒加载对象的实例。
   * <p>
   * 如果懒加载对象的实例尚未被初始化，则此方法不会执行任何操作。
   * <p>
   * 如果懒加载对象的实例已经被初始化，则调用资源释放器来释放资源。
   */
  public void release() {
    if (initialized) {
      synchronized (this) {
        if (initialized) {
          if (releaser != null && value != null) {
            releaser.accept(value);
          }
          value = null;
          initialized = false;
        }
      }
    }
  }
}