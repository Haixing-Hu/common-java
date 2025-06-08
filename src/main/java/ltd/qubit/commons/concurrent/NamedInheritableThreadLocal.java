////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.concurrent;

import ltd.qubit.commons.lang.Argument;

/**
 * {@link InheritableThreadLocal} 的子类，它将指定的名称作为 {@link #toString()} 的结果公开
 * （以允许内省）。
 * <p>
 * 此类是 {@code org.springframework.core.NamedInheritableThreadLocal} 的副本，
 * 略有修改。它用于避免对 Spring Framework 的依赖。
 *
 * @param <T>
 *     值的类型
 * @author Juergen Hoeller
 * @author 胡海星
 * @see NamedThreadLocal
 */
public class NamedInheritableThreadLocal<T> extends InheritableThreadLocal<T> {

  private final String name;

  /**
   * 使用指定的名称构造一个新的 {@link NamedInheritableThreadLocal}。
   *
   * @param name
   *     此线程局部变量的描述性名称。
   */
  public NamedInheritableThreadLocal(final String name) {
    this.name = Argument.requireNonEmpty("name", name);;
  }

  @Override
  public String toString() {
    return this.name;
  }

}