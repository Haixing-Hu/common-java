////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.html;

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
 * The {@link HyperLink} class represents a hyper link in HTML pages.
 *
 * @author Haixing Hu
 */
@Immutable
public final class HyperLink implements Comparable<HyperLink>,
        CloneableEx<HyperLink>, Serializable {

  private static final long serialVersionUID = - 6896074974015880689L;

  public static final String ROOT_NODE = "hyper-link";
  public static final String URL_ATTRIBUTE = "url";

  static {
    BinarySerialization.register(HyperLink.class, HyperLinkBinarySerializer.INSTANCE);
    XmlSerialization.register(HyperLink.class, HyperLinkXmlSerializer.INSTANCE);
  }

  private final @Nullable Url url;
  private final @Nullable String anchor;

  public HyperLink() {
    url = null;
    anchor = null;
  }

  public HyperLink(@Nullable final Url url) {
    this.url = url;
    anchor = null;
  }

  public HyperLink(@Nullable final Url url, @Nullable final String anchor) {
    this.url = url;
    this.anchor = anchor;
  }

  public Url url() {
    return url;
  }

  public String anchor() {
    return anchor;
  }

  @Override
  public HyperLink clone() {
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
