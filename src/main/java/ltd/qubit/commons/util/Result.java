////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util;

/**
 * 一个非常简单的对象，用于封装函数的返回结果和一个表示函数是否成功的标志。
 *
 * <p>注意， {@link java.util.Optional} 具有类似的功能，但它不能表示 {@code null} 的返回值。
 *
 * @param <T>
 *     结果值的类型。
 * @author 胡海星
 */
public class Result<T> {

  public final T value;

  public final boolean success;

  /**
   * 构造一个表示成功的 {@code Result} 对象。
   *
   * @param value
   *     结果值。
   */
  public Result(final T value) {
    this.value = value;
    this.success = true;
  }

  /**
   * 构造一个 {@code Result} 对象。
   *
   * @param value
   *     结果值。
   * @param success
   *     一个标志，指示调用是否成功。
   */
  public Result(final T value, final boolean success) {
    this.value = value;
    this.success = success;
  }

  /**
   * 构造一个 {@code Result} 对象。
   *
   * @param success
   *     一个标志，指示调用是否成功。
   */
  public Result(final boolean success) {
    this.value = null;
    this.success = success;
  }
}