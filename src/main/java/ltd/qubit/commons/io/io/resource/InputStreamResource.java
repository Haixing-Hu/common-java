////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.io.resource;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * {@link Resource} implementation for a given {@link InputStream}.
 * <p>
 * Should only be used if no other specific {@code Resource} implementation
 * is applicable. In particular, prefer
 * {@link org.springframework.core.io.ByteArrayResource} or any of the
 * file-based {@code Resource} implementations where possible.
 *
 * <p>In contrast to other {@code Resource} implementations, this is a
 * descriptor
 * for an <i>already opened</i> resource - therefore returning {@code true} from
 * {@link #isOpen()}. Do not use an {@code InputStreamResource} if you need to
 * keep the resource descriptor somewhere, or if you need to read from a stream
 * multiple times.
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @see ByteArrayResource
 * @see ClassPathResource
 * @see FileSystemResource
 * @see UrlResource
 * @since 28.12.2003
 */
public class InputStreamResource extends AbstractResource {

  private final InputStream inputStream;

  private final String description;

  private boolean read = false;

  /**
   * Create a new InputStreamResource.
   *
   * @param inputStream
   *     the InputStream to use
   */
  public InputStreamResource(final InputStream inputStream) {
    this(inputStream, "resource loaded through InputStream");
  }

  /**
   * Create a new InputStreamResource.
   *
   * @param inputStream
   *     the InputStream to use
   * @param description
   *     where the InputStream comes from
   */
  public InputStreamResource(final InputStream inputStream,
      @Nullable final String description) {
    if (inputStream == null) {
      throw new IllegalArgumentException("InputStream must not be null");
    }
    this.inputStream = inputStream;
    this.description = (description != null ? description : "");
  }


  /**
   * This implementation always returns {@code true}.
   */
  @Override
  public boolean exists() {
    return true;
  }

  /**
   * This implementation always returns {@code true}.
   */
  @Override
  public boolean isOpen() {
    return true;
  }

  /**
   * This implementation throws IllegalStateException if attempting to read the
   * underlying stream multiple times.
   */
  @Override
  public InputStream getInputStream()
      throws IOException, IllegalStateException {
    if (this.read) {
      throw new IllegalStateException("InputStream has already been read - "
          + "do not use InputStreamResource if a stream needs to be read multiple times");
    }
    this.read = true;
    return this.inputStream;
  }

  /**
   * This implementation returns a description that includes the passed-in
   * description, if any.
   */
  @Override
  public String getDescription() {
    return "InputStream resource [" + this.description + "]";
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final InputStreamResource other = (InputStreamResource) o;
    return Equality.equals(inputStream, other.inputStream);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, inputStream);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("inputStream", inputStream)
        .append("description", description)
        .append("read", read)
        .toString();
  }
}
