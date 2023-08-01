////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import javax.annotation.Nullable;

/**
 * 此枚举表示排序时对NULL值得处理方式。
 *
 * @author 胡海星
 */
public enum NullSortOption {

  /**
   * 不管排序方式是升序还是降序，NULL值永远在排序结果的最前面。
   */
  NULL_FIRST,

  /**
   * 不管排序方式是升序还是降序，NULL值永远在排序结果的最后面。
   */
  NULL_LAST,

  /**
   * NULL值被当做是最小的值，在结果中的具体位置取决于排序方式是升序还是降序。
   */
  NULL_SMALLEST,

  /**
   * NULL值被当做是最大的值，在结果中的具体位置取决于排序方式是升序还是降序。
   */
  NULL_LARGEST;

  /**
   * 根据此对象表示的比较策略，比较两个对象，其中至少一个是{@code null}。
   *
   * @param lhs
   *     左边的对象。
   * @param rhs
   *     右边的对象。
   * @param order
   *     指定排序是升序还是降序。
   * @return
   *     按照此对象的比较策略，如果左边的对象小于/等于/大于右边的对象，分别返回-1, 0, 1。
   * @throws IllegalArgumentException
   *     如果{@code lhs != null && rhs != null}。
   */
  public int compare(@Nullable final Object lhs, @Nullable final Object rhs,
      final SortOrder order) {
    if (lhs == null) {
      if (rhs == null) {
        return 0;
      } else {  // lhs == null && rhs != null
        switch (this) {
          case NULL_FIRST:
            return -1;
          case NULL_LAST:
            return +1;
          case NULL_SMALLEST:
            return (order == SortOrder.ASC ? -1 : +1);
          case NULL_LARGEST:
            return (order == SortOrder.ASC ? +1 : -1);
          default:
            throw new IllegalArgumentException("Unsupported SortNullOption: " + name());
        }
      }
    } else if (rhs == null) { // lhs != null && rhs == null
      switch (this) {
        case NULL_FIRST:
          return +1;
        case NULL_LAST:
          return -1;
        case NULL_SMALLEST:
          return (order == SortOrder.ASC ? +1 : -1);
        case NULL_LARGEST:
          return (order == SortOrder.ASC ? -1 : +1);
        default:
          throw new IllegalArgumentException("Unsupported SortNullOption: " + name());
      }
    } else {
      throw new IllegalArgumentException("Either lhs or rhs should be null.");
    }
  }
}
