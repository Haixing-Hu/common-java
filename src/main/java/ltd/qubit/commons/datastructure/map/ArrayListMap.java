////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * 使用数组列表存储键和值的映射实现。
 *
 * @author 胡海星
 */
@NotThreadSafe
public class ArrayListMap<KEY, VALUE> extends AbstractListMap<KEY, VALUE> {

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<AbstractListMap.Entry<KEY, VALUE>> makeList() {
    return new ArrayList<>();
  }

  /**
   * 构造一个新的 {@link ArrayListMap}。
   */
  public ArrayListMap() {
  }

  /**
   * 构造一个新的 {@link ArrayListMap}，并使用给定的映射初始化。
   * 
   * @param map
   *     要初始化的映射
   */
  public ArrayListMap(final Map<? extends KEY, ? extends VALUE> map) {
    this.putAll(map);
  }

}