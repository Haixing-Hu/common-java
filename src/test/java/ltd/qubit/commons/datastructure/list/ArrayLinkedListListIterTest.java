////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure.list;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import ltd.qubit.commons.lang.ArrayUtils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test for the {@link ArrayLinkedList.ListIter} class.
 *
 * @author Haixing Hu
 */
public class ArrayLinkedListListIterTest {

  // stop checkstyle: MagicNumberCheck
  static final Integer[] ARRAY_0 = {};
  static final Integer[] ARRAY_1 = {1};
  static final Integer[] ARRAY_2 = {1, 2, 3, 4, null, 6, 7, null, 9, 10};

  /**
   * Test method for {@link ArrayLinkedList.ListIter#ListIter(int)}.
   */
  @Test
  public void testListIter() {
    testListIter(ARRAY_0);
    testListIter(ARRAY_1);
    testListIter(ARRAY_2);
  }

  private void testListIter(final Integer[] array) {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<>();
    ListIterator<Integer> iter = list.listIterator(0);
    assertEquals(0, iter.nextIndex());

    list.addAll(Arrays.asList(array));
    iter = list.listIterator(0);
    assertEquals(0, iter.nextIndex());
    if (array.length >= 1) {
      iter = list.listIterator(1);
      assertEquals(1, iter.nextIndex());
    }
    iter = list.listIterator(array.length / 2);
    assertEquals(array.length / 2, iter.nextIndex());
    iter = list.listIterator(array.length);
    assertEquals(array.length, iter.nextIndex());

    try {
      iter = list.listIterator(list.size() + 1);
      fail("should throw IndexOutOfBoundsException");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }

    try {
      iter = list.listIterator(-1);
      fail("should throw IndexOutOfBoundsException");
    } catch (final IndexOutOfBoundsException e) {
      // pass
    }
  }

  /**
   * Test method for {@link ArrayLinkedList.ListIter#hasNext()}.
   */
  @Test
  public void testHasNext() {
    testHasNext(ARRAY_0);
    testHasNext(ARRAY_1);
    testHasNext(ARRAY_2);
  }

  private void testHasNext(final Integer[] array) {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<>();
    ListIterator<Integer> iter = list.listIterator();
    assertFalse(iter.hasNext());

    list.addAll(Arrays.asList(array));
    iter = list.listIterator();
    for (int i = 0; i < array.length; ++i) {
      assertTrue(iter.hasNext());
      iter.next();
    }
    assertFalse(iter.hasNext());
  }

  /**
   * Test method for {@link ArrayLinkedList.ListIter#next()}.
   */
  @Test
  public void testNext() {
    testNext(ARRAY_0);
    testNext(ARRAY_1);
    testNext(ARRAY_2);
  }

  private void testNext(final Integer[] array) {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<>();
    ListIterator<Integer> iter = list.listIterator();
    assertFalse(iter.hasNext());
    try {
      iter.next();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }
    list.addAll(Arrays.asList(array));
    iter = list.listIterator();
    for (int i = 0; i < array.length; ++i) {
      assertTrue(iter.hasNext());
      final Integer obj = iter.next();
      assertEquals(array[i], obj);
    }
    assertFalse(iter.hasNext());
    try {
      iter.next();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }

    list.add(1);
    try {
      iter.next();
      fail("should throw ConcurrentModificationException");
    } catch (final ConcurrentModificationException e) {
      // pass
    }
  }

  /**
   * Test method for {@link ArrayLinkedList.ListIter#hasPrevious()}.
   */
  @Test
  public void testHasPrevious() {
    testHasPrevious(ARRAY_0);
    testHasPrevious(ARRAY_1);
    testHasPrevious(ARRAY_2);
  }

  private void testHasPrevious(final Integer[] array) {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<>();
    ListIterator<Integer> iter = list.listIterator();
    assertFalse(iter.hasPrevious());

    list.addAll(Arrays.asList(array));
    iter = list.listIterator(list.size());
    for (int i = 0; i < array.length; ++i) {
      assertTrue(iter.hasPrevious());
      iter.previous();
    }
    assertFalse(iter.hasPrevious());
  }

  /**
   * Test method for {@link ArrayLinkedList.ListIter#previous()}.
   */
  @Test
  public void testPrevious() {
    testPrevious(ARRAY_0);
    testPrevious(ARRAY_1);
    testPrevious(ARRAY_2);
  }

  private void testPrevious(final Integer[] array) {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<>();
    ListIterator<Integer> iter = list.listIterator();
    assertFalse(iter.hasPrevious());
    try {
      iter.previous();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }

    list.addAll(Arrays.asList(array));
    iter = list.listIterator(list.size());
    for (int i = array.length - 1; i >= 0; --i) {
      assertTrue(iter.hasPrevious());
      final Integer obj = iter.previous();
      assertEquals(array[i], obj);
    }
    assertFalse(iter.hasPrevious());
    try {
      iter.previous();
      fail("should throw NoSuchElementException");
    } catch (final NoSuchElementException e) {
      // pass
    }

    list.add(1);
    try {
      iter.previous();
      fail("should throw ConcurrentModificationException");
    } catch (final ConcurrentModificationException e) {
      // pass
    }
  }

