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
import java.util.HashSet;
import java.util.Set;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * 抽象树结构。
 *
 * @param <KEY>
 *     树节点键的类型。
 * @param <VALUE>
 *     树节点值的类型。
 * @author 胡海星
 */
@NotThreadSafe
public abstract class AbstractTree<KEY, VALUE> implements Tree<KEY, VALUE> {

  /**
   * 树节点的键。
   */
  protected KEY key;
  
  /**
   * 树节点的值。
   */
  protected VALUE value;

  /**
   * 构造一个空的抽象树。
   */
  protected AbstractTree() {
    key = null;
    value = null;
  }

  /**
   * 构造一个具有指定键和值的抽象树。
   *
   * @param key
   *     树节点的键。
   * @param value
   *     树节点的值。
   */
  protected AbstractTree(final KEY key, final VALUE value) {
    this.key = key;
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isLeaf() {
    return getChildrenCount() == 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public KEY getKey() {
    return key;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getValue() {
    return value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(final VALUE value) {
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsChild(final KEY key) {
    return getChild(key) != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract int getChildrenCount();

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract Collection<Tree<KEY, VALUE>> getChildren();

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract Tree<KEY, VALUE> getChild(KEY key);

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract Tree<KEY, VALUE> addChild(Tree<KEY, VALUE> child);

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract Tree<KEY, VALUE> removeChild(KEY key);

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract int clearChildren();

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValid() {
    final Set<KEY> visited = new HashSet<>();
    return checkValidity(visited);
  }

  /**
   * 递归检查节点的有效性。
   *
   * @param visited
   *     已访问的节点键集合。
   * @return
   *     如果节点有效则返回 {@code true}，否则返回 {@code false}。
   */
  private boolean checkValidity(final Set<KEY> visited) {
    // Recursively check the validity of this node: a node is valid if and only if
    // there is no cycle nor bridge between the descendants of the node.
    // Note that the time complexity of this algorithm is O(n), where n is the
    // size of the subtree of this node.
    if (visited.contains(key)) {
      return false;
    }
    visited.add(key);
    final Collection<Tree<KEY, VALUE>> children = getChildren();
    if ((children == null) || children.isEmpty()) {
      return true;
    }
    for (final Tree<KEY, VALUE> child : children) {
      if (!((AbstractTree<KEY, VALUE>) child).checkValidity(visited)) {
        return false;
      }
    }
    return true;
  }
}