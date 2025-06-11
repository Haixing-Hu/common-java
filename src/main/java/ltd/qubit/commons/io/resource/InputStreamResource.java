////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.resource;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 给定 {@link InputStream} 的 {@link Resource} 实现。
 * <p>
 * 仅在没有其他特定的 {@code Resource} 实现适用时才应使用。特别地，在可能的情况
 * 下，优先使用 {@link ByteArrayResource} 或任何基于文件的 {@code Resource} 实现。
 * <p>
 * 与其他 {@code Resource} 实现相反，这是一个<i>已经打开的</i>资源的描述符，因此
 * 从 {@link #isOpen()} 返回 {@code true}。如果您需要将资源描述符保存在某处，
 * 或者需要多次从流中读取，请不要使用 {@code InputStreamResource}。
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author 胡海星
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
   * 创建一个新的 {@code InputStreamResource}。
   *
   * @param inputStream
   *     要使用的 {@link InputStream}。
   */
  public InputStreamResource(final InputStream inputStream) {
    this(inputStream, "resource loaded through InputStream");
  }

  /**
   * 创建一个新的 {@code InputStreamResource}。
   *
   * @param inputStream
   *     要使用的 {@link InputStream}。
   * @param description
   *     {@link InputStream} 的来源描述。
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
   * 此实现始终返回 {@code true}。
   */
  @Override
  public boolean exists() {
    return true;
  }

  /**
   * 此实现始终返回 {@code true}。
   */
  @Override
  public boolean isOpen() {
    return true;
  }

  /**
   * 如果尝试多次读取底层流，此实现将抛出 {@link IllegalStateException}。
   */
  @Nonnull
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
   * 此实现返回一个描述，其中包含传入的描述（如果有）。
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