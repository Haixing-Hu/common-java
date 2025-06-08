////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表的转换器,它将指定的转换器应用于列表的每个元素。
 *
 * @param <T>
 *        列表中元素的类型。
 * @author 胡海星
 */
public class ListTransformer<T> extends AbstractTransformer<List<T>> {

  private Transformer<T>  elementTransformer;
  private boolean ignoreNull = false;

  /**
   * 获取列表中元素的转换器。
   *
   * @return
   *     列表中元素的转换器。
   */
  public final Transformer<T> getElementTransformer() {
    return elementTransformer;
  }

  /**
   * 设置列表中元素的转换器。
   *
   * @param elementTransformer
   *     新的列表中元素的转换器。
   * @return
   *     返回当前对象。
   */
  public final ListTransformer<T> setElementTransformer(
      final Transformer<T> elementTransformer) {
    this.elementTransformer = elementTransformer;
    return this;
  }

  /**
   * 返回是否忽略{@code null}元素。
   *
   * @return
   *     如果忽略{@code null}元素，则返回{@code true}；否则返回{@code false}。
   */
  public final boolean isIgnoreNull() {
    return ignoreNull;
  }

  /**
   * 设置是否忽略{@code null}元素。
   *
   * @param ignoreNull
   *     指定是否忽略{@code null}元素。
   * @return
   *     返回当前对象。
   */
  public final ListTransformer<T> setIgnoreNull(final boolean ignoreNull) {
    this.ignoreNull = ignoreNull;
    return this;
  }

  @Override
  public List<T> transform(final List<T> list) {
    if (list == null) {
      return null;
    }
    final List<T> result = new ArrayList<>();
    for (final T ele : list) {
      final T element = elementTransformer.transform(ele);
      if (ignoreNull && element == null) {
        continue;
      }
      result.add(element);
    }
    return result;
  }
}