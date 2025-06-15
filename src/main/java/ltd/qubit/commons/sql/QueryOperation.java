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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.sql.ResultSet.CLOSE_CURSORS_AT_COMMIT;
import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_FORWARD_ONLY;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 执行简单SQL查询的JDBC操作。
 *
 * @author 胡海星
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

  /**
   * 构造一个查询操作。
   *
   * @param sql
   *     SQL查询语句。
   * @param rowMapper
   *     行映射器。
   */
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
   * 获取此查询操作的SQL语句。
   *
   * @return
   *     此查询操作的SQL语句。
   */
  public String getSql() {
    return sql;
  }

  /**
   * 设置此查询操作的SQL语句。
   *
   * @param sql
   *     新的SQL语句。
   */
  public void setSql(final String sql) {
    this.sql = requireNonNull("sql", sql);
  }

  /**
   * 获取此查询操作的行映射器。
   *
   * @return
   *     此查询操作的行映射器。
   */
  public RowMapper<R> getRowMapper() {
    return rowMapper;
  }

  /**
   * 设置此查询操作的行映射器。
   *
   * @param rowMapper
   *     新的行映射器。
   */
  public void setRowMapper(final RowMapper<R> rowMapper) {
    this.rowMapper = requireNonNull("rowMapper", rowMapper);
  }

  /**
   * 获取此{@link QueryOperation}对象生成的ResultSet对象的结果集类型。
   *
   * @return
   *     ResultSet.TYPE_FORWARD_ONLY、ResultSet.TYPE_SCROLL_INSENSITIVE
   *     或ResultSet.TYPE_SCROLL_SENSITIVE之一。
   */
  public int getResultSetType() {
    return resultSetType;
  }

  /**
   * 设置此{@link QueryOperation}对象生成的ResultSet对象的结果集类型。
   *
   * @param resultSetType
   *     ResultSet.TYPE_FORWARD_ONLY、ResultSet.TYPE_SCROLL_INSENSITIVE
   *     或ResultSet.TYPE_SCROLL_SENSITIVE之一。
   */
  public void setResultSetType(final int resultSetType) {
    this.resultSetType = resultSetType;
  }

  /**
   * 获取此{@link QueryOperation}对象生成的ResultSet对象的结果集并发性。
   *
   * @return
   *     ResultSet.CONCUR_READ_ONLY或ResultSet.CONCUR_UPDATABLE。
   */
  public int getResultSetConcurrency() {
    return resultSetConcurrency;
  }

  /**
   * 设置此{@link QueryOperation}对象生成的ResultSet对象的结果集并发性。
   *
   * @param resultSetConcurrency
   *     ResultSet.CONCUR_READ_ONLY或ResultSet.CONCUR_UPDATABLE。
   */
  public void setResultSetConcurrency(final int resultSetConcurrency) {
    this.resultSetConcurrency = resultSetConcurrency;
  }

  /**
   * 获取此{@link QueryOperation}对象创建的ResultSet对象的当前可保持性。
   *
   * @return
   *     可保持性，ResultSet.HOLD_CURSORS_OVER_COMMIT或
   *     ResultSet.CLOSE_CURSORS_AT_COMMIT之一。
   */
  public int getResultSetHoldability() {
    return resultSetHoldability;
  }

  /**
   * 设置此{@link QueryOperation}对象创建的ResultSet对象的可保持性。
   *
   * @param resultSetHoldability
   *     可保持性，ResultSet.HOLD_CURSORS_OVER_COMMIT或
   *     ResultSet.CLOSE_CURSORS_AT_COMMIT之一。
   */
  public void setResultSetHoldability(final int resultSetHoldability) {
    this.resultSetHoldability = resultSetHoldability;
  }

  /**
   * 获取此{@link QueryOperation}对象创建的ResultSet对象的获取大小。
   *
   * <p>获取大小为JDBC驱动程序提供一个提示，即当此{@link QueryOperation}
   * 生成的ResultSet对象需要更多行时，应该从数据库中获取的行数。
   * 如果指定的值为零，则忽略该提示。默认值为零。
   *
   * @return
   *     此{@link QueryOperation}对象创建的ResultSet对象的获取大小。
   */
  public int getFetchSize() {
    return fetchSize;
  }

  /**
   * 设置此{@link QueryOperation}对象创建的ResultSet对象的获取大小。
   *
   * <p>获取大小为JDBC驱动程序提供一个提示，即当此{@link QueryOperation}
   * 生成的ResultSet对象需要更多行时，应该从数据库中获取的行数。
   * 如果指定的值为零，则忽略该提示。默认值为零。
   *
   * @param fetchSize
   *     此{@link QueryOperation}对象创建的ResultSet对象的获取大小。
   */
  public void setFetchSize(final int fetchSize) {
    this.fetchSize = fetchSize;
  }

  /**
   * 获取此{@link QueryOperation}对象生成的ResultSet对象中字符和二进制列值
   * 可以返回的最大字节数限制。
   *
   * <p>此限制仅适用于BINARY、VARBINARY、LONGVARBINARY、CHAR、VARCHAR、
   * NCHAR、NVARCHAR、LONGNVARCHAR和LONGVARCHAR字段。如果超出限制，
   * 多余的数据将被静默丢弃。为了最大的可移植性，请使用大于256的值。
   *
   * @return
   *     列大小限制（以字节为单位）；零表示没有限制。
   */
  public int getMaxFieldSize() {
    return maxFieldSize;
  }

  /**
   * 设置此{@link QueryOperation}对象生成的ResultSet对象中字符和二进制列值
   * 可以返回的最大字节数限制。
   *
   * <p>此限制仅适用于BINARY、VARBINARY、LONGVARBINARY、CHAR、VARCHAR、
   * NCHAR、NVARCHAR、LONGNVARCHAR和LONGVARCHAR字段。如果超出限制，
   * 多余的数据将被静默丢弃。为了最大的可移植性，请使用大于256的值。
   *
   * @param maxFieldSize
   *     新的列大小限制（以字节为单位）；零表示没有限制。
   */
  public void setMaxFieldSize(final int maxFieldSize) {
    this.maxFieldSize = maxFieldSize;
  }

  /**
   * 获取驱动程序等待{@link Statement}对象执行的秒数。
   *
   * @return
   *     当前查询超时限制（以秒为单位）；零表示没有限制。
   */
  public int getQueryTimeout() {
    return queryTimeout;
  }

  /**
   * 设置驱动程序等待{@link Statement}对象执行的秒数。
   *
   * @param queryTimeout
   *     新的查询超时限制（以秒为单位）；零表示没有限制。
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