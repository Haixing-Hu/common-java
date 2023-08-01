////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.translate;

import java.io.IOException;

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.text.Searcher;

/**
 * Translator object for unescaping backslash escaped entries.
 *
 * @author Haixing Hu
 */
@Immutable
public class XsiUnescaper extends CharSequenceTranslator {

  /**
   * Escaped backslash constant.
   */
  private static final char BACKSLASH = '\\';

  @Override
  public int translate(final CharSequence input, final int index,
      final Appendable appendable) throws IOException {
    if (index != 0) {
      throw new IllegalStateException("XsiUnescaper should never reach the [1] index");
    }
    final Searcher searcher = new Searcher().forChar(BACKSLASH);
    int segmentStart = 0;
    int searchOffset = 0;
    while (true) {
      final int pos = searcher.startFrom(searchOffset).findFirstIndexIn(input);
      if (pos < 0) {
        if (segmentStart < input.length()) {
          appendable.append(input, segmentStart, input.length());
        }
        break;
      }
      if (pos > segmentStart) {
        appendable.append(input, segmentStart, pos);
      }
      segmentStart = pos + 1;
      searchOffset = pos + 2;
    }
    return Character.codePointCount(input, 0, input.length());
  }

  public XsiUnescaper clone() {
    return new XsiUnescaper();
  }
}
