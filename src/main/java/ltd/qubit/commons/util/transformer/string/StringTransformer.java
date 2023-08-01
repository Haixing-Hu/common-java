////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.string;

import ltd.qubit.commons.lang.CloneableEx;
import ltd.qubit.commons.util.transformer.Transformer;

/**
 * A StringTransformer object transform a string to another.
 *
 * @author Haixing Hu
 */
@FunctionalInterface
public interface StringTransformer extends Transformer<String>,
        CloneableEx<StringTransformer> {

  default StringTransformer clone() {
    return this;
  }
}
