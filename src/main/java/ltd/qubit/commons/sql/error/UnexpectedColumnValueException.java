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
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 当在 {@link ResultSet} 中遇到意外的列值时抛出此异常。
 *
 * @author 胡海星
 */
public class UnexpectedColumnValueException extends SQLException {

  @Serial
  private static final long serialVersionUID = 6610363067051998337L;

  /**
   * 使用列索引和值创建一个新的 {@code UnexpectedColumnValueException} 实例。
   *
   * @param columnIndex
   *      列索引
   * @param value
   *      意外的列值
   */
  public UnexpectedColumnValueException(final int columnIndex,
      final Object value) {
    super("Unexpected column value at column index " + columnIndex + ": "
        + value);
  }

  /**
   * 使用列标签和值创建一个新的 {@code UnexpectedColumnValueException} 实例。
   *
   * @param columnLabel
   *      列标签
   * @param value
   *      意外的列值
   */
  public UnexpectedColumnValueException(final String columnLabel,
      final Object value) {
    super("Unexpected column value at column '" + columnLabel + "': " + value);
  }

}