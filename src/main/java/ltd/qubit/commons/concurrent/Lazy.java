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

  private final Supplier<T> supplier;

  private volatile boolean initialized;

  private volatile T value;

  private Lazy(final Supplier<T> supplier) {
    this.supplier = supplier;
    this.initialized = false;
    this.value = null;
  }

  /**
   * Creates a new lazy initialized value.
   *
   * @param <T>
   *     the type of the value.
   * @param supplier
   *     the supplier of the value.
   * @return
   *     the created lazy initialized value.
   */
  public static <T> Lazy<T> of(final Supplier<T> supplier) {
    if (supplier == null) {
      throw new IllegalArgumentException("The supplier is null.");
    }
    return new Lazy<>(supplier);
  }

  /**
   * Gets the lazy initialized value.
   *
   * @return
   *     the lazy initialized value.
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
   * Refreshes the lazy initialized value.
   * <p>
   * If the value has not been initialized, this method does nothing.
   * Otherwise, this method will re-evaluate the supplier to get the refreshed value.
   */
  public void refresh() {
    if (initialized) {
      synchronized (this) {
        value = supplier.get();
      }
    }
  }
}