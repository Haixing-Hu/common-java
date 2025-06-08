////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.concurrent;

import java.util.Objects;
import java.util.function.Supplier;

import ltd.qubit.commons.lang.Argument;

/**
 * {@link ThreadLocal} 的子类，它将指定的名称作为 {@link #toString()} 的结果公开（以允许内省）。
 * <p>
 * 此类是 {@code org.springframework.core.NamedThreadLocal} 的副本，略有修改。
 * 它用于避免对 Spring Framework 的依赖。
 *
 * @param <T>
 *     值的类型
 * @author Juergen Hoeller
 * @author Qimiao Chen
 * @author 胡海星
 * @see NamedInheritableThreadLocal
 */
public class NamedThreadLocal<T> extends ThreadLocal<T> {

  private final String name;


  /**
   * 使用指定的名称构造一个新的 {@link NamedThreadLocal}。
   *
   * @param name
   *     此线程局部变量的描述性名称。
   */
  public NamedThreadLocal(final String name) {
    this.name = Argument.requireNonEmpty("name", name);
  }

  @Override
  public String toString() {
    return this.name;
  }


  /**
   * 创建一个命名的线程局部变量。变量的初始值是通过调用{@code Supplier}上的{@code get}方法来确定的。
   *
   * @param <S>
   *     命名的线程局部变量的值的类型
   * @param name
   *     线程局部变量的描述性名称
   * @param supplier
   *     用于确定初始值的 supplier
   * @return 一个新的命名线程局部变量
   * @since 6.1
   */
  public static <S> ThreadLocal<S> withInitial(final String name, final Supplier<? extends S> supplier) {
    return new SuppliedNamedThreadLocal<>(name, supplier);
  }


  /**
   * {@link NamedThreadLocal}的扩展，它从指定的{@code Supplier}获取其初始值。
   *
   * @param <T>
   *     命名的线程局部变量的值的类型
   */
  private static final class SuppliedNamedThreadLocal<T> extends NamedThreadLocal<T> {

    private final Supplier<? extends T> supplier;

    /**
     * 构造一个 {@link SuppliedNamedThreadLocal}。
     *
     * @param name
     *     此线程局部变量的描述性名称。
     * @param supplier
     *     用于确定初始值的 supplier。
     */
    SuppliedNamedThreadLocal(final String name, final Supplier<? extends T> supplier) {
      super(name);
      this.supplier = Objects.requireNonNull(supplier);
    }

    @Override
    protected T initialValue() {
      return this.supplier.get();
    }
  }
}