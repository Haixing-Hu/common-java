////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.error;

/**
 * 当将默认配置资源加载到 CommonsConfig 对象时出现问题时抛出。
 *
 * @author 胡海星
 */
public class ConfigurationError extends Error {

  private static final long serialVersionUID = -8146105276111522899L;

  private static final String DEFAULT_MESSAGE =
      "There is an error in the configuration. ";

  /**
   * 构造一个新的 {@link ConfigurationError}。
   */
  public ConfigurationError() {
    super(DEFAULT_MESSAGE);
  }

  /**
   * 构造一个新的 {@link ConfigurationError}。
   *
   * @param t
   *     异常原因。
   */
  public ConfigurationError(final Throwable t) {
    super(DEFAULT_MESSAGE + t.toString(), t);
  }

  /**
   * 构造一个新的 {@link ConfigurationError}。
   *
   * @param msg
   *     错误消息。
   */
  public ConfigurationError(final String msg) {
    super(msg);
  }

  /**
   * 构造一个新的 {@link ConfigurationError}。
   *
   * @param msg
   *     错误消息。
   * @param t
   *     异常原因。
   */
  public ConfigurationError(final String msg, final Throwable t) {
    super(msg, t);
  }
}