////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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
 * An abstract tree structure.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public abstract class AbstractTree<KEY, VALUE> implements Tree<KEY, VALUE> {

  protected KEY key;
  protected VALUE value;

  protected AbstractTree() {
    key = null;
    value = null;
  }

  protected AbstractTree(final KEY key, final VALUE value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public boolean isLeaf() {
    return getChildrenCount() == 0;
  }

  @Override
  public KEY getKey() {
    return key;
  }

  @Override
  public VALUE getValue() {
    return value;
  }

  @Override
  public void setValue(final VALUE value) {
    this.value = value;
  }

  @Override
  public boolean containsChild(final KEY key) {
    return getChild(key) != null;
  }

  @Override
  public abstract int getChildrenCount();

  @Override
  public abstract Collection<Tree<KEY, VALUE>> getChildren();

  @Override
  public abstract Tree<KEY, VALUE> getChild(KEY key);

  @Override
  public abstract Tree<KEY, VALUE> addChild(Tree<KEY, VALUE> child);

  @Override
  public abstract Tree<KEY, VALUE> removeChild(KEY key);

  @Override
  public abstract int clearChildren();

  @Override
  public boolean isValid() {
    final Set<KEY> visited = new HashSet<>();
    return checkValidity(visited);
  }

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
