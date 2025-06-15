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
 * 用于逐行处理{@link ResultSet}行的对象的接口。
 *
 * <p>此接口的实现执行处理每一行的实际工作，但不需要担心异常处理。
 *
 * @author 胡海星
 * @see RowMapper
 */
public interface RowProcessor {

  /**
   * 实现必须实现此方法以处理{@link ResultSet}中数据的每一行。
   *
   * <p>此方法不应在{@link ResultSet}上调用{@link ResultSet#next()}；
   * 它只是提取当前行的值。
   *
   * <p>实现选择做什么完全取决于它：一个简单的实现可能只是计数行，
   * 而另一个实现可能构建XML文档。
   *
   * @param rs
   *     要处理的ResultSet（预先初始化为当前行）。
   * @param rowNumber
   *     当前行的行号。注意行号从1开始计数。
   * @throws SQLException
   *     如果在获取列值时遇到SQLException（即不需要捕获SQLException）
   */
  void processRow(ResultSet rs, int rowNumber) throws SQLException;
}