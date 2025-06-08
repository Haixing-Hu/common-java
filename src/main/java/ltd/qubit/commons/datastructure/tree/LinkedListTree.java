////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.tree;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * 使用链表存储子节点的树实现。
 *
 * @param <KEY>
 *     树节点键的类型。
 * @param <VALUE>
 *     树节点值的类型。
 * @author 胡海星
 */
@NotThreadSafe
public class LinkedListTree<KEY, VALUE> extends AbstractListTree<KEY, VALUE> {

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<Tree<KEY, VALUE>> makeTreeList() {
    return new LinkedList<>();
  }

  /**
   * 构造一个具有指定键和值的链表树。
   *
   * @param key
   *     树节点的键。
   * @param value
   *     树节点的值。
   */
  public LinkedListTree(final KEY key, final VALUE value) {
    super(key, value);
  }

}