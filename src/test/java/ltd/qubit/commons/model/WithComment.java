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
 * 此接口表示实体类具有备注属性。
 *
 * @author Haixing Hu
 */
public interface WithComment {

  /**
   * 获取当前对象的备注。
   *
   * @return
   *     当前对象的备注。
   */
  String getComment();

  /**
   * 设置当前对象的备注。
   *
   * @param comment
   *     待设置的新的备注。
   */
  void setComment(String comment);
}
