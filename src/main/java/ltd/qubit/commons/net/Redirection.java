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

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * {@link Redirection}对象存储URL重定向的信息。
 *
 * @author 胡海星
 */
@Immutable
public final class Redirection implements Serializable,
        CloneableEx<Redirection>, Comparable<Redirection> {

  private static final long serialVersionUID = 7029539141176579563L;

  public final Url  url;
  public final boolean temporary;
  public final long    refreshTime;

  /**
   * 构造一个重定向对象。
   *
   * @param url
   *     重定向的目标URL
   * @param temporary
   *     是否为临时重定向
   * @param refreshTime
   *     刷新时间
   */
  public Redirection(final Url url, final boolean temporary,
      final long refreshTime) {
    this.url = Argument.requireNonNull("url", url);
    this.temporary = temporary;
    this.refreshTime = refreshTime;
  }

  /**
   * 克隆当前重定向对象。
   *
   * @return 克隆的重定向对象
   */
  @Override
  public Redirection cloneEx() {
    return new Redirection(url, temporary, refreshTime);
  }

  @Override
  public int hashCode() {
    final int multiplier = 11;
    int code = 17;
    code = Hash.combine(code, multiplier, url);
    code = Hash.combine(code, multiplier, temporary);
    code = Hash.combine(code, multiplier, refreshTime);
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
    final Redirection other = (Redirection) obj;
    return url.equals(other.url)
        && (temporary == other.temporary)
        && (refreshTime == other.refreshTime);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("url", url)
               .append("temporary", temporary)
               .append("refreshTime", refreshTime)
               .toString();
  }

  @Override
  public int compareTo(final Redirection other) {
    return url.compareTo(other.url);
  }
}