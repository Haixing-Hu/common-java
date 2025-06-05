////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
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
 * {@link Url}对象存储标准化绝对URL的信息。
 * <p>
 * URL的标准化将对URL字符串执行以下转换：</p>
 *
 * <ul>
 * <li>去除URL的前导和尾随空白字符。</li>
 * <li>解码URL中的所有转义八位字节。</li>
 * <li>将URL的协议和主机转换为小写。例如，将
 * "Http://WWW.google.Com/Index.html" 转换为 "http://www.google.com/Index.html"。
 * 注意URL路径的大小写不会改变。</li>
 * <li>移除URL的默认端口。例如，将
 * "http://www.google.com:80/current.html" 转换为 "http://www.google.com/current.html"</li>
 * <li>移除URL的片段（或"锚点"，也称为"引用"）部分。例如，将
 * "http://www.google.com/current.html#top" 转换为
 * "http://www.google.com/current.html"</li>
 * <li>如果路径为空，在主机后添加缺失的'/'。例如，
 * 将"http://www.google.com" 转换为 "http://www.google.com/"</li>
 * <li>移除URL路径中连续的'/'，例如，将
 * "http://www.google.com/map//current.html" 转换为
 * "http://www.google.com/map/current.html"</li>
 * <li>移除所有冗余的"."和".."。例如，将
 * "http://www.google.com/map/./../../current.html" 转换为
 * "http://www.google.com/current.html"</li>
 * </ul>
 *
 * @author 胡海星
 */
@Immutable
public final class Url implements Comparable<Url>, CloneableEx<Url>, Serializable {

  private static final long serialVersionUID = -2751270791515501665L;

  static {
    BinarySerialization.register(Url.class, UrlBinarySerializer.INSTANCE);
    XmlSerialization.register(Url.class, UrlXmlSerializer.INSTANCE);
  }

  /**
   * 通过解析给定字符串创建URL。
   *
   * <p>这个便捷的工厂方法就像调用{@link #Url(String)}构造函数一样工作；
   * 构造函数抛出的任何{@link MalformedURLException}都会被捕获并包装在新的
   * {@link IllegalArgumentException}对象中，然后抛出。
   *
   * <p>此方法适用于已知给定字符串是合法URL的情况，例如程序中声明的URL常量，
   * 因此字符串无法解析将被视为编程错误。在从用户输入或其他可能出错的来源构造URL时，
   * 应使用直接抛出{@link MalformedURLException}的构造函数。
   *
   * @param str
   *          要解析为URL的字符串
   * @return 新的URL
   * @throws NullPointerException
   *           如果{@code str}为{@code null}
   * @throws IllegalArgumentException
   *           如果给定字符串违反RFC&nbsp;2396
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

  /**
   * 构造一个空的URL对象。
   */
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

  /**
   * 从字符串构造URL对象。
   *
   * @param str
   *     URL字符串
   * @throws MalformedURLException
   *     如果字符串不是有效的URL
   */
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

  /**
   * 构造URL对象。
   *
   * @param scheme
   *     协议
   * @param hostname
   *     主机名
   * @param path
   *     路径
   */
  public Url(@Nullable final String scheme,
             @Nullable final String hostname,
             @Nullable final String path) {
    this(scheme, null, hostname, -1, path, null, null);
  }

  /**
   * 构造URL对象。
   *
   * @param scheme
   *     协议
   * @param hostname
   *     主机名
   * @param port
   *     端口
   * @param path
   *     路径
   */
  public Url(@Nullable final String scheme,
             @Nullable final String hostname,
             final int port,
             @Nullable final String path) {
    this(scheme, null, hostname, port, path, null, null);
  }

  /**
   * 构造URL对象。
   *
   * @param scheme
   *     协议
   * @param userInfo
   *     用户信息
   * @param hostname
   *     主机名
   * @param port
   *     端口
   * @param path
   *     路径
   * @param query
   *     查询
   * @param fragment
   *     片段
   */
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

  /**
   * 从基础URL和相对路径构造URL对象。
   *
   * @param base
   *     基础URL
   * @param relative
   *     相对路径
   * @throws MalformedURLException
   *     如果构造的URL无效
   */
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

