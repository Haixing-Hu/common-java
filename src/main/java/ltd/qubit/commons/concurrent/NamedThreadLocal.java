////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * {@link ThreadLocal} subclass that exposes a specified name as
 * {@link #toString()} result (allowing for introspection).
 * <p>
 * This class is a copy of {@code org.springframework.core.NamedThreadLocal}
 * with slight modifications. It is used to avoid the dependency of Spring
 * Framework.
 *
 * @param <T> the value type
 * @author Juergen Hoeller
 * @author Qimiao Chen
 * @author Haixing Hu
 * @see NamedInheritableThreadLocal
 */
public class NamedThreadLocal<T> extends ThreadLocal<T> {

  private final String name;


  /**
   * Create a new NamedThreadLocal with the given name.
   * @param name a descriptive name for this ThreadLocal
   */
  public NamedThreadLocal(final String name) {
    this.name = Argument.requireNonEmpty("name", name);
  }

  @Override
  public String toString() {
    return this.name;
  }


  /**
   * Create a named thread local variable. The initial value of the variable is
   * determined by invoking the {@code get} method on the {@code Supplier}.
   * @param <S> the type of the named thread local's value
   * @param name a descriptive name for the thread local
   * @param supplier the supplier to be used to determine the initial value
   * @return a new named thread local
   * @since 6.1
   */
  public static <S> ThreadLocal<S> withInitial(final String name, final Supplier<? extends S> supplier) {
    return new SuppliedNamedThreadLocal<>(name, supplier);
  }


  /**
   * An extension of NamedThreadLocal that obtains its initial value from
   * the specified {@code Supplier}.
   * @param <T> the type of the named thread local's value
   */
  private static final class SuppliedNamedThreadLocal<T> extends NamedThreadLocal<T> {

    private final Supplier<? extends T> supplier;

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
