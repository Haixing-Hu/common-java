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
 * 此接口表示一个带有排序请求参数的对象。
 *
 * @author 胡海星
 */
public interface WithSortRequestParams {
  /**
   * 获取排序字段。
   *
   * @return
   *     排序字段，以 lowercase camel 命名风格表示。如果为{@code null}，则表示不排序。
   */
  @Nullable
  String getSortField();

  /**
   * 获取排序顺序。
   *
   * @return
   *     排序顺序。如果为{@code null}，则表示不排序。
   */
  @Nullable
  SortOrder getSortOrder();

  /**
   * 获取空值排序选项。
   *
   * @return
   *     空值排序选项。如果为{@code null}，则使用默认值{@link NullSortOption#NULL_FIRST}。
   */
  @Nullable
  NullSortOption getNullSortOption();
}
