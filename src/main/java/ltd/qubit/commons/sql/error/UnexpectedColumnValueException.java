////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql.error;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Thrown to indicated an unexpected column value in a {@link ResultSet}.
 *
 * @author Haixing Hu
 */
public class UnexpectedColumnValueException extends SQLException {

  private static final long serialVersionUID = 6610363067051998337L;

  public UnexpectedColumnValueException(final int columnIndex,
      final Object value) {
    super("Unexpected column value at column index " + columnIndex + ": "
        + value);
  }

  public UnexpectedColumnValueException(final String columnLabel,
      final Object value) {
    super("Unexpected column value at column '" + columnLabel + "': " + value);
  }

}
