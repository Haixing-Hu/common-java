/// /////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2024 - 2025.
//    Nanjing Xinglin Digital Technology Co., Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JdbcUtilsTest {

  @Test
  void closeQuietlyClosesConnection() throws SQLException {
    final Connection conn = mock(Connection.class);
    JdbcUtils.closeQuietly(conn);
    verify(conn, times(1)).close();
  }

  @Test
  void closeQuietlyHandlesNullConnection() {
    JdbcUtils.closeQuietly((Connection) null);
  }

  @Test
  void rollbackQuietlyRollsBackTransaction() throws SQLException {
    final Connection conn = mock(Connection.class);
    JdbcUtils.rollbackQuietly(conn);
    verify(conn, times(1)).rollback();
  }

  @Test
  void rollbackQuietlyHandlesNullConnection() {
    JdbcUtils.rollbackQuietly((Connection) null);
  }

  @Test
  void getDatabaseNameReturnsCorrectName() throws SQLException {
    final DataSource dataSource = mock(DataSource.class);
    final Connection connection = mock(Connection.class);
    final DatabaseMetaData metaData = mock(DatabaseMetaData.class);

    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.getMetaData()).thenReturn(metaData);
    when(metaData.getURL()).thenReturn("jdbc:mysql://localhost:3306/testdb");

    final String dbName = JdbcUtils.getDatabaseName(dataSource);
    assertEquals("testdb", dbName);
  }

  @Test
  void getDatabaseHostReturnsCorrectHost() throws SQLException {
    final DataSource dataSource = mock(DataSource.class);
    final Connection connection = mock(Connection.class);
    final DatabaseMetaData metaData = mock(DatabaseMetaData.class);

    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.getMetaData()).thenReturn(metaData);
    when(metaData.getURL()).thenReturn("jdbc:mysql://localhost:3306/testdb");

    final String dbHost = JdbcUtils.getDatabaseHost(dataSource);
    assertEquals("localhost", dbHost);
  }

  @Test
  void supportsWithRecursiveCTEReturnsTrueForSupportedDatabase() throws SQLException {
    final DataSource dataSource = mock(DataSource.class);
    final Connection connection = mock(Connection.class);
    final Statement statement = mock(Statement.class);
    final ResultSet resultSet = mock(ResultSet.class);

    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.createStatement()).thenReturn(statement);
    when(statement.executeQuery(anyString())).thenReturn(resultSet);

    assertTrue(JdbcUtils.supportsWithRecursiveCTE(dataSource));
  }

  @Test
  void supportsWithRecursiveCTEReturnsFalseForUnsupportedDatabase() throws SQLException {
    final DataSource dataSource = mock(DataSource.class);
    final Connection connection = mock(Connection.class);
    final Statement statement = mock(Statement.class);

    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.createStatement()).thenReturn(statement);
    when(statement.executeQuery(anyString())).thenThrow(new SQLException("Syntax error"));

    assertFalse(JdbcUtils.supportsWithRecursiveCTE(dataSource));
  }
}
