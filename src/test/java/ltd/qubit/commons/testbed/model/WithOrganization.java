////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.testbed.model;

/**
 * 此接口表示实体类属于某个机构。
 *
 * @author 胡海星
 */
public interface WithOrganization {

  /**
   * 获取当前对象所属机构的基本信息。
   *
   * @return
   *     当前对象所属机构的基本信息。
   */
  StatefulInfo getOrganization();

  /**
   * 设置当前对象所属机构的基本信息。
   *
   * @param organization
   *     当前对象所属的新的机构的基本信息。
   */
  void setOrganization(StatefulInfo organization);
}
