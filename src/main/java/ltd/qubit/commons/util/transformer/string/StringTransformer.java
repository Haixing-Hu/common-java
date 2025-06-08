////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.string;

import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.util.transformer.Transformer;

/**
 * StringTransformer对象将一个字符串转换为另一个字符串。
 *
 * @author 胡海星
 */
@FunctionalInterface
public interface StringTransformer extends Transformer<String>,
        CloneableEx<StringTransformer> {

  @Override
  default StringTransformer cloneEx() {
    return this;
  }
}