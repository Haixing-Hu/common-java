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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;

import javax.annotation.Nullable;

import ltd.qubit.commons.io.InputStreamSource;
import ltd.qubit.commons.io.IoUtils;

/**
 * 资源描述符的接口，它抽象了底层资源的实际类型，例如文件或类路径资源。
 * <p>
 * 如果资源以物理形式存在，则可以为每个资源打开一个 InputStream，但对于某些资源，
 * 只能返回 URL 或 File 句柄。实际行为是特定于实现的。
 * <p>
 * 此类是 {@code org.springframework.core.io.Resource} 的副本，经过少量修改。
 * 它用于避免对 Spring Framework 的依赖。
 *
 * @author Juergen Hoeller
 * @author Arjen Poutsma
 * @author 胡海星
 * @see #getInputStream()
 * @see #getURL()
 * @see #getURI()
 * @see #getFile()
 * @see WritableResource
 * @see ContextResource
 * @see UrlResource
 * @see FileUrlResource
 * @see FileSystemResource
 * @see ClassPathResource
 * @see ByteArrayResource
 * @see InputStreamResource
 * @since 28.12.2003
 */
public interface Resource extends InputStreamSource {

  /**
   * 判断此资源是否以物理形式实际存在。
   * <p>
   * 此方法执行确定的存在性检查，而 {@code Resource} 句柄的存在仅保证了有效的描述符句柄。
   */
  boolean exists();

  /**
   * 指示是否可以通过 {@link #getInputStream()} 读取此资源的非空内容。
   * <p>
   * 对于存在的典型资源描述符，将为 {@code true}，因为它严格地暗示了自 5.1 版本
   * 以来的 {@link #exists()} 语义。请注意，实际的内容读取在尝试时仍可能失败。但是，
   * {@code false} 的值是资源内容无法读取的明确指示。
   *
   * @see #getInputStream()
   * @see #exists()
   */
  default boolean isReadable() {
    return exists();
  }

  /**
   * 指示此资源是否表示具有打开流的句柄。如果为 {@code true}，则
   * InputStream 不能被多次读取，并且必须被读取和关闭以避免资源泄漏。
   * <p>
   * 对于典型的资源描述符，将为 {@code false}。
   */
  default boolean isOpen() {
    return false;
  }

  /**
   * 判断此资源是否表示文件系统中的文件。
   * <p>值为 {@code true} 强烈建议（但不保证）{@link #getFile()} 调用会成功。
   * <p>默认情况下，此值为保守的 {@code false}。
   *
   * @see #getFile()
   */
  default boolean isFile() {
    return false;
  }

  /**
   * 返回此资源的 URL 句柄。
   *
   * @throws IOException
   *     如果资源无法解析为 URL，即资源不可用作描述符。
   */
  URL getURL() throws IOException;

  /**
   * 返回此资源的 URI 句柄。
   *
   * @throws IOException
   *     如果资源无法解析为 URI，即资源不可用作描述符。
   */
  URI getURI() throws IOException;

  /**
   * 返回此资源的 File 句柄。
   *
   * @throws FileNotFoundException
   *     如果资源无法解析为绝对文件路径，即资源在文件系统中不可用。
   * @throws IOException
   *     在发生常规解析/读取失败时。
   * @see #getInputStream()
   */
  File getFile() throws IOException;

  /**
   * 返回一个 {@link ReadableByteChannel}。
   * <p>
   * 期望每次调用都会创建一个<i>新的</i>通道。
   * <p>
   * 默认实现返回 {@link Channels#newChannel(InputStream)}，其参数为
   * {@link #getInputStream()} 的结果。
   *
   * @return 底层资源的字节通道（不得为 {@code null}）。
   * @throws FileNotFoundException
   *     如果底层资源不存在。
   * @throws IOException
   *     如果无法打开内容通道。
   * @see #getInputStream()
   * @since 5.0
   */
  default ReadableByteChannel readableChannel() throws IOException {
    return Channels.newChannel(getInputStream());
  }

  /**
   * 以字节数组形式返回此资源的内容。
   *
   * @return 此资源的内容，形式为字节数组。
   * @throws FileNotFoundException
   *     如果资源无法解析为绝对文件路径，即资源在文件系统中不可用。
   * @throws IOException
   *     在发生常规解析/读取失败时。
   */
  default byte[] getContentAsByteArray() throws IOException {
    final InputStream in = getInputStream();
    try (in) {
      return IoUtils.getBytes(in, Integer.MAX_VALUE);
    }
  }

  /**
   * 使用指定的字符集，以字符串形式返回此资源的内容。
   *
   * @param charset
   *     用于解码的字符集。
   * @return 此资源的内容，形式为 {@code String}。
   * @throws FileNotFoundException
   *     如果资源无法解析为绝对文件路径，即资源在文件系统中不可用。
   * @throws IOException
   *     在发生常规解析/读取失败时。
   * @since 6.0.5
   */
  default String getContentAsString(final Charset charset) throws IOException {
    final InputStream in = getInputStream();
    try (in) {
      return IoUtils.toString(in, charset);
    }
  }

  /**
   * 确定此资源的内容长度。
   *
   * @throws IOException
   *     如果资源无法解析（在文件系统中或作为其他已知的物理资源类型）。
   */
  long contentLength() throws IOException;

  /**
   * 确定此资源的最后修改时间戳。
   *
   * @throws IOException
   *     如果资源无法解析（在文件系统中或作为其他已知的物理资源类型）。
   */
  long lastModified() throws IOException;

  /**
   * 创建一个相对于此资源的资源。
   *
   * @param relativePath
   *     相对路径（相对于此资源）。
   * @return 相对资源的资源句柄。
   * @throws IOException
   *     如果无法确定相对资源。
   */
  Resource createRelative(String relativePath)
      throws IOException;

  /**
   * 确定此资源的文​​件名——通常是路径的最后一部分——例如，{@code "myfile.txt"}。
   * <p>如果此类型的资源没有文件名，则返回 {@code null}。
   * <p>鼓励实现返回未编码的文件名。
   */
  @Nullable
  String getFilename();

  /**
   * 返回此资源的描述，用于处理资源时发生错误时的输出。
   * <p>鼓励实现也从其 {@code toString} 方法返回此值。
   *
   * @see Object#toString()
   */
  String getDescription();

}