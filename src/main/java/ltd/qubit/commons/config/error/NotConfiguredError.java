////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.error;

import ltd.qubit.commons.config.Configurable;

/**
 * 抛出以表示{@link Configurable}未配置。
 *
 * @author 胡海星
 */
public class NotConfiguredError extends ConfigurationError {

  private static final long serialVersionUID = - 6212497530366587501L;

  private static final String DEFAULT_MESSAGE =
      "The configurable object is not configured. ";

  /**
   * 构造一个新的 {@link NotConfiguredError}。
   */
  public NotConfiguredError() {
    super(DEFAULT_MESSAGE);
  }

  /**
   * 构造一个新的 {@link NotConfiguredError}。
   *
   * @param t
   *     异常原因。
   */
  public NotConfiguredError(final Throwable t) {
    super(DEFAULT_MESSAGE + t.toString(), t);
  }

  /**
   * 构造一个新的 {@link NotConfiguredError}。
   *
   * @param msg
   *     错误消息。
   */
  public NotConfiguredError(final String msg) {
    super(msg);
  }

  /**
   * 构造一个新的 {@link NotConfiguredError}。
   *
   * @param msg
   *     错误消息。
   * @param t
   *     异常原因。
   */
  public NotConfiguredError(final String msg, final Throwable t) {
    super(msg, t);
  }
}