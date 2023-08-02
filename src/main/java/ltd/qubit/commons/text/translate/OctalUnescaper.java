////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.translate;

import java.io.IOException;

import javax.annotation.concurrent.Immutable;

import static ltd.qubit.commons.lang.CharUtils.isAsciiOctalDigit;

/**
 * Translate escaped octal Strings back to their octal values.
 *
 * <p>For example, "\45" should go back to being the specific value (a '%').</p>
 *
 * <p>Note that this currently only supports the viable range of octal for Java;
 * namely 1 to 377. This is because parsing Java is the main use case.</p>
 *
 * @since 1.0
 */
@Immutable
public class OctalUnescaper extends CharSequenceTranslator {

  @Override
  public int translate(final CharSequence input, final int index,
      final Appendable appendable) throws IOException {
    // how many characters left, ignoring the first '\\' character
    final int remaining = input.length() - index - 1;
    int len = 0;
    if (input.charAt(index) == '\\'
        && (remaining > 0)
        && isAsciiOctalDigit(input.charAt(index + 1))) {
      final int next = index + 1;
      final int next2 = index + 2;
      final int next3 = index + 3;
      // we know this is good as we checked it in the if block above
      len = 2;
      if ((remaining > 1) && isAsciiOctalDigit(input.charAt(next2))) {
        len = 3;
        // Note that this currently only supports the viable range of octal
        // for Java; namely 1 to 377. This is because parsing Java is the
        // main use case.
        if ((remaining > 2)
            && isZeroToThree(input.charAt(next))
            && isAsciiOctalDigit(input.charAt(next3))) {
          len = 4;
        }
      }
      // now parse the octal number from input[index + 1, index + len).
      final int value = parseOctal(input, index + 1, index + len);
      appendable.append((char) value);
      return len;
    }
    return 0;
  }

  private static boolean isZeroToThree(final char ch) {
    return ch >= '0' && ch <= '3';
  }

  private static int parseOctal(final CharSequence input, final int start,
      final int end) {
    int result = 0;
    for (int i = start; i < end; ++i) {
      final int digit = input.charAt(i) - '0';
      result <<= 3;
      result |= digit;
    }
    return result;
  }

  public OctalUnescaper clone() {
    return new OctalUnescaper();
  }
}
