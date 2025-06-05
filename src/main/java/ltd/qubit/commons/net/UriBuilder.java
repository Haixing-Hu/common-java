////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * 用于构建带参数URI的构建器。
 *
 * @author 胡海星
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
   * 构造空实例。
   */
  public UriBuilder() {
    this.port = -1;
  }

  /**
   * 从字符串构造实例，该字符串必须是有效的URI。
   *
   * @param string
   *     字符串形式的有效URI
   * @throws URISyntaxException
   *     如果输入不是有效的URI
   */
  public UriBuilder(final String string) throws URISyntaxException {
    this(new URI(string), null);
  }

  /**
   * 从提供的URI构造实例。
   *
   * @param uri
   *     提供的URI
   */
  public UriBuilder(final URI uri) {
    this(uri, null);
  }

  /**
   * 从字符串构造实例，该字符串必须是有效的URI。
   *
   * @param string
   *     字符串形式的有效URI
   * @param charset
   *     指定的字符集
   * @throws URISyntaxException
   *     如果输入不是有效的URI
   */
  public UriBuilder(final String string, final Charset charset)
      throws URISyntaxException {
    this(new URI(string), charset);
  }

  /**
   * 从提供的URI构造实例。
   *
   * @param uri
   *     提供的URI
   * @param charset
   *     指定的字符集
   */
  public UriBuilder(final URI uri, final Charset charset) {
    setCharset(charset);
    digestUri(uri);
  }

  /**
   * 设置字符集。
   *
   * @param charset
   *     指定的字符集
   * @return 当前实例
   */
  public UriBuilder setCharset(final Charset charset) {
    this.charset = charset;
    return this;
  }

  /**
   * 获取编码字符集。
   *
   * @return 编码字符集
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
   * 构建一个{@link URI}实例。
   *
   * @return 构建结果
   * @throws URISyntaxException
   *     如果URI格式无效
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
   * 设置URI的协议。
   *
   * @param scheme
   *     URI协议
   * @return 当前实例
   */
  public UriBuilder setScheme(final String scheme) {
    this.scheme = scheme;
    return this;
  }

  /**
   * 设置URI用户信息。值应该是未转义的，可能包含非ASCII字符。
   *
   * @param userInfo
   *     用户信息
   * @return 当前实例
   */
  public UriBuilder setUserInfo(final String userInfo) {
    this.userInfo = userInfo;
    this.encodedSchemeSpecificPart = null;
    this.encodedAuthority = null;
    this.encodedUserInfo = null;
    return this;
  }

  /**
   * 设置URI用户信息为用户名和密码的组合。这些值应该是未转义的，可能包含非ASCII字符。
   *
   * @param username
   *     用户名
   * @param password
   *     密码
   * @return 当前实例
   */
  public UriBuilder setUserInfo(final String username, final String password) {
    return setUserInfo(username + ':' + password);
  }

  /**
   * 设置URI主机。
   *
   * @param host
   *     主机名
   * @return 当前实例
   */
  public UriBuilder setHost(final String host) {
    this.host = host;
    this.encodedSchemeSpecificPart = null;
    this.encodedAuthority = null;
    return this;
  }

  /**
   * 设置URI端口。
   *
   * @param port
   *     端口号
   * @return 当前实例
   */
  public UriBuilder setPort(final int port) {
    this.port = (port < 0 ? -1 : port);
    this.encodedSchemeSpecificPart = null;
    this.encodedAuthority = null;
    return this;
  }

  /**
   * 设置URI路径。值应该是未转义的，可能包含非ASCII字符。
   *
   * @param path
   *     路径
   * @return 当前实例
   */
  public UriBuilder setPath(final String path) {
    return setPathSegments(
        path != null ? UrlEncodingUtils.splitPathSegments(path) : null);
  }

  /**
   * 设置URI路径段。值应该是未转义的，可能包含非ASCII字符。
   *
   * @param pathSegments
   *     路径段数组
   * @return 当前实例
   */
  public UriBuilder setPathSegments(final String... pathSegments) {
    this.pathSegments = (pathSegments.length > 0 ? Arrays.asList(pathSegments) :
                         null);
    this.encodedSchemeSpecificPart = null;
    this.encodedPath = null;
    return this;
  }

  /**
   * 设置URI路径段列表。值应该是未转义的，可能包含非ASCII字符。
   *
   * @param pathSegments
   *     路径段列表
   * @return 当前实例
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
   * 移除URI查询。
   *
   * @return 当前实例
   */
  public UriBuilder removeQuery() {
    this.queryParams = null;
    this.query = null;
    this.encodedQuery = null;
    this.encodedSchemeSpecificPart = null;
    return this;
  }

  /**
   * 设置URI查询参数。参数名/值应该是未转义的，可能包含非ASCII字符。
   * <p>
   * 请注意查询参数和自定义查询组件是互斥的。此方法将删除已存在的自定义查询。
   *
   * @param nvps
   *     名称-值对列表
   * @return 当前实例
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
   * 设置URI查询参数。参数名/值应该是未转义的，可能包含非ASCII字符。
   * <p>
   * 请注意查询参数和自定义查询组件是互斥的。此方法将删除已存在的自定义查询。
   *
   * @param nvps
   *     名称-值对数组
   * @return 当前实例
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
   * 设置URI查询参数，覆盖已存在的值。参数名和值应该是未转义的，可能包含非ASCII字符。
   * <p>
   * 请注意查询参数和自定义查询组件是互斥的。此方法将删除已存在的自定义查询。
   *
   * @param params
   *     参数映射
   * @return 当前实例
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
   * 设置URI查询参数，覆盖已存在的值。参数名和值应该是未转义的，可能包含非ASCII字符。
   * <p>
   * 请注意查询参数和自定义查询组件是互斥的。此方法将删除已存在的自定义查询。
   *
   * @param param
   *     参数名
   * @param value
   *     参数值
   * @return 当前实例
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
   * 向URI查询添加参数。参数名和值应该是未转义的，可能包含非ASCII字符。
   * <p>
   * 请注意查询参数和自定义查询组件是互斥的。此方法将删除已存在的自定义查询。
   *
   * @param param
   *     参数名
   * @param value
   *     参数值
   * @return 当前实例
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
   * 向URI查询添加参数。参数名/值应该是未转义的，可能包含非ASCII字符。
   * <p>
   * 请注意查询参数和自定义查询组件是互斥的。此方法将删除已存在的自定义查询。
   *
   * @param pairs
   *     要添加的参数列表
   * @return 当前实例
   */
  public UriBuilder addParameters(final List<NameValuePair> pairs) {
    if (queryParams == null) {
      queryParams = new ArrayList<>();
    }
    queryParams.addAll(pairs);
    encodedQuery = null;
    encodedSchemeSpecificPart = null;
    query = null;
    return this;
  }

  /**
   * 向URI查询添加参数。参数名和值应该是未转义的，可能包含非ASCII字符。
   * <p>
   * 请注意查询参数和自定义查询组件是互斥的。此方法将删除已存在的自定义查询。
   *
   * @param <T>
   *     参数值的类型
   * @param params
   *     要添加的参数映射
   * @return 当前实例
   */
  public <T> UriBuilder addParameter(final Map<String, T> params) {
    if (queryParams == null) {
      queryParams = new ArrayList<>();
    }
    for (final Map.Entry<String, T> entry : params.entrySet()) {
      final String value = entry.getValue() != null ? entry.getValue().toString() : null;
      queryParams.add(new NameValuePair(entry.getKey(), value));
    }
    encodedQuery = null;
    encodedSchemeSpecificPart = null;
    query = null;
    return this;
  }

  /**
   * 清除URI查询参数。
   *
   * @return 当前实例
   */
  public UriBuilder clearParameters() {
    queryParams = null;
    encodedQuery = null;
    encodedSchemeSpecificPart = null;
    return this;
  }

  /**
   * 设置自定义URI查询。值应该是未转义的，可能包含非ASCII字符。
   * <p>
   * 请注意查询参数和自定义查询组件是互斥的。此方法将删除已存在的查询参数。
   *
   * @param query
   *     自定义查询字符串
   * @return 当前实例
   */
  public UriBuilder setCustomQuery(final String query) {
    this.query = query;
    this.encodedQuery = null;
    this.encodedSchemeSpecificPart = null;
    this.queryParams = null;
    return this;
  }

  /**
   * 设置URI片段。值应该是未转义的，可能包含非ASCII字符。
   *
   * @param fragment
   *     片段字符串
   * @return 当前实例
   */
  public UriBuilder setFragment(final String fragment) {
    this.fragment = fragment;
    this.encodedFragment = null;
    return this;
  }

  /**
   * 检查URI是否为绝对URI。
   *
   * @return 如果是绝对URI返回true
   */
  public boolean isAbsolute() {
    return scheme != null;
  }

  /**
   * 检查URI是否为不透明URI。
   *
   * @return 如果是不透明URI返回true
   */
  public boolean isOpaque() {
    return (pathSegments == null) && (encodedPath == null);
  }

  /**
   * 获取URI的协议。
   *
   * @return URI协议
   */
  public String getScheme() {
    return scheme;
  }

  /**
   * 获取URI的用户信息。
   *
   * @return 用户信息
   */
  public String getUserInfo() {
    return userInfo;
  }

  /**
   * 获取URI的主机。
   *
   * @return 主机名
   */
  public String getHost() {
    return host;
  }

  /**
   * 获取URI的端口。
   *
   * @return 端口号
   */
  public int getPort() {
    return port;
  }

  /**
   * 检查路径是否为空。
   *
   * @return 如果路径为空返回true
   */
  public boolean isPathEmpty() {
    return (pathSegments == null || pathSegments.isEmpty()) && (encodedPath
        == null || encodedPath.isEmpty());
  }

  /**
   * 获取路径段列表。
   *
   * @return 路径段列表
   */
  public List<String> getPathSegments() {
    if (pathSegments != null) {
      return new ArrayList<>(pathSegments);
    } else {
      return Collections.emptyList();
    }
  }

  /**
   * 获取路径字符串。
   *
   * @return 路径字符串
   */
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

  /**
   * 检查查询是否为空。
   *
   * @return 如果查询为空返回true
   */
  public boolean isQueryEmpty() {
    return (queryParams == null || queryParams.isEmpty()) && (encodedQuery
        == null);
  }

  /**
   * 获取查询参数列表。
   *
   * @return 查询参数列表
   */
  public List<NameValuePair> getQueryParams() {
    if (queryParams != null) {
      return new ArrayList<>(queryParams);
    } else {
      return Collections.emptyList();
    }
  }

  /**
   * 获取片段。
   *
   * @return 片段字符串
   */
  public String getFragment() {
    return fragment;
  }

  @Override
  public String toString() {
    return buildString();
  }
}
