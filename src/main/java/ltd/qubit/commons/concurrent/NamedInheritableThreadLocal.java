////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.concurrent;

import ltd.qubit.commons.lang.Argument;

/**
 * {@link InheritableThreadLocal} subclass that exposes a specified name as
 * {@link #toString()} result (allowing for introspection).
 * <p>
 * This class is a copy of
 * {@code org.springframework.core.NamedInheritableThreadLocal} with slight
 * modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @param <T> the value type
 * @author Juergen Hoeller
 * @author Haixing Hu
 * @see NamedThreadLocal
 */
public class NamedInheritableThreadLocal<T> extends InheritableThreadLocal<T> {

  private final String name;

  /**
   * Create a new NamedInheritableThreadLocal with the given name.
   * @param name a descriptive name for this ThreadLocal
   */
  public NamedInheritableThreadLocal(final String name) {
    this.name = Argument.requireNonEmpty("name", name);;
  }

  @Override
  public String toString() {
    return this.name;
  }

}
