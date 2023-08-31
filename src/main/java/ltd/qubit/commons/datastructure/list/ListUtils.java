////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 提供{@link List}相关的辅助函数。
 *
 * @author Haixing Hu
 */
public class ListUtils {

  /**
   * 对列表中元素去重，此操作不改变列表中元素的顺序。
   *
   * @param <T>
   *     列表中元素的类型。
   * @param list
   *     输入的列表，可以为{@code null}.
   * @return 去重后的列表，若{@code list}是{@code null}则返回{@code null}。
   */
  public static <T> List<T> unique(@Nullable final List<T> list) {
    if (list == null) {
      return null;
    }
    final ArrayList<T> result = new ArrayList<>();
    final Set<T> exist = new HashSet<>();
    for (final T value : list) {
      if (!exist.contains(value)) {
        result.add(value);
        exist.add(value);
      }
    }
    return result;
  }
}
