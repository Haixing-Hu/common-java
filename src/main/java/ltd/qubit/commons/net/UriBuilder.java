////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotNull;

import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.util.pair.NameValuePair;

import static java.nio.charset.StandardCharsets.UTF_8;

import static ltd.qubit.commons.lang.ObjectUtils.defaultIfNull;

/**
 * A builder used to build the URI with parameters.
 *
 * @author Haixing Hu
 */
public class UriBuilder {

  private String scheme;
  private String encodedSchemeSpecificPart;
  private String encodedAuthority;
  private String userInfo;
  private String encodedUserInfo;
  private String host;
  private int port;
  private String encodedPath;
  private List<String> pathSegments;
  private String encodedQuery;
  private List<NameValuePair> queryParams;
  private String query;
  private Charset charset = null;
  private String fragment;
  private String encodedFragment;

  /**
   * Constructs an empty instance.
   */
  public UriBuilder() {
    this.port = -1;
  }

  /**
   * Construct an instance from the string which must be a valid URI.
   *
   * @param string
   *     a valid URI in string form
   * @throws URISyntaxException
   *     if the input is not a valid URI
   */
  public UriBuilder(final String string) throws URISyntaxException {
    this(new URI(string), null);
  }

  /**
   * Construct an instance from the provided URI.
   *
   * @param uri
   *     the provided URI.
   */
  public UriBuilder(final URI uri) {
    this(uri, null);
  }

  /**
   * Construct an instance from the string which must be a valid URI.
   *
   * @param string
   *     a valid URI in string form
   * @throws URISyntaxException
   *     if the input is not a valid URI
   */
  public UriBuilder(final String string, final Charset charset)
      throws URISyntaxException {
    this(new URI(string), charset);
  }

  /**
   * Construct an instance from the provided URI.
   *
   * @param uri
   *     the provided URI.
   * @param charset
   *     the specified charset.
   */
  public UriBuilder(final URI uri, final Charset charset) {
    setCharset(charset);
    digestUri(uri);
  }

  /**
   * Construct an instance.
   *
   * @param charset
   *     the specified charset.
   */
  public UriBuilder setCharset(final Charset charset) {
    this.charset = charset;
    return this;
  }

  /**
   * Gets the encoding charset.
   *
   * @return the encoding charset.
   */
  public Charset getCharset() {
    return charset;
  }

  private List<NameValuePair> parseQuery(final String query,
      final Charset charset) {
    if (query != null && !query.isEmpty()) {
      return UrlEncodingUtils.parse(query, charset);
    }
    return null;
  }

  private List<String> parsePath(final String path, final Charset charset) {
    if (path != null && !path.isEmpty()) {
      return UrlEncodingUtils.parsePathSegments(path, charset);
    }
    return null;
  }

  /**
   * Builds a {@link URI} instance.
   *
   * @return the building result.
   */
  public URI build() throws URISyntaxException {
    return new URI(buildString());
  }

  private String buildString() {
    final StringBuilder sb = new StringBuilder();
    if (this.scheme != null) {
      sb.append(this.scheme).append(':');
    }
    if (this.encodedSchemeSpecificPart != null) {
      sb.append(this.encodedSchemeSpecificPart);
    } else {
      if (this.encodedAuthority != null) {
        sb.append("//").append(this.encodedAuthority);
      } else if (this.host != null) {
        sb.append("//");
        if (this.encodedUserInfo != null) {
          sb.append(this.encodedUserInfo).append("@");
        } else if (this.userInfo != null) {
          sb.append(encodeUserInfo(this.userInfo)).append("@");
        }
        if (InetAddressUtils.isIPv6Address(this.host)) {
          sb.append("[").append(this.host).append("]");
        } else {
          sb.append(this.host);
        }
        if (this.port >= 0) {
          sb.append(":").append(this.port);
        }
      }
      if (this.encodedPath != null) {
        sb.append(normalizePath(this.encodedPath, sb.length() == 0));
      } else if (this.pathSegments != null) {
        sb.append(encodePath(this.pathSegments));
      }
      if (this.encodedQuery != null) {
        sb.append("?").append(this.encodedQuery);
      } else if (this.queryParams != null && !this.queryParams.isEmpty()) {
        sb.append("?").append(encodeUrlForm(this.queryParams));
      } else if (this.query != null) {
        sb.append("?").append(encodeUric(this.query));
      }
    }
    if (this.encodedFragment != null) {
      sb.append("#").append(this.encodedFragment);
    } else if (this.fragment != null) {
      sb.append("#").append(encodeUric(this.fragment));
    }
    return sb.toString();
  }

  private static String normalizePath(final String path,
      final boolean relative) {
    String s = path;
    if (StringUtils.isBlank(s)) {
      return "";
    }
    if (!relative && !s.startsWith("/")) {
      s = "/" + s;
    }
    return s;
  }

