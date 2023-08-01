////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import ltd.qubit.commons.lang.ClassKey;
import ltd.qubit.commons.net.Url;
import ltd.qubit.commons.sql.error.UnsupportedJavaTypeException;
import ltd.qubit.commons.sql.error.UnsupportedSqlTypeException;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Map;

import static java.util.Map.entry;

/**
 * Standard mapping between SQL type to Java class.
 *
 * @author Haixing Hu
 * @see <a href="http://docs.oracle.com/javase/6/docs/technotes/guides/jdbc/getstart/mapping.html">Mapping
 *     SQL and Java Types</a>
 */
public final class StandardTypeMapping {

  /**
   * The array of all allowed SQL types.
   */
  public static final int[] SQL_TYPES = {
      Types.ARRAY,
      Types.BIGINT,
      Types.BINARY,
      Types.BIT,
      Types.BLOB,
      Types.BOOLEAN,
      Types.CHAR,
      Types.CLOB,
      Types.DATALINK,
      Types.DATE,
      Types.DECIMAL,
      Types.DISTINCT,
      Types.DOUBLE,
      Types.FLOAT,
      Types.INTEGER,
      Types.JAVA_OBJECT,
      Types.LONGNVARCHAR,
      Types.LONGVARBINARY,
      Types.LONGVARCHAR,
      Types.NCHAR,
      Types.NCLOB,
      Types.NULL,
      Types.NUMERIC,
      Types.NVARCHAR,
      Types.OTHER,
      Types.REAL,
      Types.REF,
      Types.ROWID,
      Types.SMALLINT,
      Types.SQLXML,
      Types.STRUCT,
      Types.TIME,
      Types.TIMESTAMP,
      Types.TINYINT,
      Types.VARBINARY,
      Types.VARCHAR,
  };

  private static final Map<Integer, ClassKey> SQL_TO_JAVA_MAP =
      Map.ofEntries(
          entry(Types.CHAR, new ClassKey(String.class)),
          entry(Types.VARCHAR, new ClassKey(String.class)),
          entry(Types.LONGVARCHAR, new ClassKey(Reader.class)),
          entry(Types.CLOB, new ClassKey(Clob.class)),
          entry(Types.BINARY, new ClassKey(byte[].class)),
          entry(Types.VARBINARY, new ClassKey(byte[].class)),
          entry(Types.LONGVARBINARY, new ClassKey(InputStream.class)),
          entry(Types.BLOB, new ClassKey(Blob.class)),
          entry(Types.BIT, new ClassKey(Boolean.class)),
          entry(Types.BOOLEAN, new ClassKey(Boolean.class)),
          entry(Types.TINYINT, new ClassKey(Byte.class)),
          entry(Types.SMALLINT, new ClassKey(Short.class)),
          entry(Types.INTEGER, new ClassKey(Integer.class)),
          entry(Types.BIGINT, new ClassKey(Long.class)),
          entry(Types.REAL, new ClassKey(Float.class)),
          entry(Types.DOUBLE, new ClassKey(Double.class)),
          entry(Types.FLOAT, new ClassKey(Double.class)),
          entry(Types.NUMERIC, new ClassKey(BigDecimal.class)),
          entry(Types.DECIMAL, new ClassKey(BigDecimal.class)),
          entry(Types.DATE, new ClassKey(Date.class)),
          entry(Types.TIME, new ClassKey(Time.class)),
          entry(Types.TIMESTAMP, new ClassKey(Timestamp.class)),
          entry(Types.DATALINK, new ClassKey(URL.class)),
          entry(Types.ARRAY, new ClassKey(java.sql.Array.class)),
          entry(Types.REF, new ClassKey(java.sql.Ref.class)),
          entry(Types.STRUCT, new ClassKey(java.sql.Struct.class))
      );

