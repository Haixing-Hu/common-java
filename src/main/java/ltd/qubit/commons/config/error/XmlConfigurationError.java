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
 * 抛出以表示XML配置中存在错误。
 *
 * @author 胡海星
 */
public class XmlConfigurationError extends ConfigurationError {

  private static final long serialVersionUID = -1685548290362488987L;

  /**
   * 构造一个 {@link XmlConfigurationError}。
   *
   * @param resource
   *     发生错误的XML配置资源的名称。
   * @param cause
   *     错误的根本原因。
   */
  public XmlConfigurationError(final String resource, final Throwable cause) {
    super("An error occurs while loading the XML configuration "
        + resource + ": " + cause.toString());
  }

}