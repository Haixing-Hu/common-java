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
 * 此接口规定了类T的同类型赋值操作。
 *
 * @author 胡海星
 */
public interface Assignable<T> extends CloneableEx<T> {

  /**
   * 将另一个对象赋值给此对象。
   *
   * <p>此函数会将另一个对象的所有字段克隆到此对象的字段中。
   *
   * @param other
   *     另一个对象，与此对象属于同一个类。
   */
  void assign(T other);
}