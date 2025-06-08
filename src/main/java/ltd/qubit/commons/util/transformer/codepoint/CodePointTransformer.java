////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.codepoint;

import ltd.qubit.commons.util.transformer.Transformer;

/**
 * 将一个代码点转换为另一个代码点的{@link Transformer}。
 *
 * @author 胡海星
 */
@FunctionalInterface
public interface CodePointTransformer extends Transformer<Integer> {
  //  empty
}