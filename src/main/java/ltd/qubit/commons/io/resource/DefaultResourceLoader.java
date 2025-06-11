////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.resource;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.SystemUtils;

/**
 * {@link ResourceLoader} 接口的默认实现。
 * <p>
 * 由 {@link ResourceEditor} 使用，并作为 
 * {@link org.springframework.context.support.AbstractApplicationContext} 的基类。
 * 也可以独立使用。
 * <p>
 * 如果位置值是 URL，将返回 {@link UrlResource}；如果是非 URL 路径或 "classpath:" 伪 URL，
 * 将返回 {@link ClassPathResource}。
 * <p>
 * 此类是 {@code org.springframework.core.io.DefaultResourceLoader} 的副本，
 * 略有修改。用于避免对 Spring Framework 的依赖。
 *
 * @author 胡海星
 * @see FileSystemResourceLoader
 */
public class DefaultResourceLoader implements ResourceLoader {

  @Nullable
  private ClassLoader classLoader;

  private final Set<ProtocolResolver> protocolResolvers = new LinkedHashSet<>(
      4);

  private final Map<Class<?>, Map<Resource, ?>> resourceCaches = new ConcurrentHashMap<>(
      4);

  /**
   * 创建新的 DefaultResourceLoader。
   * <p>
   * ClassLoader 访问将在实际资源访问时使用线程上下文类加载器（自 5.3 起）。
   * 为了更好的控制，请将特定的 ClassLoader 传递给 {@link #DefaultResourceLoader(ClassLoader)}。
   *
   * @see java.lang.Thread#getContextClassLoader()
   */
  public DefaultResourceLoader() {}

  /**
   * 创建新的 DefaultResourceLoader。
   *
   * @param classLoader
   *     用于加载类路径资源的 ClassLoader，或 {@code null} 表示在实际资源访问时使用线程上下文类加载器
   */
  public DefaultResourceLoader(@Nullable final ClassLoader classLoader) {
    this.classLoader = classLoader;
  }


  /**
   * 指定用于加载类路径资源的 ClassLoader，或 {@code null} 表示在实际资源访问时使用线程上下文类加载器。
   * <p>
   * 默认情况下，ClassLoader 访问将在实际资源访问时使用线程上下文类加载器（自 5.3 起）。
   */
  public void setClassLoader(@Nullable final ClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  /**
   * 返回用于加载类路径资源的 ClassLoader。
   * <p>
   * 将传递给此资源加载器创建的所有 ClassPathResource 对象的 ClassPathResource 构造函数。
   *
   * @see org.springframework.core.io.ClassPathResource
   */
  @Override
  @Nullable
  public ClassLoader getClassLoader() {
    return (classLoader != null ? classLoader : SystemUtils.getDefaultClassLoader());
  }

  /**
   * 向此资源加载器注册给定的解析器，允许处理其他协议。
   * <p>
   * 任何此类解析器都将在此加载器的标准解析规则之前被调用。因此它也可能覆盖任何默认规则。
   *
   * @see #getProtocolResolvers()
   * @since 4.3
   */
  public void addProtocolResolver(final ProtocolResolver resolver) {
    if (resolver == null) {
      throw new IllegalArgumentException("ProtocolResolver must not be null");
    }
    this.protocolResolvers.add(resolver);
  }

  /**
   * 返回当前注册的协议解析器集合，允许内省和修改。
   *
   * @see #addProtocolResolver(ProtocolResolver)
   * @since 4.3
   */
  public Collection<ProtocolResolver> getProtocolResolvers() {
    return this.protocolResolvers;
  }

  /**
   * 获取给定值类型的缓存，以 {@link org.springframework.core.io.Resource} 为键。
   *
   * @param valueType
   *     值类型，例如 ASM {@code MetadataReader}
   * @return 缓存 {@link Map}，在 {@code ResourceLoader} 级别共享
   */
  @SuppressWarnings("unchecked")
  public <T> Map<Resource, T> getResourceCache(final Class<T> valueType) {
    return (Map<Resource, T>) this.resourceCaches.computeIfAbsent(valueType,
        key -> new ConcurrentHashMap<>());
  }

  /**
   * 清除此资源加载器中的所有资源缓存。
   *
   * @see #getResourceCache
   */
  public void clearResourceCaches() {
    this.resourceCaches.clear();
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public Resource getResource(final String location) {
    if (location == null) {
      throw new IllegalArgumentException("Location must not be null");
    }
    for (final ProtocolResolver protocolResolver : getProtocolResolvers()) {
      final Resource resource = protocolResolver.resolve(location, this);
      if (resource != null) {
        return resource;
      }
    }
    if (location.startsWith("/")) {
      return getResourceByPath(location);
    } else if (location.startsWith(CLASSPATH_URL_PREFIX)) {
      return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()), getClassLoader());
    } else {
      try {
        // Try to parse the location as a URL...
        final URL url = ResourceUtils.toURL(location);
        if (ResourceUtils.isFileURL(url)) {
          return new FileUrlResource(url);
        } else {
          return new UrlResource(url);
        }
      } catch (final MalformedURLException ex) {
        // No URL -> resolve as resource path.
        return getResourceByPath(location);
      }
    }
  }

  /**
   * 返回给定路径处资源的 Resource 句柄。
   * <p>默认实现支持类路径位置。这应该适用于独立实现，但可以被覆盖，
   * 例如针对 Servlet 容器的实现。
   *
   * @param path
   *     资源的路径
   * @return 相应的 Resource 句柄
   * @see ClassPathResource
   */
  protected Resource getResourceByPath(final String path) {
    return new ClassPathContextResource(path, getClassLoader());
  }

  /**
   * 通过实现 ContextResource 接口明确表达上下文相对路径的 ClassPathResource。
   */
  protected static class ClassPathContextResource extends ClassPathResource
      implements ContextResource {

    /**
     * 构造一个新的 ClassPathContextResource。
     *
     * @param path 资源路径
     * @param classLoader 类加载器
     */
    public ClassPathContextResource(final String path,
        @Nullable final ClassLoader classLoader) {
      super(path, classLoader);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPathWithinContext() {
      return getPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Resource createRelative(final String relativePath) {
      final String pathToUse = ResourceUtils.applyRelativePath(getPath(), relativePath);
      return new ClassPathContextResource(pathToUse, getClassLoader());
    }
  }

}