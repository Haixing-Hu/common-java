////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * A {@link NumberFormatSymbols} object is used to store the symbols for
 * formatting and parsing numbers.
 *
 * @author Haixing Hu
 */
public final class NumberFormatSymbols implements Assignable<NumberFormatSymbols> {

  public static final char[] DEFAULT_LOWERCASE_DIGITS     =  CharUtils.LOWERCASE_DIGITS;

  public static final char[] DEFAULT_UPPERCASE_DIGITS     =  CharUtils.UPPERCASE_DIGITS;

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

  public static final char DEFAULT_POSITIVE_SIGN = '+';

  public static final char DEFAULT_NEGATIVE_SIGN = '-';

  public static final char DEFAULT_RADIX_SEPARATOR = '.';

  public static final char DEFAULT_GROUPING_SEPARATOR = ',';

  public static final String DEFAULT_EXPONENT_SEPARATOR = "E";

  public static final char DEFAULT_PERCENT_SYMBOL = '%';

  public static final char DEFAULT_PERMILLE_SYMBOL = '‰';

  public static final String DEFAULT_CURRENCY_SYMBOL = "$";

  public static final char DEFAULT_MONETARY_RADIX_SEPARATOR = '.';

  public static final char DEFAULT_MONETARY_GROUPING_SEPARATOR = ',';

  public static final String DEFAULT_INFINITY_SYMBOL = "∞";

  public static final String DEFAULT_NAN_SYMBOL = "NaN.";

  @GuardedBy("NumberFormatSymbols.class")
  private static final Map<Locale, DecimalFormatSymbols> symbolsCache = new HashMap<>();

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

  public NumberFormatSymbols() {
    reset();
  }

  public NumberFormatSymbols(final Locale locale) {
    reset(locale);
  }

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

  public char[] getLowercaseDigits() {
    return lowercaseDigits;
  }

  public void setLowercaseDigits(final char[] lowercaseDigits) {
    this.lowercaseDigits = requireLengthAtLeast("lowercaseDigits",
        lowercaseDigits, HEX_RADIX);
  }

  public char[] getUppercaseDigits() {
    return uppercaseDigits;
  }

  public void setUppercaseDigits(final char[] uppercaseDigits) {
    this.uppercaseDigits = requireLengthAtLeast("uppercaseDigits",
        uppercaseDigits, HEX_RADIX);
  }

  public char[] getDigits(final boolean uppercase) {
    return (uppercase ? uppercaseDigits : lowercaseDigits);
  }

  public String[] getLowercaseRadixPrefixes() {
    return lowercaseRadixPrefixes;
  }

  public void setLowercaseRadixPrefixes(final String[] lowercaseRadixPrefixes) {
    this.lowercaseRadixPrefixes = requireLengthAtLeast("lowercaseRadixPrefixes",
        lowercaseRadixPrefixes, HEX_RADIX);
  }

  public String[] getUppercaseRadixPrefixes() {
    return uppercaseRadixPrefixes;
  }

  public void setUppercaseRadixPrefixes(final String[] uppercaseRadixPrefixes) {
    this.uppercaseRadixPrefixes = requireLengthAtLeast("uppercaseRadixPrefixes",
        uppercaseRadixPrefixes, HEX_RADIX);
  }

  public String[] getRadixPrefixes(final boolean uppercase) {
    return (uppercase ? uppercaseRadixPrefixes : lowercaseRadixPrefixes);
  }

  public char getPositiveSign() {
    return positiveSign;
  }

  public void setPositiveSign(final char positiveSign) {
    this.positiveSign = positiveSign;
  }

  public char getNegativeSign() {
    return negativeSign;
  }

  public void setNegativeSign(final char negativeSign) {
    this.negativeSign = negativeSign;
  }

  public char getRadixSeparator() {
    return radixSeparator;
  }

  public void setRadixSeparator(final char radixSeparator) {
    this.radixSeparator = radixSeparator;
  }

  public char getGroupingSeparator() {
    return groupingSeparator;
  }

  public void setGroupingSeparator(final char groupingSeparator) {
    this.groupingSeparator = groupingSeparator;
  }

  public String getExponentSeparator() {
    return exponentSeparator;
  }

  public void setExponentSeparator(final String exponentSeparator) {
    this.exponentSeparator = requireNonNull("exponentSeparator",
        exponentSeparator);
  }

  public char getPercentSymbol() {
    return percentSymbol;
  }

  public void setPercentSymbol(final char percentSymbol) {
    this.percentSymbol = percentSymbol;
  }

  public char getPermilleSymbol() {
    return permilleSymbol;
  }

  public void setPermilleSymbol(final char permilleSymbol) {
    this.permilleSymbol = permilleSymbol;
  }

  public String getInfinitySymbol() {
    return infinitySymbol;
  }

  public void setInfinitySymbol(final String infinitySymbol) {
    this.infinitySymbol = requireNonNull("infinitySymbol", infinitySymbol);
  }

  public String getNanSymbol() {
    return nanSymbol;
  }

  public void setNanSymbol(final String nanSymbol) {
    this.nanSymbol = requireNonNull("nanSymbol", nanSymbol);
  }

  public String getCurrencySymbol() {
    return currencySymbol;
  }

  public void setCurrencySymbol(final String currencySymbol) {
    this.currencySymbol = requireNonNull("currencySymbol", currencySymbol);
  }

  public char getMonetaryRadixSeparator() {
    return monetaryRadixSeparator;
  }

  public void setMonetaryRadixSeparator(final char monetaryRadixSeparator) {
    this.monetaryRadixSeparator = monetaryRadixSeparator;
  }

  public char getMonetaryGroupingSeparator() {
    return monetaryGroupingSeparator;
  }

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
