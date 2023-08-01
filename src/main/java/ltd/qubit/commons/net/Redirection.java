////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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
 * A {@link Redirection} object stores the information about a URL redirection.
 *
 * @author Haixing Hu
 */
@Immutable
public final class Redirection implements Serializable,
        CloneableEx<Redirection>, Comparable<Redirection> {

  private static final long serialVersionUID = 7029539141176579563L;

  public final Url  url;
  public final boolean temporary;
  public final long    refreshTime;

  public Redirection(final Url url, final boolean temporary,
      final long refreshTime) {
    this.url = Argument.requireNonNull("url", url);
    this.temporary = temporary;
    this.refreshTime = refreshTime;
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
  public int compareTo(final Redirection other) {
    return url.compareTo(other.url);
  }

  @Override
  public Redirection clone() {
    return new Redirection(url, temporary, refreshTime);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("url", url)
               .append("temporary", temporary)
               .append("refreshTime", refreshTime)
               .toString();
  }
}
