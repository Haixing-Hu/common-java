////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.concurrent.GuardedBy;

import ltd.qubit.commons.lang.Assignable;
import ltd.qubit.commons.lang.CharUtils;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.lang.Argument.requireLengthAtLeast;
import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.text.NumberFormat.HEX_RADIX;

/**
 * {@link NumberFormatSymbols} 对象用于存储数字格式化和解析的符号。
 *
 * @author 胡海星
 */
public final class NumberFormatSymbols implements Assignable<NumberFormatSymbols> {

  /**
   * 默认的小写数字字符数组。
   */
  public static final char[] DEFAULT_LOWERCASE_DIGITS     =  CharUtils.LOWERCASE_DIGITS;

  /**
   * 默认的大写数字字符数组。
   */
  public static final char[] DEFAULT_UPPERCASE_DIGITS     =  CharUtils.UPPERCASE_DIGITS;

  /**
   * 默认的小写进制前缀数组。
   */
  public static final String[] DEFAULT_LOWERCASE_RADIX_PREFIXES = {
    /* 0 */  null,
    /* 1 */  null,
    /* 2 */  "0b",
    /* 3 */  null,
    /* 4 */  null,
    /* 5 */  null,
    /* 6 */  null,
    /* 7 */  null,
    /* 8 */  "0",
    /* 9 */  null,
    /* 10 */ null,
    /* 11 */ null,
    /* 12 */ null,
    /* 13 */ null,
    /* 14 */ null,
    /* 15 */ null,
    /* 16 */ "0x",
  };

  /**
   * 默认的大写进制前缀数组。
   */
  public static final String[] DEFAULT_UPPERCASE_RADIX_PREFIXES = {
    /* 0 */  null,
    /* 1 */  null,
    /* 2 */  "0B",
    /* 3 */  null,
    /* 4 */  null,
    /* 5 */  null,
    /* 6 */  null,
    /* 7 */  null,
    /* 8 */  "0",
    /* 9 */  null,
    /* 10 */ null,
    /* 11 */ null,
    /* 12 */ null,
    /* 13 */ null,
    /* 14 */ null,
    /* 15 */ null,
    /* 16 */ "0X",
  };

  /**
   * 默认的正号字符。
   */
  public static final char DEFAULT_POSITIVE_SIGN = '+';

  /**
   * 默认的负号字符。
   */
  public static final char DEFAULT_NEGATIVE_SIGN = '-';

  /**
   * 默认的小数分隔符字符。
   */
  public static final char DEFAULT_RADIX_SEPARATOR = '.';

  /**
   * 默认的分组分隔符字符。
   */
  public static final char DEFAULT_GROUPING_SEPARATOR = ',';

  /**
   * 默认的指数分隔符字符串。
   */
  public static final String DEFAULT_EXPONENT_SEPARATOR = "E";

  /**
   * 默认的百分号符号字符。
   */
  public static final char DEFAULT_PERCENT_SYMBOL = '%';

  /**
   * 默认的千分号符号字符。
   */
  public static final char DEFAULT_PERMILLE_SYMBOL = '‰';

  /**
   * 默认的货币符号字符串。
   */
  public static final String DEFAULT_CURRENCY_SYMBOL = "$";

  /**
   * 默认的货币小数分隔符字符。
   */
  public static final char DEFAULT_MONETARY_RADIX_SEPARATOR = '.';

  /**
   * 默认的货币分组分隔符字符。
   */
  public static final char DEFAULT_MONETARY_GROUPING_SEPARATOR = ',';

  /**
   * 默认的无穷大符号字符串。
   */
  public static final String DEFAULT_INFINITY_SYMBOL = "∞";

  /**
   * 默认的NaN符号字符串。
   */
  public static final String DEFAULT_NAN_SYMBOL = "NaN.";

  @GuardedBy("NumberFormatSymbols.class")
  private static final Map<Locale, DecimalFormatSymbols> symbolsCache = new HashMap<>();

  /**
   * 获取指定语言环境的缓存的十进制格式符号。
   *
   * @param locale
   *     指定的语言环境。
   * @return
   *     指定语言环境的十进制格式符号。
   */
  private static synchronized DecimalFormatSymbols getCachedSymbols(final Locale locale) {
    requireNonNull("locale", locale);
    DecimalFormatSymbols symbols = symbolsCache.get(locale);
    if (symbols == null) {
      symbols = DecimalFormatSymbols.getInstance(locale);
      symbolsCache.put(locale, symbols);
    }
    return symbols;
  }

