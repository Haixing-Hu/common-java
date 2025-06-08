////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql.error;

import java.sql.SQLException;

/**
 * Thrown to indicate that the data source is not specified.
 *
 * @author Haixing Hu
 */
public class NoDataSourceException extends SQLException {

  private static final long serialVersionUID = 1176648404381564211L;

  public NoDataSourceException() {
    super("The DataSource is not set.");
  }
}