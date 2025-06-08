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
import java.nio.charset.Charset;
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
 * {@link Resource} implementation for {@code java.net.URL} locators. Supports
 * resolution as a {@code URL} and also as a {@code File} in case of the
 * {@code "file:"} protocol.
 * <p>
 * This class is a copy of {@code org.springframework.core.io.UrlResource} with
 * slight modifications. It is used to avoid the dependency of Spring
 * Framework.
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Haxiing Hu
 * @see URL
 */
public class UrlResource extends AbstractFileResolvingResource {

  private static final String AUTHORIZATION = "Authorization";

  /**
   * Original URI, if available; used for URI and File access.
   */
  @Nullable
  private final URI uri;

  /**
   * Original URL, used for actual access.
   */
  private final URL url;

  /**
   * Cleaned URL String (with normalized path), used for comparisons.
   */
  @Nullable
  private volatile String cleanedUrl;


  /**
   * Create a new {@code UrlResource} based on the given URL object.
   *
   * @param url
   *     a URL
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
   * Create a new {@code UrlResource} based on the given URI object.
   *
   * @param uri
   *     a URI
   * @throws MalformedURLException
   *     if the given URL path is not valid
   * @since 2.5
   */
  public UrlResource(final URI uri) throws MalformedURLException {
    if (uri == null) {
      throw new IllegalArgumentException("URI must not be null");
    }
    this.uri = uri;
    this.url = uri.toURL();
  }

  /**
   * Create a new {@code UrlResource} based on a URI path.
   * <p>Note: The given path needs to be pre-encoded if necessary.
   *
   * @param path
   *     a URI path
   * @throws MalformedURLException
   *     if the given URI path is not valid
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
   * Create a new {@code UrlResource} based on a URI specification.
   * <p>
   * The given parts will automatically get encoded if necessary.
   *
   * @param protocol
   *     the URL protocol to use (e.g. "jar" or "file" - without colon); also
   *     known as "scheme"
   * @param location
   *     the location (e.g. the file path within that protocol); also known as
   *     "scheme-specific part"
   * @throws MalformedURLException
   *     if the given URL specification is not valid
   * @see URI#URI(String, String, String)
   */
  public UrlResource(final String protocol, final String location)
      throws MalformedURLException {
    this(protocol, location, null);
  }

  /**
   * Create a new {@code UrlResource} based on a URI specification.
   * <p>
   * The given parts will automatically get encoded if necessary.
   *
   * @param protocol
   *     the URL protocol to use (e.g. "jar" or "file" - without colon); also
   *     known as "scheme"
   * @param location
   *     the location (e.g. the file path within that protocol); also known as
   *     "scheme-specific part"
   * @param fragment
   *     the fragment within that location (e.g. anchor on an HTML page, as
   *     following after a "#" separator)
   * @throws MalformedURLException
   *     if the given URL specification is not valid
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
   * Create a new {@code UrlResource} from the given {@link URI}.
   * <p>
   * This factory method is a convenience for {@link #UrlResource(URI)} that
   * catches any {@link MalformedURLException} and rethrows it wrapped in an
   * {@link UncheckedIOException}; suitable for use in
   * {@link Stream} and {@link Optional} APIs or
   * other scenarios when a checked {@link IOException} is undesirable.
   *
   * @param uri
   *     a URI
   * @throws UncheckedIOException
   *     if the given URL path is not valid
   * @see #UrlResource(URI)
   * @since 6.0
   */
  public static UrlResource from(final URI uri) throws UncheckedIOException {
    try {
      return new UrlResource(uri);
    } catch (final MalformedURLException ex) {
      throw new UncheckedIOException(ex);
    }
  }

  /**
   * Create a new {@code UrlResource} from the given URL path.
   * <p>This factory method is a convenience for {@link #UrlResource(String)}
   * that catches any {@link MalformedURLException} and rethrows it wrapped in
   * an {@link UncheckedIOException}; suitable for use in
   * {@link Stream} and {@link Optional} APIs or
   * other scenarios when a checked {@link IOException} is undesirable.
   *
   * @param path
   *     a URL path
   * @throws UncheckedIOException
   *     if the given URL path is not valid
   * @see #UrlResource(String)
   * @since 6.0
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
   * Lazily determine a cleaned URL for the given original URL.
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
   * This implementation opens an InputStream for the given URL.
   * <p>It sets the {@code useCaches} flag to {@code false},
   * mainly to avoid jar file locking on Windows.
   *
   * @see URL#openConnection()
   * @see URLConnection#setUseCaches(boolean)
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
   * This implementation returns the underlying URL reference.
   */
  @Override
  public URL getURL() {
    return this.url;
  }

  /**
   * This implementation returns the underlying URI directly, if possible.
   */
  @Override
  public URI getURI() throws IOException {
    if (this.uri != null) {
      return this.uri;
    } else {
      return super.getURI();
    }
  }

  @Override
  public boolean isFile() {
    if (this.uri != null) {
      return super.isFile(this.uri);
    } else {
      return super.isFile();
    }
  }

  /**
   * This implementation returns a File reference for the underlying URL/URI,
   * provided that it refers to a file in the file system.
   *
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
   * This implementation creates a {@code UrlResource}, delegating to
   * {@link #createRelativeURL(String)} for adapting the relative path.
   *
   * @see #createRelativeURL(String)
   */
  @Override
  public Resource createRelative(final String relativePath)
      throws MalformedURLException {
    return new UrlResource(createRelativeURL(relativePath));
  }

  /**
   * This delegate creates a {@code java.net.URL}, applying the given path
   * relative to the path of the underlying URL of this resource descriptor. A
   * leading slash will get dropped; a "#" symbol will get encoded.
   *
   * @see #createRelative(String)
   * @see ResourceUtils#toRelativeURL(URL, String)
   * @since 5.2
   */
  protected URL createRelativeURL(String relativePath) throws MalformedURLException {
    if (relativePath.startsWith("/")) {
      relativePath = relativePath.substring(1);
    }
    return toRelativeURL(this.url, relativePath);
  }

  /**
   * This implementation returns the URL-decoded name of the file that this URL
   * refers to.
   *
   * @see URL#getPath()
   * @see URLDecoder#decode(String, Charset)
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
   * This implementation returns a description that includes the URL.
   */
  @Override
  public String getDescription() {
    return "URL [" + (uri != null ? uri : url) + "]";
  }

  /**
   * This implementation compares the underlying URL references.
   */
  @Override
  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UrlResource)) {
      return false;
    }
    final UrlResource other = (UrlResource) o;
    return (getCleanedUrl().equals(other.getCleanedUrl()));
  }

  /**
   * This implementation returns the hash code of the underlying URL reference.
   */
  @Override
  public int hashCode() {
    return getCleanedUrl().hashCode();
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("uri", uri)
        .append("url", url)
        .append("cleanedUrl", cleanedUrl)
        .toString();
  }
}