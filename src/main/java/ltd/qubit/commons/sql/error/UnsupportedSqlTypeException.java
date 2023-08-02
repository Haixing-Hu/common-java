////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql.error;

import java.sql.SQLException;

/**
 * Thrown to indicate the specified SQL type is not supported.
 *
 * @author Haixing Hu
 */
public class UnsupportedSqlTypeException extends SQLException {

  private static final long serialVersionUID = 3456095486814136529L;

  public UnsupportedSqlTypeException(final int type) {
    super("The SQL type '" + type + "' is not supported.");
  }

  public UnsupportedSqlTypeException(final String type) {
    super("The SQL type '" + type + "' is not supported.");
  }
}
