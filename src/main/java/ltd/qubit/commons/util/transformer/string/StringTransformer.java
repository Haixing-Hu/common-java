////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
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