  /**
   * Test method for {@link ArrayLinkedList.ListIter#nextIndex()}.
   */
  @Test
  public void testNextIndex() {
    testNextIndex(ARRAY_0);
    testNextIndex(ARRAY_1);
    testNextIndex(ARRAY_2);
  }

  private void testNextIndex(final Integer[] array) {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<>();
    ListIterator<Integer> iter = list.listIterator();
    assertEquals(0, iter.nextIndex());

    list.addAll(Arrays.asList(array));
    iter = list.listIterator();
    for (int i = 0; i < array.length; ++i) {
      assertTrue(iter.hasNext());
      assertEquals(i, iter.nextIndex());
      iter.next();
    }
    assertFalse(iter.hasNext());
    assertEquals(list.size(), iter.nextIndex());
  }

  /**
   * Test method for {@link ArrayLinkedList.ListIter#previousIndex()}.
   */
  @Test
  public void testPreviousIndex() {
    testPreviousIndex(ARRAY_0);
    testPreviousIndex(ARRAY_1);
    testPreviousIndex(ARRAY_2);
  }

  private void testPreviousIndex(final Integer[] array) {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<>();
    ListIterator<Integer> iter = list.listIterator();
    assertEquals(-1, iter.previousIndex());

    list.addAll(Arrays.asList(array));
    iter = list.listIterator(array.length);
    for (int i = array.length - 1; i >= 0; --i) {
      assertTrue(iter.hasPrevious());
      assertEquals(i, iter.previousIndex());
      iter.previous();
    }
    assertFalse(iter.hasPrevious());
    assertEquals(-1, iter.previousIndex());
  }

  /**
   * Test method for {@link ArrayLinkedList.ListIter#remove()}.
   */
  @Test
  public void testRemove() {
    testRemove(ARRAY_0);
    testRemove(ARRAY_1);
    testRemove(ARRAY_2);
  }

  private void testRemove(final Integer[] array) {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<>();
    ListIterator<Integer> iter = list.listIterator();

    try {
      iter.remove();
      fail("should throw IllegalStateException");
    } catch (final IllegalStateException e) {
      // pass
    }

    list.addAll(Arrays.asList(array));
    iter = list.listIterator();

    // test the remove() after calling next()
    for (int i = 0; i < array.length; ++i) {
      assertTrue(iter.hasNext());
      assertEquals(i, iter.nextIndex());
      final Integer obj = iter.next();
      assertEquals(i + 1, iter.nextIndex());
      assertEquals(array[i], obj);

      iter.remove();
      assertEquals(i, iter.nextIndex());

      final Integer[] expected = ArrayUtils.remove(array, i);
      assertArrayEquals(expected, list.toArray());
      // can't call remove() again
      try {
        iter.remove();
        fail("should throw IllegalStateException");
      } catch (final IllegalStateException e) {
        // pass
      }
      iter.add(obj);
      assertEquals(i + 1, iter.nextIndex());
      // can't call remove() again
      try {
        iter.remove();
        fail("should throw IllegalStateException");
      } catch (final IllegalStateException e) {
        // pass
      }
    }
    assertFalse(iter.hasNext());
    assertEquals(array.length, iter.nextIndex());

    // test the remove() after calling previous()
    for (int i = array.length - 1; i >= 0; --i) {
      assertTrue(iter.hasPrevious());
      assertEquals(i, iter.previousIndex());
      final Integer obj = iter.previous();
      assertEquals(i - 1, iter.previousIndex());
      assertEquals(array[i], obj);
      iter.remove();
      assertEquals(i - 1, iter.previousIndex());
      final Integer[] expected = ArrayUtils.remove(array, i);
      assertArrayEquals(expected, list.toArray());
      // can't call remove() again
      try {
        iter.remove();
        fail("should throw IllegalStateException");
      } catch (final IllegalStateException e) {
        // pass
      }
      iter.add(obj);
      assertEquals(i, iter.previousIndex());
      // can't call remove() again
      try {
        iter.remove();
        fail("should throw IllegalStateException");
      } catch (final IllegalStateException e) {
        // pass
      }
      assertEquals(obj, iter.previous());
    }

    // test the concurrent modification
    list.add(null);
    try {
      iter.remove();
      fail("should throw ConcurrentModificationException");
    } catch (final ConcurrentModificationException e) {
      // pass
    }

    // should check concurrent modification before checking index
    iter = list.listIterator(list.size());
    list.pop();
    try {
      iter.remove();
      fail("should throw ConcurrentModificationException");
    } catch (final ConcurrentModificationException e) {
      // pass
    }
  }

