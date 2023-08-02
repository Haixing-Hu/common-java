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

import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * Translates escaped Unicode values of the form \\u+\d\d\d\d back to Unicode.
 *
 * <p>It supports multiple 'u' characters and will work with or without the +.</p>
 *
 * @author Haixing Hu
 */
@Immutable
public class UnicodeUnescaper extends CharSequenceTranslator {

  @Override
  public int translate(final CharSequence input, final int index,
      final Appendable appendable) throws IOException {
    if ((input.charAt(index) == '\\')
        && (index + 1 < input.length())
        && (input.charAt(index + 1) == 'u')) {
      // consume optional additional 'u' chars
      int i = 2;
      while (index + i < input.length() && input.charAt(index + i) == 'u') {
        i++;
      }
      if (index + i < input.length() && input.charAt(index + i) == '+') {
        i++;
      }
      if (index + i + 4 <= input.length()) {
        // Get 4 hex digits
        final CharSequence unicode = input.subSequence(index + i, index + i + 4);
        try {
          // FIXME: this implementation do not support code point > 0xFFFF,
          //   for example: \u100AB
          final int value = Integer.parseInt(unicode.toString(), 16);
          appendable.append((char) value);
        } catch (final NumberFormatException nfe) {
          throw new IllegalArgumentException("Unable to parse unicode value: " + unicode, nfe);
        }
        return i + 4;
      }
      throw new IllegalArgumentException("Less than 4 hex digits in unicode value: '"
          + input.subSequence(index, input.length())
          + "' due to end of CharSequence");
    }
    return 0;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .toString();
  }

  @Override
  public UnicodeUnescaper clone() {
    return new UnicodeUnescaper();
  }
}
