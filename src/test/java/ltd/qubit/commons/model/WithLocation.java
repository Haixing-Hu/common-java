////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

/**
 * 此接口表示实体类具有地理位置坐标属性。
 *
 * @author 胡海星
 */
public interface WithLocation {

  /**
   * 获取当前对象的地理位置坐标。
   *
   * @return
   *     当前对象的地理位置坐标。
   */
  Location getLocation();

  /**
   * 设置当前对象的地理位置坐标。
   *
   * @param location
   *     待设置的新的地理位置坐标。
   */
  void setLocation(Location location);
}
