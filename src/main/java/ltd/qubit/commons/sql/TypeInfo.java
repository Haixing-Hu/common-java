////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.ArrayUtils;
import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.lang.StringUtils;
import ltd.qubit.commons.sql.error.UnexpectedColumnValueException;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static java.sql.DatabaseMetaData.typeNoNulls;
import static java.sql.DatabaseMetaData.typeNullable;
import static java.sql.DatabaseMetaData.typeNullableUnknown;
import static java.sql.DatabaseMetaData.typePredBasic;
import static java.sql.DatabaseMetaData.typePredChar;
import static java.sql.DatabaseMetaData.typePredNone;
import static java.sql.DatabaseMetaData.typeSearchable;
import static java.sql.Types.ARRAY;
import static java.sql.Types.BIGINT;
import static java.sql.Types.BINARY;
import static java.sql.Types.BIT;
import static java.sql.Types.BLOB;
import static java.sql.Types.BOOLEAN;
import static java.sql.Types.CHAR;
import static java.sql.Types.CLOB;
import static java.sql.Types.DATALINK;
import static java.sql.Types.DATE;
import static java.sql.Types.DECIMAL;
import static java.sql.Types.DISTINCT;
import static java.sql.Types.DOUBLE;
import static java.sql.Types.FLOAT;
import static java.sql.Types.INTEGER;
import static java.sql.Types.JAVA_OBJECT;
import static java.sql.Types.LONGNVARCHAR;
import static java.sql.Types.LONGVARBINARY;
import static java.sql.Types.LONGVARCHAR;
import static java.sql.Types.NCHAR;
import static java.sql.Types.NCLOB;
import static java.sql.Types.NUMERIC;
import static java.sql.Types.NVARCHAR;
import static java.sql.Types.OTHER;
import static java.sql.Types.REAL;
import static java.sql.Types.REF;
import static java.sql.Types.ROWID;
import static java.sql.Types.SMALLINT;
import static java.sql.Types.SQLXML;
import static java.sql.Types.STRUCT;
import static java.sql.Types.TIME;
import static java.sql.Types.TIMESTAMP;
import static java.sql.Types.TINYINT;
import static java.sql.Types.VARBINARY;
import static java.sql.Types.VARCHAR;

import static ltd.qubit.commons.lang.Argument.requireGreaterEqual;
import static ltd.qubit.commons.lang.Argument.requireInEnum;
import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.text.NumberFormat.DECIMAL_RADIX;

/**
 * Stores the information about a type in the database.
 *
 * <p>An object of {@link TypeInfo} corresponds to a row in the returned
 * {@link ResultSet} of the {@link DatabaseMetaData#getTypeInfo()} function.
 *
 * @author Haixing Hu
 * @see DatabaseMetaData#getTypeInfo()
 */
public final class TypeInfo {

  public static final int[] DATA_TYPE_ENUM = {
      ARRAY,
      BIGINT,
      BINARY,
      BIT,
      BLOB,
      BOOLEAN,
      CHAR,
      CLOB,
      DATALINK,
      DATE,
      DECIMAL,
      DISTINCT,
      DOUBLE,
      FLOAT,
      INTEGER,
      JAVA_OBJECT,
      LONGNVARCHAR,
      LONGVARBINARY,
      LONGVARCHAR,
      NCHAR,
      NCLOB,
      NUMERIC,
      NVARCHAR,
      OTHER,
      REAL,
      REF,
      ROWID,
      SMALLINT,
      SQLXML,
      STRUCT,
      TIME,
      TIMESTAMP,
      TINYINT,
      VARBINARY,
      VARCHAR,
  };

  public static final short[] NULLABLE_ENUM = {
      typeNoNulls,
      typeNullable,
      typeNullableUnknown,
  };

  public static final short[] SEARCHABLE_ENUM = {
      typePredNone,
      typePredChar,
      typePredBasic,
      typeSearchable,
  };

  public static final String TYPE_NAME = "TYPE_NAME";

  public static final String DATA_TYPE = "DATA_TYPE";

  public static final String PRECISION = "PRECISION";

  public static final String LITERAL_PREFIX = "LITERAL_PREFIX";

  public static final String LITERAL_SUFFIX = "LITERAL_SUFFIX";

  public static final String CREATE_PARAMS = "CREATE_PARAMS";

  public static final String NULLABLE = "NULLABLE";

  public static final String CASE_SENSITIVE = "CASE_SENSITIVE";

  public static final String SEARCHABLE = "SEARCHABLE";

  public static final String UNSIGNED_ATTRIBUTE = "UNSIGNED_ATTRIBUTE";

  public static final String FIXED_PREC_SCALE = "FIXED_PREC_SCALE";

  public static final String AUTO_INCREMENT = "AUTO_INCREMENT";

  public static final String LOCAL_TYPE_NAME = "LOCAL_TYPE_NAME";

  public static final String MINIMUM_SCALE = "MINIMUM_SCALE";

  public static final String MAXIMUM_SCALE = "MAXIMUM_SCALE";

