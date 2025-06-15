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
 * 提供 JDBC 操作的实用工具函数。
 *
 * @author 胡海星
 */
public final class JdbcUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(JdbcUtils.class);

  /**
   * 静默关闭一个 {@link Connection}，不抛出任何异常。
   *
   * <p>抛出的异常将被记录到日志中。
   *
   * @param conn
   *     一个 {@link Connection}，可以为 null。
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
   * 静默关闭一个 {@link Statement}，不抛出任何异常。
   *
   * <p>抛出的异常将被记录到日志中。
   *
   * @param stmt
   *     一个 {@link Statement}，可以为 null。
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
   * 静默关闭一个 {@link ResultSet}，不抛出任何异常。
   *
   * <p>抛出的异常将被记录到日志中。
   *
   * @param rs
   *     一个 {@link ResultSet}，可以为 null。
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
   * 静默回滚 {@link Connection} 的事务，不抛出任何异常。
   *
   * <p>抛出的异常将被记录到日志中。
   *
   * @param conn
   *     一个 {@link Connection}，可以为 null。
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
   * 将 {@link ResultSet} 映射到对象列表。
   *
   * <p><b>注意：</b> 调用此函数后，即使发生任何错误，结果集也会被关闭。
   *
   * @param <T>
   *     从行映射的对象类型。
   * @param rs
   *     一个 {@link ResultSet}。调用此函数后，即使发生任何错误，此结果集也会被关闭。
   * @param mapper
   *     一个 {@link RowMapper}。
   * @return 作为结果集行映射结果的对象列表。
   * @throws SQLException
   *     如果发生任何错误。
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
   * 将 {@link ResultSet} 映射到单个对象。
   *
   * <p><b>注意：</b> 如果结果集中有多行，此函数只考虑第一行。如果结果集中没有行，
   * 此函数简单地返回 null。
   *
   * <p><b>注意：</b> 调用此函数后，即使发生任何错误，结果集也会被关闭。
   *
   * @param <T>
   *     从行映射的对象类型。
   * @param rs
   *     一个 {@link ResultSet}。调用此函数后，即使发生任何错误，此结果集也会被关闭。
   * @param mapper
   *     一个 {@link RowMapper}。
   * @return 结果集第一行的映射结果，如果结果集没有行则返回 null。
   * @throws SQLException
   *     如果发生任何错误。
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
   * 处理 {@link ResultSet} 的每一行。
   *
   * <p><b>注意：</b> 调用此函数后，即使发生任何错误，结果集也会被关闭。
   *
   * @param rs
   *     一个 {@link ResultSet}。调用此函数后，即使发生任何错误，此结果集也会被关闭。
   * @param processor
   *     一个 {@link RowProcessor}。
   * @throws SQLException
   *     如果发生任何错误。
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
   * 从数据库元数据获取支持的客户端信息。
   *
   * @param metadata
   *     数据库元数据。
   * @return 名称到 {@link ClientInfo} 的映射表。
   * @throws SQLException
   *     如果发生任何错误。
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
   * 从数据库元数据获取类型信息。
   *
   * @param metadata
   *     数据库元数据。
   * @return SQL 类型值到 {@link TypeInfo} 的映射表。
   * @throws SQLException
   *     如果发生任何错误。
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
   * 在事务中执行 JDBC 操作。
   *
   * <p>这是一个模板方法，用于简化 JDBC 事务的异常处理。
   *
   * <p><b>注意：</b> 调用此函数后，即使发生任何错误，连接也会被关闭。
   *
   * @param <R>
   *     操作返回值的类型。如果操作没有返回值，将此类型参数设置为 {@link Void}
   *     并在 {@link JdbcOperation#perform(Connection)} 函数中返回 {@code null}。
   * @param conn
   *     一个已打开的 JDBC 连接。调用此函数后，即使发生任何错误，此连接也会被关闭。
   * @param operation
   *     要执行的操作。
   * @return 操作的返回值。
   * @throws SQLException
   *     如果发生任何错误。
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
   * 执行查询并从查询结果创建对象列表。
   *
   * <p><b>注意：</b> 调用此函数后，JDBC 连接将<b>不会</b>被关闭。
   *
   * @param <T>
   *     从行映射的对象类型。
   * @param conn
   *     一个已打开的 JDBC 连接。
   * @param sql
   *     查询的简单 SQL 语句。
   * @param rowMapper
   *     用于将结果集的每一行映射到对象的行映射器。
   * @return 从查询结果创建的对象列表。
   * @throws SQLException
   *     如果发生任何错误。
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
   * 执行查询并从查询结果创建对象列表。
   *
   * <p><b>注意：</b> 调用此函数后，JDBC 连接将<b>不会</b>被关闭。
   *
   * @param <T>
   *     从行映射的对象类型。
   * @param conn
   *     一个已打开的 JDBC 连接。
   * @param preparedSql
   *     查询的预准备 SQL 语句。
   * @param setter
   *     用于设置预准备 SQL 语句参数的设置器。
   * @param rowMapper
   *     用于将结果集的每一行映射到对象的行映射器。
   * @return 从查询结果创建的对象列表。
   * @throws SQLException
   *     如果发生任何错误。
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
   * 执行查询并从查询结果创建对象。
   *
   * <p><b>注意：</b> 调用此函数后，JDBC 连接将<b>不会</b>被关闭。
   *
   * @param <T>
   *     从行映射的对象类型。
   * @param conn
   *     一个已打开的 JDBC 连接。
   * @param sql
   *     查询的简单 SQL 语句。
   * @param rowMapper
   *     用于将结果集的每一行映射到对象的行映射器。
   * @return 从查询结果的第一行创建的对象；如果查询没有结果则返回 null。
   * @throws SQLException
   *     如果发生任何错误。
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
   * 执行查询并从查询结果创建对象。
   *
   * <p><b>注意：</b> 调用此函数后，JDBC 连接将<b>不会</b>被关闭。
   *
   * @param <T>
   *     从行映射的对象类型。
   * @param conn
   *     一个已打开的 JDBC 连接。
   * @param preparedSql
   *     查询的预准备 SQL 语句。
   * @param setter
   *     用于设置预准备 SQL 语句参数的设置器。
   * @param rowMapper
   *     用于将结果集的每一行映射到对象的行映射器。
   * @return 从查询结果的第一行创建的对象；如果查询没有结果则返回 null。
   * @throws SQLException
   *     如果发生任何错误。
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
   * 执行更新操作。<b>注意：</b> 调用此函数后，JDBC 连接将<b>不会</b>被关闭。
   *
   * @param conn
   *     一个已打开的 JDBC 连接。
   * @param sql
   *     一个简单的 SQL 语句，必须是 SQL 数据操作语言 (DML) 语句，
   *     如 INSERT、UPDATE 或 DELETE；或不返回任何内容的 SQL 语句，如 DDL 语句。
   * @throws SQLException
   *     如果发生任何错误。
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
   * 执行更新操作。<b>注意：</b> 调用此函数后，JDBC 连接将<b>不会</b>被关闭。
   *
   * @param conn
   *     一个已打开的 JDBC 连接。
   * @param preparedSql
   *     一个预准备的 SQL 语句，必须是 SQL 数据操作语言 (DML) 语句，
   *     如 INSERT、UPDATE 或 DELETE；或不返回任何内容的 SQL 语句，如 DDL 语句。
   * @param setter
   *     用于设置预准备 SQL 语句参数的设置器。
   * @throws SQLException
   *     如果发生任何错误。
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
   * 获取由 {@link DataSource} 打开的数据库名称。
   *
   * @param dataSource
   *     一个 {@link DataSource}。
   * @return
   *     由数据源打开的数据库名称。
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
   * 获取由 {@link DataSource} 打开的数据库主机名。
   *
   * @param dataSource
   *     一个 {@link DataSource}。
   * @return
   *     由数据源打开的数据库主机名。
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
   * 测试数据库是否支持 WITH RECURSIVE CTE。
   *
   * @param dataSource
   *    一个 {@link DataSource}。
   * @return
   *    如果数据库支持 WITH RECURSIVE CTE 则返回 {@code true}；否则返回 {@code false}。
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