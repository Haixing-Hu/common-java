////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.mapper;

import java.util.List;
import java.util.Map;

/**
 * 此接口表示将实体对象序列化为表格数据行的映射器。
 *
 * @param <T>
 *     被映射的实体的类型。
 */
public interface RowMapper<T> {
  /**
   * 获取被映射的实体的类的类对象。
   *
   * @return
   *     被映射的实体的类的类对象。
   */
  Class<?> getType();

  /**
   * 判定指定的列是否延续上一行的数据。
   * <p>
   * 如果指定的列延续上一行的数据，则当当前行的该列数据为空时，将使用上一行该列的数据，
   * 并将当前行该列的数据设置为上一行该列的数据。
   *
   * @param header
   *     指定列的列头名称。
   * @return
   *     指定的列是否延续上一行的数据。
   */
  boolean isContinueLastRow(final String header);

  /**
   * 判定是否把第一行数据当做是列头。
   *
   * @return
   *     是否把第一行数据当做是列头。
   */
  boolean isFirstRowAsHeaders();

  /**
   * 返回实体对象所对应的表格的列头名称列表。
   *
   * @return
   *     实体对象所对应的表格的列头名称列表。
   */
  List<String> getHeaders();

  /**
   * 将实体对象映射为表格的一行数据。
   * <p>
   * 表格的每行数据表示为一个映射，其中键是列头名称，值是该列对应的数据。
   *
   * @param entity
   *     要映射的实体对象。
   * @return
   *     映射的表格的一行数据。
   */
  Map<String, String> toRow(T entity);

  /**
   * 将表格的一行数据映射为实体对象。
   * <p>
   * 表格的每行数据表示为一个映射，其中键是列头名称，值是该列对应的数据。
   *
   * @param lastRow
   *     上一行数据。若为{@code null}则表示当前行是第一行。有时候我们需要根据上一行某些列
   *     的数据来填充当前行对应列的数据，所以提供此参数。
   * @param currentRow
   *     当前行数据。
   * @return
   *     映射的实体对象。
   */
  T fromRow(Map<String, String> lastRow, Map<String, String> currentRow);

  /**
   * 对列头名称做转换。
   *
   * @param index
   *     列头的编号，从0开始。
   * @param header
   *     列头的名称。
   * @return
   *     转换后列头的名称。
   */
  default String transformColumnHeader(final int index, final String header) {
    return header;
  }
}
