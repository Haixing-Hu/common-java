////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import ltd.qubit.commons.sql.error.UnexpectedColumnValueException;

/**
 * A {@link RowMapper} which maps a row of a {@link ResultSet} to a
 * {@link ClientInfo} object.
 *
 * @author Haixing Hu
 */
public final class ClientInfoRowMapper implements RowMapper<ClientInfo> {

  public static final ClientInfoRowMapper INSTANCE = new ClientInfoRowMapper();

  @Override
  public ClientInfo mapRow(final ResultSet rs, final int rowNum)
      throws SQLException {
    final String name = rs.getString(1);
    if (name == null) {
      throw new UnexpectedColumnValueException(1, null);
    }
    final int maxLength = rs.getInt(2);
    if (maxLength < 0) {
      throw new UnexpectedColumnValueException(1, maxLength);
    }
    final String defaultValue = rs.getString(3);
    final String description = rs.getString(4);
    return new ClientInfo(name, maxLength, defaultValue, description);
  }

}