  private void digestUri(final URI uri) {
    scheme = uri.getScheme();
    encodedSchemeSpecificPart = uri.getRawSchemeSpecificPart();
    encodedAuthority = uri.getRawAuthority();
    host = uri.getHost();
    port = uri.getPort();
    encodedUserInfo = uri.getRawUserInfo();
    userInfo = uri.getUserInfo();
    encodedPath = uri.getRawPath();
    final Charset actualCharset = defaultIfNull(charset, UTF_8);
    pathSegments = parsePath(uri.getRawPath(), actualCharset);
    encodedQuery = uri.getRawQuery();
    queryParams = parseQuery(uri.getRawQuery(), actualCharset);
    encodedFragment = uri.getRawFragment();
    fragment = uri.getFragment();
  }

  private String encodeUserInfo(@NotNull final String userInfo) {
    return UrlEncodingUtils.encUserInfo(userInfo,
        defaultIfNull(charset, UTF_8));
  }

  private String encodePath(@NotNull final List<String> pathSegments) {
    return UrlEncodingUtils.formatSegments(pathSegments,
        defaultIfNull(charset, UTF_8));
  }

  private String encodeUrlForm(@NotNull final List<NameValuePair> params) {
    return UrlEncodingUtils.format(params, defaultIfNull(charset, UTF_8));
  }

  private String encodeUric(@NotNull final String fragment) {
    return UrlEncodingUtils.encUric(fragment, defaultIfNull(charset, UTF_8));
  }

  /**
   * Sets URI scheme.
   */
  public UriBuilder setScheme(final String scheme) {
    this.scheme = scheme;
    return this;
  }

  /**
   * Sets URI user info. The value is expected to be unescaped and may contain
   * non ASCII characters.
   */
  public UriBuilder setUserInfo(final String userInfo) {
    this.userInfo = userInfo;
    this.encodedSchemeSpecificPart = null;
    this.encodedAuthority = null;
    this.encodedUserInfo = null;
    return this;
  }

  /**
   * Sets URI user info as a combination of username and password. These values
   * are expected to be unescaped and may contain non ASCII characters.
   */
  public UriBuilder setUserInfo(final String username, final String password) {
    return setUserInfo(username + ':' + password);
  }

  /**
   * Sets URI host.
   */
  public UriBuilder setHost(final String host) {
    this.host = host;
    this.encodedSchemeSpecificPart = null;
    this.encodedAuthority = null;
    return this;
  }

  /**
   * Sets URI port.
   */
  public UriBuilder setPort(final int port) {
    this.port = (port < 0 ? -1 : port);
    this.encodedSchemeSpecificPart = null;
    this.encodedAuthority = null;
    return this;
  }

  /**
   * Sets URI path. The value is expected to be unescaped and may contain non
   * ASCII characters.
   *
   * @return this.
   */
  public UriBuilder setPath(final String path) {
    return setPathSegments(
        path != null ? UrlEncodingUtils.splitPathSegments(path) : null);
  }

  /**
   * Sets URI path. The value is expected to be unescaped and may contain non
   * ASCII characters.
   *
   * @return this.
   */
  public UriBuilder setPathSegments(final String... pathSegments) {
    this.pathSegments = (pathSegments.length > 0 ? Arrays.asList(pathSegments) :
                         null);
    this.encodedSchemeSpecificPart = null;
    this.encodedPath = null;
    return this;
  }

  /**
   * Sets URI path. The value is expected to be unescaped and may contain non
   * ASCII characters.
   *
   * @return this.
   */
  public UriBuilder setPathSegments(final List<String> pathSegments) {
    if (pathSegments != null && pathSegments.size() > 0) {
      this.pathSegments = new ArrayList<>(pathSegments);
    } else {
      this.pathSegments = null;
    }
    this.encodedSchemeSpecificPart = null;
    this.encodedPath = null;
    return this;
  }

  /**
   * Removes URI query.
   */
  public UriBuilder removeQuery() {
    this.queryParams = null;
    this.query = null;
    this.encodedQuery = null;
    this.encodedSchemeSpecificPart = null;
    return this;
  }

  /**
   * Sets URI query parameters. The parameter name / values are expected to be
   * unescaped and may contain non ASCII characters.
   * <p>
   * Please note query parameters and custom query component are mutually
   * exclusive. This method will remove custom query if present.
   */
  public UriBuilder setParameters(final List<NameValuePair> nvps) {
    if (queryParams == null) {
      queryParams = new ArrayList<>();
    } else {
      queryParams.clear();
    }
    queryParams.addAll(nvps);
    encodedQuery = null;
    encodedSchemeSpecificPart = null;
    query = null;
    return this;
  }

  /**
   * Sets URI query parameters. The parameter name / values are expected to be
   * unescaped and may contain non ASCII characters.
   * <p>
   * Please note query parameters and custom query component are mutually
   * exclusive. This method will remove custom query if present.
   */
  public UriBuilder setParameters(final NameValuePair... nvps) {
    if (queryParams == null) {
      queryParams = new ArrayList<>();
    } else {
      queryParams.clear();
    }
    Collections.addAll(queryParams, nvps);
    encodedQuery = null;
    encodedSchemeSpecificPart = null;
    query = null;
    return this;
  }

