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
 * 用于加载资源（例如类路径或文件系统资源）的策略接口。
 * {@link org.springframework.context.ApplicationContext} 需要提供此功能以及扩展的
 * {@link org.springframework.core.io.support.ResourcePatternResolver} 支持。
 * <p>
 * {@link DefaultResourceLoader} 是一个独立的实现，可在 ApplicationContext
 * 之外使用，并且也被 {@link ResourceEditor} 使用。
 * <p>
 * 在 ApplicationContext 中运行时，可以使用特定上下文的资源加载策略，从字符串填充
 * {@code Resource} 和 {@code Resource[]} 类型的 Bean 属性。
 * <p>
 * 此类是 {@code org.springframework.core.io.ResourceLoader} 的副本，经过少量修改。
 * 它用于避免对 Spring Framework 的依赖。
 *
 * @author 胡海星
 * @see Resource
 * @see org.springframework.core.io.support.ResourcePatternResolver
 */
public interface ResourceLoader {

  /**
   * 用于从类路径加载的伪 URL 前缀："classpath:"。
   */
  String CLASSPATH_URL_PREFIX = ResourceUtils.CLASSPATH_URL_PREFIX;

  /**
   * 返回指定资源位置的 {@code Resource} 句柄。
   * <p>
   * 该句柄应始终是可重用的资源描述符，允许多次调用 {@link Resource#getInputStream()}。
   * <ul>
   * <li>必须支持完全限定的 URL，例如 "file:C:/test.dat"。
   * <li>必须支持类路径伪 URL，例如 "classpath:test.dat"。
   * <li>应支持相对文件路径，例如 "WEB-INF/test.dat"。（这将是特定于实现的，通常由
   * ApplicationContext 实现提供。）
   * </ul>
   * <p>
   * 请注意，{@code Resource} 句柄并不意味着资源存在；您需要调用
   * {@link Resource#exists()} 来检查是否存在。
   *
   * @param location
   *     资源位置。
   * @return 相应的 {@code Resource} 句柄（永不为 {@code null}）。
   * @see #CLASSPATH_URL_PREFIX
   * @see Resource#exists()
   * @see Resource#getInputStream()
   */
  Resource getResource(String location);

  /**
   * 暴露此 {@code ResourceLoader} 使用的 {@link ClassLoader}。
   * <p>
   * 需要直接访问 {@code ClassLoader} 的客户端可以使用 {@code ResourceLoader}
   * 以统一的方式进行，而不是依赖于线程上下文 {@code ClassLoader}。
   *
   * @return
   *     {@code ClassLoader}（仅当系统 {@code ClassLoader} 也无法访问时才为
   *     {@code null}）。
   */
  @Nullable
  ClassLoader getClassLoader();
}