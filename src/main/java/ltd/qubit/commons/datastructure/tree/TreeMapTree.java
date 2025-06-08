////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.tree;

import java.util.Map;
import java.util.TreeMap;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * 使用树映射存储子节点的树实现。
 *
 * @param <KEY>
 *     树节点键的类型。
 * @param <VALUE>
 *     树节点值的类型。
 * @author 胡海星
 */
@NotThreadSafe
public class TreeMapTree<KEY, VALUE> extends AbstractMapTree<KEY, VALUE> {

  /**
   * {@inheritDoc}
   */
  @Override
  protected Map<KEY, Tree<KEY, VALUE>> makeTreeMap() {
    return new TreeMap<>();
  }

  /**
   * 构造一个具有指定键和值的树映射树。
   *
   * @param key
   *     树节点的键。
   * @param value
   *     树节点的值。
   */
  public TreeMapTree(final KEY key, final VALUE value) {
    super(key, value);
  }

}