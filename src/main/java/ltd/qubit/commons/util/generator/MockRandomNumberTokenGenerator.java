////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static ltd.qubit.commons.text.NumberFormat.DECIMAL_RADIX;

/**
 * The mock implementation of {@link TokenGenerator} which generated a random
 * number with a fixed number of digits.
 *
 * @author Haixing Hu
 */
public final class MockRandomNumberTokenGenerator implements TokenGenerator {

  public static final int MAX_DIGITS = 9;

  public static final int DEFAULT_DIGITS = 4;

  private final Random random;
  private int digits;
  private final Map<String, String> verifyCodes;

  public MockRandomNumberTokenGenerator() {
    random = new Random();
    digits = DEFAULT_DIGITS;
    verifyCodes = new HashMap<>();
  }

  public int getDigits() {
    return digits;
  }

  public void setDigits(final int digits) {
    if ((digits < 1) || (digits > MAX_DIGITS)) {
      throw new IllegalArgumentException("Unsupported number of digits: " + digits);
    }
    this.digits = digits;
  }

  @Override
  public String generate(final String key) {
    int bound = 1;
    for (int i = 0; i < digits; ++i) {
      bound *= DECIMAL_RADIX;
    }
    int lower = 0;
    if (digits > 1) {
      lower = bound / DECIMAL_RADIX;
    }
    final int code = random.nextInt(bound - lower) + lower;
    final String result = String.valueOf(code);
    verifyCodes.put(key, result);   // remember the generated code
    return result;
  }

  public Map<String, String> verifyCodes() {
    return verifyCodes;
  }

  @Override
  public boolean isValid(final String token) {
    if (token == null || token.length() != digits) {
      return false;
    }
    for (final char ch : token.toCharArray()) {
      if (ch < '0' || ch > '9') {
        return false;
      }
    }
    return true;
  }
}
