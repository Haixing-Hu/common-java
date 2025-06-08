////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer;

/**
 * 此接口提供将一个对象转换为另一个对象的函数。
 *
 * @author 胡海星
 */
@FunctionalInterface
public interface Transformer<T> {

  /**
   * 将一个对象从一种形式转换为另一种形式。
   *
   * @param t
   *     要转换的对象。
   * @return
   *     转换后的对象。
   */
  T transform(T t);

}