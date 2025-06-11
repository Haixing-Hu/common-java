////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.resource;

import javax.annotation.Nullable;

/**
 * 特定协议资源句柄的解析策略。
 * <p>
 * 用作 {@link DefaultResourceLoader} 的 SPI，允许处理自定义协议，而无需子类化
 * 加载器实现（或应用程序上下文实现）。
 * <p>
 * 此类是 {@code org.springframework.core.io.ProtocolResolver} 的副本，经过少量修改。
 * 它用于避免对 Spring Framework 的依赖。
 *
 * @author 胡海星
 * @see DefaultResourceLoader#addProtocolResolver
 */
@FunctionalInterface
public interface ProtocolResolver {

  /**
   * 如果此实现的协议匹配，则根据给定的资源加载器解析给定的位置。
   *
   * @param location
   *     用户指定的资源位置。
   * @param resourceLoader
   *     关联的资源加载器。
   * @return
   *     如果给定位置与此解析器的协议匹配，则为相应的 {@code Resource} 句柄，
   *     否则为 {@code null}。
   */
  @Nullable
  Resource resolve(String location, ResourceLoader resourceLoader);
}