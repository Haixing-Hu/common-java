////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.model;

/**
 * 此接口表示实体类属于某个App。
 *
 * @author 胡海星
 */
public interface WithApp {

  /**
   * 获取当前对象所属App的基本信息。
   *
   * @return
   *     当前对象所属App的基本信息。
   */
  StatefulInfo getApp();

  /**
   * 设置当前对象所属App的基本信息。
   *
   * @param app
   *     当前对象所属的新的App的基本信息。
   */
  void setApp(StatefulInfo app);
}