  private char[] lowercaseDigits;
  private char[] uppercaseDigits;
  private String[] lowercaseRadixPrefixes;
  private String[] uppercaseRadixPrefixes;
  private char positiveSign;
  private char negativeSign;
  private char radixSeparator;
  private char groupingSeparator;
  private String exponentSeparator;
  private String infinitySymbol;
  private String nanSymbol;
  private char percentSymbol;
  private char permilleSymbol;
  private String currencySymbol;
  private char monetaryRadixSeparator;
  private char monetaryGroupingSeparator;

  /**
   * 构造一个新的数字格式符号对象，使用默认值初始化。
   */
  public NumberFormatSymbols() {
    reset();
  }

  /**
   * 构造一个新的数字格式符号对象，使用指定语言环境的符号初始化。
   *
   * @param locale
   *     指定的语言环境。
   */
  public NumberFormatSymbols(final Locale locale) {
    reset(locale);
  }

  /**
   * 重置此对象为默认值。
   */
  public void reset() {
    lowercaseDigits = DEFAULT_LOWERCASE_DIGITS;
    uppercaseDigits = DEFAULT_UPPERCASE_DIGITS;
    lowercaseRadixPrefixes = DEFAULT_LOWERCASE_RADIX_PREFIXES;
    uppercaseRadixPrefixes = DEFAULT_UPPERCASE_RADIX_PREFIXES;
    positiveSign = DEFAULT_POSITIVE_SIGN;
    negativeSign = DEFAULT_NEGATIVE_SIGN;
    radixSeparator = DEFAULT_RADIX_SEPARATOR;
    groupingSeparator = DEFAULT_GROUPING_SEPARATOR;
    exponentSeparator = DEFAULT_EXPONENT_SEPARATOR;
    infinitySymbol = DEFAULT_INFINITY_SYMBOL;
    nanSymbol = DEFAULT_NAN_SYMBOL;
    percentSymbol = DEFAULT_PERCENT_SYMBOL;
    permilleSymbol = DEFAULT_PERMILLE_SYMBOL;
    currencySymbol = DEFAULT_CURRENCY_SYMBOL;
    monetaryRadixSeparator = DEFAULT_MONETARY_RADIX_SEPARATOR;
    monetaryGroupingSeparator = DEFAULT_MONETARY_GROUPING_SEPARATOR;
  }

  /**
   * 重置此对象为指定语言环境的符号。
   *
   * @param locale
   *     指定的语言环境。
   */
  public void reset(final Locale locale) {
    final DecimalFormatSymbols symbols = getCachedSymbols(locale);
    if (symbols == null) {
      reset();
    } else { // formatSymbols != null
      lowercaseDigits = DEFAULT_LOWERCASE_DIGITS;
      uppercaseDigits = DEFAULT_UPPERCASE_DIGITS;
      lowercaseRadixPrefixes = DEFAULT_LOWERCASE_RADIX_PREFIXES;
      uppercaseRadixPrefixes = DEFAULT_UPPERCASE_RADIX_PREFIXES;
      // note that no positive sign is provided by the DecimalFormatSymbols in
      // JDK 1.6
      positiveSign = DEFAULT_POSITIVE_SIGN;
      negativeSign = symbols.getMinusSign();
      radixSeparator = symbols.getDecimalSeparator();
      groupingSeparator = symbols.getGroupingSeparator();
      exponentSeparator = symbols.getExponentSeparator();
      infinitySymbol = symbols.getInfinity();
      nanSymbol = symbols.getNaN();
      percentSymbol = symbols.getPercent();
      permilleSymbol = symbols.getPerMill();
      currencySymbol = symbols.getCurrencySymbol();
      monetaryRadixSeparator = symbols.getMonetaryDecimalSeparator();
      // note that no monetary grouping separator is provided by the
      // DecimalFormatSymbols in JDK 1.6
      monetaryGroupingSeparator = symbols.getGroupingSeparator();
    }
  }

  /**
   * 获取小写数字字符数组。
   *
   * @return
   *     小写数字字符数组。
   */
  public char[] getLowercaseDigits() {
    return lowercaseDigits;
  }

