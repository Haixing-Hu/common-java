////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.io.FileUtils;
import ltd.qubit.commons.io.IoUtils;
import ltd.qubit.commons.util.codec.Base64Utils;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.net.InetAddressUtils.isIPv4Address;

/**
 * 此类提供处理URL的实用函数。
 *
 * @author 胡海星
 */
@ThreadSafe
public final class UrlUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(UrlUtils.class);

  /**
   * 给定主机名，返回主机名的域名。主机的域名是主机名中不包含子域名的子字符串。
   *
   * <p>例如：
   * <pre><code>
   * UrlUtils.getDomain("www.google.com")    = "google.com"
   * UrlUtils.getDomain("www.sina.com.cn")   = "sina.com.cn"
   * </code></pre>
   *
   * @param host
   *     主机名
   * @return 主机名的域名。
   */
  public static String getDomain(@Nonnull final String host) {
    if (isIPv4Address(host)) {
      return host;
    }
    final DomainSuffixRegistry tlds = DomainSuffixRegistry.getInstance();
    String candidate = host;
    int index = 0;
    while (index >= 0) {
      index = candidate.indexOf('.');
      final String subCandidate = candidate.substring(index + 1);
      if (tlds.isDomainSuffix(subCandidate)) {
        return candidate;
      }
      candidate = subCandidate;
    }
    return candidate;
  }

  /**
   * 给定主机名，返回对应于主机名最后公共部分的域名后缀。
   *
   * <p>例如：
   * <pre><code>
   * UrlUtils.getDomainSuffix("www.google.com")    = "com"
   * UrlUtils.getDomainSuffix("www.sina.com.cn")   = "com.cn"
   * </code></pre>
   *
   * @param host
   *     主机名
   * @return 对应于主机名最后公共部分的域名后缀
   */
  public static DomainSuffix getDomainSuffix(@Nonnull final String host) {
    final DomainSuffixRegistry tlds = DomainSuffixRegistry.getInstance();
    String candidate = host;
    int index = 0;
    while (index >= 0) {
      index = candidate.indexOf('.');
      final String subCandidate = candidate.substring(index + 1);
      final DomainSuffix suffix = tlds.getDomainSuffix(subCandidate);
      if (suffix != null) {
        return suffix;
      }
      candidate = subCandidate;
    }
    return null;
  }

  /**
   * 为{@link URL}打开{@link InputStream}。
   * <p>
   * 此函数与{@link URL#openStream()}执行相同的操作，但它会解码压缩流。
   *
   * @param url
   *     {@link URL}对象
   * @return 为URL打开的{@link InputStream}。如果内容使用某种压缩算法编码，
   *         该函数将解码编码的流
   * @throws IOException
   *     如果发生任何错误
   */
  public static InputStream openStream(final URL url) throws IOException {
    final String userAgent = RandomUserAgent.get(RandomUserAgent.CHROME);
    final URLConnection connection = url.openConnection();
    connection.setRequestProperty("User-Agent", userAgent);
    final String encoding = connection.getContentEncoding();
    final InputStream input = connection.getInputStream();
    if (encoding == null) {
      return input;
    } else if ("gzip".equals(encoding) || "x-gzip".equals(encoding)) {
      return new GZIPInputStream(input);
    } else if ("deflate".equals(encoding)) {
      return new InflaterInputStream(input);
    } else {
      LOGGER.warn("Unknown content encoding '{}'."
          + "The stream is returned without decoding: {}", encoding, url);
      return input;
    }
  }

  /**
   * 为{@link Url}打开{@link InputStream}。
   *
   * <p>此函数与{@link URL#openStream()}执行相同的操作，但它会解码压缩流。
   *
   * @param url
   *     {@link Url}对象
   * @return 为URL打开的{@link InputStream}。如果内容使用某种压缩算法编码，
   *         该函数将解码编码的流
   * @throws IOException
   *     如果发生任何错误
   */
  public static InputStream openStream(final Url url) throws IOException {
    final URL theUrl;
    try {
      theUrl = url.toURL();
    } catch (final MalformedURLException e) {
      throw new IOException(e);
    }
    return openStream(theUrl);
  }

  /**
   * 为{@link URI}打开{@link InputStream}。
   *
   * <p>此函数与{@link URL#openStream()}执行相同的操作，但它会解码压缩流。
   *
   * @param uri
   *     {@link URI}对象
   * @return 为URI打开的{@link InputStream}。如果内容使用某种压缩算法编码，
   *         该函数将解码编码的流
   * @throws IOException
   *     如果发生任何错误
   */
  public static InputStream openStream(final URI uri) throws IOException {
    final URL url;
    try {
      url = uri.toURL();
    } catch (final MalformedURLException e) {
      throw new IOException(e);
    }
    return openStream(url);
  }

  /**
   * 将字符串列表转换为{@link Url}对象列表。
   *
   * @param urls
   *     要转换的字符串列表
   * @return 从字符串列表转换的{@link Url}对象列表
   * @throws MalformedURLException
   *     如果列表中的任何字符串是格式错误的URL
   */
  public static List<Url> toUrls(final List<String> urls)
      throws MalformedURLException {
    final List<Url> result = new ArrayList<>();
    if (urls != null) {
      for (final String url : urls) {
        result.add(new Url(url));
      }
    }
    return result;
  }

  /**
   * 下载URL内容到临时文件。
   *
   * @param url
   *     要下载的URL
   * @return 下载内容的临时文件
   * @throws IOException
   *     如果发生I/O错误
   */
  public static File download(final URL url) throws IOException {
    final File temp = FileUtils.getTempFile();
    download(url, temp);
    return temp;
  }

  /**
   * 下载URL内容到指定文件。
   *
   * @param url
   *     要下载的URL
   * @param outputFile
   *     输出文件
   * @throws IOException
   *     如果发生I/O错误
   */
  public static void download(final URL url, final File outputFile)
      throws IOException {
    OutputStream output = null;
    InputStream input = null;
    try {
      input = openStream(url);
      output = Files.newOutputStream(outputFile.toPath());
      IoUtils.copy(input, output);
    } catch (final IOException e) {
      LOGGER.error("Failed to download '{}': {}", url, e.getMessage(), e);
      throw e;
    } finally {
      IoUtils.closeQuietly(input);
      IoUtils.closeQuietly(output);
    }
  }

  /**
   * 下载URL内容为字符串。
   *
   * @param url
   *     要下载的URL
   * @param charset
   *     字符编码
   * @return 下载的内容字符串
   * @throws IOException
   *     如果发生I/O错误
   */
  public static String downloadAsString(final URL url, final Charset charset)
      throws IOException {
    try (final InputStream input = openStream(url)) {
      return IoUtils.toString(input, charset);
    } catch (final IOException e) {
      LOGGER.error("Failed to download '{}': {}", url, e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 构建带参数的URI。
   *
   * @param uri
   *     指定的URI
   * @param params
   *     参数映射
   * @return
   *     构建的带参数URI
   * @throws RuntimeException
   *     如果URI格式无效
   */
  public static URI buildUri(final URI uri, final Map<String, String> params) {
    final UriBuilder builder = new UriBuilder(uri);
    params.forEach(builder::addParameter);
    try {
      return builder.build();
    } catch (final URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 从表示为BASE-64编码字符串的二进制数据构建BASE-64编码的数据URL。
   *
   * @param mimeType
   *     数据的MIME类型
   * @param base64String
   *     BASE-64编码字符串
   * @return
   *     构建的URL，表示为字符串。注意URL不能表示为{@link URL}对象，
   *     因为旧的{@link URL}类不支持{@code data:}协议
   */
  public static String buildBase64DataUrl(final String mimeType, final String base64String) {
    requireNonNull("mimeType", mimeType);
    requireNonNull("base64String", base64String);
    return "data:" + mimeType + ";base64," + base64String;
  }

  /**
   * 从表示为BASE-64编码字符串的二进制数据构建BASE-64编码的数据URI。
   *
   * @param mimeType
   *     数据的MIME类型
   * @param base64String
   *     BASE-64编码字符串
   * @return
   *     构建的URI
   */
  public static URI buildBase64DataUri(final String mimeType, final String base64String) {
    final String url = buildBase64DataUrl(mimeType, base64String);
    return URI.create(url);
  }

  /**
   * 从二进制数据构建BASE-64编码的数据URL。
   *
   * @param mimeType
   *     数据的MIME类型
   * @param data
   *     二进制数据
   * @return
   *     构建的URL，表示为字符串。注意URL不能表示为{@link URL}对象，
   *     因为旧的{@link URL}类不支持{@code data:}协议
   * @throws RuntimeException
   *     如果发生任何I/O错误
   */
  public static String buildBase64DataUrl(final String mimeType, final byte[] data) {
    requireNonNull("mimeType", mimeType);
    requireNonNull("data", data);
    try {
      final String base64String = Base64Utils.encodeToString(data);
      return "data:" + mimeType + ";base64," + base64String;
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 从二进制数据构建BASE-64编码的数据URI。
   *
   * @param mimeType
   *     数据的MIME类型
   * @param data
   *     二进制数据
   * @return
   *     构建的URI
   */
  public static URI buildBase64DataUri(final String mimeType, final byte[] data) {
    final String url = buildBase64DataUrl(mimeType, data);
    return URI.create(url);
  }
}