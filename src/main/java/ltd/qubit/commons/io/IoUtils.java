////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.lang.SystemUtils;
import ltd.qubit.commons.net.UrlUtils;
import ltd.qubit.commons.util.expand.ExpansionPolicy;

import static ltd.qubit.commons.lang.Argument.requirePositive;
import static ltd.qubit.commons.lang.SystemUtils.LINE_SEPARATOR;

/**
 * 此类提供常见的文件操作函数。
 *
 * @author 胡海星
 */
@ThreadSafe
public final class IoUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(IoUtils.class);

  /**
   * 要使用的默认缓冲区大小。
   */
  public static final int BUFFER_SIZE = 16384;

  private static final String NEGATIVE_MAX_LENGTH =
      "The maxLength argument can't be negative.";

  /**
   * 无条件关闭一个 {@code Closeable} 对象。
   *
   * <p>等价于 {@link Closeable#close()}，除了任何异常都会被忽略并记录为警告消息。
   * 这通常在 finally 块中使用。
   *
   * @param closeable
   *     要关闭的 Closeable 对象，可能为 null 或已经关闭。
   */
  public static void closeQuietly(final Closeable closeable) {
    try {
      if (closeable != null) {
        closeable.close();
      }
    } catch (final IOException e) {
      final Logger logger = LoggerFactory.getLogger(IoUtils.class);
      logger.error("An exception was thrown during closing the "
          + "Closeable object. ", e);
    }
  }

  /**
   * 无条件关闭一个 {@code Socket} 对象。
   *
   * <p>等价于 {@link Socket#close()}，除了任何异常都会被忽略并记录为警告消息。
   * 这通常在 finally 块中使用。
   *
   * @param socket
   *     要关闭的套接字，可能为 null 或已经关闭。
   */
  public static void closeQuietly(final Socket socket) {
    try {
      if (socket != null) {
        socket.close();
      }
    } catch (final IOException e) {
      final Logger logger = LoggerFactory.getLogger(IoUtils.class);
      logger.error("An exception was thrown during closing the Socket object. ",
          e);
    }
  }

  /**
   * 无条件关闭一个 {@code Connection} 对象。
   *
   * <p>等价于 {@link Closeable#close()}，除了任何异常都会被忽略并记录为警告消息。
   * 这通常在 finally 块中使用。
   *
   * @param connection
   *     要关闭的 Connection 对象，可能为 null 或已经关闭。
   */
  public static void closeQuietly(final Connection connection) {
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (final SQLException e) {
      final Logger logger = LoggerFactory.getLogger(IoUtils.class);
      logger
          .error("An exception was thrown during closing the connection. ", e);
    }
  }

  /**
   * 尝试从输入流读取指定数量的字节。
   *
   * <p>此函数将尝试从输入流读取，直到读取了指定数量的字节或遇到文件结尾。
   *
   * @param input
   *     要读取的 {@link InputStream}。
   * @param bytes
   *     需要读取的字节数。
   * @param buffer
   *     用于存储字节的缓冲区，必须足够大以容纳这些字节。
   * @return 实际读取的字节数。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static int readFully(final InputStream input, final int bytes,
      final byte[] buffer) throws IOException {
    if (bytes <= 0) {
      return 0;
    }
    final int totalBytes = Math.min(bytes, buffer.length);
    int count = 0;
    while (count < totalBytes) {
      final int n = input.read(buffer, count, totalBytes - count);
      if (n < 0) {
        return count;
      }
      count += n;
    }
    return count;
  }

  /**
   * 尝试从输入流读取指定数量的字节。
   *
   * <p>此函数将尝试从输入流读取，直到读取了指定数量的字节或遇到文件结尾。
   *
   * @param input
   *     要读取的 {@link InputStream}。
   * @param bytes
   *     需要读取的字节数。
   * @return 已读取的字节。
   * @throws EOFException
   *     如果在读取所需字节数之前遇到文件结尾。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static byte[] readFully(final InputStream input, final int bytes)
      throws IOException {
    if (bytes <= 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    }
    final byte[] buffer = new byte[bytes];
    int count = 0;
    while (count < bytes) {
      final int n = input.read(buffer, count, bytes - count);
      if (n < 0) {
        throw new EOFException();
      }
      count += n;
    }
    return buffer;
  }

  /**
   * 将字节数组中的字节复制到 {@link OutputStream}。
   * <p>
   * 此方法在内部缓冲输入，因此无需使用 {@link BufferedInputStream}。
   * <p>
   * <b>注意：</b>此方法不会关闭输出流。
   * <p>
   * TODO: 添加进度显示回调函数的支持。
   *
   * @param input
   *     要读取的字节数组。
   * @param output
   *     要写入的 {@link OutputStream}。
   * @return 复制的字节数，可能大于 2 GB。
   * @throws IOException
   *     如果发生任何 I/O 错误
   * @see #copy(InputStream, long, OutputStream, byte[])
   */
  public static long copy(final byte[] input, final OutputStream output)
      throws IOException {
    return copy(new ByteArrayInputStream(input), Long.MAX_VALUE, output);
  }

  /**
   * 将字节从 {@link InputStream} 复制到 {@link OutputStream}。
   * <p>
   * 此方法在内部缓冲输入，因此无需使用 {@link BufferedInputStream}。
   * <p>
   * <b>注意：</b>此方法不会关闭输入流，也不会关闭输出流。
   * <p>
   * TODO: 添加进度显示回调函数的支持。
   *
   * @param input
   *     要读取的 {@link InputStream}。
   * @param output
   *     要写入的 {@link OutputStream}。
   * @return 复制的字节数，可能大于 2 GB。
   * @throws IOException
   *     如果发生任何 I/O 错误
   * @see #copy(InputStream, long, OutputStream, byte[])
   */
  public static long copy(final InputStream input, final OutputStream output)
      throws IOException {
    return copy(input, Long.MAX_VALUE, output);
  }

  /**
   * 将字节从 {@link InputStream} 复制到 {@link OutputStream}。
   * <p>
   * 此方法在内部缓冲输入，因此无需使用 {@link BufferedInputStream}。
   * <p>
   * <b>注意：</b>此方法不会关闭输入流，也不会关闭输出流。
   *
   * <p>TODO: 添加进度显示回调函数的支持。
   *
   * @param input
   *     要读取的 {@link InputStream}。
   * @param maxBytes
   *     要复制的最大字节数。可以是 {@code Long.MAX_VALUE}，表示无限制。
   * @param output
   *     要写入的 {@link OutputStream}。
   * @return 复制的字节数，可能大于 2 GB。
   * @throws IOException
   *     如果发生任何 I/O 错误
   * @see #copy(InputStream, long, OutputStream, byte[])
   */
  public static long copy(final InputStream input, final long maxBytes,
      final OutputStream output) throws IOException {
    final byte[] buffer = new byte[BUFFER_SIZE];
    return copy(input, maxBytes, output, buffer);
  }

  /**
   * 将字节从 {@link InputStream} 复制到 {@link OutputStream}。
   * <p>
   * 此方法在内部缓冲输入，因此无需使用 {@link BufferedInputStream}。
   * <p>
   * <b>注意：</b>此方法不会关闭输入流，也不会关闭输出流。
   * <p>
   * TODO: 添加进度显示回调函数的支持。
   *
   * @param input
   *     要读取的 {@link InputStream}
   * @param output
   *     要写入的 {@link OutputStream}。
   * @param maxBytes
   *     要复制的最大字节数。可以是 {@code Long.MAX_VALUE}，表示无限制。
   * @param buffer
   *     用于复制的缓冲区。
   * @return 复制的字节数，可能大于 2 GB。
   * @throws IOException
   *     如果发生任何 I/O 错误
   * @see #copy(InputStream, long, OutputStream)
   */
  public static long copy(final InputStream input, final long maxBytes,
      final OutputStream output, final byte[] buffer) throws IOException {
    LOGGER.trace("Copying at most {} bytes.", maxBytes);
    if (maxBytes <= 0) {
      return 0;
    }
    requirePositive("buffer.length", buffer.length);
    long count = 0;
    while (count < maxBytes) {
      LOGGER.trace("{} bytes copied.", count);
      int n;
      final long remained = maxBytes - count;
      if (remained < buffer.length) {
        n = (int) remained;
      } else {
        n = buffer.length;
      }
      LOGGER.trace("Try to copy {} bytes.", n);
      n = input.read(buffer, 0, n);
      LOGGER.trace("Actually copied {} bytes.", n);
      if (n < 0) {  // EOF
        break;
      } else if (n > 0) {
        output.write(buffer, 0, n);
        count += n;
      }
    }
    LOGGER.trace("Totally {} bytes copied.", count);
    return count;
  }

  /**
   * 将字符从 {@code Reader} 复制到 {@code Writer}。
   * <p>
   * 此方法在内部缓冲输入，因此无需使用 {@code BufferedReader}。
   * <p>
   * <b>注意：</b>此方法不会关闭输入阅读器，也不会关闭输出写入器。
   *
   * <p>TODO: 添加进度显示回调函数的支持。
   *
   * @param input
   *     要读取的 {@code Reader}。
   * @param maxChars
   *     要复制的最大字符数。可以是 Long.MAX_VALUE，表示无限制。
   * @param output
   *     要写入的 {@code Writer}。
   * @return 复制的字符数，可能大于 2 GB。
   * @throws IOException
   *     如果发生任何 I/O 错误
   * @see #copy(Reader, long, Writer, char[])
   */
  public static long copy(final Reader input, final long maxChars, final Writer output)
      throws IOException {
    final char[] buffer = new char[BUFFER_SIZE];
    return copy(input, maxChars, output, buffer);
  }

  /**
   * 将字符从 {@code Reader} 复制到 {@code Writer}。
   * <p>
   * 此方法在内部缓冲输入，因此无需使用 {@code BufferedReader}。
   * <p>
   * <b>注意：</b>此方法不会关闭输入阅读器，也不会关闭输出写入器。
   * <p>
   * TODO: 添加进度显示回调函数的支持。
   *
   * @param input
   *     要读取的 {@code Reader}。
   * @param maxChars
   *     要复制的最大字符数。可以是 Long.MAX_VALUE，表示无限制。
   * @param output
   *     要写入的 {@code Writer}。
   * @param buffer
   *     用于复制的缓冲区。
   * @return 复制的字符数，可能大于 2 GB。
   * @throws IOException
   *     如果发生任何 I/O 错误
   * @see #copy(Reader, long, Writer)
   */
  public static long copy(final Reader input, final long maxChars,
      final Writer output, final char[] buffer) throws IOException {
    LOGGER.trace("Copying at most {} characters.", maxChars);
    if (maxChars <= 0) {
      return 0;
    }
    requirePositive("buffer.length", buffer.length);
    long count = 0;
    while (count < maxChars) {
      LOGGER.trace("{} characters copied.", count);
      int n;
      final long remained = maxChars - count;
      if (remained < buffer.length) {
        n = (int) remained;
      } else {
        n = buffer.length;
      }
      LOGGER.trace("Try to copy {} characters.", n);
      n = input.read(buffer, 0, n);
      LOGGER.trace("Actually copied {} characters.", n);
      if (n < 0) {  // EOF
        break;
      } else if (n > 0) {
        output.write(buffer, 0, n);
        count += n;
      }
    }
    LOGGER.trace("Totally {} characters copied.", count);
    return count;
  }

  /**
   * 获取 {@link InputStream} 的前几个字节。
   * <p>
   * <b>注意：</b>此方法不会关闭输入流。
   *
   * @param input
   *     输入流。
   * @param maxBytes
   *     要获取的最大字节数。如果是 {@link Integer#MAX_VALUE}，
   *     则对要获取的字节数没有限制（除了内存限制）。
   * @return {@link InputStream} 的前几个字节；如果 {@link InputStream} 的字节数少于 maxLength，
   *     则获取并返回 {@link InputStream} 的所有字节；否则，获取并返回 {@link InputStream} 的前 
   *     {@code maxLength} 个字节；如果 {@link InputStream} 因为 {@code EOF} 而没有内容，
   *     则返回长度为 0 的字节数组。
   * @throws IOException
   *     如果发生任何 I/O 错误。
   */
  public static byte[] getBytes(final InputStream input, final int maxBytes)
      throws IOException {
    LOGGER.trace("Gets at most {} bytes.", maxBytes);
    if (maxBytes < 0) {
      throw new IllegalArgumentException(NEGATIVE_MAX_LENGTH);
    }
    byte[] buffer = new byte[Math.min(BUFFER_SIZE, maxBytes)];
    int hasRead = 0;
    int remained = maxBytes;
    final ExpansionPolicy expansionPolicy = ExpansionPolicy.getDefault();
    while (remained > 0) {
      LOGGER.trace("{} bytes has been get.", hasRead);
      if (hasRead == buffer.length) { // buffer is full, expand it
        buffer = expansionPolicy.expand(buffer, hasRead, hasRead + 1);
      }
      final int toRead = Math.min(buffer.length - hasRead, remained);
      LOGGER.trace("Try to get {} bytes.", toRead);
      final int n = input.read(buffer, hasRead, toRead);
      LOGGER.trace("Actually get {} bytes.", n);
      if (n < 0) { // EOF
        break;
      }
      hasRead += n;
      remained -= n;
    }
    if (hasRead == 0) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    } else if (hasRead < buffer.length) {
      buffer = expansionPolicy.resize(buffer, hasRead, hasRead);
      return buffer;
    } else {
      assert (hasRead == buffer.length);
      return buffer;
    }
  }

  /**
   * 获取 {@code InputStream} 的内容作为 {@code byte[]}。
   * <p>
   * 此方法在内部缓冲输入，因此无需使用 {@code BufferedInputStream}。
   * <p>
   * <b>注意：</b>此方法不会关闭输入流。
   *
   * @param input
   *     要读取的 {@code InputStream}。
   * @return 请求的字节数组
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static byte[] toByteArray(final InputStream input) throws IOException {
    final ByteArrayOutputStream output = new ByteArrayOutputStream();
    copy(input, Long.MAX_VALUE, output);
    return output.toByteArray();
  }

  /**
   * 获取 {@code Reader} 的内容作为字符数组。
   * <p>
   * 此方法在内部缓冲输入，因此无需使用 {@code BufferedReader}。
   * <p>
   * <b>注意：</b>此方法不会关闭输入阅读器。
   *
   * @param input
   *     要读取的 {@code Reader}
   * @return 请求的字符数组
   * @throws NullPointerException
   *     如果输入为 null
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static char[] toCharArray(final Reader input) throws IOException {
    final CharArrayWriter output = new CharArrayWriter();
    copy(input, Long.MAX_VALUE, output);
    return output.toCharArray();
  }

  /**
   * 获取 {@code Reader} 的内容作为字符串。
   * <p>
   * <b>注意：</b>此方法不会关闭输入阅读器。
   * <p>
   * 此方法在内部缓冲输入，因此无需使用 {@code BufferedReader}。
   *
   * @param input
   *     要读取的 {@code Reader}
   * @return 请求的字符串
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static String toString(final Reader input) throws IOException {
    final StringWriter output = new StringWriter();
    copy(input, Long.MAX_VALUE, output);
    return output.toString();
  }

  /**
   * 获取 {@code Reader} 的内容作为字符串。
   * <p>
   * <b>注意：</b>此方法不会关闭输入流。
   *
   * @param input
   *     要读取的 {@code InputStream}，将使用系统默认字符集进行解码。
   * @return 请求的字符串
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static String toString(final InputStream input)
      throws IOException {
    final InputStreamReader reader = new InputStreamReader(input);
    return toString(reader);
  }

  /**
   * 获取 {@code Reader} 的内容作为字符串。
   * <p>
   * <b>注意：</b>此方法不会关闭输入流。
   *
   * @param input
   *     要读取的 {@code InputStream}。
   * @param charset
   *     资源的字符集。
   * @return 请求的字符串
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static String toString(final InputStream input, final Charset charset)
      throws IOException {
    final InputStreamReader reader = new InputStreamReader(input, charset);
    return toString(reader);
  }

  /**
   * 获取指定 {@code File} 的内容作为字符串。
   * <p>
   * 此方法在内部缓冲输入，因此无需使用 {@code BufferedReader}。
   *
   * @param file
   *     要读取内容的 {@code File}。
   * @param charset
   *     资源的字符集。
   * @return 请求的字符串。
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static String toString(final File file, final Charset charset)
      throws IOException {
    if (file == null) {
      return null;
    }
    try (final InputStream in = new FileInputStream(file)) {
      final InputStreamReader reader = new InputStreamReader(in, charset);
      return toString(reader);
    }
  }

  /**
   * 获取位于指定 {@code URL} 的资源内容作为字符串。
   *
   * <p>此方法在内部缓冲输入，因此无需使用 {@code BufferedReader}。
   *
   * @param url
   *     要读取资源的 {@code URL}。
   * @param charset
   *     资源的字符集。
   * @return 请求的字符串。
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static String toString(final URL url, final Charset charset)
      throws IOException {
    if (url == null) {
      return null;
    }
    try (final InputStream in = UrlUtils.openStream(url)) {
      final InputStreamReader reader = new InputStreamReader(in, charset);
      return toString(reader);
    }
  }

  /**
   * 获取指定资源的内容作为字符串。
   *
   * <p>此方法在内部缓冲输入，因此无需使用 {@code BufferedReader}。
   *
   * @param resource
   *     要读取的文件资源，不能为 null。
   * @param cls
   *     将使用其类加载器来加载资源的类。
   * @param charset
   *     文件编码的字符集，不能为 null。
   * @return 请求的字符串。
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static String toString(final String resource, final Class<?> cls,
      final Charset charset) throws IOException {
    final URL url = SystemUtils.getResource(resource, cls);
    if (url == null) {
      throw new IOException("Resource not found: " + resource);
    }
    return toString(url, charset);
  }

  /**
   * 获取 {@code Reader} 的内容作为字符串列表，每行一个条目。
   *
   * <p>此方法在内部缓冲输入，因此无需使用 {@code BufferedReader}。
   *
   * <p>注意调用者必须自己关闭阅读器。
   *
   * @param input
   *     要读取的 {@code Reader}，不能为 null。
   * @return 字符串的数组列表。不能为 null，但可以为空。
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static List<String> readLines(final Reader input) throws IOException {
    final BufferedReader reader = new BufferedReader(input);
    final List<String> result = new ArrayList<>();
    String line = reader.readLine();
    while (line != null) {
      result.add(line);
      line = reader.readLine();
    }
    return result;
  }

  /**
   * 获取文件的内容作为字符串列表，每行一个条目。
   *
   * <p>注意调用者必须自己关闭阅读器。
   *
   * @param file
   *     要读取的文件路径，不能为 null。
   * @param charset
   *     文件编码的字符集，不能为 null。
   * @return 字符串的数组列表。不能为 null，但可以为空。
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static List<String> readLines(final File file, final Charset charset)
      throws IOException {
    try (final Reader reader = FileUtils.openReader(file, charset)) {
      return readLines(reader);
    }
  }

  /**
   * 获取文件的内容作为字符串列表，每行一个条目。
   *
   * <p>注意调用者必须自己关闭阅读器。
   *
   * @param path
   *     要读取的文件路径，不能为 null。
   * @param charset
   *     文件编码的字符集，不能为 null。
   * @return 字符串的数组列表。不能为 null，但可以为空。
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static List<String> readLines(final Path path, final Charset charset)
      throws IOException {
    try (final InputStream in = Files.newInputStream(path)) {
      final InputStreamReader reader = new InputStreamReader(in, charset);
      return readLines(reader);
    }
  }

  /**
   * 获取文件的内容作为字符串列表，每行一个条目。
   *
   * <p>注意调用者必须自己关闭阅读器。
   *
   * @param url
   *     要读取文件的 URL，不能为 null。
   * @param charset
   *     文件编码的字符集，不能为 null。
   * @return 字符串的数组列表。不能为 null，但可以为空。
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static List<String> readLines(final URL url, final Charset charset)
      throws IOException {
    try (final InputStream in = UrlUtils.openStream(url)) {
      final InputStreamReader reader = new InputStreamReader(in, charset);
      return readLines(reader);
    }
  }

  /**
   * 获取文件的内容作为字符串列表，每行一个条目。
   *
   * <p>注意调用者必须自己关闭阅读器。
   *
   * @param resource
   *     要读取的文件资源，不能为 null。
   * @param cls
   *     将使用其类加载器来加载资源的类。
   * @param charset
   *     文件编码的字符集，不能为 null。
   * @return 字符串的数组列表。不能为 null，但可以为空。
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static List<String> readLines(final String resource,
      final Class<?> cls, final Charset charset) throws IOException {
    final URL url = SystemUtils.getResource(resource, cls);
    if (url == null) {
      throw new IOException("Resource not found: " + resource);
    }
    return readLines(url, charset);
  }

  /**
   * 将集合中每个项的 {@code toString()} 值逐行写入 {@code Writer}，使用指定的行结束符。
   *
   * <p>注意调用此函数后，{@code Writer} 不会被刷新也不会被关闭，
   * 而是必须由调用者刷新或关闭。
   *
   * @param lines
   *     要写入的行，null 条目产生空行。
   * @param lineEnding
   *     要使用的行分隔符，null 表示系统默认值。
   * @param writer
   *     要写入的 {@code Writer}，非 null，未关闭。
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static void writeLines(final Iterable<String> lines,
      @Nullable final String lineEnding, final Writer writer)
      throws IOException {
    if (lines == null) {
      return;
    }
    final String ending = (lineEnding != null ? lineEnding : LINE_SEPARATOR);
    for (final String line : lines) {
      if ((line != null) && (line.length() > 0)) {
        writer.write(line);
      }
      writer.write(ending);
    }
  }

  /**
   * 将集合中每个项的 {@code toString()} 值逐行写入 {@code Writer}，使用指定的行结束符。
   *
   * <p>注意调用此函数后，{@code OutputStream} 不会被刷新也不会被关闭，
   * 而是必须由调用者刷新或关闭。
   *
   * @param lines
   *     要写入的行，null 条目产生空行。
   * @param lineEnding
   *     要使用的行分隔符，null 表示系统默认值。
   * @param output
   *     要写入的 {@code OutputStream}，非 null，未关闭。
   * @param charset
   *     文件编码的字符集，不能为 null。
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static void writeLines(final Iterable<String> lines,
      @Nullable final String lineEnding, final OutputStream output,
      final Charset charset) throws IOException {
    final Writer writer = new OutputStreamWriter(output, charset);
    writeLines(lines, lineEnding, writer);
  }

  /**
   * 将集合中每个项的 {@code toString()} 值逐行写入文件，使用指定的行结束符。
   *
   * @param lines
   *     要写入的行，null 条目产生空行。
   * @param lineEnding
   *     要使用的行分隔符，null 表示系统默认值。
   * @param file
   *     要写入的文件路径，非 null，未关闭。
   * @param charset
   *     文件编码的字符集，不能为 null。
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static void writeLines(final Iterable<String> lines,
      @Nullable final String lineEnding, final File file,
      final Charset charset) throws IOException {
    try (final OutputStream out = new FileOutputStream(file)) {
      writeLines(lines, lineEnding, out, charset);
    }
  }

  /**
   * 将集合中每个项的 {@code toString()} 值逐行写入文件，使用指定的行结束符。
   *
   * @param lines
   *     要写入的行，null 条目产生空行。
   * @param lineEnding
   *     要使用的行分隔符，null 表示系统默认值。
   * @param path
   *     要写入的文件路径，非 null，未关闭。
   * @param charset
   *     文件编码的字符集，不能为 null。
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static void writeLines(final Iterable<String> lines,
      @Nullable final String lineEnding, final Path path,
      final Charset charset) throws IOException {
    try (final OutputStream out = Files.newOutputStream(path)) {
      writeLines(lines, lineEnding, out, charset);
    }
  }

  /**
   * 按字典序比较两个输入流的内容。
   *
   * <p>如果输入流尚未缓冲，此方法会使用 {@code BufferedInputStream} 在内部缓冲输入。
   *
   * <p>注意调用此函数后，两个输入流不会被此函数关闭，
   * 而是必须由调用者关闭。
   *
   * @param in1
   *     第一个流
   * @param in2
   *     第二个流
   * @return 一个小于、等于或大于 0 的整数，如果第一个输入流的内容按字典序比较
   *     小于、等于或大于第二个输入流的内容。
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static int compareContent(final InputStream in1, final InputStream in2)
      throws IOException {
    final InputStream input1 = (in1 instanceof BufferedInputStream
                                ? in1
                                : new BufferedInputStream(in1));
    final InputStream input2 = (in2 instanceof BufferedInputStream
                                ? in2
                                : new BufferedInputStream(in2));
    int ch1 = input1.read();
    while (ch1 != -1) {
      final int ch2 = input2.read();
      if (ch1 != ch2) {
        if (ch2 == -1) {  // input2 is shorter than input1
          return +1;
        } else {
          return (ch1 - ch2);
        }
      }
      ch1 = input1.read();
    }
    if (input2.read() == -1) {
      return 0;
    } else {  // input2 is longer than input1
      return -1;
    }
  }

  /**
   * 按字典序比较两个阅读器的内容。
   *
   * <p>如果阅读器尚未缓冲，此方法会使用 {@code BufferedReader} 在内部缓冲输入。
   *
   * <p>注意调用此函数后，两个阅读器不会被此函数关闭，
   * 而是必须由调用者关闭。
   *
   * @param in1
   *     第一个阅读器
   * @param in2
   *     第二个阅读器
   * @return 一个小于、等于或大于 0 的整数，如果第一个阅读器的内容按字典序比较
   *     小于、等于或大于第二个阅读器的内容。
   * @throws IOException
   *     如果发生 I/O 错误
   */
  public static int compareContent(final Reader in1, final Reader in2)
      throws IOException {
    final Reader r1 = (in1 instanceof BufferedReader ? in1 : new BufferedReader(in1));
    final Reader r2 = (in2 instanceof BufferedReader ? in2 : new BufferedReader(in2));
    int ch1 = r1.read();
    while (ch1 != -1) {
      final int ch2 = r2.read();
      if (ch1 != ch2) {
        if (ch2 == -1) {  // r2 is shorter than r1
          return +1;
        } else {
          return (ch1 - ch2);
        }
      }
      ch1 = r1.read();
    }
    if (r2.read() == -1) {
      return 0;
    } else {  // r2 is longer than r1
      return -1;
    }
  }
}