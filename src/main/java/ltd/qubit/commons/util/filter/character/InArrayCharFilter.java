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
 * 一个字符过滤器，仅接受在指定字符数组中存在的字符。
 *
 * @author 胡海星
 */
public final class InArrayCharFilter implements CharFilter {

  /**
   * 可接受的字符数组。
   */
  private char[] acceptedChars;

  /**
   * 构造一个 {@link InArrayCharFilter}。
   */
  public InArrayCharFilter() {
    this.acceptedChars = null;
  }

  /**
   * 构造一个 {@link InArrayCharFilter}。
   *
   * @param acceptedChars
   *     可接受的字符数组。
   */
  public InArrayCharFilter(final char[] acceptedChars) {
    this.acceptedChars = acceptedChars;
  }

  /**
   * 获取可接受的字符数组。
   *
   * @return 可接受的字符数组。
   */
  public char[] getAcceptedChars() {
    return acceptedChars;
  }

  /**
   * 设置可接受的字符数组。
   *
   * @param acceptedChars
   *     新的可接受的字符数组。
   */
  public void setAcceptedChars(final char[] acceptedChars) {
    this.acceptedChars = acceptedChars;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean accept(final Character ch) {
    if ((ch == null) || (acceptedChars == null)) {
      return false;
    }
    for (final char c : acceptedChars) {
      if (c == ch) {
        return true;
      }
    }
    return false;
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final InArrayCharFilter other = (InArrayCharFilter) o;
    return Equality.equals(acceptedChars, other.acceptedChars);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, acceptedChars);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("acceptedChars", acceptedChars)
        .toString();
  }
}