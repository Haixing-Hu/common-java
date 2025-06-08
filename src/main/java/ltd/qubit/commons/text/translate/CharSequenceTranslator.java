////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.translate;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UncheckedIOException;

import ltd.qubit.commons.util.transformer.string.StringTransformer;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * An API for translating text.
 *
 * <p>Its core use is to escape and unescape text. Because escaping and unescaping
 * is completely contextual, the API does not present two separate signatures.</p>
 *
 * @author Haixing Hu
 */
public abstract class CharSequenceTranslator implements StringTransformer {

  @Override
  public abstract CharSequenceTranslator cloneEx();

  /**
   * Translate a set of code points, represented by an int index into a character
   * sequence, into another set of code points.
   *
   * <p>The number of code points consumed must be returned, and the only
   * IOExceptions thrown must be from interacting with the Writer so that
   * the top level API may reliably ignore StringWriter IOExceptions. </p>
   *
   * @param input
   *     the character sequence that is being translated.
   * @param index
   *     the current point of translation.
   * @param appendable
   *     the appendable where to translate the text to.
   * @return
   *     number of <b>code points</b> that consumed by this function.
   * @throws IOException
   *     if and only if the Writer produces an IOException.
   */
  public abstract int translate(CharSequence input, int index, Appendable appendable)
      throws IOException;

  /**
   * Transform an input character sequence and append the result to an
   * {@link Appendable} object.
   *
   * <p>This is intentionally final as its algorithm is tightly coupled with
   * the abstract method of this class.</p>
   *
   * @param input
   *     the character sequence that is being translated.
   * @param appendable
   *     the appendable object where to translate the text to.
   * @throws IOException
   *     if and only if the writer produces an IOException.
   */
  public final void transform(final CharSequence input, final Appendable appendable)
      throws IOException {
    requireNonNull("appendable", appendable);
    if (input == null) {
      return;
    }
    int pos = 0;
    final int len = input.length();
    while (pos < len) {
      final int consumed = translate(input, pos, appendable);
      if (consumed == 0) {
        // inlined implementation of Character.toChars(Character.codePointAt(input, pos))
        // avoids allocating temp char arrays and duplicate checks
        final char c1 = input.charAt(pos);
        appendable.append(c1);
        pos++;
        if (Character.isHighSurrogate(c1) && pos < len) {
          final char c2 = input.charAt(pos);
          if (Character.isLowSurrogate(c2)) {
            appendable.append(c2);
            pos++;
          }
        }
        continue;
      }
      // contract with translators is that they have to understand code points
      // and they just took care of a surrogate pair
      for (int pt = 0; pt < consumed; pt++) {
        pos += Character.charCount(Character.codePointAt(input, pos));
      }
    }
  }

  /**
   * Transform an input character sequence and append the result to a
   * {@link StringBuilder}.
   *
   * <p>This is intentionally final as its algorithm is tightly coupled with
   * the abstract method of this class.</p>
   *
   * @param input
   *     the character sequence that is being translated.
   * @param builder
   *     the {@link StringBuilder} where to translate the text to.
   */
  public final void transform(final CharSequence input, final StringBuilder builder) {
    try {
      transform(input, (Appendable) builder);
    } catch (final IOException e) {
      // should not throw
      throw new UncheckedIOException(e);
    }
  }

  @Override
  public String transform(final String str) {
    if (str == null) {
      return null;
    }
    try {
      final StringWriter writer = new StringWriter(str.length() * 2);
      transform(str, writer);
      return writer.toString();
    } catch (final IOException ioe) {
      // this should never ever happen while writing to a StringWriter
      throw new UncheckedIOException(ioe);
    }
  }

  /**
   * Helper method to create a merger of this translator with another set of
   * translators.
   *
   * <p>Useful in customizing the standard functionality.</p>
   *
   * @param translators
   *     array of {@link CharSequenceTranslator} to merge with this one.
   * @return
   *     {@link CharSequenceTranslator} merging this translator with the others.
   */
  public final CharSequenceTranslator with(final CharSequenceTranslator... translators) {
    final CharSequenceTranslator[] newArray = new CharSequenceTranslator[translators.length + 1];
    newArray[0] = this;
    System.arraycopy(translators, 0, newArray, 1, translators.length);
    return new AggregateTranslator(newArray);
  }
}