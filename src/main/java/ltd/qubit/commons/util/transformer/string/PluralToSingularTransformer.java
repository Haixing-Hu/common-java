////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.string;

import ltd.qubit.commons.text.Inflector;

/**
 * 将英文单词的复数形式转换为单数形式。
 *
 * @author 胡海星
 */
public class PluralToSingularTransformer extends AbstractStringTransformer {

  public static final PluralToSingularTransformer INSTANCE = new PluralToSingularTransformer();

  private final Inflector inflector = Inflector.getInstance();

  @Override
  public String transform(final String word) {
    return inflector.singularize(word);
  }

  @Override
  public PluralToSingularTransformer clone() {
    return new PluralToSingularTransformer();
  }
}
