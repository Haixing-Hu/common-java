////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.SystemUtils;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.io.resource.ResourceUtils.applyRelativePath;
import static ltd.qubit.commons.io.resource.ResourceUtils.classPackageAsResourcePath;
import static ltd.qubit.commons.io.resource.ResourceUtils.cleanPath;

/**
 * {@link Resource} implementation for class path resources. Uses either a given
 * {@link ClassLoader} or a given {@link Class} for loading resources.
 * <p>
 * Supports resolution as {@code java.io.File} if the class path resource
 * resides in the file system, but not for resources in a JAR. Always supports
 * resolution as {@code java.net.URL}.
 * <p>
 * This class is a copy of {@code org.springframework.core.io.ClassPathResource}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Haixing Hu
 * @see ClassLoader#getResourceAsStream(String)
 * @see ClassLoader#getResource(String)
 * @see Class#getResourceAsStream(String)
 * @see Class#getResource(String)
 */
public class ClassPathResource extends AbstractFileResolvingResource {

  /**
   * Internal representation of the original path supplied by the user, used for
   * creating relative paths and resolving URLs and InputStreams.
   */
  private final String path;

  private final String absolutePath;

  @Nullable
  private final ClassLoader classLoader;

  @Nullable
  private final Class<?> clazz;

  /**
   * Create a new {@code ClassPathResource} for {@code ClassLoader} usage.
   * <p>
   * A leading slash will be removed, as the {@code ClassLoader} resource
   * access methods will not accept it.
   * <p>
   * The default class loader will be used for loading the resource.
   *
   * @param path
   *     the absolute path within the class path
   * @see SystemUtils#getDefaultClassLoader()
   */
  public ClassPathResource(final String path) {
    this(path, (ClassLoader) null);
  }

  /**
   * Create a new {@code ClassPathResource} for {@code ClassLoader} usage.
   * <p>
   * A leading slash will be removed, as the {@code ClassLoader} resource
   * access methods will not accept it.
   * <p>
   * If the supplied {@code ClassLoader} is {@code null}, the default class
   * loader will be used for loading the resource.
   *
   * @param path
   *     the absolute path within the class path
   * @param classLoader
   *     the class loader to load the resource with
   * @see SystemUtils#getDefaultClassLoader()
   */
  public ClassPathResource(final String path, @Nullable final ClassLoader classLoader) {
    if (path == null) {
      throw new IllegalArgumentException("Path must not be null");
    }
    String pathToUse = cleanPath(path);
    if (pathToUse.startsWith("/")) {
      pathToUse = pathToUse.substring(1);
    }
    this.path = pathToUse;
    this.absolutePath = pathToUse;
    this.classLoader = (classLoader != null ? classLoader : SystemUtils.getDefaultClassLoader());
    this.clazz = null;
  }

  /**
   * Create a new {@code ClassPathResource} for {@code Class} usage.
   * <p>The path can be relative to the given class, or absolute within
   * the class path via a leading slash.
   * <p>If the supplied {@code Class} is {@code null}, the default class
   * loader will be used for loading the resource.
   * <p>This is also useful for resource access within the module system,
   * loading a resource from the containing module of a given {@code Class}. See
   * {@link ModuleResource} and its javadoc.
   *
   * @param path
   *     relative or absolute path within the class path
   * @param clazz
   *     the class to load resources with
   * @see SystemUtils#getDefaultClassLoader()
   * @see ModuleResource
   */
  public ClassPathResource(final String path, @Nullable final Class<?> clazz) {
    if (path == null) {
      throw new IllegalArgumentException("Path must not be null");
    }
    this.path = cleanPath(path);
    String theAbsolutePath = this.path;
    if (clazz != null && !theAbsolutePath.startsWith("/")) {
      theAbsolutePath = classPackageAsResourcePath(clazz)
          + "/" + theAbsolutePath;
    } else if (theAbsolutePath.startsWith("/")) {
      theAbsolutePath = theAbsolutePath.substring(1);
    }
    this.absolutePath = theAbsolutePath;

    this.classLoader = null;
    this.clazz = clazz;
  }


  /**
   * Return the <em>absolute path</em> for this resource, as a
   * {@linkplain ResourceUtils#cleanPath(String) cleaned} resource path within the
   * class path.
   * <p>The path returned by this method does not have a leading slash and is
   * suitable for use with {@link ClassLoader#getResource(String)}.
   */
  public final String getPath() {
    return this.absolutePath;
  }

