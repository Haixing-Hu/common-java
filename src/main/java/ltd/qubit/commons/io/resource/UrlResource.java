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
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.io.resource.ResourceUtils.cleanPath;
import static ltd.qubit.commons.io.resource.ResourceUtils.toRelativeURL;
import static ltd.qubit.commons.io.resource.ResourceUtils.toURI;
import static ltd.qubit.commons.io.resource.ResourceUtils.toURL;

/**
 * {@link Resource} a {@code java.net.URL} 的实现。
 * <p>
 * 支持将此资源解析为 {@code URL}；如果此资源对应于{@code "file:"} 协议，也支持
 * 将其解析为 {@code File}。
 * <p>
 * 此类是 {@code org.springframework.core.io.UrlResource} 的一个副本，
 * 略有修改。它用于避免对 Spring Framework 的依赖。
 *
 * @author 胡海星
 * @see URL
 */
public class UrlResource extends AbstractFileResolvingResource {

  private static final String AUTHORIZATION = "Authorization";

  /**
   * 原始的URI（如果可用）；用于访问URI和文件。
   */
  @Nullable
  private final URI uri;

  /**
   * 原始的URL；用于实际访问。
   */
  private final URL url;

  /**
   * 清理后的URL字符串（路径已经规范化），用于比较。
   */
  @Nullable
  private volatile String cleanedUrl;


  /**
   * 根据给定的URL对象创建一个新的 {@code UrlResource}。
   *
   * @param url
   *     一个 URL。
   * @see #UrlResource(URI)
   * @see #UrlResource(String)
   */
  public UrlResource(final URL url) {
    if (url == null) {
      throw new IllegalArgumentException("URL must not be null");
    }
    this.uri = null;
    this.url = url;
  }

  /**
   * 根据给定的URI对象创建一个新的 {@code UrlResource}。
   *
   * @param uri
   *     一个 URI。
   * @throws MalformedURLException
   *     如果给定的URI无法转换为URL。
   */
  public UrlResource(final URI uri) throws MalformedURLException {
    if (uri == null) {
      throw new IllegalArgumentException("URI must not be null");
    }
    this.uri = uri;
    this.url = uri.toURL();
  }

  /**
   * 根据给定的URL路径创建一个新的 {@code UrlResource}。
   * <p>
   * 请注意：如有需要，给定的路径应预先进行编码。
   *
   * @param path
   *     一个 URL 路径。
   * @throws MalformedURLException
   *     如果给定的URL路径无效。
   * @see ResourceUtils#toURI(String)
   */
  public UrlResource(final String path) throws MalformedURLException {
    if (path == null) {
      throw new IllegalArgumentException("Path must not be null");
    }
    final String cleanedPath = cleanPath(path);
    URI theUri;
    URL theUrl;
    try {
      // Prefer URI construction with toURL conversion (as of 6.1)
      theUri = toURI(cleanedPath);
      theUrl = theUri.toURL();
    } catch (final URISyntaxException | IllegalArgumentException ex) {
      theUri = null;
      theUrl = toURL(path);
    }
    this.uri = theUri;
    this.url = theUrl;
    this.cleanedUrl = cleanedPath;
  }

  /**
   * 根据给定的URI规范创建一个新的 {@code UrlResource}。
   * <p>
   * 如有需要，给定的各个部分将自动进行编码。
   *
   * @param protocol
   *     要使用的URL协议（例如"jar"或"file"，不带冒号）；也称为"scheme"。
   * @param location
   *     该协议中的位置（例如文件路径）；也称为"scheme-specific part"。
   * @throws MalformedURLException
   *     如果给定的URL规范无效。
   * @see URI#URI(String, String, String)
   */
  public UrlResource(final String protocol, final String location)
      throws MalformedURLException {
    this(protocol, location, null);
  }

  /**
   * 根据给定的URI规范创建一个新的 {@code UrlResource}。
   * <p>
   * 如有需要，给定的各个部分将自动进行编码。
   *
   * @param protocol
   *     要使用的URL协议（例如"jar"或"file"，不带冒号）；也称为"scheme"。
   * @param location
   *     该协议中的位置（例如文件路径）；也称为"scheme-specific part"。
   * @param fragment
   *     该位置中的片段（例如HTML页面上的锚点，跟在"#"分隔符之后）。
   * @throws MalformedURLException
   *     如果给定的URL规范无效。
   * @see URI#URI(String, String, String)
   */
  public UrlResource(final String protocol, final String location,
      @Nullable final String fragment) throws MalformedURLException {
    try {
      this.uri = new URI(protocol, location, fragment);
      this.url = this.uri.toURL();
    } catch (final URISyntaxException e) {
      final MalformedURLException ex = new MalformedURLException(e.getMessage());
      ex.initCause(e);
      throw ex;
    }
  }