  public static final String SQL_DATA_TYPE = "SQL_DATA_TYPE";

  public static final String SQL_DATETIME_SUB = "SQL_DATETIME_SUB";

  public static final String NUM_PREC_RADIX = "NUM_PREC_RADIX";

  private String typeName;
  private int dataType;
  private int precision;
  private String literalPrefix;
  private String literalSuffix;
  private String createParams;
  private short nullable;
  private boolean caseSensitive;
  private short searchable;
  private boolean unsignedAttribute;
  private boolean fixedPrecScale;
  private boolean autoIncrement;
  private String localTypeName;
  private short minimumScale;
  private short maximumScale;
  private int sqlDataType;
  private int sqlDatetimeSub;
  private int numPrecRadix;

  public TypeInfo() {
    typeName = StringUtils.EMPTY;
    dataType = OTHER;
    precision = 0;
    literalPrefix = null;
    literalSuffix = null;
    createParams = null;
    nullable = typeNullableUnknown;
    caseSensitive = true;
    searchable = typePredNone;
    unsignedAttribute = false;
    fixedPrecScale = false;
    autoIncrement = false;
    localTypeName = null;
    minimumScale = 0;
    maximumScale = 0;
    sqlDataType = 0;
    sqlDatetimeSub = 0;
    numPrecRadix = DECIMAL_RADIX;
  }

  public TypeInfo(final ResultSet rs) throws SQLException {
    typeName = rs.getString("TYPE_NAME");
    dataType = rs.getInt("DATA_TYPE");
    precision = rs.getInt("PRECISION");
    literalPrefix = rs.getString("LITERAL_PREFIX");
    literalSuffix = rs.getString("LITERAL_SUFFIX");
    createParams = rs.getString("CREATE_PARAMS");
    nullable = rs.getShort("NULLABLE");
    caseSensitive = rs.getBoolean("CASE_SENSITIVE");
    searchable = rs.getShort("SEARCHABLE");
    unsignedAttribute = rs.getBoolean("UNSIGNED_ATTRIBUTE");
    fixedPrecScale = rs.getBoolean("FIXED_PREC_SCALE");
    autoIncrement = rs.getBoolean("AUTO_INCREMENT");
    localTypeName = rs.getString("LOCAL_TYPE_NAME");
    minimumScale = rs.getShort("MINIMUM_SCALE");
    maximumScale = rs.getShort("MAXIMUM_SCALE");
    sqlDataType = rs.getInt("SQL_DATA_TYPE");
    sqlDatetimeSub = rs.getInt("SQL_DATETIME_SUB");
    numPrecRadix = rs.getInt("NUM_PREC_RADIX");
    if (typeName == null) {
      throw new UnexpectedColumnValueException(TYPE_NAME, null);
    }
    if (ArrayUtils.indexOf(DATA_TYPE_ENUM, dataType) < 0) {
      throw new UnexpectedColumnValueException(DATA_TYPE, dataType);
    }
    if (precision < 0) {
      throw new UnexpectedColumnValueException(PRECISION, precision);
    }
    if (ArrayUtils.indexOf(NULLABLE_ENUM, nullable) < 0) {
      throw new UnexpectedColumnValueException(NULLABLE, nullable);
    }
    if (ArrayUtils.indexOf(SEARCHABLE_ENUM, searchable) < 0) {
      throw new UnexpectedColumnValueException(SEARCHABLE, searchable);
    }
    if (numPrecRadix < 2) {
      throw new UnexpectedColumnValueException(NUM_PREC_RADIX, numPrecRadix);
    }
  }

  public String getTypeName() {
    return typeName;
  }

  void setTypeName(final String typeName) {
    this.typeName = requireNonNull("typeName", typeName);
  }

  public int getDataType() {
    return dataType;
  }

  void setDataType(final int dataType) {
    this.dataType = requireInEnum("dataType", dataType, DATA_TYPE_ENUM);
  }

  public int getPrecision() {
    return precision;
  }

  void setPrecision(final int precision) {
    this.precision = requireGreaterEqual("precision", precision, "zero", 0);
  }

  public String getLiteralPrefix() {
    return literalPrefix;
  }

  void setLiteralPrefix(@Nullable final String literalPrefix) {
    this.literalPrefix = literalPrefix;
  }

  public String getLiteralSuffix() {
    return literalSuffix;
  }

  void setLiteralSuffix(@Nullable final String literalSuffix) {
    this.literalSuffix = literalSuffix;
  }

  public String getCreateParams() {
    return createParams;
  }

  void setCreateParams(@Nullable final String createParams) {
    this.createParams = createParams;
  }

  public short getNullable() {
    return nullable;
  }

  void setNullable(final short nullable) {
    this.nullable = requireInEnum("nullable", nullable, NULLABLE_ENUM);
  }

  public boolean isCaseSensitive() {
    return caseSensitive;
  }

  void setCaseSensitive(final boolean caseSensitive) {
    this.caseSensitive = caseSensitive;
  }

  public short getSearchable() {
    return searchable;
  }

