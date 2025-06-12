////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import java.lang.reflect.Array;
import java.util.Map;

import ltd.qubit.commons.lang.Argument;
import ltd.qubit.commons.util.expand.ExpansionPolicy;

/**
 * {@link TrieMap} 使用字典树实现了 {@link Map}。
 *
 * @param <VALUE> 映射的值类型
 * @author 胡海星
 */
public final class TrieMap<VALUE> {

  private static final int INITIAL_CHILDREN_CAPACITY    = 4;

  private final class Node {
    char   ch;
    VALUE  value;
    int    childrenCount;
    Node[] children;

    Node(final char ch, final VALUE value) {
      this.ch = ch;
      this.value = value;
      childrenCount = 0;
      children = null;
    }

    Node getChild(final char ch) {
      if (childrenCount == 0) {
        return null;
      }
      int min = 0;
      int max = childrenCount - 1;
      int mid = 0;
      // binary search the child
      while (min < max) {
        mid = (min + max) / 2;
        final Node child = children[mid];
        if (child.ch == ch) {
          return child;
        }
        if (child.ch < ch) {
          min = mid + 1;
        } else {
          max = mid - 1;
        }
      }
      if (min == max) {
        final Node child = children[min];
        if (child.ch == ch) {
          return child;
        }
      }
      return null;
    }

    @SuppressWarnings("unchecked")
    Node getOrAddChild(final char ch) {
      if (childrenCount == 0) {
        final Node newChild = new Node(ch, null);
        children = (Node[]) Array.newInstance(Node.class, INITIAL_CHILDREN_CAPACITY);
        children[0] = newChild;
        childrenCount = 1;
        return newChild;
      }
      int min = 0;
      int max = childrenCount - 1;
      int mid = 0;
      // binary search the child
      final ExpansionPolicy expansionPolicy = ExpansionPolicy.getDefault();
      while (min < max) {
        mid = (min + max) / 2;
        final Node child = children[mid];
        if (child.ch == ch) {
          return child;
        }
        if (child.ch < ch) {
          min = mid + 1;
        } else {
          max = mid - 1;
        }
      }
      final Node child = children[min];
      if (child.ch == ch) {
        return child;
      }
      final Node newChild = new Node(ch, null);
      // calculate the position where to insert the new child
      int pos = min;
      if (child.ch < ch) {
        ++pos;
      }
      // now insert the new child into the children array at pos
      if (childrenCount == children.length) {
        children = expansionPolicy.expand(children, childrenCount,
            childrenCount + 1, Node.class);
      }
      // move children after the pos
      assert (childrenCount < children.length);
      for (int i = childrenCount - 1; i >= pos; --i) {
        children[i + 1] = children[i];
      }
      children[pos] = newChild;
      ++childrenCount;
      return newChild;
    }

    @SuppressWarnings("unchecked")
    void compact() {
      if (childrenCount == 0) {
        return;
      }
      int nonEmpty = 0;
      for (int i = 0; i < childrenCount; ++i) {
        assert (children[i] != null);
        children[i].compact();
        if ((children[i].childrenCount == 0)
            && (children[i].value == null)) {
          children[i] = null; // delete children[i]
        } else {
          ++nonEmpty;
        }
      }
      if (nonEmpty == 0) {
        childrenCount = 0;
        children = null;
      } else if (nonEmpty < childrenCount) {
        // compact the children array
        final Node[] newChildren = (Node[]) Array.newInstance(Node.class, nonEmpty);
        int index = 0;
        for (int i = 0; i < childrenCount; ++i) {
          if (children[i] != null) {
            newChildren[index++] = children[i];
          }
        }
        children = newChildren;
        childrenCount = nonEmpty;
      }
    }

    int nodeCount() {
      int result = 1;
      for (int i = 0; i < childrenCount; ++i) {
        assert (children[i] != null);
        result += children[i].nodeCount();
      }
      return result;
    }
  }

  /**
   * 根节点。
   */
  private final Node root;

  /**
   * 字典树映射中键值对的总数量。
   */
  private int size;

  /**
   * 是否不区分大小写。
   */
  private final boolean caseInsensitive;

  /**
   * 构造一个大小写敏感的字典树映射。
   */
  public TrieMap() {
    root = new Node('\0', null);
    size = 0;
    caseInsensitive = false;
  }

  /**
   * 构造一个字典树映射。
   *
   * @param caseInsensitive 是否不区分大小写
   */
  public TrieMap(final boolean caseInsensitive) {
    root = new Node('\0', null);
    size = 0;
    this.caseInsensitive = caseInsensitive;
  }

  /**
   * 检查此字典树映射是否不区分大小写。
   *
   * @return 如果此字典树映射不区分大小写则返回{@code true}，否则返回{@code false}
   */
  public boolean isCaseInsensitive() {
    return caseInsensitive;
  }

