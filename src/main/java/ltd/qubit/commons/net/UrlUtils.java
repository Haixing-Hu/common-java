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
   * Given a host name, returns the domain suffix corresponding to the last
   * public part of the host name.
   *
   * <p>For example,
   * <pre><code>
   * UrlUtils.getDomainSuffix("www.google.com")    = "com"
   * UrlUtils.getDomainSuffix("www.sina.com.cn")   = "com.cn"
   * </code></pre>
   *
   * @param host
   *     a host name.
   * @return the domain suffix corresponding to the last public part of the host
   *     name.
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
   * Opens an {@link InputStream} for a {@link URL}.
   * <p>
   * This function does the same thing as {@link URL#openStream()}, except
   * that it will decode the compressed stream.
   *
   * @param url
   *     an {@link URL} object.
   * @return the {@link InputStream} opened for the URL. If the content is
   *     encoded using some compression algorithm, the function will decode the
   *     encoded stream.
   * @throws IOException
   *     if any error occurs.
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
   * Opens an {@link InputStream} for a {@link URL}.
   *
   * <p>This function does the same thing as {@link URL#openStream()}, except
   * that it will decode the compressed stream.
   *
   * @param url
   *     an {@link Url} object.
   * @return the {@link InputStream} opened for the URL. If the content is
   *     encoded using some compression algorithm, the function will decode the
   *     encoded stream.
   * @throws IOException
   *     if any error occurs.
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
   * Opens an {@link InputStream} for a {@link URI}.
   *
   * <p>This function does the same thing as {@link URL#openStream()}, except
   * that it will decode the compressed stream.
   *
   * @param uri
   *     an {@link URI} object.
   * @return the {@link InputStream} opened for the URL. If the content is
   *     encoded using some compression algorithm, the function will decode the
   *     encoded stream.
   * @throws IOException
   *     if any error occurs.
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
   * Converts a list of strings to a list of {@link Url} objects.
   *
   * @param urls
   *     the list of strings to be converted.
   * @return the list of {@link Url} objects converted from the list of strings.
   * @throws MalformedURLException
   *     if any string in the list is a malformed URL.
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

  public static File download(final URL url) throws IOException {
    final File temp = FileUtils.getTempFile();
    download(url, temp);
    return temp;
  }

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
   * Build a URI with parameters.
   *
   * @param uri
   *     the specified URI.
   * @param params
   *     the parameters.
   * @return
   *     the built URI with parameters.
   * @throws RuntimeException
   *     if the format of the URI is invalid.
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
   * Build a BASE-64 encoded data URL from the binary data represented as a
   * BASE-64 encoded string.
   *
   * @param mimeType
   *     the MIME type of the data.
   * @param base64String
   *     the BASE-64 encoded string.
   * @return
   *     the built URL, represented as a string. Note that the URL cannot be
   *     represented as a {@link URL} object, since the old {@link URL} class
   *     does not support the {@code data:} protocol.
   */
  public static String buildBase64DataUrl(final String mimeType, final String base64String) {
    requireNonNull("mimeType", mimeType);
    requireNonNull("base64String", base64String);
    return "data:" + mimeType + ";base64," + base64String;
  }

  /**
   * Build a BASE-64 encoded data URI from the binary data represented as a
   * BASE-64 encoded string.
   *
   * @param mimeType
   *     the MIME type of the data.
   * @param base64String
   *     the BASE-64 encoded string.
   * @return
   *     the built URI.
   */
  public static URI buildBase64DataUri(final String mimeType, final String base64String) {
    final String url = buildBase64DataUrl(mimeType, base64String);
    return URI.create(url);
  }

  /**
   * Build a BASE-64 encoded data URL from the binary data.
   *
   * @param mimeType
   *     the MIME type of the data.
   * @param data
   *     the binary data.
   * @return
   *     the built URL, represented as a string. Note that the URL cannot be
   *     represented as a {@link URL} object, since the old {@link URL} class
   *     does not support the {@code data:} protocol.
   * @throws RuntimeException
   *     if any I/O error occurs.
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
   * Build a BASE-64 encoded data URL from the binary data.
   *
   * @param mimeType
   *     the MIME type of the data.
   * @param data
   *     the binary data.
   * @return
   *     the built URI.
   */
  public static URI buildBase64DataUri(final String mimeType, final byte[] data) {
    final String url = buildBase64DataUrl(mimeType, data);
    return URI.create(url);
  }
}