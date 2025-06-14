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
 * 当数据源未指定时抛出此异常。
 *
 * @author 胡海星
 */
public class NoDataSourceException extends SQLException {

  @Serial
  private static final long serialVersionUID = 1176648404381564211L;

  /**
   * 创建一个新的 {@code NoDataSourceException} 实例。
   */
  public NoDataSourceException() {
    super("The DataSource is not set.");
  }
}