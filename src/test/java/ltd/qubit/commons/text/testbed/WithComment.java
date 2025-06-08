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
 * 此接口表示实体类具有备注属性。
 *
 * @author 胡海星
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