  /**
   * 设置小写数字字符数组。
   *
   * @param lowercaseDigits
   *     新的小写数字字符数组，长度必须至少为16。
   */
  public void setLowercaseDigits(final char[] lowercaseDigits) {
    this.lowercaseDigits = requireLengthAtLeast("lowercaseDigits",
        lowercaseDigits, HEX_RADIX);
  }

  /**
   * 获取大写数字字符数组。
   *
   * @return
   *     大写数字字符数组。
   */
  public char[] getUppercaseDigits() {
    return uppercaseDigits;
  }

  /**
   * 设置大写数字字符数组。
   *
   * @param uppercaseDigits
   *     新的大写数字字符数组，长度必须至少为16。
   */
  public void setUppercaseDigits(final char[] uppercaseDigits) {
    this.uppercaseDigits = requireLengthAtLeast("uppercaseDigits",
        uppercaseDigits, HEX_RADIX);
  }

  /**
   * 根据指定的大小写模式获取数字字符数组。
   *
   * @param uppercase
   *     是否使用大写字符。
   * @return
   *     相应的数字字符数组。
   */
  public char[] getDigits(final boolean uppercase) {
    return (uppercase ? uppercaseDigits : lowercaseDigits);
  }

  /**
   * 获取小写进制前缀数组。
   *
   * @return
   *     小写进制前缀数组。
   */
  public String[] getLowercaseRadixPrefixes() {
    return lowercaseRadixPrefixes;
  }

  /**
   * 设置小写进制前缀数组。
   *
   * @param lowercaseRadixPrefixes
   *     新的小写进制前缀数组，长度必须至少为16。
   */
  public void setLowercaseRadixPrefixes(final String[] lowercaseRadixPrefixes) {
    this.lowercaseRadixPrefixes = requireLengthAtLeast("lowercaseRadixPrefixes",
        lowercaseRadixPrefixes, HEX_RADIX);
  }

  /**
   * 获取大写进制前缀数组。
   *
   * @return
   *     大写进制前缀数组。
   */
  public String[] getUppercaseRadixPrefixes() {
    return uppercaseRadixPrefixes;
  }

  /**
   * 设置大写进制前缀数组。
   *
   * @param uppercaseRadixPrefixes
   *     新的大写进制前缀数组，长度必须至少为16。
   */
  public void setUppercaseRadixPrefixes(final String[] uppercaseRadixPrefixes) {
    this.uppercaseRadixPrefixes = requireLengthAtLeast("uppercaseRadixPrefixes",
        uppercaseRadixPrefixes, HEX_RADIX);
  }

  /**
   * 根据指定的大小写模式获取进制前缀数组。
   *
   * @param uppercase
   *     是否使用大写字符。
   * @return
   *     相应的进制前缀数组。
   */
  public String[] getRadixPrefixes(final boolean uppercase) {
    return (uppercase ? uppercaseRadixPrefixes : lowercaseRadixPrefixes);
  }

  /**
   * 获取正号字符。
   *
   * @return
   *     正号字符。
   */
  public char getPositiveSign() {
    return positiveSign;
  }

  /**
   * 设置正号字符。
   *
   * @param positiveSign
   *     新的正号字符。
   */
  public void setPositiveSign(final char positiveSign) {
    this.positiveSign = positiveSign;
  }

  /**
   * 获取负号字符。
   *
   * @return
   *     负号字符。
   */
  public char getNegativeSign() {
    return negativeSign;
  }

  /**
   * 设置负号字符。
   *
   * @param negativeSign
   *     新的负号字符。
   */
  public void setNegativeSign(final char negativeSign) {
    this.negativeSign = negativeSign;
  }

  /**
   * 获取小数分隔符字符。
   *
   * @return
   *     小数分隔符字符。
   */
  public char getRadixSeparator() {
    return radixSeparator;
  }

  /**
   * 设置小数分隔符字符。
   *
   * @param radixSeparator
   *     新的小数分隔符字符。
   */
  public void setRadixSeparator(final char radixSeparator) {
    this.radixSeparator = radixSeparator;
  }

  /**
   * 获取分组分隔符字符。
   *
   * @return
   *     分组分隔符字符。
   */
  public char getGroupingSeparator() {
    return groupingSeparator;
  }

