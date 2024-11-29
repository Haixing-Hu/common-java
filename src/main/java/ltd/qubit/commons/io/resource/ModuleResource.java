////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * {@link Resource} implementation for {@link Module} resolution,
 * performing {@link #getInputStream()} access via {@link Module#getResourceAsStream}.
 * <p>
 * Alternatively, consider accessing resources in a module path layout via
 * {@link ClassPathResource} for exported resources, or specifically relative to
 * a {@code Class} via {@link ClassPathResource#ClassPathResource(String, Class)}
 * for local resolution within the containing module of that specific class.
 * In common scenarios, module resources will simply be transparently visible as
 * classpath resources and therefore do not need any special treatment at all.
 * <p>
 * This class is a copy of {@code org.springframework.core.io.ModuleResource}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Haixing Hu
 * @see Module#getResourceAsStream
 * @see ClassPathResource
 */
public class ModuleResource extends AbstractResource {

  private final Module module;

  private final String path;

  /**
   * Create a new {@code ModuleResource} for the given {@link Module} and the
   * given resource path.
   *
   * @param module
   *     the runtime module to search within
   * @param path
   *     the resource path within the module
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
   * Return the {@link Module} for this resource.
   */
  public final Module getModule() {
    return this.module;
  }

  /**
   * Return the path for this resource.
   */
  public final String getPath() {
    return this.path;
  }

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

  @Override
  public Resource createRelative(final String relativePath) {
    final String pathToUse = ResourceUtils.applyRelativePath(path, relativePath);
    return new ModuleResource(module, pathToUse);
  }

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
