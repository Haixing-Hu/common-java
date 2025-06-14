////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql.error;

import java.io.Serial;
import java.sql.SQLException;

/**
 * 当指定的 SQL 类型不受支持时抛出此异常。
 *
 * @author 胡海星
 */
public class UnsupportedSqlTypeException extends SQLException {

  @Serial
  private static final long serialVersionUID = 3456095486814136529L;

  /**
   * 使用指定的 SQL 类型代码创建一个新的 {@code UnsupportedSqlTypeException} 实例。
   *
   * @param type
   *      不支持的 SQL 类型代码
   */
  public UnsupportedSqlTypeException(final int type) {
    super("The SQL type '" + type + "' is not supported.");
  }

  /**
   * 使用指定的 SQL 类型名称创建一个新的 {@code UnsupportedSqlTypeException} 实例。
   *
   * @param type
   *      不支持的 SQL 类型名称
   */
  public UnsupportedSqlTypeException(final String type) {
    super("The SQL type '" + type + "' is not supported.");
  }
}