  /**
   * Test method for {@link ArrayLinkedList.ListIter#set(Object)}.
   */
  @Test
  public void testSet() {
    testSet(ARRAY_0);
    testSet(ARRAY_1);
    testSet(ARRAY_2);
  }

  private void testSet(final Integer[] array) {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<>();
    ListIterator<Integer> iter = list.listIterator();

    try {
      iter.set(null);
      fail("should throw IllegalStateException");
    } catch (final IllegalStateException e) {
      // pass
    }

    list.addAll(Arrays.asList(array));
    iter = list.listIterator();

    // test the set() after calling next()
    for (int i = 0; i < array.length; ++i) {
      assertTrue(iter.hasNext());
      final Integer obj = iter.next();
      assertEquals(array[i], obj);
      iter.set(null);
      array[i] = null;
      assertArrayEquals(array, list.toArray());
      // can call set again
      iter.set(obj);
      array[i] = obj;
      assertArrayEquals(array, list.toArray());
    }
    // test the set() after calling previous()
    for (int i = array.length - 1; i >= 0; --i) {
      assertTrue(iter.hasPrevious());
      final Integer obj = iter.previous();
      assertEquals(array[i], obj);
      iter.set(Integer.valueOf(i));
      array[i] = Integer.valueOf(i);
      assertArrayEquals(array, list.toArray());
      // can call set again
      iter.set(obj);
      array[i] = obj;
      assertArrayEquals(array, list.toArray());
    }

    // test the concurrent modification
    list.add(null);
    try {
      iter.set(null);
      fail("should throw ConcurrentModificationException");
    } catch (final ConcurrentModificationException e) {
      // pass
    }

    // should check concurrent modification before checking index
    iter = list.listIterator(list.size());
    list.pop();
    try {
      iter.set(null);
      fail("should throw ConcurrentModificationException");
    } catch (final ConcurrentModificationException e) {
      // pass
    }
  }

  /**
   * Test method for {@link ArrayLinkedList.ListIter#add(Object)}.
   */
  @Test
  public void testAdd() {
    final ArrayLinkedList<Integer> list = new ArrayLinkedList<>();
    ListIterator<Integer> iter = list.listIterator();

    assertEquals(0, iter.nextIndex());
    assertEquals(-1, iter.previousIndex());
    assertFalse(iter.hasNext());
    assertFalse(iter.hasPrevious());
    assertEquals(0, list.size());
    Integer[] array = new Integer[]{};
    assertArrayEquals(array, list.toArray());

    // add to an empty list
    iter.add(Integer.valueOf(0));

    assertEquals(1, iter.nextIndex());
    assertEquals(0, iter.previousIndex());
    assertFalse(iter.hasNext());
    assertTrue(iter.hasPrevious());
    assertEquals(1, list.size());
    array = new Integer[]{0};
    assertArrayEquals(array, list.toArray());

    // add at the end of a non-empty list
    iter.add(Integer.valueOf(1));

    assertEquals(2, iter.nextIndex());
    assertEquals(1, iter.previousIndex());
    assertFalse(iter.hasNext());
    assertTrue(iter.hasPrevious());
    assertEquals(2, list.size());
    array = new Integer[]{0, 1};
    assertArrayEquals(array, list.toArray());

    // add at the middle of a non-empty list
    iter.previous();
    assertEquals(1, iter.nextIndex());
    assertEquals(0, iter.previousIndex());
    assertTrue(iter.hasNext());
    assertTrue(iter.hasPrevious());

    iter.add(null);

    assertEquals(2, iter.nextIndex());
    assertEquals(1, iter.previousIndex());
    assertTrue(iter.hasNext());
    assertTrue(iter.hasPrevious());
    assertEquals(3, list.size());
    array = new Integer[]{0, null, 1};
    assertArrayEquals(array, list.toArray());

    // add at the beginning of a non-empty list
    iter.previous();
    iter.previous();
    assertEquals(0, iter.nextIndex());
    assertEquals(-1, iter.previousIndex());
    assertTrue(iter.hasNext());
    assertFalse(iter.hasPrevious());

    iter.add(Integer.valueOf(3));
    assertEquals(1, iter.nextIndex());
    assertEquals(0, iter.previousIndex());
    assertTrue(iter.hasNext());
    assertTrue(iter.hasPrevious());
    assertEquals(4, list.size());
    array = new Integer[]{3, 0, null, 1};
    assertArrayEquals(array, list.toArray());

    // test the concurrent modification
    list.add(100);
    try {
      iter.add(null);
      fail("should throw ConcurrentModificationException");
    } catch (final ConcurrentModificationException e) {
      // pass
    }
    iter = list.listIterator();
    iter.add(null);
    array = new Integer[]{null, 3, 0, null, 1, 100};
    assertArrayEquals(array, list.toArray());
  }
  // resume checkstyle: MagicNumberCheck
}
