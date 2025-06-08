////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.filter.character;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * 一个字符过滤器，仅接受不在指定字符串中存在的字符。
 *
 * @author 胡海星
 */
public final class NotInStringCharFilter implements CharFilter {

  /**
   * 被拒绝的字符序列。
   */
  private CharSequence rejectedChars;

  /**
   * 构造一个 {@link NotInStringCharFilter}。
   */
  public NotInStringCharFilter() {
    this.rejectedChars = null;
  }

  /**
   * 构造一个 {@link NotInStringCharFilter}。
   *
   * @param rejectedChars
   *     被拒绝的字符序列。
   */
  public NotInStringCharFilter(final CharSequence rejectedChars) {
    this.rejectedChars = rejectedChars;
  }

  /**
   * 获取被拒绝的字符序列。
   *
   * @return 被拒绝的字符序列。
   */
  public CharSequence getRejectedChars() {
    return rejectedChars;
  }

  /**
   * 设置被拒绝的字符序列。
   *
   * @param rejectedChars
   *     新的被拒绝的字符序列。
   */
  public void setRejectedChars(final CharSequence rejectedChars) {
    this.rejectedChars = rejectedChars;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean accept(final Character ch) {
    if (ch == null) {
      return false;
    }
    if (rejectedChars == null) {
      return true;
    }
    final int n = rejectedChars.length();
    for (int i = 0; i < n; ++i) {
      if (rejectedChars.charAt(i) == ch) {
        return false;
      }
    }
    return true;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final NotInStringCharFilter other = (NotInStringCharFilter) o;
    return Equality.equals(rejectedChars, other.rejectedChars);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, rejectedChars);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("rejectedChars", rejectedChars)
        .toString();
  }
}