  /**
   * 根据给定的 {@link URI} 创建一个新的 {@code UrlResource}。
   * <p>
   * 此工厂方法是 {@link #UrlResource(URI)} 的便捷方式，它会捕获任何
   * {@link MalformedURLException} 并将其包装在 {@link UncheckedIOException}
   * 中重新抛出；适用于在 {@link Stream} 和 {@link Optional} API
   * 或其他不希望出现受检 {@link IOException} 的场景中使用。
   *
   * @param uri
   *     一个 URI。
   * @throws UncheckedIOException
   *     如果给定的URI无法转为URL。
   * @see #UrlResource(URI)
   */
  public static UrlResource from(final URI uri) throws UncheckedIOException {
    try {
      return new UrlResource(uri);
    } catch (final MalformedURLException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  /**
   * 根据给定的URL路径创建一个新的 {@code UrlResource}。
   * <p>
   * 此工厂方法是 {@link #UrlResource(String)} 的便捷方式，它会捕获任何
   * {@link MalformedURLException} 并将其包装在 {@link UncheckedIOException}
   * 中重新抛出；适用于在 {@link Stream} 和 {@link Optional} API
   * 或其他不希望出现受检 {@link IOException} 的场景中使用。
   *
   * @param path
   *     一个URL路径。
   * @throws UncheckedIOException
   *     如果给定的URL路径无效。
   * @see #UrlResource(String)
   */
  public static UrlResource from(final String path)
      throws UncheckedIOException {
    try {
      return new UrlResource(path);
    } catch (final MalformedURLException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  /**
   * 为给定的原始URL延迟确定一个清理过的URL。
   */
  private String getCleanedUrl() {
    String theCleanedUrl = this.cleanedUrl;
    if (theCleanedUrl != null) {
      return theCleanedUrl;
    }
    final String originalPath = (uri != null ? uri : url).toString();
    theCleanedUrl = cleanPath(originalPath);
    this.cleanedUrl = theCleanedUrl;
    return theCleanedUrl;
  }

  /**
   * 此实现返回此资源引用的URL的 {@link InputStream}。
   * <p>
   * 如果需要，它会打开一个 {@link URLConnection}，并在返回的输入流上委托其
   * {@link URLConnection#getInputStream()} 方法。
   * <p>
   * 请注意，此实现将为每次调用返回一个新的 {@link InputStream}。调用者有责任关闭每个流。
   *
   * @return 此资源的 {@link InputStream}。
   * @throws IOException
   *     如果发生I/O错误。
   * @see URL#openConnection()
   * @see URLConnection#getInputStream()
   */
  @Nonnull
  @Override
  public InputStream getInputStream() throws IOException {
    final URLConnection con = url.openConnection();
    customizeConnection(con);
    try {
      return con.getInputStream();
    } catch (final IOException ex) {
      // Close the HTTP connection (if applicable).
      if (con instanceof HttpURLConnection) {
        final HttpURLConnection httpConn = (HttpURLConnection) con;
        httpConn.disconnect();
      }
      throw ex;
    }
  }

  /**
   * 自定义此资源用于获取 {@link InputStream} 的 {@link URLConnection}。
   * <p>
   * 对于 {@link HttpURLConnection}，此实现将 "Authorization"
   * 标头设置为 {@linkplain URL#getUserInfo() a Basic Authentication credential}，
   * 前提是 {@code userInfo} 可用。
   * <p>
   * 可以在子类中重写以自定义连接，例如设置请求属性。
   *
   * @param con
   *     要自定义的 {@link URLConnection}。
   * @throws IOException
   *     如果发生I/O错误。
   */
  @Override
  protected void customizeConnection(final URLConnection con) throws IOException {
    super.customizeConnection(con);
    final String userInfo = this.url.getUserInfo();
    if (userInfo != null) {
      final Encoder encoder = Base64.getUrlEncoder();
      final String encodedCredentials = encoder.encodeToString(userInfo.getBytes());
      con.setRequestProperty(AUTHORIZATION, "Basic " + encodedCredentials);
    }
  }

  /**
   * 此实现返回此资源引用的 {@link URL}。
   *
   * @return 此资源的 {@link URL}。
   * @throws IOException
   *     如果URL无法解析。
   */
  @Override
  public URL getURL() {
    return this.url;
  }

  /**
   * 此实现返回此资源引用的 {@link URI}。
   *
   * @return 此资源的 {@link URI}。
   * @throws IOException
   *     如果URI无法解析。
   */
  @Override
  public URI getURI() throws IOException {
    if (this.uri != null) {
      return this.uri;
    } else {
      return super.getURI();
    }
  }

  /**
   * 此实现通过 {@link ResourceUtils#isFileURL(URL)} 检查URL是否为文件URL，
   * 并委托给 {@link #getFile(URI)} 的结果（如果可用）。
   *
   * @return 此资源是否对应文件系统中的文件。
   * @see #getFile()
   * @see ResourceUtils#isFileURL(URL)
   */
  @Override
  public boolean isFile() {
    if (this.uri != null) {
      return super.isFile(this.uri);
    } else {
      return super.isFile();
    }
  }

  /**
   * 此实现返回一个 {@link File}，该文件引用了底层的URL，前提是它引用了文件系统中的
   * 一个文件（即，它具有"file"协议）。
   *
   * @return 对应的文件。
   * @throws IOException
   *     如果URL无法解析为绝对文件路径，即它不指向文件系统中的文件。
   * @see ResourceUtils#getFile(URL, String)
   */
  @Override
  public File getFile() throws IOException {
    if (this.uri != null) {
      return super.getFile(this.uri);
    } else {
      return super.getFile();
    }
  }

  /**
   * 此实现为给定的相对路径创建一个 {@code UrlResource}，并委托给
   * {@link #createRelativeURL(String)}。
   *
   * @param relativePath
   *     相对路径（相对于此资源）。
   * @return 相对路径的资源。
   * @throws MalformedURLException
   *     如果无法为相对路径构造URL。
   * @see #createRelativeURL(String)
   */
  @Override
  public Resource createRelative(final String relativePath)
      throws MalformedURLException {
    return new UrlResource(createRelativeURL(relativePath));
  }

  /**
   * 此实现委托给 {@link ResourceUtils#toRelativeURL(URL, String)}
   * 来创建相对URL。
   *
   * @param relativePath
   *     相对路径（相对于此资源）。
   * @return 相对路径的URL。
   * @throws MalformedURLException
   *     如果无法为相对路径构造URL。
   * @see ResourceUtils#toRelativeURL(URL, String)
   */
  protected URL createRelativeURL(String relativePath) throws MalformedURLException {
    if (relativePath.startsWith("/")) {
      relativePath = relativePath.substring(1);
    }
    return toRelativeURL(this.url, relativePath);
  }

  /**
   * 此实现返回URL的文件名。
   * <p>
   * 程序首先检查URL的文件名是否为空。如果为空，则程序从URL路径的最后一部分解码文件名。
   *
   * @return URL的文件名，如果URL的文件名为空，则为 {@code null}。
   * @see URL#getFile()
   * @see File#getName()
   */
  @Override
  @Nullable
  public String getFilename() {
    if (uri != null) {
      final String path = uri.getPath();
      if (path != null) {
        // Prefer URI path: decoded and has standard separators
        return ResourceUtils.getFilename(uri.getPath());
      }
    }
    // Otherwise, process URL path
    final String filename = ResourceUtils.getFilename(cleanPath(url.getPath()));
    if (filename != null) {
      return URLDecoder.decode(filename, StandardCharsets.UTF_8);
    } else {
      return null;
    }
  }

  /**
   * 此实现返回一个描述，其中包含URL。
   *
   * @return 描述字符串。
   */
  @Override
  public String getDescription() {
    return "URL [" + (uri != null ? uri : url) + "]";
  }

  /**
   * 此实现比较底层的URL对象。
   */
  @Override
  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (this.getClass() != o.getClass())) {
      return false;
    }
    final UrlResource other = (UrlResource) o;
    return (getCleanedUrl().equals(other.getCleanedUrl()));
  }

  /**
   * 此实现返回清理后URL的哈希码。
   */
  @Override
  public int hashCode() {
    return getCleanedUrl().hashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("uri", uri)
        .append("url", url)
        .append("cleanedUrl", cleanedUrl)
        .toString();
  }
}