////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * Default implementation of the {@link ResourceLoader} interface.
 * <p>
 * Used by {@link ResourceEditor}, and serves as base class for
 * {@link org.springframework.context.support.AbstractApplicationContext}. Can
 * also be used standalone.
 * <p>
 * Will return a {@link UrlResource} if the location value is a URL, and a
 * {@link ClassPathResource} if it is a non-URL path or a "classpath:"
 * pseudo-URL.
 * <p>
 * This class is a copy of
 * {@code org.springframework.core.io.DefaultResourceLoader} with slight
 * modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Haixing Hu
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
   * Create a new DefaultResourceLoader.
   * <p>
   * ClassLoader access will happen using the thread context class loader at the
   * time of actual resource access (since 5.3). For more control, pass a
   * specific ClassLoader to {@link #DefaultResourceLoader(ClassLoader)}.
   *
   * @see Thread#getContextClassLoader()
   */
  public DefaultResourceLoader() {}

  /**
   * Create a new DefaultResourceLoader.
   *
   * @param classLoader
   *     the ClassLoader to load class path resources with, or {@code null} for
   *     using the thread context class loader at the time of actual resource
   *     access
   */
  public DefaultResourceLoader(@Nullable final ClassLoader classLoader) {
    this.classLoader = classLoader;
  }


  /**
   * Specify the ClassLoader to load class path resources with, or {@code null}
   * for using the thread context class loader at the time of actual resource
   * access.
   * <p>
   * The default is that ClassLoader access will happen using the thread context
   * class loader at the time of actual resource access (since 5.3).
   */
  public void setClassLoader(@Nullable final ClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  /**
   * Return the ClassLoader to load class path resources with.
   * <p>
   * Will get passed to ClassPathResource's constructor for all
   * ClassPathResource objects created by this resource loader.
   *
   * @see org.springframework.core.io.ClassPathResource
   */
  @Override
  @Nullable
  public ClassLoader getClassLoader() {
    return (classLoader != null ? classLoader : SystemUtils.getDefaultClassLoader());
  }

  /**
   * Register the given resolver with this resource loader, allowing for
   * additional protocols to be handled.
   * <p>
   * Any such resolver will be invoked ahead of this loader's standard
   * resolution rules. It may therefore also override any default rules.
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
   * Return the collection of currently registered protocol resolvers, allowing
   * for introspection as well as modification.
   *
   * @see #addProtocolResolver(ProtocolResolver)
   * @since 4.3
   */
  public Collection<ProtocolResolver> getProtocolResolvers() {
    return this.protocolResolvers;
  }

  /**
   * Obtain a cache for the given value type, keyed by
   * {@link org.springframework.core.io.Resource}.
   *
   * @param valueType
   *     the value type, e.g. an ASM {@code MetadataReader}
   * @return the cache {@link Map}, shared at the {@code ResourceLoader} level
   * @since 5.0
   */
  @SuppressWarnings("unchecked")
  public <T> Map<Resource, T> getResourceCache(final Class<T> valueType) {
    return (Map<Resource, T>) this.resourceCaches.computeIfAbsent(valueType,
        key -> new ConcurrentHashMap<>());
  }

  /**
   * Clear all resource caches in this resource loader.
   *
   * @see #getResourceCache
   * @since 5.0
   */
  public void clearResourceCaches() {
    this.resourceCaches.clear();
  }


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
   * Return a Resource handle for the resource at the given path.
   * <p>The default implementation supports class path locations. This should
   * be appropriate for standalone implementations but can be overridden, e.g.
   * for implementations targeted at a Servlet container.
   *
   * @param path
   *     the path to the resource
   * @return the corresponding Resource handle
   * @see ClassPathResource
   */
  protected Resource getResourceByPath(final String path) {
    return new ClassPathContextResource(path, getClassLoader());
  }

  /**
   * ClassPathResource that explicitly expresses a context-relative path through
   * implementing the ContextResource interface.
   */
  protected static class ClassPathContextResource extends ClassPathResource
      implements ContextResource {

    public ClassPathContextResource(final String path,
        @Nullable final ClassLoader classLoader) {
      super(path, classLoader);
    }

    @Override
    public String getPathWithinContext() {
      return getPath();
    }

    @Override
    public Resource createRelative(final String relativePath) {
      final String pathToUse = ResourceUtils.applyRelativePath(getPath(), relativePath);
      return new ClassPathContextResource(pathToUse, getClassLoader());
    }
  }

}
