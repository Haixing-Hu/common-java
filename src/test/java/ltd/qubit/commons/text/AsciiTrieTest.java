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
 * Unit test of the {@link AsciiTrie} class.
 *
 * @author Haixing Hu
 */
public class AsciiTrieTest {

  /**
   * Test method for {@link AsciiTrie#size()}.
   */
  @Test
  public void testSize() {
    final AsciiTrie trie = new AsciiTrie();

    assertEquals(0, trie.size());
    trie.add("hello");
    assertEquals(1, trie.size());
    trie.add("hello");
    assertEquals(2, trie.size());
    trie.add("world");
    assertEquals(3, trie.size());

    trie.add("hello\u00ff");
    assertEquals(3, trie.size());

    trie.remove("hello");
    assertEquals(2, trie.size());

    trie.remove("hihi");
    assertEquals(2, trie.size());

    trie.remove("hello");
    assertEquals(1, trie.size());

    trie.remove("world");
    assertEquals(0, trie.size());

    trie.add("hello");
    trie.add("world");
    trie.add("hello\u00ff");
    assertEquals(2, trie.size());
    trie.clear();
    assertEquals(0, trie.size());
  }

  /**
   * Test method for {@link AsciiTrie#isEmpty()}.
   */
  @Test
  public void testIsEmpty() {
    final AsciiTrie trie = new AsciiTrie();

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
    assertEquals(true, trie.isEmpty());

    trie.add("hello");
    trie.add("world");
    trie.add("hello\u00ff");
    assertEquals(false, trie.isEmpty());
    trie.clear();
    assertEquals(true, trie.isEmpty());
  }

  /**
   * Test method for {@link AsciiTrie#contains(String)}.
   */
  @Test
  public void testContains() {
    final AsciiTrie trie = new AsciiTrie();

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
    assertEquals(false, trie.contains("world\u00ff"));

    trie.remove("world");
    assertEquals(false, trie.contains("world"));

  }

  /**
   * Test method for {@link AsciiTrie#containsPrefix(String)}.
   */
  @Test
  public void testContainsPrefix() {
    final AsciiTrie trie = new AsciiTrie();

    assertEquals(true, trie.containsPrefix(""));
    assertEquals(false, trie.containsPrefix("a"));

    trie.add("");
    assertEquals(true, trie.containsPrefix(""));
    assertEquals(false, trie.containsPrefix("a"));

    trie.add("hello");
    assertEquals(false, trie.containsPrefix("abc"));
    assertEquals(false, trie.containsPrefix("hea"));
    assertEquals(false, trie.containsPrefix("hal"));
    assertEquals(true, trie.containsPrefix("hel"));
    assertEquals(true, trie.containsPrefix("hell"));
    assertEquals(true, trie.containsPrefix("hello"));
    assertEquals(false, trie.containsPrefix("helloo"));

    trie.add("hellp");
    assertEquals(false, trie.containsPrefix("abc"));
    assertEquals(false, trie.containsPrefix("hea"));
    assertEquals(false, trie.containsPrefix("hal"));
    assertEquals(true, trie.containsPrefix("hel"));
    assertEquals(true, trie.containsPrefix("hell"));
    assertEquals(true, trie.containsPrefix("hello"));
    assertEquals(true, trie.containsPrefix("hellp"));
    assertEquals(false, trie.containsPrefix("helloo"));
  }


