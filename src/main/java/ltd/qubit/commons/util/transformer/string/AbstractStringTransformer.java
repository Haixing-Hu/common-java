////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.string;

import ltd.qubit.commons.util.transformer.AbstractTransformer;

/**
 * The abstract base class for implementation of {@link StringTransformer}.
 *
 * @author Haixing Hu
 */
public abstract class AbstractStringTransformer extends AbstractTransformer<String>
    implements StringTransformer {

  @Override
  public abstract String transform(String str);

  @Override
  public abstract AbstractStringTransformer clone();
}