  /**
   * 获取此字典树映射中键值对的数量。
   *
   * @return 此字典树映射中键值对的数量
   */
  public int size() {
    return size;
  }

  /**
   * 检查此字典树映射是否为空。
   *
   * @return 如果此字典树映射为空则返回{@code true}，否则返回{@code false}
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * 获取此字典树映射中节点的总数量。
   *
   * @return 此字典树映射中节点的总数量
   */
  public int nodeCount() {
    return root.nodeCount();
  }

  /**
   * 清空此字典树映射。
   */
  public void clear() {
    root.value = null;
    root.childrenCount = 0;
    root.children = null;
    size = 0;
  }

  private Node getNode(final String str) {
    final int len = str.length();
    Node node = root;
    for (int i = 0; i < len; ++i) {
      char ch = str.charAt(i);
      if (caseInsensitive) {
        ch = Character.toLowerCase(ch);
      }
      node = node.getChild(ch);
      if (node == null) {
        return null;
      }
    }
    return node;
  }

  /**
   * 获取与指定键关联的值。
   *
   * @param str 要查找的键
   * @return 与指定键关联的值，如果不存在则返回{@code null}
   */
  public VALUE get(final String str) {
    final Node node = getNode(str);
    if (node == null) {
      return null;
    } else {
      return node.value;
    }
  }

  /**
   * 检查此字典树映射是否包含指定的键。
   *
   * @param str 要检查的键
   * @return 如果此字典树映射包含指定的键则返回{@code true}，否则返回{@code false}
   */
  public boolean contains(final String str) {
    final Node node = getNode(str);
    if (node == null) {
      return false;
    } else {
      return (node.value != null);
    }
  }

  /**
   * 检查此字典树映射是否包含指定前缀的键。
   *
   * @param prefix 要检查的前缀
   * @return 如果此字典树映射包含指定前缀的键则返回{@code true}，否则返回{@code false}
   */
  public boolean containsPrefix(final String prefix) {
    final Node node = getNode(prefix);
    return (node != null);
  }

  private Node getNodeByPrefixOf(final String str) {
    final int len = str.length();
    Node node = root;
    // note that is str is empty, the root is returned
    Node target = root;
    for (int i = 0; i < len; ++i) {
      char ch = str.charAt(i);
      if (caseInsensitive) {
        ch = Character.toLowerCase(ch);
      }
      node = node.getChild(ch);
      if (node == null) {
        return target;
      }
      // remember the last node which contains a value
      if (node.value != null) {
        target = node;
      }
    }
    return target;
  }

  /**
   * 获取指定字符串的前缀在此字典树映射中对应的值。
   *
   * @param str 要查找前缀的字符串
   * @return 指定字符串的前缀在此字典树映射中对应的值
   */
  public VALUE getPrefixOf(final String str) {
    final Node node = getNodeByPrefixOf(str);
    assert (node != null);
    return node.value;
  }

  /**
   * 检查此字典树映射是否包含指定字符串的前缀。
   *
   * @param str 要检查的字符串
   * @return 如果此字典树映射包含指定字符串的前缀则返回{@code true}，否则返回{@code false}
   */
  public boolean containsPrefixOf(final String str) {
    final Node node = getNodeByPrefixOf(str);
    assert (node != null);
    return (node.value != null);
  }

  private Node getOrAddNode(final String str) {
    final int len = str.length();
    Node node = root;
    for (int i = 0; i < len; ++i) {
      char ch = str.charAt(i);
      if (caseInsensitive) {
        ch = Character.toLowerCase(ch);
      }
      node = node.getOrAddChild(ch);
    }
    return node;
  }

  /**
   * 将指定的键值对添加到此字典树映射中。
   *
   * @param str 要添加的键
   * @param value 要添加的值
   * @return 与指定键关联的先前值，如果不存在则返回{@code null}
   * @throws NullPointerException 如果键或值为{@code null}
   */
  public VALUE put(final String str, final VALUE value) {
    Argument.requireNonNull("str", str);
    Argument.requireNonNull("value", value);
    final Node node = getOrAddNode(str);
    assert (node != null);
    if (node.value == null) {
      node.value = value;
      ++size;
      return null;
    } else {
      final VALUE oldValue = node.value;
      node.value = value;
      return oldValue;
    }
  }

  /**
   * 从此字典树映射中移除指定的键。
   *
   * @param str 要移除的键
   * @return 如果成功移除则返回{@code true}，否则返回{@code false}
   * @throws NullPointerException 如果键为{@code null}
   */
  public boolean remove(final String str) {
    Argument.requireNonNull("str", str);
    if (size == 0) {
      return false;
    }
    final Node node = getNode(str);
    if ((node == null) || (node.value == null)) {
      return false;
    } else {
      node.value = null;
      --size;
      return true;
    }
  }

  /**
   * 压缩此字典树映射，移除不包含任何值的节点。
   */
  public void compact() {
    root.compact();
  }
}