  /**
   * Sets parameter of URI query overriding existing value if set. The parameter
   * name and value are expected to be unescaped and may contain non ASCII
   * characters.
   * <p>
   * Please note query parameters and custom query component are mutually
   * exclusive. This method will remove custom query if present.
   */
  public UriBuilder setParameters(final Map<String, String> params) {
    if (queryParams == null) {
      queryParams = new ArrayList<>();
    }
    if (!queryParams.isEmpty()) {
      queryParams.removeIf(nvp -> params.containsKey(nvp.getName()));
    }
    params.forEach((k, v) -> queryParams.add(new NameValuePair(k, v)));
    encodedQuery = null;
    encodedSchemeSpecificPart = null;
    query = null;
    return this;
  }

  /**
   * Sets parameter of URI query overriding existing value if set. The parameter
   * name and value are expected to be unescaped and may contain non ASCII
   * characters.
   * <p>
   * Please note query parameters and custom query component are mutually
   * exclusive. This method will remove custom query if present.
   */
  public UriBuilder setParameter(final String param, final String value) {
    if (queryParams == null) {
      queryParams = new ArrayList<>();
    }
    if (!queryParams.isEmpty()) {
      queryParams.removeIf(nvp -> nvp.getName().equals(param));
    }
    queryParams.add(new NameValuePair(param, value));
    encodedQuery = null;
    encodedSchemeSpecificPart = null;
    query = null;
    return this;
  }

  /**
   * Adds parameter to URI query. The parameter name and value are expected to
   * be unescaped and may contain non ASCII characters.
   * <p>
   * Please note query parameters and custom query component are mutually
   * exclusive. This method will remove custom query if present.
   */
  public UriBuilder addParameter(final String param, final String value) {
    if (queryParams == null) {
      queryParams = new ArrayList<>();
    }
    queryParams.add(new NameValuePair(param, value));
    encodedQuery = null;
    encodedSchemeSpecificPart = null;
    query = null;
    return this;
  }

  /**
   * Adds URI query parameters. The parameter name / values are expected to be
   * unescaped and may contain non ASCII characters.
   * <p>
   * Please note query parameters and custom query component are mutually
   * exclusive. This method will remove custom query if present.
   */
  public UriBuilder addParameters(final List<NameValuePair> nvps) {
    if (queryParams == null) {
      queryParams = new ArrayList<>();
    }
    queryParams.addAll(nvps);
    encodedQuery = null;
    encodedSchemeSpecificPart = null;
    query = null;
    return this;
  }

  /**
   * Clears URI query parameters.
   */
  public UriBuilder clearParameters() {
    queryParams = null;
    encodedQuery = null;
    encodedSchemeSpecificPart = null;
    return this;
  }

  /**
   * Sets custom URI query. The value is expected to be unescaped and may
   * contain non ASCII characters.
   * <p>
   * Please note query parameters and custom query component are mutually
   * exclusive. This method will remove query parameters if present.
   */
  public UriBuilder setCustomQuery(final String query) {
    this.query = query;
    this.encodedQuery = null;
    this.encodedSchemeSpecificPart = null;
    this.queryParams = null;
    return this;
  }

  /**
   * Sets URI fragment. The value is expected to be unescaped and may contain
   * non ASCII characters.
   */
  public UriBuilder setFragment(final String fragment) {
    this.fragment = fragment;
    this.encodedFragment = null;
    return this;
  }

  public boolean isAbsolute() {
    return scheme != null;
  }

  public boolean isOpaque() {
    return (pathSegments == null) && (encodedPath == null);
  }

  public String getScheme() {
    return scheme;
  }

  public String getUserInfo() {
    return userInfo;
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }

  public boolean isPathEmpty() {
    return (pathSegments == null || pathSegments.isEmpty()) && (encodedPath
        == null || encodedPath.isEmpty());
  }

  public List<String> getPathSegments() {
    if (pathSegments != null) {
      return new ArrayList<>(pathSegments);
    } else {
      return Collections.emptyList();
    }
  }

  public String getPath() {
    if (pathSegments == null) {
      return null;
    }
    final StringBuilder result = new StringBuilder();
    for (final String segment : pathSegments) {
      result.append('/').append(segment);
    }
    return result.toString();
  }

  public boolean isQueryEmpty() {
    return (queryParams == null || queryParams.isEmpty()) && (encodedQuery
        == null);
  }

  public List<NameValuePair> getQueryParams() {
    if (queryParams != null) {
      return new ArrayList<>(queryParams);
    } else {
      return Collections.emptyList();
    }
  }

  public String getFragment() {
    return fragment;
  }

  @Override
  public String toString() {
    return buildString();
  }
}
