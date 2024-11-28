////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.string;

import ltd.qubit.commons.text.Inflector;

/**
 * 将英文单词的单数形式转换为复数形式。
 *
 * @author 胡海星
 */
public class SingularToPluralTransformer extends AbstractStringTransformer {

  public static final SingularToPluralTransformer INSTANCE = new SingularToPluralTransformer();

  private final Inflector inflector = Inflector.getInstance();

  @Override
  public String transform(final String word) {
    return inflector.pluralize(word);
  }

  @Override
  public SingularToPluralTransformer cloneEx() {
    return new SingularToPluralTransformer();
  }
}
