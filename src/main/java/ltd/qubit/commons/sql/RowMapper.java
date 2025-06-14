////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * An interface for the objects mapping rows of a {@link ResultSet} on a per-row
 * basis.
 *
 * <p>Implementations of this interface perform the actual work of mapping each
 * row to a result object, but don't need to worry about exception handling.
 *
 * @param <T>
 *     the type of objects mapped from rows.
 * @author Haixing Hu
 */
@FunctionalInterface
public interface RowMapper<T> {

  /**
   * Implementations must implement this method to map each row of data in the
   * {@link ResultSet}.
   *
   * <p>This method should not call {@link ResultSet#next()} on the
   * {@link ResultSet}; it is only supposed to map values of the current row.
   *
   * @param rs
   *     the ResultSet to map (pre-initialized for the current row).
   * @param rowNumber
   *     the number of the current row. Note that the row number is count from
   *     1.
   * @return the result object for the current row.
   * @throws SQLException
   *     if a SQLException is encountered getting column values (that is,
   *     there's no need to catch SQLException)
   */
  T mapRow(ResultSet rs, int rowNumber) throws SQLException;

}