////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons;

import ltd.qubit.commons.concurrent.Lazy;
import ltd.qubit.commons.config.Config;

import static ltd.qubit.commons.config.ConfigUtils.loadXmlConfig;

/**
 * 提供获取通用模块配置的功能，并定义属性的名称和默认值。
 *
 * @author Haixing Hu
 */
public final class CommonsConfig {

  /**
   * 通用模块配置的 XML 资源的系统属性名称。
   */
  public static final String PROPERTY_RESOURCE = "CommonsConfig";

  /**
   * 通用模块配置的 XML 资源的默认名称。
   */
  public static final String DEFAULT_RESOURCE = "common-java.xml";

  /**
   * 静态的 {@link Config} 对象的懒加载实例。
   */
  private static final Lazy<Config> lazyConfig = Lazy.of(() ->
      loadXmlConfig(PROPERTY_RESOURCE, DEFAULT_RESOURCE, CommonsConfig.class));

  /**
   * 获取通用模块的配置。
   *
   * <p>该函数将首先尝试在系统属性中搜索配置的 XML 资源名称，如果没有找到这样的系统属性，
   * 它将使用默认的 XML 资源。然后它将尝试从 XML 文件加载配置，如果成功则返回配置，
   * 如果失败则返回空配置。
   *
   * @return 通用模块的配置，如果失败则返回空配置。
   */
  public static Config get() {
    return lazyConfig.get();
  }
}