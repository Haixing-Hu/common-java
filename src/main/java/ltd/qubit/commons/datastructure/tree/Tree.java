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

/**
 * The interface of the tree structure.
 *
 * @param <KEY>
 *     the type of the keys of tree nodes.
 * @param <VALUE>
 *     the type of the values of tree nodes.
 * @author Haixing Hu
 */
public interface Tree<KEY, VALUE> {

  /**
   * Tests whether this tree node is a leaf, i.e., has no children.
   *
   * @return true if this tree node is a leaf, false otherwise.
   */
  boolean isLeaf();

  /**
   * Gets the key of this tree node.
   *
   * @return the key of this tree node.
   */
  KEY getKey();

  /**
   * Gets the value of this tree node.
   *
   * @return the value of this tree node.
   */
  VALUE getValue();

  /**
   * Sets the value of this tree node.
   *
   * @param value
   *     the new value to be set.
   */
  void setValue(VALUE value);

  /**
   * Tests whether the tree node contains a child of the specified key.
   *
   * @param key
   *     a specified key, which could be null.
   * @return true if the tree node contains a child of the specified key, false
   *     otherwise.
   */
  boolean containsChild(KEY key);

  /**
   * Gets the amount of children of this tree node.
   *
   * @return the amount of children of this tree node.
   */
  int getChildrenCount();

  /**
   * Gets the collection of children node of this tree node.
   *
   * @return the collection of children node of this tree node.
   */
  Collection<Tree<KEY, VALUE>> getChildren();

  /**
   * Gets the child of this tree node with the specified key.
   *
   * @param key
   *     a specified key, which could be null.
   * @return the child of this tree node with the specified key, or null if this
   *     tree node has no child with the specified key.
   */
  Tree<KEY, VALUE> getChild(KEY key);

  /**
   * Adds a child node to this tree node.
   *
   * @param child
   *     the child node to be added.
   * @return if this tree node already has a child with the same key as the new
   *     child node, this function returns the old node, and use the new child
   *     node to replace the old node; otherwise, this function add the new
   *     child node to the children of this tree node, and returns null.
   * @throws NullPointerException
   *     if child is null.
   * @throws IllegalArgumentException
   *     if this == child.
   */
  Tree<KEY, VALUE> addChild(Tree<KEY, VALUE> child);

  /**
   * Removes the child node of this tree node with the specified key.
   *
   * @param key
   *     a specified key, which could be null.
   * @return if this tree node has a child with the specified key, this function
   *     will remove that child node from the children of this node, and returns
   *     the removed child node; otherwise, this function returns null.
   */
  Tree<KEY, VALUE> removeChild(KEY key);

  /**
   * Removes all the children node of this tree node.
   *
   * @return the total number of children nodes removed from this tree node.
   */
  int clearChildren();

  /**
   * Tests whether this node is valid. A node is valid if and only if there is
   * NO cycle NOR bridge between the descendants of the node.
   *
   * @return true if this node is valid, false otherwise.
   */
  boolean isValid();

}
