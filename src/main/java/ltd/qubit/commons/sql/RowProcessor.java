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
 * An interface for objects processing rows of a {@link ResultSet} on a per-row
 * basis.
 *
 * <p>Implementations of this interface perform the actual work of processing
 * each row but don't need to worry about exception handling.
 *
 * @author 胡海星
 * @see RowMapper
 */
public interface RowProcessor {

  /**
   * Implementations must implement this method to process each row of data in
   * the {@link ResultSet}.
   *
   * <p>This method should not call {@link ResultSet#next()} on the
   * {@link ResultSet}; it is only supposed to extract values of the current
   * row.
   *
   * <p>Exactly what the implementation chooses to do is up to it: A trivial
   * implementation might simply count rows, while another implementation might
   * build an XML document.
   *
   * @param rs
   *     the ResultSet to process (pre-initialized for the current row).
   * @param rowNumber
   *     the number of the current row. Note that the row number is count from
   *     1.
   * @throws SQLException
   *     if a SQLException is encountered getting column values (that is,
   *     there's no need to catch SQLException)
   */
  void processRow(ResultSet rs, int rowNumber) throws SQLException;
}