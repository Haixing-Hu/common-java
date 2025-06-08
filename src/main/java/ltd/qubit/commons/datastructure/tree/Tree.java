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

/**
 * 树形结构的接口。
 *
 * @param <KEY>
 *     树节点的键的类型。
 * @param <VALUE>
 *     树节点的值的类型。
 * @author 胡海星
 */
public interface Tree<KEY, VALUE> {

  /**
   * 测试此树节点是否为叶子节点，即没有子节点。
   *
   * @return 如果此树节点是叶子节点，则为 true；否则为 false。
   */
  boolean isLeaf();

  /**
   * 获取此树节点的键。
   *
   * @return 此树节点的键。
   */
  KEY getKey();

  /**
   * 获取此树节点的值。
   *
   * @return 此树节点的值。
   */
  VALUE getValue();

  /**
   * 设置此树节点的值。
   *
   * @param value
   *     要设置的新值。
   */
  void setValue(VALUE value);

  /**
   * 测试树节点是否包含具有指定键的子节点。
   *
   * @param key
   *     指定的键，可以为 null。
   * @return 如果树节点包含具有指定键的子节点，则为 true；否则为 false。
   */
  boolean containsChild(KEY key);

  /**
   * 获取此树节点的子节点数。
   *
   * @return 此树节点的子节点数。
   */
  int getChildrenCount();

  /**
   * 获取此树节点的子节点集合。
   *
   * @return 此树节点的子节点集合。
   */
  Collection<Tree<KEY, VALUE>> getChildren();

  /**
   * 获取具有指定键的此树节点的子节点。
   *
   * @param key
   *     指定的键，可以为 null。
   * @return 具有指定键的此树节点的子节点，如果此树节点没有具有指定键的子节点，则为 null。
   */
  Tree<KEY, VALUE> getChild(KEY key);

  /**
   * 将一个子节点添加到此树节点。
   *
   * @param child
   *     要添加的子节点。
   * @return 如果此树节点已经有一个与新子节点具有相同键的子节点，
   *     则此函数返回旧节点，并使用新子节点替换旧节点；
   *     否则，此函数将新子节点添加到此树节点的子节点中，并返回 null。
   * @throws NullPointerException
   *     如果 child 为 null。
   * @throws IllegalArgumentException
   *     如果 this == child。
   */
  Tree<KEY, VALUE> addChild(Tree<KEY, VALUE> child);

  /**
   * 移除具有指定键的此树节点的子节点。
   *
   * @param key
   *     指定的键，可以为 null。
   * @return 如果此树节点具有指定键的子节点，则此函数将从该节点的子节点中移除该子节点，
   *     并返回被移除的子节点；否则，此函数返回 null。
   */
  Tree<KEY, VALUE> removeChild(KEY key);

  /**
   * 移除此树节点的所有子节点。
   *
   * @return 从此树节点移除的子节点总数。
   */
  int clearChildren();

  /**
   * 测试此节点是否有效。
   *
   * <p>一个节点是有效的，当且仅当该节点的后代中没有循环或桥接。
   *
   * @return 如果此节点有效，则为 true；否则为 false。
   */
  boolean isValid();

}