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

import ltd.qubit.commons.text.Replacer;
import ltd.qubit.commons.text.Searcher;

/**
 * Translator for escaping Comma Separated Values (CSV).
 *
 * @author Haixing Hu
 */
@Immutable
public class CsvEscaper extends SinglePassTranslator implements CsvConstant {

  @Override
  void translateWhole(final CharSequence input, final Appendable appendable)
      throws IOException {
    final String inputSting = input.toString();
    if (new Searcher().forCharsIn(CSV_SEARCH_CHARS).isNotContainedIn(inputSting)) {
      appendable.append(inputSting);
    } else {  // input needs quoting
      appendable.append(CSV_QUOTE);
      new Replacer()
          .forChar(CSV_QUOTE)
          .withString(CSV_ESCAPED_QUOTE_STR)
          .replace(inputSting, appendable);
      appendable.append(CSV_QUOTE);
    }
  }

  public CsvEscaper clone() {
    return new CsvEscaper();
  }
}
