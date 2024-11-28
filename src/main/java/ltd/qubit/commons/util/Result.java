////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

/**
 * A very simple object encapsulates the return result of a function and a flag
 * indicates whether the function succeed or not.
 *
 * <p>Note that {@link java.util.Optional} has the similar function, but it
 * cannot represent a {@code null} return value.
 *
 * @author Haixing Hu
 */
public class Result<T> {

  public final T value;

  public final boolean success;

  public Result(final T value) {
    this.value = value;
    this.success = true;
  }

  public Result(final T value, final boolean success) {
    this.value = value;
    this.success = success;
  }

  public Result(final boolean success) {
    this.value = null;
    this.success = success;
  }
}