  /**
   * Test method for {@link AsciiTrie#containsPrefixOf(String)}.
   */
  @Test
  public void testContainsPrefixOf() {
    final AsciiTrie trie = new AsciiTrie();

    assertEquals(false, trie.containsPrefixOf(""));
    assertEquals(false, trie.containsPrefixOf("a"));

    trie.add("");
    assertEquals(false, trie.containsPrefixOf(""));
    assertEquals(false, trie.containsPrefixOf("a"));

    trie.add("hello");
    assertEquals(false, trie.containsPrefixOf("hel"));
    assertEquals(false, trie.containsPrefixOf("hell"));
    assertEquals(true, trie.containsPrefixOf("hello"));
    assertEquals(true, trie.containsPrefixOf("hello, world"));
    assertEquals(false, trie.containsPrefixOf("hell, world"));

    trie.add("hellp");
    assertEquals(false, trie.containsPrefixOf("abc"));
    assertEquals(false, trie.containsPrefixOf("hea"));
    assertEquals(false, trie.containsPrefixOf("hal"));
    assertEquals(false, trie.containsPrefixOf("hel"));
    assertEquals(false, trie.containsPrefixOf("hell"));
    assertEquals(true, trie.containsPrefixOf("hello"));
    assertEquals(true, trie.containsPrefixOf("hellp"));
    assertEquals(true, trie.containsPrefixOf("hellp, world"));
  }

  /**
   * Test method for {@link AsciiTrie#getOccurence(String)}.
   */
  @Test
  public void testGetOccurence() {
    final AsciiTrie trie = new AsciiTrie();

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
    assertEquals(0, trie.count("world\u00ff"));

    trie.remove("world");
    assertEquals(0, trie.count("world"));
  }

  /**
   * Test method for {@link AsciiTrie#add(String)}.
   */
  @Test
  public void testAdd() {
    final AsciiTrie trie = new AsciiTrie();
    boolean result = false;

    assertEquals(false, trie.contains(""));
    result = trie.add("");
    assertEquals(true, result);
    assertEquals(true, trie.contains(""));
    trie.remove("");
    assertEquals(false, trie.contains(""));

    assertEquals(false, trie.contains("hello"));
    result = trie.add("hello");
    assertEquals(true, result);
    assertEquals(true, trie.contains("hello"));

    assertEquals(false, trie.contains("world"));
    result = trie.add("world");
    assertEquals(true, result);
    assertEquals(true, trie.contains("world"));

    assertEquals(false, trie.contains("world\u00ff"));
    result = trie.add("world\u00ff");
    assertEquals(false, result);
    assertEquals(false, trie.contains("world\u00ff"));

    result = trie.remove("world");
    assertEquals(true, result);
    assertEquals(false, trie.contains("world"));
  }

  /**
   * Test method for {@link AsciiTrie#remove(String)}.
   */
  @Test
  public void testRemove() {
    final AsciiTrie trie = new AsciiTrie();
    boolean result = false;

    assertEquals(0, trie.count(""));
    result = trie.add("");
    result = trie.add("");
    result = trie.add("");
    assertEquals(3, trie.count(""));
    result = trie.remove("");
    assertEquals(true, result);
    assertEquals(2, trie.count(""));

    assertEquals(0, trie.count("hello"));
    result = trie.add("hello");
    assertEquals(true, result);
    assertEquals(1, trie.count("hello"));
    result = trie.add("hello");
    assertEquals(true, result);
    assertEquals(2, trie.count("hello"));
    result = trie.remove("hello");
    assertEquals(true, result);
    assertEquals(1, trie.count("hello"));

    assertEquals(0, trie.count("world"));
    result = trie.add("world");
    assertEquals(true, result);
    assertEquals(1, trie.count("world"));
    result = trie.remove("world");
    assertEquals(true, result);
    assertEquals(0, trie.count("world"));

    assertEquals(0, trie.count("world\u00ff"));
    result = trie.add("world\u00ff");
    assertEquals(false, result);
    assertEquals(0, trie.count("world\u00ff"));
    result = trie.remove("world\u00ff");
    assertEquals(false, result);
    assertEquals(0, trie.count("world\u00ff"));

    result = trie.remove("world");
    assertEquals(false, result);
    assertEquals(0, trie.count("world"));
  }

  /**
   * Test method for {@link AsciiTrie#compact()}.
   */
  @Test
  public void testCompact() {
    final AsciiTrie trie = new AsciiTrie();

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
