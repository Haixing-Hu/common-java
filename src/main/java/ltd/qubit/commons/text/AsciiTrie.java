////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * 基于树的ASCII字符串前缀树。
 *
 * @author 胡海星
 */
@NotThreadSafe
public class AsciiTrie {

  /**
   * 前缀树的节点。
   */
  private static class Node {
    /**
     * 该节点的出现次数。
     */
    int occurrence;

    /**
     * 子节点数组。
     */
    Node[] children;

    /**
     * 构造一个新的节点。
     */
    Node() {
      occurrence = 0;
      children = new Node[Ascii.MAX];
    }

    /**
     * 压缩节点树，移除不必要的空节点。
     *
     * @return 如果当前节点可以被删除则返回 {@code true}，否则返回 {@code false}。
     */
    boolean compact() {
      boolean result = true;
      for (int i = 0; i < Ascii.MAX; ++i) {
        if (children[i] != null) {
          if (children[i].compact()) {
            children[i] = null; // delete children[i]
          } else {
            result = false;
          }
        }
      }
      return result & (occurrence == 0);
    }

    /**
     * 计算以此节点为根的子树中的节点总数。
     *
     * @return 节点总数。
     */
    int nodeCount() {
      int result = 1;
      if (children != null) {
        for (int i = 0; i < Ascii.MAX; ++i) {
          if (children[i] != null) {
            result += children[i].nodeCount();
          }
        }
      }
      return result;
    }
  }

  /**
   * 根节点。
   */
  private final Node root;

  /**
   * 前缀树中字符串的总数量。
   */
  private int size;

  /**
   * 是否忽略大小写。
   */
  private final boolean caseInsensitive;

  /**
   * 构造一个新的区分大小写的 {@link AsciiTrie} 对象。
   */
  public AsciiTrie() {
    root = new Node();
    size = 0;
    caseInsensitive = false;
  }

  /**
   * 构造一个新的 {@link AsciiTrie} 对象。
   *
   * @param caseInsensitive
   *          是否忽略大小写。
   */
  public AsciiTrie(final boolean caseInsensitive) {
    root = new Node();
    size = 0;
    this.caseInsensitive = caseInsensitive;
  }

  /**
   * 检查此前缀树是否忽略大小写。
   *
   * @return 如果忽略大小写则返回 {@code true}，否则返回 {@code false}。
   */
  public boolean isCaseInsensitive() {
    return caseInsensitive;
  }

  /**
   * 获取此前缀树中字符串的总数量。
   *
   * @return 字符串的总数量。
   */
  public int size() {
    return size;
  }

  /**
   * 检查此前缀树是否为空。
   *
   * @return 如果为空则返回 {@code true}，否则返回 {@code false}。
   */
  public boolean isEmpty() {
    return (size == 0);
  }

  /**
   * 获取此前缀树中节点的总数量。
   *
   * @return 节点的总数量。
   */
  public int nodeCount() {
    return root.nodeCount();
  }

  /**
   * 清空此前缀树。
   */
  public void clear() {
    for (int i = 0; i < Ascii.MAX; ++i) {
      root.children[i] = null;
    }
    root.occurrence = 0;
    size = 0;
  }

  /**
   * 根据字符串获取对应的节点。
   *
   * @param str
   *          字符串。
   * @return 对应的节点，如果不存在则返回 {@code null}。
   */
  private Node getNode(final String str) {
    final int len = str.length();
    Node node = root;
    for (int i = 0; i < len; ++i) {
      char ch = str.charAt(i);
      if (! Ascii.isAscii(ch)) {
        return null;
      }
      if (caseInsensitive) {
        ch = Ascii.toLowerCase(ch);
      }
      node = node.children[ch];
      if (node == null) {
        return null;
      }
    }
    return node;
  }

  /**
   * 检查是否包含指定的字符串。
   *
   * @param str
   *          要检查的字符串。
   * @return 如果包含则返回 {@code true}，否则返回 {@code false}。
   */
  public boolean contains(final String str) {
    final Node node = getNode(str);
    return ((node != null) && (node.occurrence > 0));
  }

  /**
   * 检查是否包含指定前缀的字符串。
   *
   * @param prefix
   *          要检查的前缀。
   * @return 如果包含指定前缀的字符串则返回 {@code true}，否则返回 {@code false}。
   */
  public boolean containsPrefix(final String prefix) {
    final Node node = getNode(prefix);
    return (node != null);
  }

