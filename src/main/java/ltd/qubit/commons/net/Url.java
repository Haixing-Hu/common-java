////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.serialize.BinarySerialization;
import ltd.qubit.commons.io.serialize.XmlSerialization;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.text.NumberFormat;
import ltd.qubit.commons.text.Splitter;
import ltd.qubit.commons.text.Stripper;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.ObjectUtils.defaultIfNull;
import static ltd.qubit.commons.lang.StringUtils.EMPTY;

/**
 * A {@link Url} object stores the information of a normalized absolute URL.
 *
 * <p>Normalization of a URL will do the follow transformation on the URL
 * string:</p>
 *
 * <ul>
 * <li>Trim the leading and trailing white spaces of the URL.</li>
 * <li>Decode all escaped octets in the URL.</li>
 * <li>Lower case the scheme and host of the URL. For example, transform
 * "Http://WWW.google.Com/Index.html" to "http://www.google.com/Index.html".
 * Note that the case of the path of the URL is not changed.</li>
 * <li>Remove the default port of the URL. For example, transform
 * "http://www.google.com:80/current.html" to "http://www.google.com/current.html"</li>
 * <li>Remove the fragment (or "anchor", also known as the "reference") part of
 * the URL. For example, transform "http://www.google.com/current.html#top" to
 * "http://www.google.com/current.html"</li>
 * <li>Add the missing '/' after the host if path is empty. For example,
 * transform "http://www.google.com" to "http://www.google.com/"</li>
 * <li>Remove the consecutive '/' in the path of the URL, for example, transform
 * "http://www.google.com/map//current.html" to
 * "http://www.google.com/map/current.html"</li>
 * <li>Remove the consecutive '/' in the path of the URL, for example, transform
 * "http://www.google.com/map//current.html" to
 * "http://www.google.com/map/current.html"</li>
 * <li>Remove all the redundant "." and "..". For example, transform
 * "http://www.google.com/map/./../../current.html" to
 * "http://www.google.com/current.html"</li>
 * </ul>
 *
 * @author Haixing Hu
 */
@Immutable
public final class Url implements Comparable<Url>, CloneableEx<Url>, Serializable {

  private static final long serialVersionUID = -2751270791515501665L;

  static {
    BinarySerialization.register(Url.class, UrlBinarySerializer.INSTANCE);
    XmlSerialization.register(Url.class, UrlXmlSerializer.INSTANCE);
  }

