////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.string;

import ltd.qubit.commons.util.transformer.AbstractTransformer;

/**
 * 实现{@link StringTransformer}的抽象基类。
 *
 * @author 胡海星
 */
public abstract class AbstractStringTransformer extends AbstractTransformer<String>
    implements StringTransformer {

  @Override
  public abstract String transform(String str);

  @Override
  public abstract AbstractStringTransformer cloneEx();
}