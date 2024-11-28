////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import javax.annotation.concurrent.NotThreadSafe;

import ltd.qubit.commons.util.expand.ExpansionPolicy;

/**
 * A tree based trie for Unicode strings.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class Trie {

  private static final int INITIAL_CHILDREN_CAPACITY    = 4;

  private static class Node {
    char ch;
    int occurence;
    int childrenCount;
    Node[] children;

    Node(final char ch, final int occurence) {
      assert (occurence >= 0);
      this.ch = ch;
      this.occurence = occurence;
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

    Node getOrAddChild(final char ch) {
      if (childrenCount == 0) {
        final Node newChild = new Node(ch, 0);
        children = new Node[INITIAL_CHILDREN_CAPACITY];
        children[0] = newChild;
        childrenCount = 1;
        return newChild;
      }
      int min = 0;
      int max = childrenCount - 1;
      int mid = 0;
      final ExpansionPolicy expansionPolicy = ExpansionPolicy.getDefault();
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
      final Node child = children[min];
      if (child.ch == ch) {
        return child;
      }
      final Node newChild = new Node(ch, 0);
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

    void compact() {
      if (childrenCount == 0) {
        return;
      }
      int nonEmptyChildrenCount = 0;
      for (int i = 0; i < childrenCount; ++i) {
        assert (children[i] != null);
        children[i].compact();
        if ((children[i].childrenCount == 0)
            && (children[i].occurence == 0)) {
          children[i] = null; // delete children[i]
        } else {
          ++nonEmptyChildrenCount;
        }
      }
      if (nonEmptyChildrenCount == 0) {
        childrenCount = 0;
        children = null;
      } else if (nonEmptyChildrenCount < childrenCount) {
        // compact the children array
        final Node[] newChildren = new Node[nonEmptyChildrenCount];
        int index = 0;
        for (int i = 0; i < childrenCount; ++i) {
          if (children[i] != null) {
            newChildren[index++] = children[i];
          }
        }
        children = newChildren;
        childrenCount = nonEmptyChildrenCount;
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

  private final Node root;
  private int size;
  private final boolean caseInsensitive;

  public Trie() {
    root = new Node('\0', 0);
    size = 0;
    caseInsensitive = false;
  }

  public Trie(final boolean caseInsensitive) {
    root = new Node('\0', 0);
    size = 0;
    this.caseInsensitive = caseInsensitive;
  }

  public boolean isCaseInsensitive() {
    return caseInsensitive;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return (size == 0);
  }

  public int nodeCount() {
    return root.nodeCount();
  }

  public void clear() {
    root.occurence = 0;
    root.childrenCount = 0;
    root.children = null;
    size = 0;
  }

  public boolean contains(final String str) {
    final Node node = getNode(str);
    return ((node != null) && (node.occurence > 0));
  }

  public boolean containsPrefix(final String prefix) {
    final Node node = getNode(prefix);
    return (node != null);
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

  public boolean containsPrefixOf(final String str) {
    final int len = str.length();
    Node node = root;
    for (int i = 0; i < len; ++i) {
      char ch = str.charAt(i);
      if (caseInsensitive) {
        ch = Character.toLowerCase(ch);
      }
      node = node.getChild(ch);
      if (node == null) {
        return false;
      }
      if (node.occurence > 0) {
        return true;
      }
    }
    return false;
  }

  public int count(final String str) {
    final Node node = getNode(str);
    if (node == null) {
      return 0;
    } else {
      return node.occurence;
    }
  }

  public void add(final String str) {
    final Node node = getOrAddNode(str);
    assert (node != null);
    ++node.occurence;
    ++size;
  }

  public void add(final String str, final int occurence) {
    if (occurence < 0) {
      throw new IllegalArgumentException();
    }
    if (occurence > 0) {
      final Node node = getOrAddNode(str);
      assert (node != null);
      node.occurence += occurence;
      size += occurence;
    }
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

  public boolean remove(final String str) {
    if (size == 0) {
      return false;
    }
    final Node node = getNode(str);
    if (node == null) {
      return false;
    }
    if (node.occurence == 0) {
      return false;
    } else {
      --node.occurence;
      --size;
      return true;
    }
  }

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
    final int occ = Math.min(occurrence, node.occurence);
    node.occurence -= occ;
    size -= occ;
    return occ;
  }

  public int removeAll(final String str) {
    if (size == 0) {
      return 0;
    }
    final Node node = getNode(str);
    if (node == null) {
      return 0;
    }
    final int occurence = node.occurence;
    node.occurence = 0;
    size -= occurence;
    return occurence;
  }

  public void compact() {
    root.compact();
  }
}
