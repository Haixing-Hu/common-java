////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import javax.annotation.concurrent.Immutable;

/**
 * {@link UnmodifiableParseOptions} 是一个创建后无法修改的 {@link ParseOptions}。
 *
 * @author 胡海星
 */
@Immutable
public final class UnmodifiableParseOptions extends ParseOptions {

  private static final long serialVersionUID = 1029792624986278503L;

  /**
   * 构造一个具有默认标志和默认基数的不可修改解析选项。
   */
  public UnmodifiableParseOptions() {
  }

  /**
   * 构造一个具有指定标志的不可修改解析选项。
   *
   * @param flags 标志值
   */
  public UnmodifiableParseOptions(final int flags) {
    super(flags);
  }

  /**
   * 构造一个具有指定标志和默认基数的不可修改解析选项。
   *
   * @param flags 标志值
   * @param defaultRadix 默认基数
   */
  public UnmodifiableParseOptions(final int flags, final int defaultRadix) {
    super(flags, defaultRadix);
  }

  /**
   * 构造一个具有指定标志、默认基数和最大数字位数的不可修改解析选项。
   *
   * @param flags 标志值
   * @param defaultRadix 默认基数
   * @param maxDigits 最大数字位数
   */
  public UnmodifiableParseOptions(final int flags, final int defaultRadix,
      final int maxDigits) {
    super(flags, defaultRadix, maxDigits);
  }

  /**
   * 构造一个与指定解析选项相同的不可修改解析选项。
   *
   * @param options 要复制的解析选项
   */
  public UnmodifiableParseOptions(final ParseOptions options) {
    super(options.flags, options.defaultRadix, options.maxDigits);
  }

  /**
   * 抛出 {@link UnsupportedOperationException}，因为此对象不可修改。
   *
   * @param flags 标志值
   * @throws UnsupportedOperationException 总是抛出此异常
   */
  @Override
  public void setFlags(final int flags) {
    throw new UnsupportedOperationException();
  }

  /**
   * 抛出 {@link UnsupportedOperationException}，因为此对象不可修改。
   *
   * @param flags 要添加的标志值
   * @throws UnsupportedOperationException 总是抛出此异常
   */
  @Override
  public void addFlags(final int flags) {
    throw new UnsupportedOperationException();
  }

  /**
   * 抛出 {@link UnsupportedOperationException}，因为此对象不可修改。
   *
   * @param flags 要添加的标志值
   * @param mask 掩码
   * @throws UnsupportedOperationException 总是抛出此异常
   */
  @Override
  public void addFlags(final int flags, final int mask) {
    throw new UnsupportedOperationException();
  }

  /**
   * 抛出 {@link UnsupportedOperationException}，因为此对象不可修改。
   *
   * @param defaultRadix 默认基数
   * @throws UnsupportedOperationException 总是抛出此异常
   */
  @Override
  public void setDefaultRadix(final int defaultRadix) {
    throw new UnsupportedOperationException();
  }

  /**
   * 抛出 {@link UnsupportedOperationException}，因为此对象不可修改。
   *
   * @param maxDigits 最大数字位数
   * @throws UnsupportedOperationException 总是抛出此异常
   */
  @Override
  public void setMaxDigits(final int maxDigits) {
    throw new UnsupportedOperationException();
  }

  /**
   * 抛出 {@link UnsupportedOperationException}，因为此对象不可修改。
   *
   * @throws UnsupportedOperationException 总是抛出此异常
   */
  @Override
  public void resetMaxDigits() {
    throw new UnsupportedOperationException();
  }

  /**
   * 抛出 {@link UnsupportedOperationException}，因为此对象不可修改。
   *
   * @param value 是否启用布尔字母
   * @throws UnsupportedOperationException 总是抛出此异常
   */
  @Override
  public void setBoolAlpha(final boolean value) {
    throw new UnsupportedOperationException();
  }

  /**
   * 抛出 {@link UnsupportedOperationException}，因为此对象不可修改。
   *
   * @param value 是否启用分组
   * @throws UnsupportedOperationException 总是抛出此异常
   */
  @Override
  public void setGrouping(final boolean value) {
    throw new UnsupportedOperationException();
  }

  /**
   * 抛出 {@link UnsupportedOperationException}，因为此对象不可修改。
   *
   * @param value 是否保留空白
   * @throws UnsupportedOperationException 总是抛出此异常
   */
  @Override
  public void setKeepBlank(final boolean value) {
    throw new UnsupportedOperationException();
  }

  /**
   * 抛出 {@link UnsupportedOperationException}，因为此对象不可修改。
   *
   * @param value 是否启用二进制
   * @throws UnsupportedOperationException 总是抛出此异常
   */
  @Override
  public void setBinary(final boolean value) {
    throw new UnsupportedOperationException();
  }

  /**
   * 抛出 {@link UnsupportedOperationException}，因为此对象不可修改。
   *
   * @param value 是否启用八进制
   * @throws UnsupportedOperationException 总是抛出此异常
   */
  @Override
  public void setOctal(final boolean value) {
    throw new UnsupportedOperationException();
  }

  /**
   * 抛出 {@link UnsupportedOperationException}，因为此对象不可修改。
   *
   * @param value 是否启用十进制
   * @throws UnsupportedOperationException 总是抛出此异常
   */
  @Override
  public void setDecimal(final boolean value) {
    throw new UnsupportedOperationException();
  }

  /**
   * 抛出 {@link UnsupportedOperationException}，因为此对象不可修改。
   *
   * @param value 是否启用十六进制
   * @throws UnsupportedOperationException 总是抛出此异常
   */
  @Override
  public void setHex(final boolean value) {
    throw new UnsupportedOperationException();
  }

  /**
   * 抛出 {@link UnsupportedOperationException}，因为此对象不可修改。
   *
   * @throws UnsupportedOperationException 总是抛出此异常
   */
  @Override
  public void clearRadixOptions() {
    throw new UnsupportedOperationException();
  }

  /**
   * 抛出 {@link UnsupportedOperationException}，因为此对象不可修改。
   *
   * @param value 是否启用定点数
   * @throws UnsupportedOperationException 总是抛出此异常
   */
  @Override
  public void setFixPoint(final boolean value) {
    throw new UnsupportedOperationException();
  }

  /**
   * 抛出 {@link UnsupportedOperationException}，因为此对象不可修改。
   *
   * @param value 是否启用科学计数法
   * @throws UnsupportedOperationException 总是抛出此异常
   */
  @Override
  public void setScientific(final boolean value) {
    throw new UnsupportedOperationException();
  }

  /**
   * 抛出 {@link UnsupportedOperationException}，因为此对象不可修改。
   *
   * @param value 是否启用通用格式
   * @throws UnsupportedOperationException 总是抛出此异常
   */
  @Override
  public void setGeneral(final boolean value) {
    throw new UnsupportedOperationException();
  }

  /**
   * 抛出 {@link UnsupportedOperationException}，因为此对象不可修改。
   *
   * @throws UnsupportedOperationException 总是抛出此异常
   */
  @Override
  public void clearRealOptions() {
    throw new UnsupportedOperationException();
  }

  /**
   * 抛出 {@link UnsupportedOperationException}，因为此对象不可修改。
   *
   * @throws UnsupportedOperationException 总是抛出此异常
   */
  @Override
  public void reset() {
    throw new UnsupportedOperationException();
  }

}