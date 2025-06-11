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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 用于 {@link Module} 解析的 {@link Resource} 实现，通过
 * {@link Module#getResourceAsStream} 执行 {@link #getInputStream()} 访问。
 * <p>
 * 或者，考虑通过 {@link ClassPathResource} 访问模块路径布局中的资源以获取导出的
 * 资源，或特别是相对于 {@code Class} 的资源通过
 * {@link ClassPathResource#ClassPathResource(String, Class)} 在特定类的包含模块
 * 内进行本地解析。在常见场景中，模块资源将简单地作为类路径资源透明可见，因此根本
 * 不需要任何特殊处理。
 * <p>
 * 此类是 {@code org.springframework.core.io.ModuleResource} 的副本，经过少量修改。
 * 它用于避免对 Spring Framework 的依赖。
 *
 * @author 胡海星
 * @see Module#getResourceAsStream
 * @see ClassPathResource
 */
public class ModuleResource extends AbstractResource {

  private final Module module;

  private final String path;

  /**
   * 为给定的 {@link Module} 和给定的资源路径创建一个新的 {@code ModuleResource}。
   *
   * @param module
   *     要在其中搜索的运行时模块。
   * @param path
   *     模块内的资源路径。
   */
  public ModuleResource(final Module module, final String path) {
    if (module == null) {
      throw new IllegalArgumentException("Module must not be null");
    }
    if (path == null) {
      throw new IllegalArgumentException("Path must not be null");
    }
    this.module = module;
    this.path = path;
  }

  /**
   * 返回此资源的 {@link Module}。
   *
   * @return 此资源的 {@link Module}。
   */
  public final Module getModule() {
    return this.module;
  }

  /**
   * 返回此资源的路径。
   *
   * @return 此资源的路径。
   */
  public final String getPath() {
    return this.path;
  }

  /**
   * {@inheritDoc}
   */
  @Nonnull
  @Override
  public InputStream getInputStream() throws IOException {
    final InputStream is = module.getResourceAsStream(path);
    if (is == null) {
      throw new FileNotFoundException(getDescription()
          + " cannot be opened because it does not exist");
    }
    return is;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Resource createRelative(final String relativePath) {
    final String pathToUse = ResourceUtils.applyRelativePath(path, relativePath);
    return new ModuleResource(module, pathToUse);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Nullable
  public String getFilename() {
    return ResourceUtils.getFilename(this.path);
  }

  @Override
  public String getDescription() {
    return "module resource [" + path + "]" +
        (module.isNamed() ? " from module [" + module.getName() + "]" : "");
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final ModuleResource other = (ModuleResource) o;
    return Equality.equals(module, other.module)
        && Equality.equals(path, other.path);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, module);
    result = Hash.combine(result, multiplier, path);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("module", module)
        .append("path", path)
        .toString();
  }
}