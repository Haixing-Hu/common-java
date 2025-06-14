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
 * Interface to be implemented by any object that wishes to be notified of the
 * {@link ResourceLoader} (typically the ApplicationContext) that it runs in.
 * This is an alternative to a full {@link ApplicationContext} dependency via
 * the {@link org.springframework.context.ApplicationContextAware} interface.
 * <p>
 * Note that {@link Resource} dependencies can also
 * be exposed as bean properties of type {@code Resource} or {@code Resource[]},
 * populated via Strings with automatic type conversion by the bean factory. This
 * removes the need for implementing any callback interface just for the purpose
 * of accessing specific file resources.
 * <p>
 * You typically need a {@link ResourceLoader} when your application object has to
 * access a variety of file resources whose names are calculated. A good strategy is
 * to make the object use a {@link DefaultResourceLoader}
 * but still implement {@code ResourceLoaderAware} to allow for overriding when
 * running in an {@code ApplicationContext}. See
 * {@link ReloadableResourceBundleMessageSource}
 * for an example.
 * <p>
 * A passed-in {@code ResourceLoader} can also be checked for the
 * {@link org.springframework.core.io.support.ResourcePatternResolver} interface
 * and cast accordingly, in order to resolve resource patterns into arrays of
 * {@code Resource} objects. This will always work when running in an ApplicationContext
 * (since the context interface extends the ResourcePatternResolver interface). Use a
 * {@link org.springframework.core.io.support.PathMatchingResourcePatternResolver} as
 * default; see also the {@code ResourcePatternUtils.getResourcePatternResolver} method.
 * <p>
 * As an alternative to a {@code ResourcePatternResolver} dependency, consider
 * exposing bean properties of type {@code Resource[]} array, populated via pattern
 * Strings with automatic type conversion by the bean factory at binding time.
 *
 * @author Juergen Hoeller
 * @author Chris Beams
 * @since 10.03.2004
 * @see Resource
 * @see ResourceLoader
 * @see org.springframework.context.ApplicationContextAware
 * @see org.springframework.core.io.support.ResourcePatternResolver
 */
public interface ResourceLoaderAware {

  /**
   * Set the ResourceLoader that this object runs in.
   * <p>This might be a ResourcePatternResolver, which can be checked
   * through {@code instanceof ResourcePatternResolver}. See also the
   * {@code ResourcePatternUtils.getResourcePatternResolver} method.
   * <p>Invoked after population of normal bean properties but before an init callback
   * like InitializingBean's {@code afterPropertiesSet} or a custom init-method.
   * Invoked before ApplicationContextAware's {@code setApplicationContext}.
   * @param resourceLoader the ResourceLoader object to be used by this object
   * @see org.springframework.core.io.support.ResourcePatternResolver
   * @see org.springframework.core.io.support.ResourcePatternUtils#getResourcePatternResolver
   */
  void setResourceLoader(ResourceLoader resourceLoader);
}