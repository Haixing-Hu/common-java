////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

/**
 * 此接口为类T提供交换操作。
 *
 * @author 胡海星
 */
public interface Swappable<T> {

  /**
   * 与另一个对象交换此对象的内容。
   *
   * @param other
   *     要与之交换内容的另一个对象。
   */
  void swap(T other);
}