////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.concurrent;
import java.util.function.Supplier;

/**
 * A lazy initialized value.
 *
 * @param <T>
 *      the type of the value.
 * @author Haixing Hu
 */
public class Lazy<T> {

  private volatile Supplier<T> supplier;

  private volatile T value;

  private Lazy(final Supplier<T> supplier) {
    this.supplier = supplier;
  }

  public static <T> Lazy<T> of(final Supplier<T> supplier) {
    if (supplier == null) {
      throw new IllegalArgumentException("The supplier is null.");
    }
    return new Lazy<>(supplier);
  }

  public T get() {
    // use double-checked locking trick to ensure the value is initialized only once.
    if (supplier != null) {
      synchronized (this) {
        if (supplier != null) {
          value = supplier.get();
          supplier = null; // Release the supplier
        }
      }
    }
    return value;
  }
}
