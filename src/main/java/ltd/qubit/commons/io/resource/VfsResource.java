////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import ltd.qubit.commons.io.VfsUtils;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 基于 JBoss VFS 的 {@link Resource} 实现。
 * <p>
 * 从 Spring 4.0 开始，这个类支持 JBoss AS 6+ 上的 VFS 3.x
 * (包名为 {@code org.jboss.vfs})，尤其与 JBoss AS 7 和 WildFly 8+ 兼容。
 * <p>
 * 这个类是 {@code org.springframework.core.io.VfsResource} 的一个副本，
 * 经过了少量修改。它的目的是为了避免对 Spring Framework 的依赖。
 *
 * @author 胡海星
 */
public class VfsResource extends AbstractResource {

  private final Object resource;

  /**
   * 创建一个新的 {@code VfsResource} 来包装给定的资源句柄。
   *
   * @param resource
   *     一个 {@code org.jboss.vfs.VirtualFile} 实例 (为了避免
   *     对 VFS API 的静态依赖，这里使用无类型的方式)。
   */
  public VfsResource(final Object resource) {
    if (resource == null) {
      throw new IllegalArgumentException("VirtualFile must not be null");
    }
    this.resource = resource;
  }

  @Override
  public InputStream getInputStream() throws IOException {
    return VfsUtils.getInputStream(this.resource);
  }

  @Override
  public boolean exists() {
    return VfsUtils.exists(this.resource);
  }

  @Override
  public boolean isReadable() {
    return VfsUtils.isReadable(this.resource);
  }

  @Override
  public URL getURL() throws IOException {
    try {
      return VfsUtils.getURL(this.resource);
    } catch (final Exception ex) {
      throw new IOException("Failed to obtain URL for file " + this.resource, ex);
    }
  }

  @Override
  public URI getURI() throws IOException {
    try {
      return VfsUtils.getURI(this.resource);
    } catch (final Exception ex) {
      throw new IOException("Failed to obtain URI for " + this.resource, ex);
    }
  }

  @Override
  public File getFile() throws IOException {
    return VfsUtils.getFile(this.resource);
  }

  @Override
  public long contentLength() throws IOException {
    return VfsUtils.getSize(this.resource);
  }

  @Override
  public long lastModified() throws IOException {
    return VfsUtils.getLastModified(this.resource);
  }

  @Override
  public Resource createRelative(final String relativePath) throws IOException {
    if (!relativePath.startsWith(".") && relativePath.contains("/")) {
      try {
        return new VfsResource(VfsUtils.getChild(this.resource, relativePath));
      } catch (final IOException ex) {
        // fall back to getRelative
      }
    }
    return new VfsResource(VfsUtils.getRelative(
        ResourceUtils.toRelativeURL(getURL(), relativePath)));
  }

  @Override
  public String getFilename() {
    return VfsUtils.getName(this.resource);
  }

  @Override
  public String getDescription() {
    return "VFS resource [" + this.resource + "]";
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final VfsResource other = (VfsResource) o;
    return Equality.equals(resource, other.resource);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, resource);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .appendSuper(super.toString())
        .append("resource", resource)
        .toString();
  }
}