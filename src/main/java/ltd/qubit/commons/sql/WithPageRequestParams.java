////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import javax.annotation.Nullable;

/**
 * 此接口表示一个带有分页请求参数的对象。
 *
 * @author 胡海星
 */
public interface WithPageRequestParams {
  /**
   * 获取分页索引。
   *
   * @return
   *     分页索引, 从0开始编号。如果为{@code null}，则表示使用默认值0。
   */
  @Nullable
  Integer getPageIndex();

  /**
   * 获取分页大小。
   *
   * @return
   *     分页大小。如果为{@code null}，则表示使用默认值{@value PageRequest#DEFAULT_PAGE_SIZE}。
   */
  @Nullable
  Integer getPageSize();

  /**
   * 是否请求所有数据。
   * <p>
   * 若为{@code true}，则忽略{@code pageIndex}和{@code pageSize}，
   *
   * @return
   *     {@code true}表示请求所有数据；{@code false}表示请求分页数据。
   */
  boolean isRequestAll();
}
