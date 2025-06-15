////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用于将{@link ResultSet}的每一行映射为对象的接口。
 *
 * <p>此接口的实现执行将每一行映射为结果对象的实际工作，但不需要担心异常处理。
 *
 * @param <T>
 *     从行映射的对象类型。
 * @author 胡海星
 */
@FunctionalInterface
public interface RowMapper<T> {

  /**
   * 实现必须实现此方法以映射{@link ResultSet}中数据的每一行。
   *
   * <p>此方法不应在{@link ResultSet}上调用{@link ResultSet#next()}；
   * 它只是映射当前行的值。
   *
   * @param rs
   *     要映射的ResultSet（预先初始化为当前行）。
   * @param rowNumber
   *     当前行的行号。注意行号从1开始计数。
   * @return 当前行的结果对象。
   * @throws SQLException
   *     如果在获取列值时遇到SQLException（即不需要捕获SQLException）
   */
  T mapRow(ResultSet rs, int rowNumber) throws SQLException;

}