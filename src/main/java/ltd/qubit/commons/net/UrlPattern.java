////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import java.io.Serializable;

import javax.annotation.Nullable;

import ltd.qubit.commons.io.io.serialize.BinarySerialization;
import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.ObjectUtils;
import ltd.qubit.commons.text.Pattern;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * A {@link UrlPattern} represents a rule of pattern used to matches URLs.
 *
 * @author Haixing Hu
 */
public class UrlPattern implements Serializable, CloneableEx<UrlPattern> {

  private static final long serialVersionUID = 9135054642012865500L;

  public static final UrlPart DEFAULT_PART = UrlPart.URL;

  static {
    BinarySerialization.register(UrlPattern.class, UrlPatternBinarySerializer.INSTANCE);
  }

  protected UrlPart part;

  protected Pattern pattern;

  public UrlPattern() {
    part = null;
    pattern = new Pattern();
  }

  public UrlPattern(final Pattern pattern) {
    part = null;
    this.pattern = Argument.requireNonNull("pattern", pattern);
  }

  public UrlPattern(final UrlPart part, final Pattern pattern) {
    this.part = part;
    this.pattern = Argument.requireNonNull("pattern", pattern);
  }

  public UrlPart getPart() {
    return part;
  }

  public void setPart(final UrlPart part) {
    this.part = part;
  }

  public Pattern getPattern() {
    return pattern;
  }

  public void setPattern(final Pattern pattern) {
    this.pattern = Argument.requireNonNull("pattern", pattern);
  }

  public boolean matches(@Nullable final Url url) {
    if (url == null) {
      return false;
    }
    final String str = url.get(ObjectUtils.defaultIfNull(part, DEFAULT_PART));
    return pattern.matches(str);
  }

  @Override
  public UrlPattern cloneEx() {
    return new UrlPattern(part, pattern.cloneEx());
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final UrlPattern other = (UrlPattern) o;
    return Equality.equals(part, other.part)
            && Equality.equals(pattern, other.pattern);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, part);
    result = Hash.combine(result, multiplier, pattern);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("part", part)
            .append("pattern", pattern)
            .toString();
  }
}
