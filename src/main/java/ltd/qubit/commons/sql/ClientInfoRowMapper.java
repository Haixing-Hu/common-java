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

import ltd.qubit.commons.sql.error.UnexpectedColumnValueException;

/**
 * 将{@link ResultSet}的行映射到{@link ClientInfo}对象的{@link RowMapper}。
 *
 * @author 胡海星
 */
public final class ClientInfoRowMapper implements RowMapper<ClientInfo> {

  /**
   * 此映射器的单例实例。
   */
  public static final ClientInfoRowMapper INSTANCE = new ClientInfoRowMapper();

  /**
   * {@inheritDoc}
   */
  @Override
  public ClientInfo mapRow(final ResultSet rs, final int rowNumber)
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