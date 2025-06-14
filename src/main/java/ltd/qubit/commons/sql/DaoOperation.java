////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

/**
 * DAO 操作的枚举。
 *
 * @author 胡海星
 */
public enum DaoOperation {

  /**
   * 表示添加或更新操作。
   */
  ADD_OR_UPDATE,

  /**
   * 表示删除操作。
   */
  DELETE,

  /**
   * 表示未知操作。
   */
  UNKNOWN,
}