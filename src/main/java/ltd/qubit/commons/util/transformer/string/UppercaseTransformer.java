////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.string;

import java.util.Locale;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * {@link UppercaseTransformer}将字符串转换为其大写形式。
 *
 * @author 胡海星
 */
public final class UppercaseTransformer extends AbstractStringTransformer {

  private Locale locale;

  /**
   * 构造一个{@link UppercaseTransformer}。
   * <p>
   * 转换时将使用默认的{@link Locale}。
   */
  public UppercaseTransformer() {
    locale = null;
  }

  /**
   * 构造一个{@link UppercaseTransformer}。
   *
   * @param locale
   *     转换时要使用的{@link Locale}。
   */
  public UppercaseTransformer(final Locale locale) {
    this.locale = locale;
  }

  /**
   * 获取转换时要使用的{@link Locale}。
   *
   * @return
   *     转换时要使用的{@link Locale}。
   */
  public Locale getLocale() {
    return locale;
  }

  /**
   * 设置转换时要使用的{@link Locale}。
   *
   * @param locale
   *     新的转换时要使用的{@link Locale}。
   */
  public void setLocale(final Locale locale) {
    this.locale = locale;
  }

  @Override
  public String transform(final String str) {
    if (locale == null) {
      return str.toUpperCase();
    } else {
      return str.toUpperCase(locale);
    }
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final UppercaseTransformer other = (UppercaseTransformer) o;
    return Equality.equals(locale, other.locale);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, locale);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("locale", locale)
               .toString();
  }

  @Override
  public UppercaseTransformer cloneEx() {
    return new UppercaseTransformer(locale);
  }
}