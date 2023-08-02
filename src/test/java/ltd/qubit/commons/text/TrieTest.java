////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test of the {@link Trie} class.
 *
 * @author Haixing Hu
 */
public class TrieTest {
  /**
   * Test method for {@link Trie#size()}.
   */
  @Test
  public void testSize() {
    final Trie trie = new Trie();

    assertEquals(0, trie.size());
    trie.add("hello");
    assertEquals(1, trie.size());
    trie.add("hello");
    assertEquals(2, trie.size());
    trie.add("world");
    assertEquals(3, trie.size());

    trie.add("hello\u00ff");
    assertEquals(4, trie.size());

    trie.remove("hello");
    assertEquals(3, trie.size());

    trie.remove("hihi");
    assertEquals(3, trie.size());

    trie.remove("hello");
    assertEquals(2, trie.size());

    trie.remove("world");
    assertEquals(1, trie.size());

    trie.remove("hello\u00ff");
    assertEquals(0, trie.size());

    trie.add("hello");
    trie.add("world");
    trie.add("hello\u00ff");
    assertEquals(3, trie.size());
    trie.clear();
    assertEquals(0, trie.size());
  }

  /**
   * Test method for {@link Trie#isEmpty()}.
   */
  @Test
  public void testIsEmpty() {
    final Trie trie = new Trie();

    assertEquals(true, trie.isEmpty());
    trie.add("hello");
    assertEquals(false, trie.isEmpty());
    trie.add("hello");
    assertEquals(false, trie.isEmpty());
    trie.add("world");
    assertEquals(false, trie.isEmpty());

    trie.add("hello\u00ff");
    assertEquals(false, trie.isEmpty());

    trie.remove("hello");
    assertEquals(false, trie.isEmpty());

    trie.remove("hihi");
    assertEquals(false, trie.isEmpty());

    trie.remove("hello");
    assertEquals(false, trie.isEmpty());

    trie.remove("world");
    assertEquals(false, trie.isEmpty());

    trie.add("hello");
    trie.add("world");
    trie.add("hello\u00ff");
    assertEquals(false, trie.isEmpty());
    trie.clear();
    assertEquals(true, trie.isEmpty());
  }

  /**
   * Test method for {@link Trie#contains(String)}.
   */
  @Test
  public void testContains() {
    final Trie trie = new Trie();

    assertEquals(false, trie.contains(""));
    trie.add("");
    assertEquals(true, trie.contains(""));

    assertEquals(false, trie.contains("hello"));
    trie.add("hello");
    assertEquals(true, trie.contains("hello"));

    assertEquals(false, trie.contains("world"));
    trie.add("world");
    assertEquals(true, trie.contains("world"));

    assertEquals(false, trie.contains("world\u00ff"));
    trie.add("world\u00ff");
    assertEquals(true, trie.contains("world\u00ff"));

    trie.remove("world");
    assertEquals(false, trie.contains("world"));

  }

  /**
   * Test method for {@link Trie#getOccurence(String)}.
   */
  @Test
  public void testGetOccurence() {
    final Trie trie = new Trie();

    assertEquals(0, trie.count(""));
    trie.add("");
    trie.add("");
    trie.add("");
    assertEquals(3, trie.count(""));
    trie.remove("");
    assertEquals(2, trie.count(""));

    assertEquals(0, trie.count("hello"));
    trie.add("hello");
    assertEquals(1, trie.count("hello"));
    trie.add("hello");
    assertEquals(2, trie.count("hello"));
    trie.add("hello");
    assertEquals(3, trie.count("hello"));

    assertEquals(0, trie.count("world"));
    trie.add("world");
    assertEquals(1, trie.count("world"));

    assertEquals(0, trie.count("world\u00ff"));
    trie.add("world\u00ff");
    assertEquals(1, trie.count("world\u00ff"));

    trie.remove("world");
    assertEquals(0, trie.count("world"));
  }

  /**
   * Test method for {@link Trie#add(String)}.
   */
  @Test
  public void testAdd() {
    final Trie trie = new Trie();

    assertEquals(false, trie.contains(""));
    trie.add("");
    assertEquals(true, trie.contains(""));
    trie.remove("");
    assertEquals(false, trie.contains(""));

    assertEquals(false, trie.contains("hello"));
    trie.add("hello");
    assertEquals(true, trie.contains("hello"));

    assertEquals(false, trie.contains("world"));
    trie.add("world");
    assertEquals(true, trie.contains("world"));

    assertEquals(false, trie.contains("world\u00ff"));
    trie.add("world\u00ff");
    assertEquals(true, trie.contains("world\u00ff"));

    trie.remove("world");
    assertEquals(false, trie.contains("world"));
  }

  /**
   * Test method for {@link Trie#remove(String)}.
   */
  @Test
  public void testRemove() {
    final Trie trie = new Trie();
    boolean result = false;

    assertEquals(0, trie.count(""));
    trie.add("");
    trie.add("");
    trie.add("");
    assertEquals(3, trie.count(""));
    result = trie.remove("");
    assertEquals(true, result);
    assertEquals(2, trie.count(""));

    assertEquals(0, trie.count("hello"));
    trie.add("hello");
    assertEquals(1, trie.count("hello"));
    trie.add("hello");
    assertEquals(2, trie.count("hello"));
    result = trie.remove("hello");
    assertEquals(true, result);
    assertEquals(1, trie.count("hello"));

    assertEquals(0, trie.count("world"));
    trie.add("world");
    assertEquals(1, trie.count("world"));
    result = trie.remove("world");
    assertEquals(true, result);
    assertEquals(0, trie.count("world"));

    assertEquals(0, trie.count("world\u00ff"));
    trie.add("world\u00ff");
    assertEquals(1, trie.count("world\u00ff"));
    result = trie.remove("world\u00ff");
    assertEquals(true, result);
    assertEquals(0, trie.count("world\u00ff"));

    result = trie.remove("world");
    assertEquals(false, result);
    assertEquals(0, trie.count("world"));
  }

  /**
   * Test method for {@link Trie#compact()}.
   */
  @Test
  public void testCompact() {
    final Trie trie = new Trie();

    assertEquals(1, trie.nodeCount());
    trie.compact();
    assertEquals(1, trie.nodeCount());

    trie.add("");
    trie.add("");
    trie.add("");
    trie.remove("");
    trie.add("hello");
    trie.add("hello");
    trie.remove("hello");
    trie.compact();
    assertEquals(3, trie.size());

    assertEquals(6, trie.nodeCount());
    trie.removeAll("");
    assertEquals(6, trie.nodeCount());
    trie.removeAll("hello");
    assertEquals(6, trie.nodeCount());
    trie.compact();
    assertEquals(1, trie.nodeCount());
  }
}