  /**
   * 设置分组分隔符字符。
   *
   * @param groupingSeparator
   *     新的分组分隔符字符。
   */
  public void setGroupingSeparator(final char groupingSeparator) {
    this.groupingSeparator = groupingSeparator;
  }

  /**
   * 获取指数分隔符字符串。
   *
   * @return
   *     指数分隔符字符串。
   */
  public String getExponentSeparator() {
    return exponentSeparator;
  }

  /**
   * 设置指数分隔符字符串。
   *
   * @param exponentSeparator
   *     新的指数分隔符字符串，不能为null。
   */
  public void setExponentSeparator(final String exponentSeparator) {
    this.exponentSeparator = requireNonNull("exponentSeparator",
        exponentSeparator);
  }

  /**
   * 获取百分号符号字符。
   *
   * @return
   *     百分号符号字符。
   */
  public char getPercentSymbol() {
    return percentSymbol;
  }

  /**
   * 设置百分号符号字符。
   *
   * @param percentSymbol
   *     新的百分号符号字符。
   */
  public void setPercentSymbol(final char percentSymbol) {
    this.percentSymbol = percentSymbol;
  }

  /**
   * 获取千分号符号字符。
   *
   * @return
   *     千分号符号字符。
   */
  public char getPermilleSymbol() {
    return permilleSymbol;
  }

  /**
   * 设置千分号符号字符。
   *
   * @param permilleSymbol
   *     新的千分号符号字符。
   */
  public void setPermilleSymbol(final char permilleSymbol) {
    this.permilleSymbol = permilleSymbol;
  }

  /**
   * 获取无穷大符号字符串。
   *
   * @return
   *     无穷大符号字符串。
   */
  public String getInfinitySymbol() {
    return infinitySymbol;
  }

  /**
   * 设置无穷大符号字符串。
   *
   * @param infinitySymbol
   *     新的无穷大符号字符串，不能为null。
   */
  public void setInfinitySymbol(final String infinitySymbol) {
    this.infinitySymbol = requireNonNull("infinitySymbol", infinitySymbol);
  }

  /**
   * 获取NaN符号字符串。
   *
   * @return
   *     NaN符号字符串。
   */
  public String getNanSymbol() {
    return nanSymbol;
  }

  /**
   * 设置NaN符号字符串。
   *
   * @param nanSymbol
   *     新的NaN符号字符串，不能为null。
   */
  public void setNanSymbol(final String nanSymbol) {
    this.nanSymbol = requireNonNull("nanSymbol", nanSymbol);
  }

  /**
   * 获取货币符号字符串。
   *
   * @return
   *     货币符号字符串。
   */
  public String getCurrencySymbol() {
    return currencySymbol;
  }

  /**
   * 设置货币符号字符串。
   *
   * @param currencySymbol
   *     新的货币符号字符串，不能为null。
   */
  public void setCurrencySymbol(final String currencySymbol) {
    this.currencySymbol = requireNonNull("currencySymbol", currencySymbol);
  }

  /**
   * 获取货币小数分隔符字符。
   *
   * @return
   *     货币小数分隔符字符。
   */
  public char getMonetaryRadixSeparator() {
    return monetaryRadixSeparator;
  }

  /**
   * 设置货币小数分隔符字符。
   *
   * @param monetaryRadixSeparator
   *     新的货币小数分隔符字符。
   */
  public void setMonetaryRadixSeparator(final char monetaryRadixSeparator) {
    this.monetaryRadixSeparator = monetaryRadixSeparator;
  }

  /**
   * 获取货币分组分隔符字符。
   *
   * @return
   *     货币分组分隔符字符。
   */
  public char getMonetaryGroupingSeparator() {
    return monetaryGroupingSeparator;
  }

  /**
   * 设置货币分组分隔符字符。
   *
   * @param monetaryGroupingSeparator
   *     新的货币分组分隔符字符。
   */
  public void setMonetaryGroupingSeparator(final char monetaryGroupingSeparator) {
    this.monetaryGroupingSeparator = monetaryGroupingSeparator;
  }

