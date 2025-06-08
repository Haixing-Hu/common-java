////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.io.Serializable;
import java.net.URI;
import java.net.URL;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.serialize.BinarySerialization;
import ltd.qubit.commons.io.serialize.XmlSerialization;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Comparison;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.NumberFormat;

import static ltd.qubit.commons.lang.ObjectUtils.defaultIfNull;

/**
 * {@link Host} 对象表示远程主机，存储其方案、主机名和端口。
 * <p>
 * 方案和主机名会自动转换为小写。
 * </p>
 * <p>
 * 注意 {@link Host} 对象是不可变的。
 * </p>
 *
 * @author 胡海星
 */
@Immutable
public final class Host implements CloneableEx<Host>, Comparable<Host>,
    Serializable {

  private static final long serialVersionUID = - 1489490467901212305L;

  /**
   * 默认方案。
   */
  public static final String DEFAULT_SCHEME = "http";

  /**
   * 默认端口。
   */
  public static final int DEFAULT_PORT = 80;

  static {
    BinarySerialization.register(Host.class, HostBinarySerializer.INSTANCE);
    XmlSerialization.register(Host.class, HostXmlSerializer.INSTANCE);
  }

  /**
   * 方案。
   */
  @Nonnull
  private final String scheme;

  /**
   * 主机名。
   */
  @Nonnull
  private final String hostname;

  /**
   * 端口号。
   */
  private final int port;

  /**
   * 使用指定主机名构造Host对象。
   * <p>
   * 方案将设置为默认方案，端口设置为-1。
   *
   * @param hostname
   *     主机名。
   */
  public Host(final String hostname) {
    scheme = DEFAULT_SCHEME;
    this.hostname = hostname.toLowerCase();
    port = - 1;
  }

  /**
   * 使用指定方案和主机名构造Host对象。
   * <p>
   * 端口将设置为指定方案的默认端口。
   *
   * @param scheme
   *     方案。
   * @param hostname
   *     主机名。
   */
  public Host(@Nonnull final String scheme, @Nonnull final String hostname) {
    this.scheme = scheme.toLowerCase();
    this.hostname = hostname.toLowerCase();
    port = DefaultPorts.get(this.scheme);
  }

  /**
   * 使用指定主机名和端口构造Host对象。
   * <p>
   * 方案将设置为默认方案。
   *
   * @param hostname
   *     主机名。
   * @param port
   *     端口号，如果小于0则设置为-1。
   */
  public Host(@Nonnull final String hostname, final int port) {
    scheme = DEFAULT_SCHEME;
    this.hostname = hostname.toLowerCase();
    if (port < 0) {
      this.port = - 1;
    } else {
      this.port = port;
    }
  }

  /**
   * 使用指定方案、主机名和端口构造Host对象。
   *
   * @param scheme
   *     方案。
   * @param hostname
   *     主机名。
   * @param port
   *     端口号，如果小于0则设置为-1。
   */
  public Host(@Nonnull final String scheme, @Nonnull final String hostname,
      final int port) {
    this.scheme = scheme.toLowerCase();
    this.hostname = hostname.toLowerCase();
    if (port < 0) {
      this.port = - 1;
    } else {
      this.port = port;
    }
  }

  /**
   * 从Url对象构造Host对象。
   *
   * @param url
   *     Url对象。
   */
  public Host(@Nonnull final Url url) {
    scheme = url.scheme();
    hostname = url.hostname();
    port = url.port();
  }

  /**
   * 从URL对象构造Host对象。
   *
   * @param url
   *     URL对象。
   */
  public Host(@Nonnull final URL url) {
    this(url.getProtocol(), url.getHost(), url.getPort(), url.getAuthority());
  }

  /**
   * 从URI对象构造Host对象。
   *
   * @param uri
   *     URI对象。
   */
  public Host(@Nonnull final URI uri) {
    this(uri.getScheme(), uri.getHost(), uri.getPort(), uri.getAuthority());
  }

  private Host(final String scheme, final String hostname, final int port,
      final String authority) {
    // FIXME: if the hostname part of the URL contains an illegal character,
    // for example, "http://local_host/dir", the URI parser will parse
    // the "local_host" as a registry-based authority, and let the hostname
    // be null.
    String theHostname = hostname;
    int thePort = port;
    if (theHostname == null) {
      // let's try to parse the userInfo, hostname and port by ourself
      if (authority != null) {
        // parse the userInfo
        int at = authority.indexOf('@');
        if (at < 0) {
          at = - 1;
        }
        // parse the port
        int colon = authority.lastIndexOf(':');
        if (colon < 0) {
          thePort = - 1;
          colon = authority.length();
        } else {
          final String str = authority.substring(colon + 1);
          final NumberFormat nf = new NumberFormat();
          thePort = nf.parseInt(str);
          if (nf.fail()) {
            thePort = - 1;
          }
        }
        // parse the hostname
        theHostname = authority.substring(at + 1, colon);
      }
    }
    // initialize the fields
    this.scheme = defaultIfNull(scheme, StringUtils.EMPTY);
    this.hostname = defaultIfNull(theHostname, StringUtils.EMPTY);
    this.port = thePort;
  }

  /**
   * 获取方案。
   *
   * @return
   *     方案。
   */
  public String scheme() {
    return scheme;
  }

  /**
   * 获取主机名。
   *
   * @return
   *     主机名。
   */
  public String hostname() {
    return hostname;
  }

  /**
   * 获取端口号。
   *
   * @return
   *     端口号。
   */
  public int port() {
    return port;
  }

  @Override
  public int hashCode() {
    final int multiplier = 131;
    int code = 31;
    code = Hash.combine(code, multiplier, scheme);
    code = Hash.combine(code, multiplier, hostname);
    code = Hash.combine(code, multiplier, port);
    return code;
  }

  @Override
  public boolean equals(@Nullable final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Host other = (Host) obj;
    return (port == other.port) && Equality.equals(scheme, other.scheme)
        && Equality.equals(hostname, other.hostname);
  }

  /**
   * 比较两个Host对象。
   * <p>
   * 比较顺序：方案、主机名、端口。
   *
   * @param other
   *     要比较的另一个Host对象。
   * @return
   *     比较结果。
   */
  @Override
  public int compareTo(@Nullable final Host other) {
    if (other == null) {
      return + 1;
    } else {
      int rc = Comparison.compare(scheme, other.scheme);
      if (rc != 0) {
        return rc;
      }
      rc = Comparison.compare(hostname, other.hostname);
      if (rc != 0) {
        return rc;
      }
      return port - other.port;
    }
  }

  /**
   * 返回此主机的字符串表示形式，不包括方案。
   *
   * @return 此主机的字符串表示形式，不包括方案。
   */
  public String toHostString() {
    if (port >= 0) {
      return hostname + ':' + port;
    } else {
      return hostname;
    }
  }

  /**
   * 返回此主机的字符串表示形式。
   *
   * @return 此主机的字符串表示形式。
   */
  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append(scheme).append("://").append(hostname);
    if (port >= 0) {
      builder.append(':').append(port);
    }
    return builder.toString();
  }

  /**
   * 克隆此对象。
   *
   * @return
   *     此对象的克隆。
   */
  @Override
  public Host cloneEx() {
    return new Host(scheme, hostname, port);
  }

}