  /**
   * 从URL对象构造Url对象。
   *
   * @param url
   *     URL对象
   * @throws MalformedURLException
   *     如果URL无效
   */
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

  /**
   * 从URI对象构造Url对象。
   *
   * @param uri
   *     URI对象
   */
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

  /**
   * 检查URL是否为空。
   *
   * @return 如果URL为空返回true
   */
  public boolean isEmpty() {
    return (url == null)
        || (url.length() == 0)
        || (scheme.length() == 0)
        || (hostname.length() == 0);
  }

  /**
   * 检查URL是否为根路径。
   *
   * @return 如果URL为根路径返回true
   */
  public boolean isRoot() {
    return (path != null) && (path.length() == 1) && (path.charAt(0) == '/');
  }

  /**
   * 获取此URL的协议。
   *
   * @return 此URL的协议，永远不会为null，但可能为空
   */
  public String scheme() {
    return scheme;
  }

  /**
   * 获取此URL的用户信息。
   *
   * @return 此URL的用户信息，永远不会为null，但可能为空
   */
  public String userInfo() {
    return userInfo;
  }

  /**
   * 获取此URL的主机名。
   *
   * @return 此URL的主机名，永远不会为null，但可能为空
   */
  public String hostname() {
    return hostname;
  }

  /**
   * 获取此URL的主机。
   *
   * @return 此URL的主机，永远不会为null
   */
  public Host host() {
    return new Host(scheme, hostname, port);
  }

  /**
   * 获取此URL的域名。
   *
   * @return 此URL的域名，永远不会为null，但可能为空
   */
  public String domain() {
    return domain;
  }

  /**
   * 获取此URL的端口。
   *
   * @return 此URL的端口，如果未指定端口或端口是标准协议的默认端口，则可能为-1
   */
  public int port() {
    return port;
  }

  /**
   * 获取此URL的路径。
   *
   * @return 此URL的路径，永远不会为null，但可能为空
   */
  public String path() {
    return path;
  }

  /**
   * 获取此URL的文件名。
   *
   * @return 此URL的文件名，永远不会为null，但可能为空
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
   * 获取此URL的查询。
   *
   * @return 此URL的查询，永远不会为null，但可能为空
   */
  public String query() {
    return query;
  }

  /**
   * 获取在此URL查询部分中编码的参数（键/值对）。
   *
   * @param parameterSeparator
   *          用于分隔参数的字符。通常此字符是'&amp;'或';'
   * @param keyValueSeparator
   *          用于分隔键和值的字符。通常此字符是'='
   * @return 编码参数的映射，如果没有此类参数则返回空列表
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
   * 获取此URL的片段。
   *
   * @return 此URL的片段，永远不会为null，但可能为空
   */
  public String fragment() {
    return fragment;
  }

  /**
   * 获取此URL的指定部分。
   *
   * @param part
   *          要获取的部分
   * @return 此URL的指定部分，永远不会为null，但可能为空
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

  /**
   * 转换为URL对象。
   *
   * @return URL对象
   * @throws MalformedURLException
   *     如果URL格式无效
   */
  public URL toURL() throws MalformedURLException {
    return new URL(url);
  }

  /**
   * 转换为URI对象。
   *
   * @return URI对象
   * @throws URISyntaxException
   *     如果URI格式无效
   */
  public URI toURI() throws URISyntaxException {
    return new URI(scheme, userInfo, hostname, port, path, query, fragment);
  }

  /**
   * 打开此URL的输入流。
   *
   * @return 输入流
   * @throws IOException
   *     如果发生I/O错误
   */
  public InputStream openStream() throws IOException {
    final URL result;
    try {
      result = new URL(url);
    } catch (final MalformedURLException e) {
      throw new IOException(e);
    }
    return UrlUtils.openStream(result);
  }

  /**
   * 克隆当前URL对象。
   *
   * @return 克隆的URL对象
   */
  @Override
  public Url cloneEx() {
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

  /**
   * 与另一个URL对象进行比较。
   *
   * @param other
   *     要比较的另一个URL对象
   * @return 比较结果
   */
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
