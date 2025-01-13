////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.testbed.model;

import javax.annotation.Nullable;

/**
 * 此接口表示实体类具有所属实体类的属性。
 *
 * @author 胡海星
 */
public interface WithEntity {

  /**
   * 获取当前对象所属实体类的名称。
   *
   * @return
   *     当前对象所属实体类的名称。
   */
  @Nullable
  String getEntity();

  /**
   * 设置当前对象所属实体类的名称。
   *
   * @param entity
   *     新的实体类的名称。
   */
  void setEntity(@Nullable String entity);

}