  /**
   * Creates a URL by parsing the given string.
   *
   * <p>This convenience factory method works as if by invoking the
   * {@link #Url(String)} constructor; any {@link MalformedURLException} thrown
   * by the constructor is caught and wrapped in a new
   * {@link IllegalArgumentException} object, which is then thrown.
   *
   * <p>This method is provided for use in situations where it is known that the
   * given string is a legal URL, for example for URL constants declared within
   * in a program, and so it would be considered a programming error for the
   * string not to parse as such. The constructors, which throw
   * {@link MalformedURLException} directly, should be used situations where a
   * URL is being constructed from user input or from some other source that may
   * be prone to errors.
   *
   * @param str
   *          The string to be parsed into a URL
   * @return The new URL
   * @throws NullPointerException
   *           If {@code str} is {@code null}.
   * @throws IllegalArgumentException
   *           If the given string violates RFC&nbsp;2396
   */
  public static Url create(final String str) {
    try {
      return new Url(str);
    } catch (final MalformedURLException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private final String scheme;
  private final @Nullable String userInfo;
  private final String hostname;
  private final int    port;
  private final @Nullable String path;
  private final @Nullable String query;
  private final @Nullable String fragment;

  private final String domain;

  private final String url;

  public Url() {
    scheme = EMPTY;
    userInfo = null;
    hostname = EMPTY;
    port = -1;
    path = null;
    query = null;
    fragment = null;
    domain = EMPTY;
    url = EMPTY;
  }

  public Url(@Nullable final String str) throws MalformedURLException {
    this(createUri(str));
  }

  private static @Nullable URI createUri(@Nullable final String str)
      throws MalformedURLException {
    if (str == null) {
      return null;
    }
    final String urlstr = new Stripper().strip(str);
    if ((urlstr == null) || (urlstr.length() == 0)) {
      return null;
    }
    try {
      return new URI(urlstr);
    } catch (final URISyntaxException e) {
      final String message = e.getMessage();
      final MalformedURLException ex = new MalformedURLException(message);
      ex.initCause(e);
      throw ex;
    }
  }

  public Url(@Nullable final String scheme,
             @Nullable final String hostname,
             @Nullable final String path) {
    this(scheme, null, hostname, -1, path, null, null);
  }

  public Url(@Nullable final String scheme,
             @Nullable final String hostname,
             final int port,
             @Nullable final String path) {
    this(scheme, null, hostname, port, path, null, null);
  }

  public Url(@Nullable final String scheme,
             @Nullable final String userInfo,
             @Nullable final String hostname,
             final int port,
             @Nullable final String path,
             @Nullable final String query,
             @Nullable final String fragment) {
    this.scheme = normalizeScheme(scheme);
    this.userInfo = userInfo;
    this.hostname = normalizeHostname(hostname);
    this.port = normalizePort(this.scheme, port);
    this.path = normalizePath(path);
    this.query = query;
    this.fragment = fragment;
    domain = getDomain(this.hostname);
    url = buildUrl(this.scheme, this.userInfo, this.hostname,
        this.port, this.path, this.query, this.fragment);
  }

  public Url(final Url base, final String relative)
      throws MalformedURLException {
    this(createUri(base, relative));
  }

  private static URI createUri(final Url base, final String relative)
      throws MalformedURLException {
    requireNonNull("base", base);
    requireNonNull("relative", relative);
    final String rel = new Stripper().strip(relative);
    try {
      final URI baseUri = base.toURI();
      final URI relativeUri = new URI(rel);
      return baseUri.resolve(relativeUri);
    } catch (final URISyntaxException e) {
      final String message = e.getMessage();
      final MalformedURLException ex = new MalformedURLException(message);
      ex.initCause(e);
      throw ex;
    }
  }

  public Url(final URL url) throws MalformedURLException {
    this(createUri(url));
  }

  private static URI createUri(final URL url) throws MalformedURLException {
    try {
      return url.toURI();
    } catch (final URISyntaxException e) {
      final String message = e.getMessage();
      final MalformedURLException ex = new MalformedURLException(message);
      ex.initCause(e);
      throw ex;
    }
  }

  public Url(@Nullable final URI uri) {
    if (uri == null) {
      scheme = EMPTY;
      userInfo = null;
      hostname = EMPTY;
      port = -1;
      path = null;
      query = null;
      fragment = null;
      domain = EMPTY;
      url = EMPTY;
      return;
    }

    String theUserInfo = uri.getUserInfo();
    String theHostname = uri.getHost();
    int thePort = uri.getPort();
    // XXX: if the hostname part of the URL contains an illegal character,
    // for example, "http://local_host/dir", the URI parser will parse
    // the "local_host" as a registry-based authority, and let the hostname
    // be null.
    if (theHostname == null) {
      //  let's try to parse the userInfo, hostname and port by ourselves
      final String theAuthority = uri.getAuthority();
      if (theAuthority != null) {
        theUserInfo = parseUserInfo(theAuthority);
        thePort = parsePort(theAuthority);
        theHostname = parseHostname(theAuthority);
      }
    }
    if (theHostname == null) {
      theHostname = EMPTY;
    }
    this.scheme = normalizeScheme(defaultIfNull(uri.getScheme(), EMPTY));
    this.userInfo = theUserInfo;
    this.hostname = normalizeHostname(theHostname);
    this.port = normalizePort(this.scheme, thePort);
    this.path = normalizePath(uri.getPath());
    this.query = uri.getQuery();
    this.fragment = uri.getFragment();
    domain = getDomain(this.hostname);
    url = buildUrl(this.scheme, this.userInfo, this.hostname,
        this.port, this.path, this.query, this.fragment);
  }

  private static String parseUserInfo(final String authority) {
    final int at = authority.indexOf('@');
    if (at < 0) {
      return null;
    } else {
      return authority.substring(0, at);
    }
  }

  private static int parsePort(final String authority) {
    final int colon = authority.lastIndexOf(':');
    if (colon < 0) {
      return -1;
    } else {
      final String str = authority.substring(colon + 1);
      final NumberFormat nf = new NumberFormat();
      int port = nf.parseInt(str);
      if (nf.fail()) {
        port = -1;
      }
      return port;
    }
  }

  private static String parseHostname(final String authority) {
    final int at = authority.indexOf('@');
    int colon = authority.lastIndexOf(':');
    if (colon < 0) {
      colon = authority.length();
    }
    if (at < 0) {
      return authority.substring(0, colon);
    } else {
      return authority.substring(at + 1, colon);
    }
  }

  private static String buildUrl(@Nullable final String scheme,
      @Nullable final String userInfo,
      @Nullable final String hostname,
      final int port,
      @Nullable final String path,
      @Nullable final String query,
      @Nullable final String fragment) {
    final StringBuilder builder = new StringBuilder();
    if (scheme != null) {
      builder.append(scheme).append("://");
    }
    if ((userInfo != null) && (userInfo.length() > 0)) {
      builder.append(userInfo).append('@');
    }
    if (hostname != null) {
      builder.append(hostname);
    }
    if (port >= 0) {
      builder.append(':').append(port);
    }
    if (path != null) {
      builder.append(path);
    }
    if (query != null) {
      builder.append('?').append(query);
    }
    if (fragment != null) {
      builder.append('#').append(fragment);
    }
    return builder.toString();
  }

  private static String normalizeScheme(final String scheme) {
    if (scheme != null) {
      return scheme.toLowerCase();
    } else {
      return EMPTY;
    }
  }

  private static String normalizeHostname(final String hostname) {
    if (hostname != null) {
      return hostname.toLowerCase();
    } else {
      return EMPTY;
    }
  }

  private static int normalizePort(final String scheme, final int port) {
    final int defaultPort = DefaultPorts.get(scheme);
    if (port == defaultPort) {
      return -1;
    } else {
      return port;
    }
  }

  private static String normalizePath(final String path) {
    final int pathLen;
    if ((path == null) || ((pathLen = path.length()) == 0)) {
      return "/";
    }
    final boolean endWithSlash = (path.charAt(pathLen - 1) == '/');
    final Stack<String> nameStack = new Stack<>();
    int first = 0;
    int last = 0;
    while (first < pathLen) {
      last = path.indexOf('/', first);
      if (last == -1) {
        last = pathLen;
      }
      // now path[first, last) is a name component, and it may be empty if first
      // == last
      if (first < last) {
        // skip the empty name component
        final String name = path.substring(first, last);
        final int nameLen = name.length();
        if ((nameLen == 1) && (name.charAt(0) == '.')) {
          // name is ".", just ignore it.
          // do nothing
        } else if ((nameLen == 2) && (name.charAt(0) == '.')
            && (name.charAt(1) == '.')) {
          // name is ".."
          if (!nameStack.isEmpty()) {
            nameStack.pop();
          }
        } else {
          nameStack.push(name);
        }
      }
      first = last + 1;
    }
    // compose the path
    if (nameStack.isEmpty()) {
      return "/";
    } else {
      final StringBuilder builder = new StringBuilder();
      for (final String name : nameStack) {
        builder.append('/').append(name);
      }
      if (endWithSlash) {
        builder.append('/');
      }
      return builder.toString();
    }
  }

  private static String getDomain(@Nullable final String hostname) {
    if (hostname == null) {
      return EMPTY;
    } else {
      return UrlUtils.getDomain(hostname);
    }
  }

  public boolean isEmpty() {
    return (url == null)
        || (url.length() == 0)
        || (scheme.length() == 0)
        || (hostname.length() == 0);
  }

  public boolean isRoot() {
    return (path != null) && (path.length() == 1) && (path.charAt(0) == '/');
  }

  /**
   * Gets the scheme of this URL.
   *
   * @return the scheme of this URL, which never be null, but could be empty.
   */
  public String scheme() {
    return scheme;
  }

  /**
   * Gets the user info of this URL.
   *
   * @return the user info of this URL, which never be null, but could be empty.
   */
  public String userInfo() {
    return userInfo;
  }

  /**
   * Gets the hostname of this URL.
   *
   * @return the hostname of this URL, which never be null, but could be empty.
   */
  public String hostname() {
    return hostname;
  }

  /**
   * Gets the host of this URL.
   *
   * @return the host of this URL, which never be null.
   */
  public Host host() {
    return new Host(scheme, hostname, port);
  }

  /**
   * Gets the domain of this URL.
   *
   * @return the domain of this URL, which never be null, but could be empty.
   */
  public String domain() {
    return domain;
  }

  /**
   * Gets the port of this URL.
   *
   * @return the port of this URL, which could be -1 if the port is not
   *         specified or if the port is the default port for standard
   *         schemes.
   */
  public int port() {
    return port;
  }

  /**
   * Gets the path of this URL.
   *
   * @return the path of this URL, which never be null, but could be empty.
   */
  public String path() {
    return path;
  }

  /**
   * Gets the filename of this URL.
   *
   * @return the filename of this URL, which never be null, but could be empty.
   */
  public String filename() {
    final int pos = path.lastIndexOf('/');
    if (pos >= 0) {
      return path.substring(pos + 1);
    } else {
      return path;
    }
  }

  /**
   * Gets the query of this URL.
   *
   * @return the query of this URL, which never be null, but could be empty.
   */
  public String query() {
    return query;
  }

  /**
   * Gets the parameters (key/value pairs) encoded in the query part of this
   * URL.
   *
   * @param parameterSeparator
   *          the character used to separator the parameters. Usually this
   *          character is '&amp;' or ';'.
   * @param keyValueSeparator
   *          the character used to separator the key and value. Usually this
   *          character is '='.
   * @return the map of encoded parameters, or an empty list if no such
   *         parameters.
   */
  public Map<String, String> getEncodedParameters(final char parameterSeparator,
      final char keyValueSeparator) {
    if (query == null) {
      return Collections.emptyMap();
    }
    final List<String> params = new Splitter()
        .byChar(parameterSeparator)
        .strip(true)
        .ignoreEmpty(true)
        .split(query);
    if (params.isEmpty()) {
      return Collections.emptyMap();
    }
    final Map<String, String> result = new HashMap<>();
    for (final String pair : params) {
      final int pos = pair.indexOf(keyValueSeparator);
      final String key;
      final String value;
      if (pos < 0) {
        // use empty string as the value
        key = pair;
        value = EMPTY;
      } else {
        key = pair.substring(0, pos);
        value = pair.substring(pos + 1);
      }
      result.put(key, value);
    }
    return result;
  }

  /**
   * Gets the fragment of this URL.
   *
   * @return the fragment of this URL, which never be null, but could be empty.
   */
  public String fragment() {
    return fragment;
  }

  /**
   * Gets the specified part of this URL.
   *
   * @param part
   *          the part to be get.
   * @return the specified part of this URL, which never be null, but could be
   *         empty.
   */
  public String get(final UrlPart part) {
    switch (part) {
      case URL:
        return url;
      case SCHEME:
        return scheme;
      case USER_INFO:
        return userInfo;
      case HOSTNAME:
        return hostname;
      case DOMAIN:
        return domain;
      case PORT:
        return Integer.toString(port);
      case PATH:
        return path;
      case QUERY:
        return query;
      case FRAGMENT:
        return fragment;
      default:
        return url;
    }
  }

  //  stop checkstyle: AbbreviationAsWordInNameCheck
  public URL toURL() throws MalformedURLException {
    return new URL(url);
  }

  public URI toURI() throws URISyntaxException {
    return new URI(scheme, userInfo, hostname, port, path, query, fragment);
  }
  //  resume checkstyle: AbbreviationAsWordInNameCheck

  public InputStream openStream() throws IOException {
    final URL result;
    try {
      result = new URL(url);
    } catch (final MalformedURLException e) {
      throw new IOException(e);
    }
    return UrlUtils.openStream(result);
  }

  @Override
  public Url clone() {
    return new Url(scheme, userInfo, hostname, port, path,
        query, fragment, domain, url);
  }

  // used internally by clone()
  private Url(@Nullable final String scheme, @Nullable final String userInfo,
      @Nullable final String hostname,
      final int port,
      @Nullable final String path,
      @Nullable final String query,
      @Nullable final String fragment,
      @Nullable final String domain,
      @Nullable final String url) {
    this.scheme = scheme;
    this.userInfo = userInfo;
    this.hostname = hostname;
    this.port = port;
    this.path = path;
    this.query = query;
    this.fragment = fragment;
    this.domain = domain;
    this.url = url;
  }

  @Override
  public int hashCode() {
    return (url == null ? 0 : url.hashCode());
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Url other = (Url) obj;
    return Equality.equals(url, other.url);
  }

  @Override
  public int compareTo(final Url other) {
    if (this == other) {
      return 0;
    } else if (other == null) {
      return +1;
    } else if (url == other.url) {
      return 0;
    } else if (url == null) {
      return -1;
    } else if (other.url == null) {
      return +1;
    } else {
      return url.compareTo(other.url);
    }
  }

  @Override
  public String toString() {
    return url;
  }
}
