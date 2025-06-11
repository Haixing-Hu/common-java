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
 * 给定字节数组的 {@link Resource} 实现。
 * <p>
 * 为给定的字节数组创建 {@link ByteArrayInputStream}。
 * <p>
 * 对于从任何给定字节数组加载内容很有用，无需依赖一次性使用的 {@link InputStreamResource}。
 * 特别适用于从本地内容创建邮件附件，其中 JavaMail 需要能够多次读取流。
 * <p>
 * 此类是 {@code org.springframework.core.io.ByteArrayResource} 的副本，
 * 略有修改。用于避免对 Spring Framework 的依赖。
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author 胡海星
 * @see ByteArrayInputStream
 * @see InputStreamResource
 */
public class ByteArrayResource extends AbstractResource {

  private final byte[] byteArray;

  private final String description;

  /**
   * 创建一个新的 {@code ByteArrayResource}。
   *
   * @param byteArray
   *     要包装的字节数组
   */
  public ByteArrayResource(final byte[] byteArray) {
    this(byteArray, "resource loaded from byte array");
  }

  /**
   * 创建一个带有描述的新 {@code ByteArrayResource}。
   *
   * @param byteArray
   *     要包装的字节数组
   * @param description
   *     字节数组的来源
   */
  public ByteArrayResource(final byte[] byteArray, @Nullable final String description) {
    if (byteArray == null) {
      throw new IllegalArgumentException("Byte array must not be null");
    }
    this.byteArray = byteArray;
    this.description = (description != null ? description : "");
  }

  /**
   * 返回底层字节数组。
   *
   * @return 底层字节数组。
   */
  public final byte[] getByteArray() {
    return this.byteArray;
  }

  /**
   * 此实现始终返回 {@code true}。
   */
  @Override
  public boolean exists() {
    return true;
  }

  /**
   * 此实现返回底层字节数组的长度。
   */
  @Override
  public long contentLength() {
    return this.byteArray.length;
  }

  /**
   * 此实现为底层字节数组返回 ByteArrayInputStream。
   *
   * @see ByteArrayInputStream
   */
  @Override
  public InputStream getInputStream() throws IOException {
    return new ByteArrayInputStream(this.byteArray);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] getContentAsByteArray() throws IOException {
    final int length = this.byteArray.length;
    final byte[] result = new byte[length];
    System.arraycopy(this.byteArray, 0, result, 0, length);
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getContentAsString(final Charset charset) throws IOException {
    return new String(this.byteArray, charset);
  }

  /**
   * 此实现返回包含传入的 {@code description}（如果有）的描述。
   */
  @Override
  public String getDescription() {
    return "Byte array resource [" + this.description + "]";
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, byteArray);
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public String toString() {
    return new ToStringBuilder(this)
        .append("byteArray", byteArray)
        .append("description", description)
        .toString();
  }
}