  void setSearchable(final short searchable) {
    this.searchable = requireInEnum("searchable", searchable, SEARCHABLE_ENUM);
  }

  public boolean isUnsignedAttribute() {
    return unsignedAttribute;
  }

  void setUnsignedAttribute(final boolean unsignedAttribute) {
    this.unsignedAttribute = unsignedAttribute;
  }

  public boolean isFixedPrecScale() {
    return fixedPrecScale;
  }

  void setFixedPrecScale(final boolean fixedPrecScale) {
    this.fixedPrecScale = fixedPrecScale;
  }

  public boolean isAutoIncrement() {
    return autoIncrement;
  }

  void setAutoIncrement(final boolean autoIncrement) {
    this.autoIncrement = autoIncrement;
  }

  public String getLocalTypeName() {
    return localTypeName;
  }

  void setLocalTypeName(@Nullable final String localTypeName) {
    this.localTypeName = localTypeName;
  }

  public short getMinimumScale() {
    return minimumScale;
  }

  void setMinimumScale(final short minimumScale) {
    this.minimumScale = requireGreaterEqual("minimumScale", minimumScale,
        "zero", (short) 0);
  }

  public short getMaximumScale() {
    return maximumScale;
  }

  void setMaximumScale(final short maximumScale) {
    this.maximumScale = requireGreaterEqual("maximumScale", maximumScale,
        "zero", (short) 0);
  }

  public int getSqlDataType() {
    return sqlDataType;
  }

  void setSqlDataType(final int sqlDataType) {
    this.sqlDataType = sqlDataType;
  }

  public int getSqlDatetimeSub() {
    return sqlDatetimeSub;
  }

  void setSqlDatetimeSub(final int sqlDatetimeSub) {
    this.sqlDatetimeSub = sqlDatetimeSub;
  }

  public int getNumPrecRadix() {
    return numPrecRadix;
  }

  void setNumPrecRadix(final int numPrecRadix) {
    this.numPrecRadix = requireGreaterEqual("numPrecRadix", numPrecRadix,
        "zero", 0);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final TypeInfo other = (TypeInfo) o;
    return Equality.equals(dataType, other.dataType)
            && Equality.equals(precision, other.precision)
            && Equality.equals(nullable, other.nullable)
            && Equality.equals(caseSensitive, other.caseSensitive)
            && Equality.equals(searchable, other.searchable)
            && Equality.equals(unsignedAttribute, other.unsignedAttribute)
            && Equality.equals(fixedPrecScale, other.fixedPrecScale)
            && Equality.equals(autoIncrement, other.autoIncrement)
            && Equality.equals(minimumScale, other.minimumScale)
            && Equality.equals(maximumScale, other.maximumScale)
            && Equality.equals(sqlDataType, other.sqlDataType)
            && Equality.equals(sqlDatetimeSub, other.sqlDatetimeSub)
            && Equality.equals(numPrecRadix, other.numPrecRadix)
            && Equality.equals(typeName, other.typeName)
            && Equality.equals(literalPrefix, other.literalPrefix)
            && Equality.equals(literalSuffix, other.literalSuffix)
            && Equality.equals(createParams, other.createParams)
            && Equality.equals(localTypeName, other.localTypeName);
  }

  @Override
  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, typeName);
    result = Hash.combine(result, multiplier, dataType);
    result = Hash.combine(result, multiplier, precision);
    result = Hash.combine(result, multiplier, literalPrefix);
    result = Hash.combine(result, multiplier, literalSuffix);
    result = Hash.combine(result, multiplier, createParams);
    result = Hash.combine(result, multiplier, nullable);
    result = Hash.combine(result, multiplier, caseSensitive);
    result = Hash.combine(result, multiplier, searchable);
    result = Hash.combine(result, multiplier, unsignedAttribute);
    result = Hash.combine(result, multiplier, fixedPrecScale);
    result = Hash.combine(result, multiplier, autoIncrement);
    result = Hash.combine(result, multiplier, localTypeName);
    result = Hash.combine(result, multiplier, minimumScale);
    result = Hash.combine(result, multiplier, maximumScale);
    result = Hash.combine(result, multiplier, sqlDataType);
    result = Hash.combine(result, multiplier, sqlDatetimeSub);
    result = Hash.combine(result, multiplier, numPrecRadix);
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
               .append("typeName", typeName)
               .append("dataType", dataType)
               .append("precision", precision)
               .append("literalPrefix", literalPrefix)
               .append("literalSuffix", literalSuffix)
               .append("createParams", createParams)
               .append("nullable", nullable)
               .append("caseSensitive", caseSensitive)
               .append("searchable", searchable)
               .append("unsignedAttribute", unsignedAttribute)
               .append("fixedPrecScale", fixedPrecScale)
               .append("autoIncrement", autoIncrement)
               .append("localTypeName", localTypeName)
               .append("minimumScale", minimumScale)
               .append("maximumScale", maximumScale)
               .append("sqlDataType", sqlDataType)
               .append("sqlDatetimeSub", sqlDatetimeSub)
               .append("numPrecRadix", numPrecRadix)
               .toString();
  }

}