  @Override
  public void assign(final NumberFormatSymbols that) {
    if (this == that) {
      return;
    }
    lowercaseDigits = that.lowercaseDigits;
    uppercaseDigits = that.uppercaseDigits;
    lowercaseRadixPrefixes = that.lowercaseRadixPrefixes;
    uppercaseRadixPrefixes = that.uppercaseRadixPrefixes;
    positiveSign = that.positiveSign;
    negativeSign = that.negativeSign;
    radixSeparator = that.radixSeparator;
    groupingSeparator = that.groupingSeparator;
    exponentSeparator = that.exponentSeparator;
    infinitySymbol = that.infinitySymbol;
    nanSymbol = that.nanSymbol;
    percentSymbol = that.percentSymbol;
    permilleSymbol = that.permilleSymbol;
    currencySymbol = that.currencySymbol;
    monetaryRadixSeparator = that.monetaryRadixSeparator;
    monetaryGroupingSeparator = that.monetaryGroupingSeparator;
  }

  @Override
  public NumberFormatSymbols cloneEx() {
    final NumberFormatSymbols result = new NumberFormatSymbols();
    result.assign(this);
    return result;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final NumberFormatSymbols other = (NumberFormatSymbols) o;
    return Equality.equals(positiveSign, other.positiveSign)
            && Equality.equals(negativeSign, other.negativeSign)
            && Equality.equals(radixSeparator, other.radixSeparator)
            && Equality.equals(groupingSeparator, other.groupingSeparator)
            && Equality.equals(percentSymbol, other.percentSymbol)
            && Equality.equals(permilleSymbol, other.permilleSymbol)
            && Equality.equals(monetaryRadixSeparator, other.monetaryRadixSeparator)
            && Equality.equals(monetaryGroupingSeparator, other.monetaryGroupingSeparator)
            && Equality.equals(lowercaseDigits, other.lowercaseDigits)
            && Equality.equals(uppercaseDigits, other.uppercaseDigits)
            && Equality.equals(lowercaseRadixPrefixes, other.lowercaseRadixPrefixes)
            && Equality.equals(uppercaseRadixPrefixes, other.uppercaseRadixPrefixes)
            && Equality.equals(exponentSeparator, other.exponentSeparator)
            && Equality.equals(infinitySymbol, other.infinitySymbol)
            && Equality.equals(nanSymbol, other.nanSymbol)
            && Equality.equals(currencySymbol, other.currencySymbol);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, lowercaseDigits);
    result = Hash.combine(result, multiplier, uppercaseDigits);
    result = Hash.combine(result, multiplier, lowercaseRadixPrefixes);
    result = Hash.combine(result, multiplier, uppercaseRadixPrefixes);
    result = Hash.combine(result, multiplier, positiveSign);
    result = Hash.combine(result, multiplier, negativeSign);
    result = Hash.combine(result, multiplier, radixSeparator);
    result = Hash.combine(result, multiplier, groupingSeparator);
    result = Hash.combine(result, multiplier, exponentSeparator);
    result = Hash.combine(result, multiplier, infinitySymbol);
    result = Hash.combine(result, multiplier, nanSymbol);
    result = Hash.combine(result, multiplier, percentSymbol);
    result = Hash.combine(result, multiplier, permilleSymbol);
    result = Hash.combine(result, multiplier, currencySymbol);
    result = Hash.combine(result, multiplier, monetaryRadixSeparator);
    result = Hash.combine(result, multiplier, monetaryGroupingSeparator);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("lowercaseDigits", lowercaseDigits)
               .append("uppercaseDigits", uppercaseDigits)
               .append("lowercaseRadixPrefixes", lowercaseRadixPrefixes)
               .append("uppercaseRadixPrefixes", uppercaseRadixPrefixes)
               .append("positiveSign", positiveSign)
               .append("negativeSign", negativeSign)
               .append("radixSeparator", radixSeparator)
               .append("groupingSeparator", groupingSeparator)
               .append("exponentSeparator", exponentSeparator)
               .append("infinitySymbol", infinitySymbol)
               .append("nanSymbol", nanSymbol)
               .append("percentSymbol", percentSymbol)
               .append("permilleSymbol", permilleSymbol)
               .append("currencySymbol", currencySymbol)
               .append("monetaryRadixSeparator", monetaryRadixSeparator)
               .append("monetaryGroupingSeparator", monetaryGroupingSeparator)
               .toString();
  }
}