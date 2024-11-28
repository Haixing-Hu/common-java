////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.sql.SQLSyntaxErrorException;

import ltd.qubit.commons.util.filter.Filter;

/**
 * 此接口表示执行数据库查询时过滤实体的条件。
 */
public interface Criterion<T> extends Filter<T> {

  /**
   * 返回此条件所过滤的实体的类。
   *
   * @return
   *     此条件所过滤的实体的类。
   */
  Class<T> getEntityClass();

  /**
   * 判定此条件相对于指定的实体类是否合法。
   *
   * @return
   *     如果此条件相对于指定的实体类合法，则返回{@code true}；否则返回{@code false}。
   */
  boolean isValid();

  /**
   * 将此条件转化为对应的动态SQL语句。
   *
   * @return
   *     此条件所对应的动态SQL语句。
   * @throws SQLSyntaxErrorException
   *     如果此条件无法转化为合法的SQL语句。
   */
  String toSql() throws SQLSyntaxErrorException;

}
