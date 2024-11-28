////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.sql.ResultSet.CLOSE_CURSORS_AT_COMMIT;
import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_FORWARD_ONLY;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * A JDBC operation which performs a simple SQL query.
 *
 * @author Haixing Hu
 */
public final class QueryOperation<R> implements JdbcOperation<List<R>> {

  private String sql;
  private RowMapper<R> rowMapper;
  private int resultSetType;
  private int resultSetConcurrency;
  private int resultSetHoldability;
  private int fetchSize;
  private int maxFieldSize;
  private int queryTimeout;

  public QueryOperation(final String sql, final RowMapper<R> rowMapper) {
    this.sql = requireNonNull("sql", sql);
    this.rowMapper = requireNonNull("rowMapper", rowMapper);
    resultSetType = TYPE_FORWARD_ONLY;
    resultSetConcurrency = CONCUR_READ_ONLY;
    resultSetHoldability = CLOSE_CURSORS_AT_COMMIT;
    fetchSize = 0;
    maxFieldSize = 0;
    queryTimeout = 0;
  }

  /**
   * Gets the SQL of this query operation.
   *
   * @return the SQL of this query operation.
   */
  public String getSql() {
    return sql;
  }

  /**
   * Sets the SQL of this query operation.
   *
   * @param sql
   *     the new SQL.
   */
  public void setSql(final String sql) {
    this.sql = requireNonNull("sql", sql);
  }

  /**
   * Gets the row mapper of this query operation.
   *
   * @return the row mapper of this query operation.
   */
  public RowMapper<R> getRowMapper() {
    return rowMapper;
  }

  /**
   * Sets the row mapper of this query operation.
   *
   * @param rowMapper
   *     the new row mapper.
   */
  public void setRowMapper(final RowMapper<R> rowMapper) {
    this.rowMapper = requireNonNull("rowMapper", rowMapper);
  }

  /**
   * Retrieves the result set type for ResultSet objects generated by this
   * {@link QueryOperation} object.
   *
   * @return one of ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_INSENSITIVE,
   *     or ResultSet.TYPE_SCROLL_SENSITIVE.
   */
  public int getResultSetType() {
    return resultSetType;
  }

  /**
   * Sets the result set type for ResultSet objects generated by this {@link
   * QueryOperation} object.
   *
   * @param resultSetType
   *     one of ResultSet.TYPE_FORWARD_ONLY, ResultSet.TYPE_SCROLL_INSENSITIVE,
   *     or ResultSet.TYPE_SCROLL_SENSITIVE.
   */
  public void setResultSetType(final int resultSetType) {
    this.resultSetType = resultSetType;
  }

  /**
   * Retrieves the result set concurrency for ResultSet objects generated by
   * this {@link QueryOperation} object.
   *
   * @return either ResultSet.CONCUR_READ_ONLY or ResultSet.CONCUR_UPDATABLE
   */
  public int getResultSetConcurrency() {
    return resultSetConcurrency;
  }

  /**
   * Sets the result set concurrency for ResultSet objects generated by this
   * {@link QueryOperation} object.
   *
   * @param resultSetConcurrency
   *     either ResultSet.CONCUR_READ_ONLY or ResultSet.CONCUR_UPDATABLE
   */
  public void setResultSetConcurrency(final int resultSetConcurrency) {
    this.resultSetConcurrency = resultSetConcurrency;
  }

  /**
   * Retrieves the current holdability of ResultSet objects created by this
   * {@link QueryOperation} object.
   *
   * @return the holdability, one of ResultSet.HOLD_CURSORS_OVER_COMMIT or
   *     ResultSet.CLOSE_CURSORS_AT_COMMIT.
   */
  public int getResultSetHoldability() {
    return resultSetHoldability;
  }

  /**
   * Sets the holdability of ResultSet objects created created by this {@link
   * QueryOperation} object.
   *
   * @param resultSetHoldability
   *     the holdability, one of ResultSet.HOLD_CURSORS_OVER_COMMIT or
   *     ResultSet.CLOSE_CURSORS_AT_COMMIT.
   */
  public void setResultSetHoldability(final int resultSetHoldability) {
    this.resultSetHoldability = resultSetHoldability;
  }

