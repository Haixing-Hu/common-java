////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * {@link Resource} implementation for a given byte array.
 * <p>
 * Creates a {@link ByteArrayInputStream} for the given byte array.
 * <p>
 * Useful for loading content from any given byte array, without having to
 * resort to a single-use {@link InputStreamResource}. Particularly useful for
 * creating mail attachments from local content, where JavaMail needs to be able
 * to read the stream multiple times.
 * <p>
 * This class is a copy of {@code org.springframework.core.io.ByteArrayResource}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Haixing Hu
 * @see ByteArrayInputStream
 * @see InputStreamResource
 */
public class ByteArrayResource extends AbstractResource {

  private final byte[] byteArray;

  private final String description;

  /**
   * Create a new {@code ByteArrayResource}.
   *
   * @param byteArray
   *     the byte array to wrap
   */
  public ByteArrayResource(final byte[] byteArray) {
    this(byteArray, "resource loaded from byte array");
  }

  /**
   * Create a new {@code ByteArrayResource} with a description.
   *
   * @param byteArray
   *     the byte array to wrap
   * @param description
   *     where the byte array comes from
   */
  public ByteArrayResource(final byte[] byteArray, @Nullable final String description) {
    if (byteArray == null) {
      throw new IllegalArgumentException("Byte array must not be null");
    }
    this.byteArray = byteArray;
    this.description = (description != null ? description : "");
  }

  /**
   * Return the underlying byte array.
   */
  public final byte[] getByteArray() {
    return this.byteArray;
  }

  /**
   * This implementation always returns {@code true}.
   */
  @Override
  public boolean exists() {
    return true;
  }

  /**
   * This implementation returns the length of the underlying byte array.
   */
  @Override
  public long contentLength() {
    return this.byteArray.length;
  }

  /**
   * This implementation returns a ByteArrayInputStream for the underlying byte
   * array.
   *
   * @see ByteArrayInputStream
   */
  @Override
  public InputStream getInputStream() throws IOException {
    return new ByteArrayInputStream(this.byteArray);
  }

  @Override
  public byte[] getContentAsByteArray() throws IOException {
    final int length = this.byteArray.length;
    final byte[] result = new byte[length];
    System.arraycopy(this.byteArray, 0, result, 0, length);
    return result;
  }

  @Override
  public String getContentAsString(final Charset charset) throws IOException {
    return new String(this.byteArray, charset);
  }

  /**
   * This implementation returns a description that includes the passed-in
   * {@code description}, if any.
   */
  @Override
  public String getDescription() {
    return "Byte array resource [" + this.description + "]";
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final ByteArrayResource other = (ByteArrayResource) o;
    return Equality.equals(byteArray, other.byteArray);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, byteArray);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("byteArray", byteArray)
        .append("description", description)
        .toString();
  }
}