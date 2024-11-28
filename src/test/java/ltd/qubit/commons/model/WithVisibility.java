////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

/**
 * 此接口表示实体类具有可见性属性。
 *
 * @author 胡海星
 */
public interface WithVisibility {

  /**
   * 判定该对象是否可见。
   *
   * @return
   *     该对象是否可见。
   */
  Boolean isVisible();

  /**
   * 设置该对象的可见性。
   *
   * @param visible
   *     该对象是否可见。
   */
  void setVisible(Boolean visible);
}
