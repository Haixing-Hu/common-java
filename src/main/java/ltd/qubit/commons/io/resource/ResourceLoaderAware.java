////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.resource;

import ltd.qubit.commons.i18n.message.ReloadableResourceBundleMessageSource;

/**
 * 任何希望被通知其运行所在的 {@link ResourceLoader}（通常是 ApplicationContext）
 * 的对象要实现的接口。这是通过
 * {@link org.springframework.context.ApplicationContextAware} 接口实现完整
 * {@link org.springframework.context.ApplicationContext} 依赖的替代方案。
 * <p>
 * 请注意，{@link Resource} 依赖也可以作为 {@code Resource} 或
 * {@code Resource[]} 类型的 bean 属性公开，通过字符串由 bean 工厂进行自动类型转换
 * 来填充。这消除了仅仅为了访问特定文件资源而实现任何回调接口的需要。
 * <p>
 * 当您的应用程序对象必须访问其名称是计算得出的各种文件资源时，您通常需要一个
 * {@link ResourceLoader}。一个好的策略是让对象使用
 * {@link DefaultResourceLoader}，但仍然实现 {@code ResourceLoaderAware} 以便在
 * {@code ApplicationContext} 中运行时允许覆盖。有关示例，请参阅
 * {@link ReloadableResourceBundleMessageSource}。
 * <p>
 * 传入的 {@code ResourceLoader} 也可以检查是否为
 * {@link org.springframework.core.io.support.ResourcePatternResolver} 接口，
 * 并相应地进行转换，以便将资源模式解析为 {@code Resource} 对象数组。在
 * ApplicationContext 中运行时，这将始终有效（因为上下文接口扩展了
 * ResourcePatternResolver 接口）。使用
 * {@link org.springframework.core.io.support.PathMatchingResourcePatternResolver}
 * 作为默认值；另请参阅
 * {@code ResourcePatternUtils.getResourcePatternResolver} 方法。
 * <p>
 * 作为对 {@code ResourcePatternResolver} 依赖的替代方案，考虑公开
 * {@code Resource[]} 数组类型的 bean 属性，在绑定时由 bean 工厂通过模式字符串
 * 进行自动类型转换来填充。
 *
 * @author 胡海星
 * @see Resource
 * @see ResourceLoader
 * @see org.springframework.context.ApplicationContextAware
 * @see org.springframework.core.io.support.ResourcePatternResolver
 */
public interface ResourceLoaderAware {

  /**
   * 设置此对象运行所在的 ResourceLoader。
   * <p>
   * 这可能是一个 ResourcePatternResolver，可以通过
   * {@code instanceof ResourcePatternResolver} 进行检查。另请参阅
   * {@code ResourcePatternUtils.getResourcePatternResolver} 方法。
   * <p>
   * 在填充普通 bean 属性之后，但在初始化回调（如 InitializingBean 的
   * {@code afterPropertiesSet} 或自定义初始化方法）之前调用。在
   * ApplicationContextAware 的 {@code setApplicationContext} 之前调用。
   *
   * @param resourceLoader
   *     此对象要使用的 ResourceLoader 对象。
   * @see org.springframework.core.io.support.ResourcePatternResolver
   * @see org.springframework.core.io.support.ResourcePatternUtils#getResourcePatternResolver
   */
  void setResourceLoader(ResourceLoader resourceLoader);
}