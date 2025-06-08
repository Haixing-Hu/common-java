////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.generator;

import java.security.SecureRandom;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * An implementation of the {@link PasswordGenerator} which generates random
 * passwords.
 *
 * @author Haixing Hu
 */
public class RandomPasswordGenerator implements PasswordGenerator {

  public static final int DEFAULT_MIN_LENGTH = 4;

  public static final int DEFAULT_MAX_LENGTH = 32;

  private static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";

  private static final String UPPER_CASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  private static final String DIGITS = "0123456789";

  private static final String SYMBOLS = "`~!@#$%^&*()-_=+{}[]|\\:;'\",./?<>";

  /**
   * The minimum length of the generated password.
   */
  private int minLength = DEFAULT_MIN_LENGTH;

  /**
   * The maximum length of the generated password.
   */
  private int maxLength = DEFAULT_MAX_LENGTH;

  /**
   * Whether to allow lower case letters in the generated password.
   */
  private boolean allowLowerCaseLetters = true;

  /**
   * Whether to allow upper case letters in the generated password.
   */
  private boolean allowUpperCaseLetters = true;

  /**
   * Whether to allow digits in the generated password.
   */
  private boolean allowDigits = true;

  /**
   * Whether to allow symbols in the generated password.
   */
  private boolean allowSymbols = true;

  private final transient SecureRandom random = new SecureRandom();

  public int getMinLength() {
    return minLength;
  }

  public void setMinLength(final int minLength) {
    this.minLength = minLength;
  }

  public int getMaxLength() {
    return maxLength;
  }

  public void setMaxLength(final int maxLength) {
    this.maxLength = maxLength;
  }

  public boolean isAllowLowerCaseLetters() {
    return allowLowerCaseLetters;
  }

  public void setAllowLowerCaseLetters(final boolean allowLowerCaseLetters) {
    this.allowLowerCaseLetters = allowLowerCaseLetters;
  }

  public boolean isAllowUpperCaseLetters() {
    return allowUpperCaseLetters;
  }

  public void setAllowUpperCaseLetters(final boolean allowUpperCaseLetters) {
    this.allowUpperCaseLetters = allowUpperCaseLetters;
  }

  public boolean isAllowDigits() {
    return allowDigits;
  }

  public void setAllowDigits(final boolean allowDigits) {
    this.allowDigits = allowDigits;
  }

  public boolean isAllowSymbols() {
    return allowSymbols;
  }

  public void setAllowSymbols(final boolean allowSymbols) {
    this.allowSymbols = allowSymbols;
  }

  @Override
  public String generate(final String username, final String phone) {
    final StringBuilder characters = new StringBuilder();
    if (allowLowerCaseLetters) {
      characters.append(LOWER_CASE_LETTERS);
    }
    if (allowUpperCaseLetters) {
      characters.append(UPPER_CASE_LETTERS);
    }
    if (allowDigits) {
      characters.append(DIGITS);
    }
    if (allowSymbols) {
      characters.append(SYMBOLS);
    }
    if (characters.length() == 0) {
      throw new IllegalArgumentException("At least one character type should be allowed");
    }
    if (minLength > maxLength) {
      throw new IllegalArgumentException("The minimum length should be less than or equal to the maximum length");
    }
    final int length = minLength + random.nextInt(maxLength - minLength + 1);
    final StringBuilder password = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      final int index = random.nextInt(characters.length());
      final char ch = characters.charAt(index);
      password.append(ch);
    }
    return password.toString();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final RandomPasswordGenerator other = (RandomPasswordGenerator) o;
    return Equality.equals(minLength, other.minLength)
        && Equality.equals(maxLength, other.maxLength)
        && Equality.equals(allowLowerCaseLetters, other.allowLowerCaseLetters)
        && Equality.equals(allowUpperCaseLetters, other.allowUpperCaseLetters)
        && Equality.equals(allowDigits, other.allowDigits)
        && Equality.equals(allowSymbols, other.allowSymbols);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, minLength);
    result = Hash.combine(result, multiplier, maxLength);
    result = Hash.combine(result, multiplier, allowLowerCaseLetters);
    result = Hash.combine(result, multiplier, allowUpperCaseLetters);
    result = Hash.combine(result, multiplier, allowDigits);
    result = Hash.combine(result, multiplier, allowSymbols);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("minLength", minLength)
        .append("maxLength", maxLength)
        .append("allowLowerCaseLetters", allowLowerCaseLetters)
        .append("allowUpperCaseLetters", allowUpperCaseLetters)
        .append("allowNumbers", allowDigits)
        .append("allowSymbols", allowSymbols)
        .toString();
  }
}