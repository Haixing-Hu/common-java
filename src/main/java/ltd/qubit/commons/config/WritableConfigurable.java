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
 * 可以使用 {@link WritableConfig} 进行配置的组件。
 *
 * @author 胡海星
 */
public interface WritableConfigurable {

  /**
   * 获取此对象使用的配置。
   *
   * @return 此对象使用的配置。它永远不应返回 null。
   */
  WritableConfig getConfig();

  /**
   * 设置要由此对象使用的配置。
   *
   * @param config
   *     要设置到此对象的配置。它不能为 null。
   * @return
   *     此对象的当前实例。
   */
  WritableConfigurable setConfig(WritableConfig config);

}