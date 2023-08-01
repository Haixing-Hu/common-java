////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.io.Serializable;
import java.util.regex.Matcher;

import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.NotThreadSafe;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

import ltd.qubit.commons.io.serialize.BinarySerialization;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.ObjectUtils.defaultIfNull;

/**
 * A {@link Pattern} object represents a generic pattern of strings.
 *
 * <p>NOTE: This class is NOT thread safe. If more than one threads use the
 * {@link #matches(String)} method, it must be synchronized.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public final class Pattern implements CloneableEx<Pattern>, Serializable {

  private static final long serialVersionUID = -6759804850795758979L;

  public static final PatternType DEFAULT_TYPE = PatternType.LITERAL;
  public static final boolean DEFAULT_IGNORE_CASE = false;

  static {
    BinarySerialization.register(Pattern.class, PatternBinarySerializer.INSTANCE);
  }

  @XmlAttribute
  private PatternType type;

  @XmlAttribute
  private Boolean ignoreCase;

  @XmlValue
  private String expression;

  private transient Matcher matcher;

  public Pattern() {
    type = null;
    ignoreCase = null;
    expression = StringUtils.EMPTY;
    matcher = null;
  }

  public Pattern(final String expression) {
    type = null;
    ignoreCase = null;
    this.expression = requireNonNull("expression", expression);
    matcher = null;
  }

  public Pattern(final PatternType type, final String expression) {
    this.type = type;
    ignoreCase = null;
    this.expression = requireNonNull("expression", expression);
    matcher = null;
  }

  public Pattern(final PatternType type, final Boolean ignoreCase,
      final String expression) {
    this.type = type;
    this.ignoreCase = ignoreCase;
    this.expression = requireNonNull("expression", expression);
    matcher = null;
  }

  public PatternType getType() {
    return type;
  }

  public void setType(final PatternType type) {
    this.type = type;
    matcher = null;
  }

  public Boolean getIgnoreCase() {
    return ignoreCase;
  }

  public void setIgnoreCase(final Boolean ignoreCase) {
    this.ignoreCase = ignoreCase;
    matcher = null;
  }

  public String getExpression() {
    return expression;
  }

  public void setExpression(final String expression) {
    this.expression = requireNonNull("expression", expression);
    matcher = null;
  }

  public boolean matches(@Nullable final String str) {
    if (str == null) {
      return false;
    }
    switch (defaultIfNull(type, DEFAULT_TYPE)) {
      case LITERAL:
        if (defaultIfNull(ignoreCase, DEFAULT_IGNORE_CASE)) {
          return expression.equalsIgnoreCase(str);
        } else {
          return expression.equals(str);
        }
      case PREFIX:
        return new Searcher()
            .forSubstring(expression)
            .ignoreCase(defaultIfNull(ignoreCase, DEFAULT_IGNORE_CASE))
            .isAtStartOf(str);
      case SUFFIX:
        return new Searcher()
            .forSubstring(expression)
            .ignoreCase(defaultIfNull(ignoreCase, DEFAULT_IGNORE_CASE))
            .isAtEndOf(str);
      case REGEX:
        if (matcher == null) {
          setRegexMatcher();
        }
        return matcher.reset(str).matches();
      case GLOB:
        if (matcher == null) {
          setGlobMatcher();
        }
        return matcher.reset(str).matches();
      default:
        return false;
    }
  }

  @GuardedBy("this")
  private void setRegexMatcher() {
    int flags = 0;
    if (defaultIfNull(ignoreCase, DEFAULT_IGNORE_CASE)) {
      flags = java.util.regex.Pattern.CASE_INSENSITIVE;
    }
    matcher = java.util.regex.Pattern.compile(expression, flags)
                                     .matcher(StringUtils.EMPTY);
  }

  @GuardedBy("this")
  private void setGlobMatcher() {
    int flags = 0;
    if (defaultIfNull(ignoreCase, DEFAULT_IGNORE_CASE)) {
      flags = java.util.regex.Pattern.CASE_INSENSITIVE;
    }
    final String regex = Glob.toRegex(expression);
    matcher = java.util.regex.Pattern.compile(regex, flags)
                                     .matcher(StringUtils.EMPTY);
  }

  @Override
  public Pattern clone() {
    return new Pattern(type, ignoreCase, expression);
  }

  @Override
  public int hashCode() {
    final int multiplier = 71;
    int code = 771;
    code = Hash.combine(code, multiplier, type);
    code = Hash.combine(code, multiplier, ignoreCase);
    code = Hash.combine(code, multiplier, expression);
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
    final Pattern other = (Pattern) obj;
    return Equality.equals(type, other.type)
        && Equality.equals(ignoreCase, other.ignoreCase)
        && Equality.equals(expression, other.expression);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("type", type)
        .append("ignoreCase", ignoreCase)
        .append("expression", expression)
        .toString();
  }

}