  /**
   * Gets the fetch size of ResultSet objects created created by this {@link
   * QueryOperation} object.
   *
   * <p>The fetch size gives the JDBC driver a hint as to the number of rows
   * that should be fetched from the database when more rows are needed for
   * ResultSet objects generated by this {@link QueryOperation}. If the value
   * specified is zero, then the hint is ignored. The default value is zero.
   *
   * @return the fetch size of ResultSet objects created created by this {@link
   *     QueryOperation} object.
   */
  public int getFetchSize() {
    return fetchSize;
  }

  /**
   * Sets the fetch size of ResultSet objects created created by this {@link
   * QueryOperation} object.
   *
   * <p>The fetch size gives the JDBC driver a hint as to the number of rows
   * that should be fetched from the database when more rows are needed for
   * ResultSet objects generated by this {@link QueryOperation}. If the value
   * specified is zero, then the hint is ignored. The default value is zero.
   *
   * @param fetchSize
   *     the fetch size of ResultSet objects created created by this {@link
   *     QueryOperation} object.
   */
  public void setFetchSize(final int fetchSize) {
    this.fetchSize = fetchSize;
  }

  /**
   * Gets the limit for the maximum number of bytes that can be returned for
   * character and binary column values in a ResultSet object produced by this
   * {@link QueryOperation} object.
   *
   * <p>This limit applies only to BINARY, VARBINARY, LONGVARBINARY, CHAR,
   * VARCHAR, NCHAR, NVARCHAR, LONGNVARCHAR and LONGVARCHAR fields. If the limit
   * is exceeded, the excess data is silently discarded. For maximum portability,
   * use values greater than 256.
   *
   * @return the column size limit in bytes; zero means there is no limit.
   */
  public int getMaxFieldSize() {
    return maxFieldSize;
  }

  /**
   * Sets the limit for the maximum number of bytes that can be returned for
   * character and binary column values in a ResultSet object produced by this
   * {@link QueryOperation} object.
   *
   * <p>This limit applies only to BINARY, VARBINARY, LONGVARBINARY, CHAR,
   * VARCHAR, NCHAR, NVARCHAR, LONGNVARCHAR and LONGVARCHAR fields. If the limit
   * is exceeded, the excess data is silently discarded. For maximum portability,
   * use values greater than 256.
   *
   * @param maxFieldSize
   *     the new column size limit in bytes; zero means there is no limit.
   */
  public void setMaxFieldSize(final int maxFieldSize) {
    this.maxFieldSize = maxFieldSize;
  }

  /**
   * Gets the number of seconds the driver will wait for a {@link Statement}
   * object to execute.
   *
   * @return the current query timeout limit in seconds; zero means there is no
   *     limit.
   */
  public int getQueryTimeout() {
    return queryTimeout;
  }

  /**
   * Sets the number of seconds the driver will wait for a {@link Statement}
   * object to execute.
   *
   * @param queryTimeout
   *     the new query timeout limit in seconds; zero means there is no limit.
   */
  public void setQueryTimeout(final int queryTimeout) {
    this.queryTimeout = queryTimeout;
  }

  @Override
  public List<R> perform(final Connection conn) throws Exception {
    final Statement stmt = conn.createStatement(resultSetType,
        resultSetConcurrency, resultSetHoldability);
    ResultSet rs = null;
    try {
      stmt.setFetchSize(fetchSize);
      stmt.setMaxFieldSize(maxFieldSize);
      stmt.setQueryTimeout(queryTimeout);
      rs = stmt.executeQuery(sql);
      final List<R> result = new ArrayList<>();
      int rowNum = 0;
      while (rs.next()) {
        ++rowNum;
        final R obj = rowMapper.mapRow(rs, rowNum);
        result.add(obj);
      }
      return result;
    } finally {
      JdbcUtils.closeQuietly(rs);
      JdbcUtils.closeQuietly(stmt);
    }
  }
}
