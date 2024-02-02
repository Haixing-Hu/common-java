////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * JBoss VFS based {@link Resource} implementation.
 * <p>
 * As of Spring 4.0, this class supports VFS 3.x on JBoss AS 6+
 * (package {@code org.jboss.vfs}) and is in particular compatible with JBoss AS
 * 7 and WildFly 8+.
 * <p>
 * This class is a copy of {@code org.springframework.core.io.VfsResource}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Ales Justin
 * @author Juergen Hoeller
 * @author Costin Leau
 * @author Sam Brannen
 * @author Haixing Hu
 */
public class VfsResource extends AbstractResource {

  private final Object resource;

  /**
   * Create a new {@code VfsResource} wrapping the given resource handle.
   *
   * @param resource
   *     a {@code org.jboss.vfs.VirtualFile} instance (untyped in order to avoid
   *     a static dependency on the VFS API)
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