  /**
   * Return the {@link ClassLoader} that this resource will be obtained from.
   */
  @Nullable
  public final ClassLoader getClassLoader() {
    return (this.clazz != null ? this.clazz.getClassLoader() : this.classLoader);
  }

  /**
   * This implementation checks for the resolution of a resource URL.
   *
   * @see ClassLoader#getResource(String)
   * @see Class#getResource(String)
   */
  @Override
  public boolean exists() {
    return (resolveURL() != null);
  }

  /**
   * This implementation checks for the resolution of a resource URL upfront,
   * then proceeding with {@link AbstractFileResolvingResource}'s length check.
   *
   * @see ClassLoader#getResource(String)
   * @see Class#getResource(String)
   */
  @Override
  public boolean isReadable() {
    final URL url = resolveURL();
    return (url != null && checkReadable(url));
  }

  /**
   * Resolves a {@link URL} for the underlying class path resource.
   *
   * @return the resolved URL, or {@code null} if not resolvable
   */
  @Nullable
  protected URL resolveURL() {
    try {
      if (this.clazz != null) {
        return this.clazz.getResource(this.path);
      } else if (this.classLoader != null) {
        return this.classLoader.getResource(this.absolutePath);
      } else {
        return ClassLoader.getSystemResource(this.absolutePath);
      }
    } catch (final IllegalArgumentException ex) {
      // Should not happen according to the JDK's contract:
      // see https://github.com/openjdk/jdk/pull/2662
      return null;
    }
  }

  /**
   * This implementation opens an {@link InputStream} for the underlying class
   * path resource, if available.
   *
   * @see ClassLoader#getResourceAsStream(String)
   * @see Class#getResourceAsStream(String)
   * @see ClassLoader#getSystemResourceAsStream(String)
   */
  @Override
  public InputStream getInputStream() throws IOException {
    final InputStream is;
    if (this.clazz != null) {
      is = this.clazz.getResourceAsStream(this.path);
    } else if (this.classLoader != null) {
      is = this.classLoader.getResourceAsStream(this.absolutePath);
    } else {
      is = ClassLoader.getSystemResourceAsStream(this.absolutePath);
    }
    if (is == null) {
      throw new FileNotFoundException(
          getDescription() + " cannot be opened because it does not exist");
    }
    return is;
  }

  /**
   * This implementation returns a URL for the underlying class path resource,
   * if available.
   *
   * @see ClassLoader#getResource(String)
   * @see Class#getResource(String)
   */
  @Override
  public URL getURL() throws IOException {
    final URL url = resolveURL();
    if (url == null) {
      throw new FileNotFoundException(getDescription()
          + " cannot be resolved to URL because it does not exist");
    }
    return url;
  }

  /**
   * This implementation creates a {@code ClassPathResource}, applying the given
   * path relative to the path used to create this descriptor.
   *
   * @see ResourceUtils#applyRelativePath(String, String)
   */
  @Override
  public Resource createRelative(final String relativePath) {
    final String pathToUse = applyRelativePath(this.path, relativePath);
    return (this.clazz != null ?
            new ClassPathResource(pathToUse, this.clazz) :
            new ClassPathResource(pathToUse, this.classLoader));
  }

  /**
   * This implementation returns the name of the file that this class path
   * resource refers to.
   *
   * @see ResourceUtils#getFilename(String)
   */
  @Override
  @Nullable
  public String getFilename() {
    return ResourceUtils.getFilename(this.absolutePath);
  }

  /**
   * This implementation returns a description that includes the absolute class
   * path location.
   */
  @Override
  public String getDescription() {
    return "class path resource [" + this.absolutePath + "]";
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final ClassPathResource other = (ClassPathResource) o;
    return Equality.equals(absolutePath, other.absolutePath)
        && Equality.equals(getClassLoader(), other.getClassLoader());
  }

  public int hashCode() {
    return absolutePath.hashCode();
  }

  public String toString() {
    return new ToStringBuilder(this)
        .appendSuper(super.toString())
        .append("absolutePath", absolutePath)
        .append("classLoader", classLoader)
        .append("clazz", clazz)
        .toString();
  }
}