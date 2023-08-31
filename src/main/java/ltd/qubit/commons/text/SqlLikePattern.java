////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;
import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 表示SQL语法支持的LIKE字符串匹配模式。
 *
 * @author Haixing Hu
 */
public class SqlLikePattern {

  private static final int FLAGS =
      CASE_INSENSITIVE | UNICODE_CASE | CANON_EQ | MULTILINE;

  private final String sql;
  private final String regex;
  private final transient Pattern pattern;

  public SqlLikePattern(final String sql) {
    this.sql = requireNonNull("sql", sql);
    this.regex = "^" + quote(sql).replace('_', '.').replace("%", ".*") + "$";
    this.pattern = compile(regex, FLAGS);
  }

  public final String getSql() {
    return sql;
  }

  public final String getRegex() {
    return regex;
  }

  public final Pattern getPattern() {
    return pattern;
  }

  public final boolean match(final String str) {
    return pattern.matcher(str).matches();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final SqlLikePattern other = (SqlLikePattern) o;
    return Equality.equals(sql, other.sql)
        && Equality.equals(regex, other.regex);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, sql);
    result = Hash.combine(result, multiplier, regex);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("sql", sql)
        .append("regex", regex)
        .append("pattern", pattern)
        .toString();
  }
}
