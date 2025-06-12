////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import ltd.qubit.commons.lang.CharUtils;
import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireLengthAtLeast;

/**
 * {@link BooleanFormatSymbols} 对象用于存储格式化和解析布尔值的符号。
 *
 * @author 胡海星
 */
public final class BooleanFormatSymbols implements CloneableEx<BooleanFormatSymbols> {

  /**
   * 默认的二进制数字字符数组。
   */
  public static final char[] DEFAULT_BINARY_DIGITS  =  CharUtils.LOWERCASE_DIGITS;

  /**
   * 默认的真值名称。
   */
  public static final String DEFAULT_TRUE_NAME = "true";

  /**
   * 默认的假值名称。
   */
  public static final String DEFAULT_FALSE_NAME = "false";

  /**
   * 二进制数字字符数组。
   */
  private char[] binaryDigits;

  /**
   * 真值名称。
   */
  private String trueName;

  /**
   * 假值名称。
   */
  private String falseName;

  /**
   * 构造一个新的 {@link BooleanFormatSymbols} 对象。
   */
  public BooleanFormatSymbols() {
    binaryDigits = DEFAULT_BINARY_DIGITS;
    trueName = DEFAULT_TRUE_NAME;
    falseName = DEFAULT_FALSE_NAME;
  }

  /**
   * 重置此对象为默认值。
   */
  public void reset() {
    binaryDigits = DEFAULT_BINARY_DIGITS;
    trueName = DEFAULT_TRUE_NAME;
    falseName = DEFAULT_FALSE_NAME;
  }

  /**
   * 获取二进制数字字符数组。
   *
   * @return 二进制数字字符数组。
   */
  public char[] getBinaryDigits() {
    return binaryDigits;
  }

  /**
   * 设置二进制数字字符数组。
   *
   * @param binaryDigits
   *          二进制数字字符数组，长度必须至少为2。
   */
  public void setBinaryDigits(final char[] binaryDigits) {
    this.binaryDigits = requireLengthAtLeast("binaryDigits", binaryDigits, 2);
  }

  /**
   * 获取真值名称。
   *
   * @return 真值名称。
   */
  public String getTrueName() {
    return trueName;
  }

  /**
   * 设置真值名称。
   *
   * @param trueName
   *          真值名称，长度必须至少为1。
   */
  public void setTrueName(final String trueName) {
    this.trueName = requireLengthAtLeast("trueName", trueName, 1);
  }

  /**
   * 获取假值名称。
   *
   * @return 假值名称。
   */
  public String getFalseName() {
    return falseName;
  }

  /**
   * 设置假值名称。
   *
   * @param falseName
   *          假值名称，长度必须至少为1。
   */
  public void setFalseName(final String falseName) {
    this.falseName = requireLengthAtLeast("falseName", falseName, 1);
  }

  /**
   * 将另一个对象的值复制给此对象。
   *
   * @param that
   *          另一个 {@link BooleanFormatSymbols} 对象。
   */
  public void assign(final BooleanFormatSymbols that) {
    if (this == that) {
      return;
    }
    binaryDigits = that.binaryDigits;
    trueName = that.trueName;
    falseName = that.falseName;
  }

  @Override
  public BooleanFormatSymbols cloneEx() {
    final BooleanFormatSymbols result = new BooleanFormatSymbols();
    result.assign(this);
    return result;
  }

  @Override
  public int hashCode() {
    final int multiplier = 3;
    int code = 17;
    code = Hash.combine(code, multiplier, binaryDigits);
    code = Hash.combine(code, multiplier, trueName);
    code = Hash.combine(code, multiplier, falseName);
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
    final BooleanFormatSymbols other = (BooleanFormatSymbols) obj;
    return Equality.equals(binaryDigits, other.binaryDigits)
        && trueName.equals(other.trueName)
        && falseName.equals(other.falseName);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("binaryDigits", binaryDigits)
               .append("trueName", trueName)
               .append("falseName", falseName)
               .toString();
  }
}