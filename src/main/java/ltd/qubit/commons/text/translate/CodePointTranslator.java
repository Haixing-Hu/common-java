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

/**
 * Helper subclass to {@link CharSequenceTranslator} to allow for translations
 * that will replace up to one code point at a time.
 *
 * @author Haixing Hu
 */
public abstract class CodePointTranslator extends CharSequenceTranslator {

  public abstract CodePointTranslator clone();

  @Override
  public final int translate(final CharSequence input, final int index,
      final Appendable appendable) throws IOException {
    final int codePoint = Character.codePointAt(input, index);
    final boolean consumed = translate(codePoint, appendable);
    return consumed ? 1 : 0;
  }

  /**
   * Translates the specified code point into another.
   *
   * @param codePoint
   *     the code point to be translated.
   * @param appendable
   *     the appendable where to optionally push the translated output to.
   * @return
   *     whether the translation occurred or not.
   * @throws IOException
   *     if and only if the Writer produces an IOException.
   */
  public abstract boolean translate(int codePoint, Appendable appendable)
      throws IOException;

}