  /**
   * 检查是否包含指定字符串的前缀。
   *
   * @param str
   *          要检查的字符串。
   * @return 如果包含指定字符串的前缀则返回 {@code true}，否则返回 {@code false}。
   */
  public boolean containsPrefixOf(final String str) {
    if (str == null) {
      return false;
    }
    final int len = str.length();
    Node node = root;
    for (int i = 0; i < len; ++i) {
      char ch = str.charAt(i);
      if (! Ascii.isAscii(ch)) {
        return false;
      }
      if (caseInsensitive) {
        ch = Ascii.toLowerCase(ch);
      }
      node = node.children[ch];
      if (node == null) {
        return false;
      }
      if (node.occurrence > 0) {
        return true;
      }
    }
    return false;
  }

  /**
   * 获取指定字符串的出现次数。
   *
   * @param str
   *          要查询的字符串。
   * @return 出现次数，如果不存在则返回0。
   */
  public int count(final String str) {
    final Node node = getNode(str);
    if (node == null) {
      return 0;
    } else {
      return node.occurrence;
    }
  }

  /**
   * 根据字符串获取或创建对应的节点。
   *
   * @param str
   *          字符串。
   * @return 对应的节点，如果字符串包含非ASCII字符则返回 {@code null}。
   */
  private Node getOrAddNode(final String str) {
    final int len = str.length();
    Node node = root;
    for (int i = 0; i < len; ++i) {
      char ch = str.charAt(i);
      if (! Ascii.isAscii(ch)) {
        return null;
      }
      if (caseInsensitive) {
        ch = Ascii.toLowerCase(ch);
      }
      if (node.children[ch] == null) {
        node.children[ch] = new Node();
      }
      node = node.children[ch];
    }
    return node;
  }

  /**
   * 添加一个字符串到前缀树中。
   *
   * @param str
   *          要添加的字符串。
   * @return 如果成功添加则返回 {@code true}，如果字符串包含非ASCII字符则返回 {@code false}。
   */
  public boolean add(final String str) {
    final Node node = getOrAddNode(str);
    if (node == null) {
      return false;
    } else {
      ++node.occurrence;
      ++size;
      return true;
    }
  }

  /**
   * 添加指定次数的字符串到前缀树中。
   *
   * @param str
   *          要添加的字符串。
   * @param occurence
   *          添加的次数，必须大于等于0。
   * @return 实际添加的次数。
   * @throws IllegalArgumentException
   *          如果 {@code occurence} 小于0。
   */
  public int add(final String str, final int occurence) {
    if (occurence < 0) {
      throw new IllegalArgumentException();
    }
    if (occurence == 0) {
      return 0;
    }
    final Node node = getOrAddNode(str);
    if (node == null) {
      return 0;
    } else {
      node.occurrence += occurence;
      size += occurence;
      return occurence;
    }
  }

  /**
   * 从前缀树中移除一个字符串的一次出现。
   *
   * @param str
   *          要移除的字符串。
   * @return 如果成功移除则返回 {@code true}，否则返回 {@code false}。
   */
  public boolean remove(final String str) {
    if (size == 0) {
      return false;
    }
    final Node node = getNode(str);
    if (node == null) {
      return false;
    }
    if (node.occurrence == 0) {
      return false;
    } else {
      --node.occurrence;
      --size;
      return true;
    }
  }

  /**
   * 从前缀树中移除指定次数的字符串出现。
   *
   * @param str
   *          要移除的字符串。
   * @param occurrence
   *          要移除的次数，必须大于等于0。
   * @return 实际移除的次数。
   * @throws IllegalArgumentException
   *          如果 {@code occurrence} 小于0。
   */
  public int remove(final String str, final int occurrence) {
    if (occurrence < 0) {
      throw new IllegalArgumentException();
    }
    if (occurrence == 0) {
      return 0;
    }
    if (size == 0) {
      return 0;
    }
    final Node node = getNode(str);
    if (node == null) {
      return 0;
    }
    final int theOccurrence = Math.min(occurrence, node.occurrence);
    node.occurrence -= theOccurrence;
    size -= theOccurrence;
    return theOccurrence;
  }

  /**
   * 从前缀树中移除指定字符串的所有出现。
   *
   * @param str
   *          要移除的字符串。
   * @return 实际移除的次数。
   */
  public int removeAll(final String str) {
    if (size == 0) {
      return 0;
    }
    final Node node = getNode(str);
    if (node == null) {
      return 0;
    }
    final int occurrence = node.occurrence;
    node.occurrence = 0;
    size -= occurrence;
    return occurrence;
  }

  /**
   * 压缩前缀树，移除不必要的空节点。
   */
  public void compact() {
    root.compact();
  }
}