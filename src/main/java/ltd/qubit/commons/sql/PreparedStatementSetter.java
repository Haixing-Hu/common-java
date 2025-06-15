////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 此接口在 {@link PreparedStatement} 上设置值，用于使用相同 SQL 在批处理中的多次更新。
 *
 * <p>实现类负责设置任何必要的参数。带有占位符的 SQL 已经被提供。实现类不需要关心它们尝试的操作可能抛出的 {@link SQLException}。
 *
 * @author 胡海星
 */
public interface PreparedStatementSetter {

  /**
   * 在给定的 {@link PreparedStatement} 上设置参数值。
   *
   * @param ps
   *     要调用设置器方法的 {@link PreparedStatement}。
   * @throws SQLException
   *     如果遇到 SQLException（即不需要捕获 SQLException）
   */
  void setValues(PreparedStatement ps) throws SQLException;

}