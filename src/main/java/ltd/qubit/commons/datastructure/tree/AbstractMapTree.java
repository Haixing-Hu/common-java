////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.tree;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

import static ltd.qubit.commons.lang.Argument.requireNonNull;
import static ltd.qubit.commons.lang.Argument.requireNonSame;

/**
 * 使用抽象映射存储子节点的树实现。
 *
 * @param <KEY>
 *     树节点键的类型。
 * @param <VALUE>
 *     树节点值的类型。
 * @author 胡海星
 */
@NotThreadSafe
public abstract class AbstractMapTree<KEY, VALUE>
    extends AbstractTree<KEY, VALUE> {

  /**
   * 存储子节点的映射。
   */
  protected Map<KEY, Tree<KEY, VALUE>> children;

  /**
   * 创建树映射的抽象方法。
   *
   * @return
   *     新创建的树映射。
   */
  protected abstract Map<KEY, Tree<KEY, VALUE>> makeTreeMap();

  /**
   * 构造一个空的抽象映射树。
   */
  protected AbstractMapTree() {
    this.children = null;
  }

  /**
   * 构造一个具有指定键和值的抽象映射树。
   *
   * @param key
   *     树节点的键。
   * @param value
   *     树节点的值。
   */
  protected AbstractMapTree(final KEY key, final VALUE value) {
    super(key, value);
    this.children = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isLeaf() {
    return ((children == null) || children.isEmpty());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getChildrenCount() {
    return (children == null ? 0 : children.size());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<Tree<KEY, VALUE>> getChildren() {
    if (children == null) {
      return Collections.emptyList();
    } else {
      return Collections.unmodifiableCollection(children.values());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Tree<KEY, VALUE> getChild(final KEY key) {
    if (children == null) {
      return null;
    } else {
      return children.get(key);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Tree<KEY, VALUE> addChild(final Tree<KEY, VALUE> child) {
    requireNonNull("child", child);
    requireNonSame("this", this, "child", child);
    if (children == null) {
      children = makeTreeMap();
    }
    return children.put(child.getKey(), child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Tree<KEY, VALUE> removeChild(final KEY key) {
    if (children == null) {
      return null;
    }
    return children.remove(key);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int clearChildren() {
    if (children == null) {
      return 0;
    } else {
      final int result = children.size();
      children.clear();
      return result;
    }
  }
}