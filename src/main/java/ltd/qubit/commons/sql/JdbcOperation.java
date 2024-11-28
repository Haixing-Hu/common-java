////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.sql.Connection;

/**
 * An interface of the JDBC operations.
 *
 * @param <R>
 *     the type of returned value. If the operation has no return value, set
 *     this type parameter to {@link Void} and returns {@code null} in the
 *     {@link #perform(Connection)} function.
 * @author Haixing Hu
 */
public interface JdbcOperation<R> {

  /**
   * Performs a JDBC operation on a given connection.
   *
   * @param conn
   *     a given JDBC connection.
   * @return the result of operation.
   * @throws Exception
   *     if any error occurred.
   */
  R perform(Connection conn) throws Exception;
}
