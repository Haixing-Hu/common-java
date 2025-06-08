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
 * Strategy interface for loading resources (e.g., class path or file system
 * resources). An {@link org.springframework.context.ApplicationContext}
 * is required to provide this functionality plus extended
 * {@link org.springframework.core.io.support.ResourcePatternResolver} support.
 * <p>
 * {@link DefaultResourceLoader} is a standalone implementation that is
 * usable outside an ApplicationContext and is also used by {@link ResourceEditor}.
 * <p>
 * Bean properties of type {@code Resource} and {@code Resource[]} can be populated
 * from Strings when running in an ApplicationContext, using the particular
 * context's resource loading strategy.
 * <p>
 * This class is a copy of {@code org.springframework.core.io.ResourceLoader}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Haixing Hu
 * @see Resource
 * @see org.springframework.core.io.support.ResourcePatternResolver
 */
public interface ResourceLoader {

  /**
   * Pseudo URL prefix for loading from the class path: "classpath:".
   */
  String CLASSPATH_URL_PREFIX = ResourceUtils.CLASSPATH_URL_PREFIX;

  /**
   * Return a {@code Resource} handle for the specified resource location.
   * <p>
   * The handle should always be a reusable resource descriptor, allowing for
   * multiple {@link Resource#getInputStream()} calls.
   * <p>
   * <ul>
   * <li>Must support fully qualified URLs, e.g. "file:C:/test.dat".
   * <li>Must support classpath pseudo-URLs, e.g. "classpath:test.dat".
   * <li>Should support relative file paths, e.g. "WEB-INF/test.dat".
   * (This will be implementation-specific, typically provided by an
   * ApplicationContext implementation.)
   * </ul>
   * <p>
   * Note that a {@code Resource} handle does not imply an existing resource;
   * you need to invoke {@link org.springframework.core.io.Resource#exists} to
   * check for existence.
   *
   * @param location
   *     the resource location
   * @return a corresponding {@code Resource} handle (never {@code null})
   * @see #CLASSPATH_URL_PREFIX
   * @see Resource#exists()
   * @see Resource#getInputStream()
   */
  Resource getResource(String location);

  /**
   * Expose the {@link ClassLoader} used by this {@code ResourceLoader}.
   * <p>
   * Clients which need to access the {@code ClassLoader} directly can do so in
   * a uniform manner with the {@code ResourceLoader}, rather than relying on
   * the thread context {@code ClassLoader}.
   *
   * @return the {@code ClassLoader} (only {@code null} if even the system
   *     {@code ClassLoader} isn't accessible)
   */
  @Nullable
  ClassLoader getClassLoader();
}