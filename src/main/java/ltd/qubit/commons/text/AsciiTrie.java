////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * A tree based trie for ASCII strings.
 *
 * @author Haixing Hu
 */
@NotThreadSafe
public class AsciiTrie {

  private static class Node {
    int occurrence;
    Node[] children;

    Node() {
      occurrence = 0;
      children = new Node[Ascii.MAX];
    }

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

  private final Node root;
  private int size;
  private final boolean caseInsensitive;

  public AsciiTrie() {
    root = new Node();
    size = 0;
    caseInsensitive = false;
  }

  public AsciiTrie(final boolean caseInsensitive) {
    root = new Node();
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
    for (int i = 0; i < Ascii.MAX; ++i) {
      root.children[i] = null;
    }
    root.occurrence = 0;
    size = 0;
  }

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

  public boolean contains(final String str) {
    final Node node = getNode(str);
    return ((node != null) && (node.occurrence > 0));
  }

  public boolean containsPrefix(final String prefix) {
    final Node node = getNode(prefix);
    return (node != null);
  }

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

  public int count(final String str) {
    final Node node = getNode(str);
    if (node == null) {
      return 0;
    } else {
      return node.occurrence;
    }
  }

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

  public void compact() {
    root.compact();
  }
}
