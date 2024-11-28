////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * The transformer of a list which apply a specified transformer to each element
 * of the list.
 *
 * @param <T>
 *        the type of elements in the list.
 * @author Haixing Hu
 */
public class ListTransformer<T> extends AbstractTransformer<List<T>> {

  private Transformer<T>  elementTransformer;
  private boolean ignoreNull = false;

  public final Transformer<T> getElementTransformer() {
    return elementTransformer;
  }

  public final ListTransformer<T> setElementTransformer(
      final Transformer<T> elementTransformer) {
    this.elementTransformer = elementTransformer;
    return this;
  }

  public final boolean isIgnoreNull() {
    return ignoreNull;
  }

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
