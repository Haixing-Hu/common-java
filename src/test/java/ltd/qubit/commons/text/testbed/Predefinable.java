////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.testbed;

/**
 * 此接口表示模型可被预定义。
 *
 * @author 胡海星
 */
public interface Predefinable {

  /**
   * 判定此对象是否是预定义的数据。
   *
   * @return
   *     此对象是否是预定义的数据。
   */
  Boolean isPredefined();

  /**
   * 设置此对象是否是预定义的数据。
   *
   * @param predefined
   *     此对象是否是预定义的数据。
   */
  void setPredefined(final Boolean predefined);
}