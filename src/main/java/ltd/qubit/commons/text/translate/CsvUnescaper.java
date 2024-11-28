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

import javax.annotation.concurrent.Immutable;

import ltd.qubit.commons.text.Replacer;
import ltd.qubit.commons.text.Searcher;

/**
 * Translator for unescaping escaped Comma Separated Value (CSV) entries.
 *
 * @author Haixing Hu
 */
@Immutable
public class CsvUnescaper extends SinglePassTranslator implements CsvConstant {

  @Override
  void translateWhole(final CharSequence input, final Appendable appendable)
      throws IOException {
    if (input == null || input.length() == 0) {
      return;
    }
    // is input not quoted?
    if ((input.charAt(0) != CSV_QUOTE)
        || (input.charAt(input.length() - 1) != CSV_QUOTE)) {
      appendable.append(input);
      return;
    }
    // strip quotes
    final CharSequence quoteless = input.subSequence(1, input.length() - 1);
    if (new Searcher().forCharsIn(CSV_SEARCH_CHARS).isContainedIn(quoteless)) {
      // deal with escaped quotes; ie) ""
      new Replacer()
          .searchForSubstring(CSV_ESCAPED_QUOTE_STR)
          .replaceWithChar(CSV_QUOTE)
          .applyTo(quoteless, appendable);
    } else {
      appendable.append(quoteless);
    }
  }

  @Override
  public CsvUnescaper cloneEx() {
    return new CsvUnescaper();
  }
}
