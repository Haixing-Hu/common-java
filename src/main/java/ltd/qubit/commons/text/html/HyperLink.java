////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.html;

import java.io.Serial;
import java.io.Serializable;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.io.serialize.BinarySerialization;
import ltd.qubit.commons.io.serialize.XmlSerialization;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Comparison;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.net.Url;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * {@link HyperLink} 类表示 HTML 页面中的超链接。
 *
 * @author 胡海星
 */
@Immutable
public final class HyperLink implements Comparable<HyperLink>,
        CloneableEx<HyperLink>, Serializable {

  @Serial
  private static final long serialVersionUID = - 6896074974015880689L;

  /**
   * XML 根节点名称。
   */
  public static final String ROOT_NODE = "hyper-link";

  /**
   * XML URL 属性名称。
   */
  public static final String URL_ATTRIBUTE = "url";

  static {
    BinarySerialization.register(HyperLink.class, HyperLinkBinarySerializer.INSTANCE);
    XmlSerialization.register(HyperLink.class, HyperLinkXmlSerializer.INSTANCE);
  }

  private final @Nullable Url url;
  private final @Nullable String anchor;

  /**
   * 构造一个空的超链接。
   */
  public HyperLink() {
    url = null;
    anchor = null;
  }

  /**
   * 构造一个指定 URL 的超链接。
   *
   * @param url
   *     超链接的 URL，可以为 {@code null}。
   */
  public HyperLink(@Nullable final Url url) {
    this.url = url;
    anchor = null;
  }

  /**
   * 构造一个指定 URL 和锚点的超链接。
   *
   * @param url
   *     超链接的 URL，可以为 {@code null}。
   * @param anchor
   *     超链接的锚点文本，可以为 {@code null}。
   */
  public HyperLink(@Nullable final Url url, @Nullable final String anchor) {
    this.url = url;
    this.anchor = anchor;
  }

  /**
   * 获取超链接的 URL。
   *
   * @return
   *     超链接的 URL，可能为 {@code null}。
   */
  public Url url() {
    return url;
  }

  /**
   * 获取超链接的锚点文本。
   *
   * @return
   *     超链接的锚点文本，可能为 {@code null}。
   */
  public String anchor() {
    return anchor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HyperLink cloneEx() {
    return new HyperLink(url, anchor);
  }

  @Override
  public int hashCode() {
    final int multiplier = 11;
    int code = 13;
    code = Hash.combine(code, multiplier, url);
    code = Hash.combine(code, multiplier, anchor);
    return code;
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
    final HyperLink other = (HyperLink) obj;
    return Equality.equals(url, other.url)
        && Equality.equals(anchor, other.anchor);
  }

  @Override
  public int compareTo(final HyperLink other) {
    if (other == null) {
      return +1;
    }
    final int rc = Comparison.compare(url, other.url);
    if (rc != 0) {
      return rc;
    } else {
      return Comparison.compare(anchor, other.anchor);
    }
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("url", url)
               .append("anchor", anchor)
               .toString();
  }

}