  private static final Map<ClassKey, Integer> JAVA_TO_SQL_MAP =
      Map.ofEntries(
          entry(new ClassKey(String.class), Types.VARCHAR),
          entry(new ClassKey(Reader.class), Types.LONGVARCHAR),
          entry(new ClassKey(Clob.class), Types.CLOB),
          entry(new ClassKey(byte[].class), Types.VARBINARY),
          entry(new ClassKey(InputStream.class), Types.LONGVARBINARY),
          entry(new ClassKey(Blob.class), Types.BLOB),
          entry(new ClassKey(Boolean.class), Types.BOOLEAN),
          entry(new ClassKey(Byte.class), Types.TINYINT),
          entry(new ClassKey(Short.class), Types.SMALLINT),
          entry(new ClassKey(Integer.class), Types.INTEGER),
          entry(new ClassKey(Long.class), Types.BIGINT),
          entry(new ClassKey(Float.class), Types.REAL),
          entry(new ClassKey(Double.class), Types.DOUBLE),
          entry(new ClassKey(BigDecimal.class), Types.DECIMAL),
          entry(new ClassKey(Date.class), Types.DATE),
          entry(new ClassKey(Time.class), Types.TIME),
          entry(new ClassKey(Timestamp.class), Types.TIMESTAMP),
          entry(new ClassKey(URL.class), Types.DATALINK),
          entry(new ClassKey(Url.class), Types.DATALINK),
          entry(new ClassKey(java.sql.Array.class), Types.ARRAY),
          entry(new ClassKey(java.sql.Ref.class), Types.REF),
          entry(new ClassKey(java.sql.Struct.class), Types.STRUCT)
      );

  private static final Map<Integer, String> SQL_NAME_MAP =
      Map.ofEntries(
          entry(Types.ARRAY, "ARRAY"),
          entry(Types.BIGINT, "BIGINT"),
          entry(Types.BINARY, "BINARY"),
          entry(Types.BIT, "BIT"),
          entry(Types.BLOB, "BLOB"),
          entry(Types.BOOLEAN, "BOOL"),
          entry(Types.CHAR, "CHAR"),
          entry(Types.CLOB, "CLOB"),
          entry(Types.DATALINK, "DATALINK"),
          entry(Types.DATE, "DATE"),
          entry(Types.DECIMAL, "DECIMAL"),
          entry(Types.DISTINCT, "DISTINCT"),
          entry(Types.DOUBLE, "DOUBLE"),
          entry(Types.FLOAT, "FLOAT"),
          entry(Types.INTEGER, "INTEGER"),
          entry(Types.JAVA_OBJECT, "JAVA_OBJECT"),
          entry(Types.LONGNVARCHAR, "LONGNVARCHAR"),
          entry(Types.LONGVARBINARY, "LONGVARBINARY"),
          entry(Types.LONGVARCHAR, "LONGVARCHAR"),
          entry(Types.NCHAR, "NCHAR"),
          entry(Types.NCLOB, "NCLOB"),
          entry(Types.NULL, "NULL"),
          entry(Types.NUMERIC, "NUMERIC"),
          entry(Types.NVARCHAR, "NVARCHAR"),
          entry(Types.OTHER, "OTHER"),
          entry(Types.REAL, "REAL"),
          entry(Types.REF, "REF"),
          entry(Types.ROWID, "ROWID"),
          entry(Types.SMALLINT, "SMALLINT"),
          entry(Types.SQLXML, "SQLXML"),
          entry(Types.STRUCT, "STRUCT"),
          entry(Types.TIME, "TIME"),
          entry(Types.TIMESTAMP, "TIMESTAMP"),
          entry(Types.TINYINT, "TINYINT"),
          entry(Types.VARBINARY, "VARBINARY"),
          entry(Types.VARCHAR, "VARCHAR")
      );

  public static String getName(final int sqlType)
      throws UnsupportedSqlTypeException {
    final String result = SQL_NAME_MAP.get(sqlType);
    if (result == null) {
      throw new UnsupportedSqlTypeException(sqlType);
    }
    return result;
  }

  public static Class<?> getJavaType(final int sqlType)
      throws UnsupportedSqlTypeException {
    // FIXME: support the special types: Ref, Array, Struct, etc.
    final ClassKey cls = SQL_TO_JAVA_MAP.get(sqlType);
    if (cls == null) {
      throw new UnsupportedSqlTypeException(sqlType);
    }
    return cls.getActualClass();
  }

  public static int getSqlType(final Class<?> javaType)
      throws UnsupportedJavaTypeException {
    // FIXME: support the special types: Ref, Array, Struct, etc.
    final Integer result = JAVA_TO_SQL_MAP.get(new ClassKey(javaType));
    if (result != null) {
      return result;
    }
    //  deal with the special types
    if (Clob.class.isAssignableFrom(javaType)) {
      return Types.CLOB;
    }
    if (Blob.class.isAssignableFrom(javaType)) {
      return Types.BLOB;
    }
    if (java.sql.Array.class.isAssignableFrom(javaType)) {
      return Types.ARRAY;
    }
    if (java.sql.Ref.class.isAssignableFrom(javaType)) {
      return Types.REF;
    }
    if (java.sql.Struct.class.isAssignableFrom(javaType)) {
      return Types.STRUCT;
    }
    throw new UnsupportedJavaTypeException(javaType);
  }

}
