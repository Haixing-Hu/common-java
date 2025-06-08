////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config;

/**
 * 可使用 {@link Config} 进行配置的对象。
 *
 * @author 胡海星
 */
public interface Configurable {

  /**
   * 获取此对象使用的配置。
   *
   * @return 此对象使用的配置。它永远不应返回 null。
   */
  Config getConfig();

  /**
   * 设置此对象要使用的配置。
   *
   * @param config
   *     要设置到此对象的配置。它不能为 null。
   */
  void setConfig(Config config);

}