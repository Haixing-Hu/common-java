////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides utilities functions for JDBC operations.
 *
 * @author Haixing Hu
 */
public final class JdbcUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(JdbcUtils.class);

  /**
   * Closes a {@link Connection} quietly without throwing any exception.
   *
   * <p>The thrown exceptions will be logged.
   *
   * @param conn
   *     a {@link Connection}, which could be null.
   */
  public static void closeQuietly(@Nullable final Connection conn) {
    if (conn != null) {
      try {
        conn.close();
      } catch (final SQLException e) {
        LOGGER.error("An error occurred while closing JDBC connection.", e);
      } catch (final Exception e) {
        // We don't trust the JDBC driver: It might throw RuntimeException or
        // Error.
        LOGGER.error("Unexpected exception on closing JDBC connection.", e);
      }
    }
  }

  /**
   * Closes a {@link Statement} quietly without throwing any exception.
   *
   * <p>The thrown exceptions will be logged.
   *
   * @param stmt
   *     a {@link Statement}, which could be null.
   */
  public static void closeQuietly(@Nullable final Statement stmt) {
    if (stmt != null) {
      try {
        stmt.close();
      } catch (final SQLException e) {
        LOGGER.error("An error occurred while closing JDBC statement: {}",
            stmt, e);
      } catch (final Exception e) {
        // We don't trust the JDBC driver: It might throw RuntimeException or
        // Error.
        LOGGER.error("Unexpected exception on closing JDBC Statement: {}",
            stmt, e);
      }
    }
  }

  /**
   * Closes a {@link ResultSet} quietly without throwing any exception.
   *
   * <p>The thrown exceptions will be logged.
   *
   * @param rs
   *     a {@link ResultSet}, which could be null.
   */
  public static void closeQuietly(@Nullable final ResultSet rs) {
    if (rs != null) {
      try {
        rs.close();
      } catch (final SQLException e) {
        LOGGER.error("An error occurred while closing JDBC result set: {}", rs,
            e);
      } catch (final Exception e) {
        // We don't trust the JDBC driver: It might throw RuntimeException or
        // Error.
        LOGGER.error("Unexpected exception on closing JDBC result set: {}", rs,
            e);
      }
    }
  }

  /**
   * Rollback the transaction of a {@link Connection} without throwing any
   * exception.
   *
   * <p>The thrown exceptions will be logged.
   *
   * @param conn
   *     a {@link Connection}, which could be null.
   */
  public static void rollbackQuietly(@Nullable final Connection conn) {
    if (conn != null) {
      try {
        conn.rollback();
      } catch (final SQLException e) {
        LOGGER.error("An error occurred while rollback JDBC transaction:", e);
      } catch (final Exception e) {
        // We don't trust the JDBC driver: It might throw RuntimeException or
        // Error.
        LOGGER.error("Unexpected exception on rollback JDBC transaction:", e);
      }
    }
  }

  /**
   * Maps a {@link ResultSet} to a list of objects.
   *
   * <p><b>NOTE:</b> After calling this function, the result set will be closed
   * even if any error occurred.
   *
   * @param <T>
   *     the type of objects mapped from rows.
   * @param rs
   *     a {@link ResultSet}. After calling this function, this result set will
   *     be closed, even if any error occurred.
   * @param mapper
   *     a {@link RowMapper}.
   * @return a list of objects as the mapping result of the rows of the result
   *     set.
   * @throws SQLException
   *     if any error occurs.
   */
  public static <T> List<T> mapToList(final ResultSet rs,
      final RowMapper<T> mapper) throws SQLException {
    try {
      final List<T> result = new ArrayList<>();
      int rowNum = 0;
      while (rs.next()) {
        ++rowNum;
        final T t = mapper.mapRow(rs, rowNum);
        result.add(t);
      }
      return result;
    } finally {
      closeQuietly(rs);
    }
  }

  /**
   * Maps a {@link ResultSet} to a single object.
   *
   * <p><b>NOTE:</b> if there is more than one row in the result set, this
   * function
   * only consider the first row. if there is no row in the result set, this
   * function simply returns null.
   *
   * <p><b>NOTE:</b> After calling this function, the result set will be closed
   * even if any error occurred.
   *
   * @param <T>
   *     the type of objects mapped from rows.
   * @param rs
   *     a {@link ResultSet}. After calling this function, this result set will
   *     be closed, even if any error occurred.
   * @param mapper
   *     a {@link RowMapper}.
   * @return the mapping result of the first row of the result set, or null if
   *     the result set has no row.
   * @throws SQLException
   *     if any error occurs.
   */
  public static <T> T mapToObject(final ResultSet rs, final RowMapper<T> mapper)
      throws SQLException {
    try {
      if (rs.next()) {
        final T t = mapper.mapRow(rs, 1);
        return t;
      } else {
        return null;
      }
    } finally {
      closeQuietly(rs);
    }
  }

  /**
   * Process each row of a {@link ResultSet}.
   *
   * <p><b>NOTE:</b> After calling this function, the result set will be closed
   * even if any error occurred.
   *
   * @param rs
   *     a {@link ResultSet}. After calling this function, this result set will
   *     be closed, even if any error occurred.
   * @param processor
   *     a {@link RowProcessor}.
   * @throws SQLException
   *     if any error occurs.
   */
  public static void processRow(final ResultSet rs,
      final RowProcessor processor)
      throws SQLException {
    try {
      int rowNum = 0;
      while (rs.next()) {
        ++rowNum;
        processor.processRow(rs, rowNum);
      }
    } finally {
      closeQuietly(rs);
    }
  }

  /**
   * Gets the supported client information from a database metadata.
   *
   * @param metadata
   *     a database metadata.
   * @return a map mapping the name to the {@link ClientInfo}.
   * @throws SQLException
   *     if any error occurs.
   */
  public static Map<String, ClientInfo> getSupportedClientInfos(
      final DatabaseMetaData metadata) throws SQLException {
    LOGGER.debug("Getting the supported client informations...");
    final Map<String, ClientInfo> result = new HashMap<>();
    final ResultSet rs = metadata.getClientInfoProperties();
    processRow(rs, new RowProcessor() {
      @Override
      public void processRow(final ResultSet rs, final int rowNumber)
          throws SQLException {
        final ClientInfo info = ClientInfoRowMapper.INSTANCE.mapRow(rs, rowNumber);
        result.put(info.getName(), info);
      }
    });
    return result;
  }

  /**
   * Gets the type information from a database metadata.
   *
   * @param metadata
   *     a database metadata.
   * @return a map mapping the SQL type value to the {@link TypeInfo}.
   * @throws SQLException
   *     if any error occurs.
   */
  public static Map<Integer, TypeInfo> getTypeInfos(
      final DatabaseMetaData metadata) throws SQLException {
    final Map<Integer, TypeInfo> result = new HashMap<>();
    final ResultSet rs = metadata.getTypeInfo();
    processRow(rs, new RowProcessor() {
      @Override
      public void processRow(final ResultSet rs, final int rowNumber)
          throws SQLException {
        final TypeInfo info = new TypeInfo(rs);
        result.put(info.getDataType(), info);
      }
    });
    return result;
  }

  /**
   * Performs a JDBC operation in a transaction.
   *
   * <p>This is a template method used to simplify the exception catching of
   * JDBC
   * transactions.
   *
   * <p><b>NOTE:</b> After calling this function, the connection will be closed
   * even if any error occurred.
   *
   * @param <R>
   *     the type of returned value of the operation. If the operation has no
   *     return value, set this type parameter to {@link Void} and returns
   *     {@code null} in the {@link JdbcOperation#perform(Connection)}
   *     function.
   * @param conn
   *     an opened JDBC connection. After calling this function, this connection
   *     will be closed even if any error occurred.
   * @param operation
   *     the operation to be performed.
   * @return the returned value of the operation.
   * @throws SQLException
   *     if any error occurred.
   */
  public static <R> R transaction(final Connection conn,
      final JdbcOperation<R> operation) throws SQLException {
    try {
      conn.setAutoCommit(false);
      final R result = operation.perform(conn);
      conn.commit();
      return result;
    } catch (final SQLException e) {
      rollbackQuietly(conn);
      throw e;
    } catch (final Exception e) {
      rollbackQuietly(conn);
      throw new SQLException(e);
    } finally {
      closeQuietly(conn);
    }
  }

  /**
   * Executes a query and create an object list from the query result.
   *
   * <p><b>NOTE:</b> After calling this function, the JDBC connection will
   * <b>NOT</b> be closed.
   *
   * @param <T>
   *     the type of objects mapped from rows.
   * @param conn
   *     an opened JDBC connection.
   * @param sql
   *     a simple SQL statement for the query.
   * @param rowMapper
   *     a row mapper used to map each row of a result set to an object.
   * @return the list of object created from the result of the query.
   * @throws SQLException
   *     if any error occurred.
   */
  public static <T> List<T> queryList(final Connection conn, final String sql,
      final RowMapper<T> rowMapper) throws SQLException {
    final Statement st = conn.createStatement();
    try {
      final ResultSet rs = st.executeQuery(sql);
      return mapToList(rs, rowMapper);
    } finally {
      closeQuietly(st);
    }
  }

  /**
   * Executes a query and create an object list from the query result.
   *
   * <p><b>NOTE:</b> After calling this function, the JDBC connection will
   * <b>NOT</b> be closed.
   *
   * @param <T>
   *     the type of objects mapped from rows.
   * @param conn
   *     an opened JDBC connection.
   * @param preparedSql
   *     a prepared SQL statement for the query.
   * @param setter
   *     a setter used to set the parameter of the prepared SQL statement.
   * @param rowMapper
   *     a row mapper used to map each row of a result set to an object.
   * @return the list of object created from the result of the query.
   * @throws SQLException
   *     if any error occurred.
   */
  public static <T> List<T> queryList(final Connection conn,
      final String preparedSql, final PreparedStatementSetter setter,
      final RowMapper<T> rowMapper) throws SQLException {
    final PreparedStatement pst = conn.prepareStatement(preparedSql);
    try {
      setter.setValues(pst);
      final ResultSet rs = pst.executeQuery();
      return mapToList(rs, rowMapper);
    } finally {
      closeQuietly(pst);
    }
  }

  /**
   * Executes a query and create an object from the query result.
   *
   * <p><b>NOTE:</b> After calling this function, the JDBC connection will
   * <b>NOT</b> be closed.
   *
   * @param <T>
   *     the type of objects mapped from rows.
   * @param conn
   *     an opened JDBC connection.
   * @param sql
   *     a simple SQL statement for the query.
   * @param rowMapper
   *     a row mapper used to map each row of a result set to an object.
   * @return an object created from the first row of the result of the query; or
   *     null if the query has no result.
   * @throws SQLException
   *     if any error occurred.
   */
  public static <T> T queryObject(final Connection conn, final String sql,
      final RowMapper<T> rowMapper) throws SQLException {
    final Statement st = conn.createStatement();
    try {
      final ResultSet rs = st.executeQuery(sql);
      return mapToObject(rs, rowMapper);
    } finally {
      closeQuietly(st);
    }
  }

  /**
   * Executes a query and create an object from the query result.
   *
   * <p><b>NOTE:</b> After calling this function, the JDBC connection will
   * <b>NOT</b> be closed.
   *
   * @param <T>
   *     the type of objects mapped from rows.
   * @param conn
   *     an opened JDBC connection.
   * @param preparedSql
   *     a prepared SQL statement for the query.
   * @param setter
   *     a setter used to set the parameters of the prepared SQL statement.
   * @param rowMapper
   *     a row mapper used to map each row of a result set to an object.
   * @return an object created from the first row of the result of the query; or
   *     null if the query has no result.
   * @throws SQLException
   *     if any error occurred.
   */
  public static <T> T queryObject(final Connection conn,
      final String preparedSql, final PreparedStatementSetter setter,
      final RowMapper<T> rowMapper) throws SQLException {
    final PreparedStatement pst = conn.prepareStatement(preparedSql);
    try {
      setter.setValues(pst);
      final ResultSet rs = pst.executeQuery();
      return mapToObject(rs, rowMapper);
    } finally {
      closeQuietly(pst);
    }
  }

  /**
   * Executes an update. <b>NOTE:</b> After calling this function, the JDBC
   * connection will <b>NOT</b> be closed.
   *
   * @param conn
   *     an opened JDBC connection.
   * @param sql
   *     a simple SQL statement, which must be an SQL Data Manipulation Language
   *     (DML) statement, such as INSERT, UPDATE or DELETE; or an SQL statement
   *     that returns nothing, such as a DDL statement.
   * @throws SQLException
   *     if any error occurred.
   */
  public static void update(final Connection conn, final String sql)
      throws SQLException {
    final Statement st = conn.createStatement();
    try {
      st.executeUpdate(sql);
    } finally {
      closeQuietly(st);
    }
  }

  /**
   * Executes an update. <b>NOTE:</b> After calling this function, the JDBC
   * connection will <b>NOT</b> be closed.
   *
   * @param conn
   *     an opened JDBC connection.
   * @param preparedSql
   *     a prepared SQL statement, which must be an SQL Data Manipulation
   *     Language (DML) statement, such as INSERT, UPDATE or DELETE; or an SQL
   *     statement that returns nothing, such as a DDL statement.
   * @param setter
   *     a setter used to set the parameters of the prepared SQL statement.
   * @throws SQLException
   *     if any error occurred.
   */
  public static void update(final Connection conn, final String preparedSql,
      final PreparedStatementSetter setter) throws SQLException {
    final PreparedStatement pst = conn.prepareStatement(preparedSql);
    try {
      setter.setValues(pst);
      pst.executeUpdate();
    } finally {
      closeQuietly(pst);
    }
  }

  /**
   * Gets the name of the database opened by a {@link DataSource}.
   *
   * @param dataSource
   *     a {@link DataSource}.
   * @return
   *     the name of the database opened by the data source.
   */
  public static String getDatabaseName(final DataSource dataSource) throws SQLException {
    try {
      final Connection connection = dataSource.getConnection();
      final DatabaseMetaData metaData = connection.getMetaData();
      final String url = metaData.getURL();
      return extractDatabaseNameFromUrl(url);
    } catch (final SQLException e) {
      LOGGER.error("An error occurred while getting database name from data source: {}", dataSource, e);
      throw e;
    }
  }

  private static String extractDatabaseNameFromUrl(final String url) {
    if (url == null) {
      throw new IllegalArgumentException("Database URL is null");
    }
    if (url.startsWith("jdbc:mysql://") || url.startsWith("jdbc:postgresql://")) { // MySQL and PostgreSQL
      final String[] parts = url.split("/");
      if (parts.length > 0) {
        return parts[parts.length - 1].split("\\?")[0]; // Remove query parameters if present
      }
      throw new IllegalArgumentException("Database name not found in MySQL/PostgreSQL URL: " + url);
    } else if (url.startsWith("jdbc:sqlserver://")) { // SQL Server
      final String[] parts = url.split(";");
      for (final String part : parts) {
        if (part.startsWith("databaseName=")) {
          return part.split("=")[1];
        }
      }
      throw new IllegalArgumentException("Database name not found in SQL Server URL: " + url);
    } else if (url.startsWith("jdbc:oracle:thin:@")) {  // Oracle
      final String[] parts = url.split(":");
      if (parts.length > 0) {
        return parts[parts.length - 1];
      }
      throw new IllegalArgumentException("Database name not found in Oracle URL: " + url);
    } else if (url.startsWith("jdbc:sqlite:")) {  // SQLite
      return url.substring("jdbc:sqlite:".length());
    } else {
      throw new IllegalArgumentException("Unsupported database URL: " + url);
    }
  }


  /**
   * Gets the host of the database opened by a {@link DataSource}.
   *
   * @param dataSource
   *     a {@link DataSource}.
   * @return
   *     the host of the database opened by the data source.
   */
  public static String getDatabaseHost(final DataSource dataSource) throws SQLException {
    try {
      final Connection connection = dataSource.getConnection();
      final DatabaseMetaData metaData = connection.getMetaData();
      final String url = metaData.getURL();
      return extractDatabaseHostFromUrl(url);
    } catch (final SQLException e) {
      LOGGER.error("An error occurred while getting database host from data source: {}", dataSource, e);
      throw e;
    }
  }

  private static String extractDatabaseHostFromUrl(final String url) {
    if (url == null) {
      throw new IllegalArgumentException("Database URL is null");
    }
    if (url.startsWith("jdbc:mysql://") || url.startsWith("jdbc:postgresql://")) { // MySQL and PostgreSQL
      final String[] parts = url.split("/");
      if (parts.length > 2) {
        return parts[2].split(":")[0];
      }
      throw new IllegalArgumentException("Database name not found in MySQL/PostgreSQL URL: " + url);
    } else if (url.startsWith("jdbc:sqlserver://")) { // SQL Server
      final String[] parts = url.split(";");
      for (final String part : parts) {
        if (part.startsWith("serverName=")) {
          return part.split("=")[1];
        }
      }
      throw new IllegalArgumentException("Database name not found in SQL Server URL: " + url);
    } else if (url.startsWith("jdbc:oracle:thin:@")) {  // Oracle
      final String[] parts = url.split(":");
      if (parts.length > 0) {
        return parts[parts.length - 2];
      }
      throw new IllegalArgumentException("Database name not found in Oracle URL: " + url);
    } else if (url.startsWith("jdbc:sqlite:")) {  // SQLite
      return "localhost";
    } else {
      throw new IllegalArgumentException("Unsupported database URL: " + url);
    }
  }

  /**
   * Tests whether the database supports WITH RECURSIVE CTE.
   *
   * @param dataSource
   *    a {@link DataSource}.
   * @return
   *    {@code true} if the database supports WITH RECURSIVE CTE; {@code false} otherwise.
   */
  public static boolean supportsWithRecursiveCTE(final DataSource dataSource) {
    final String query = "WITH RECURSIVE test AS (SELECT 1 AS n UNION ALL SELECT n+1 FROM test WHERE n < 2) SELECT * FROM test;";
    try (final Connection connection = dataSource.getConnection();
        final Statement statement = connection.createStatement()) {
      statement.executeQuery(query);
      // 如果查询执行成功，说明数据库支持 WITH RECURSIVE
      return true;
    } catch (final SQLException e) {
      // 处理异常，如果是数据库不支持的错误，则返回 false
      LOGGER.debug("The database does not support WITH RECURSIVE CTE: {}", e.getMessage());
      return false;
    }
  }
}