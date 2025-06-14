////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.sql.Connection;

/**
 * JDBC操作的接口。
 *
 * @param <R>
 *     返回值的类型。如果操作没有返回值，请将此类型参数设置为{@link Void}，
 *     并在{@link #perform(Connection)}函数中返回{@code null}。
 * @author 胡海星
 */
public interface JdbcOperation<R> {

  /**
   * 在给定连接上执行JDBC操作。
   *
   * @param conn
   *     给定的JDBC连接
   * @return 操作的结果
   * @throws Exception
   *     如果发生任何错误
   */
  R perform(Connection conn) throws Exception;
}