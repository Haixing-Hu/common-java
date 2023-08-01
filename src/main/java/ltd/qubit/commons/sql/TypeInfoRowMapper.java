////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.sql.error.UnexpectedColumnValueException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A {@link RowMapper} which maps a row of a {@link ResultSet} to a
 * {@link TypeInfo} object.
 *
 * @author Haixing Hu
 */
public final class TypeInfoRowMapper implements RowMapper<TypeInfo> {

  public static final TypeInfoRowMapper INSTANCE = new TypeInfoRowMapper();

  @Override
  public TypeInfo mapRow(final ResultSet rs, final int rowNum)
      throws SQLException {
    final TypeInfo info = new TypeInfo();
    //  stop checkstyle: MagicNumberCheck
    info.setTypeName(rs.getString(1));
    info.setDataType(rs.getInt(2));
    info.setPrecision(rs.getInt(3));
    info.setLiteralPrefix(rs.getString(4));
    info.setLiteralSuffix(rs.getString(5));
    info.setCreateParams(rs.getString(6));
    info.setNullable(rs.getShort(7));
    info.setCaseSensitive(rs.getBoolean(8));
    info.setSearchable(rs.getShort(9));
    info.setUnsignedAttribute(rs.getBoolean(10));
    info.setFixedPrecScale(rs.getBoolean(11));
    info.setAutoIncrement(rs.getBoolean(12));
    info.setLocalTypeName(rs.getString(13));
    info.setMinimumScale(rs.getShort(14));
    info.setMaximumScale(rs.getShort(15));
    info.setSqlDataType(rs.getInt(16));
    info.setSqlDatetimeSub(rs.getInt(17));
    info.setNumPrecRadix(rs.getInt(18));
    //  resume checkstyle: MagicNumberCheck
    if (info.getTypeName() == null) {
      throw new UnexpectedColumnValueException(TypeInfo.TYPE_NAME, null);
    }
    if (ArrayUtils.indexOf(TypeInfo.DATA_TYPE_ENUM, info.getDataType()) < 0) {
      throw new UnexpectedColumnValueException(TypeInfo.DATA_TYPE,
          info.getDataType());
    }
    if (info.getPrecision() < 0) {
      throw new UnexpectedColumnValueException(TypeInfo.PRECISION,
          info.getPrecision());
    }
    if (ArrayUtils.indexOf(TypeInfo.NULLABLE_ENUM, info.getNullable()) < 0) {
      throw new UnexpectedColumnValueException(TypeInfo.NULLABLE,
          info.getNullable());
    }
    if (ArrayUtils.indexOf(TypeInfo.SEARCHABLE_ENUM, info.getSearchable()) < 0) {
      throw new UnexpectedColumnValueException(TypeInfo.SEARCHABLE,
          info.getSearchable());
    }
    if (info.getNumPrecRadix() < 2) {
      throw new UnexpectedColumnValueException(TypeInfo.NUM_PREC_RADIX,
          info.getNumPrecRadix());
    }
    return info;
  }

}
