////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.error;

import javax.annotation.Nullable;

import org.springframework.dao.NonTransientDataAccessException;

import static ltd.qubit.commons.error.ServerSideException.getTableName;

/**
 * Thrown to indicate an unknown failure of data updating.
 *
 * @author Haixing Hu
 */
public class DataUpdateFailException extends NonTransientDataAccessException {

  private static final long serialVersionUID = - 7930770879084494863L;

  private final String table;

  public DataUpdateFailException(final Class<?> entityType) {
    this(getTableName(entityType), null);
  }

  public DataUpdateFailException(final Class<?> entityType,
      @Nullable final Throwable cause) {
    this(getTableName(entityType), cause);
  }

  private DataUpdateFailException(final String table,
      @Nullable final Throwable cause) {
    super("Failed to update the database table '" + table
        + (cause == null ? "" : "': " + cause.getMessage()));
    this.table = table;
  }

  public String getTable() {
    return table;
  }
}
