////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.translate;

import java.io.IOException;

/**
 * Abstract translator for processing whole input in single pass.
 * Handles initial index checking and counting of returned code points.
 */
public abstract class SinglePassTranslator extends CharSequenceTranslator {

  @Override
  public abstract SinglePassTranslator cloneEx();

  /**
   * {@inheritDoc}
   * @throws IllegalArgumentException
   *     if {@code index != 0}
   */
  @Override
  public int translate(final CharSequence input, final int index,
      final Appendable appendable) throws IOException {
    if (index != 0) {
      final Class<? extends SinglePassTranslator> cls = this.getClass();
      final String name = (cls.isAnonymousClass() ?  cls.getName() : cls.getSimpleName());
      throw new IllegalArgumentException(name
          + ".translate(CharSequence input, int index, Writer out) "
          + "can not handle a non-zero index.");
    }
    translateWhole(input, appendable);
    return Character.codePointCount(input, index, input.length());
  }

  /**
   * Translates whole set of code points passed in input.
   *
   * @param input
   *     the character sequence that is being translated.
   * @param appendable
   *     the appendable where to translate the text to.
   * @throws IOException
   *     if and only if the writer produces an IOException.
   */
  abstract void translateWhole(CharSequence input, Appendable appendable)
      throws IOException;
}
