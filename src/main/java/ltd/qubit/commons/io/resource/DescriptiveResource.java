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

import javax.annotation.Nullable;

/**
 * Simple {@link Resource} implementation that holds a resource description
 * but does not point to an actually readable resource.
 * <p>
 * To be used as placeholder if a {@code Resource} argument is
 * expected by an API but not necessarily used for actual reading.
 * <p>
 * This class is a copy of {@code org.springframework.core.io.DescriptiveResource}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Haixing Hu
 */
public class DescriptiveResource extends AbstractResource {

  private final String description;

  /**
   * Create a new DescriptiveResource.
   * @param description the resource description
   */
  public DescriptiveResource(@Nullable final String description) {
    this.description = (description != null ? description : "");
  }

  @Override
  public boolean exists() {
    return false;
  }

  @Override
  public boolean isReadable() {
    return false;
  }

  @Nullable
  @Override
  public InputStream getInputStream() throws IOException {
    throw new FileNotFoundException(getDescription()
        + " cannot be opened because it does not point to a readable resource");
  }

  @Override
  public String getDescription() {
    return this.description;
  }

  /**
   * This implementation compares the underlying description String.
   */
  @Override
  public boolean equals(@Nullable final Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof DescriptiveResource)) {
      return false;
    }
    final DescriptiveResource that = (DescriptiveResource) other;
    return this.description.equals(that.description);
  }

  /**
   * This implementation returns the hash code of the underlying description String.
   */
  @Override
  public int hashCode() {
    return this.description.hashCode();
  }
}
