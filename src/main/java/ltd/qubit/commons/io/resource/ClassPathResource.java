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
 * 类路径资源的 {@link Resource} 实现。使用给定的 {@link ClassLoader} 或给定的 {@link Class} 加载资源。
 * <p>
 * 如果类路径资源位于文件系统中，则支持解析为 {@code java.io.File}，但不支持 JAR 中的资源。
 * 始终支持解析为 {@code java.net.URL}。
 * <p>
 * 此类是 {@code org.springframework.core.io.ClassPathResource} 的副本，
 * 略有修改。用于避免对 Spring Framework 的依赖。
 *
 * @author 胡海星
 * @see ClassLoader#getResourceAsStream(String)
 * @see ClassLoader#getResource(String)
 * @see Class#getResourceAsStream(String)
 * @see Class#getResource(String)
 */
public class ClassPathResource extends AbstractFileResolvingResource {

  /**
   * 用户提供的原始路径的内部表示，用于创建相对路径和解析 URL 和 InputStreams。
   */
  private final String path;

  private final String absolutePath;

  @Nullable
  private final ClassLoader classLoader;

  @Nullable
  private final Class<?> clazz;

  /**
   * 为 {@code ClassLoader} 使用创建新的 {@code ClassPathResource}。
   * <p>
   * 前导斜杠将被移除，因为 {@code ClassLoader} 资源访问方法不接受它。
   * <p>
   * 默认类加载器将用于加载资源。
   *
   * @param path
   *     类路径中的绝对路径
   * @see SystemUtils#getDefaultClassLoader()
   */
  public ClassPathResource(final String path) {
    this(path, (ClassLoader) null);
  }

  /**
   * 为 {@code ClassLoader} 使用创建新的 {@code ClassPathResource}。
   * <p>
   * 前导斜杠将被移除，因为 {@code ClassLoader} 资源访问方法不接受它。
   * <p>
   * 如果提供的 {@code ClassLoader} 为 {@code null}，则默认类加载器将用于加载资源。
   *
   * @param path
   *     类路径中的绝对路径
   * @param classLoader
   *     用于加载资源的类加载器
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
   * 为 {@code Class} 使用创建新的 {@code ClassPathResource}。
   * <p>路径可以相对于给定的类，或者通过前导斜杠在类路径中是绝对的。
   * <p>如果提供的 {@code Class} 为 {@code null}，则默认类加载器将用于加载资源。
   * <p>这对于模块系统中的资源访问也很有用，
   * 从给定 {@code Class} 的包含模块加载资源。参见 {@link ModuleResource} 及其 javadoc。
   *
   * @param path
   *     类路径中的相对或绝对路径
   * @param clazz
   *     用于加载资源的类
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
   * 返回此资源的<em>绝对路径</em>，作为类路径中
   * {@linkplain ResourceUtils#cleanPath(String) 清理的}资源路径。
   * <p>此方法返回的路径没有前导斜杠，适合与 {@link ClassLoader#getResource(String)} 一起使用。
   */
  public final String getPath() {
    return this.absolutePath;
  }

  /**
   * 返回将从中获取此资源的 {@link ClassLoader}。
   */
  @Nullable
  public final ClassLoader getClassLoader() {
    return (this.clazz != null ? this.clazz.getClassLoader() : this.classLoader);
  }

  /**
   * 此实现检查资源 URL 的解析。
   *
   * @see ClassLoader#getResource(String)
   * @see Class#getResource(String)
   */
  @Override
  public boolean exists() {
    return (resolveURL() != null);
  }

  /**
   * 此实现首先检查资源 URL 的解析，
   * 然后继续进行 {@link AbstractFileResolvingResource} 的长度检查。
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
   * 为底层类路径资源解析 {@link URL}。
   *
   * @return 解析的 URL，如果无法解析则返回 {@code null}
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
   * 此实现为底层类路径资源打开 {@link InputStream}（如果可用）。
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
   * 此实现为底层类路径资源返回 URL（如果可用）。
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
   * 此实现创建 {@code ClassPathResource}，应用相对于用于创建此描述符的路径的给定路径。
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
   * 此实现返回此类路径资源引用的文件名。
   *
   * @see ResourceUtils#getFilename(String)
   */
  @Override
  @Nullable
  public String getFilename() {
    return ResourceUtils.getFilename(this.absolutePath);
  }

  /**
   * 此实现返回包含绝对类路径位置的描述。
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