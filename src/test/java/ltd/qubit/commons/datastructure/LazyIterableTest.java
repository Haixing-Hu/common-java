////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LazyIterableTest {

  @Test
  public void testLazyIterableWithPositiveSize() {
    final int size = 5;
    final Supplier<Integer> supplier = () -> 42; // Fixed value for simplicity
    final LazyIterable<Integer> lazyIterable = new LazyIterable<>(size, supplier);

    int count = 0;
    for (final int value : lazyIterable) {
      assertEquals(42, value);
      count++;
    }
    assertEquals(size, count, "Expected number of elements does not match.");
  }

  @Test
  public void testLazyIterableWithZeroSize() {
    final LazyIterable<Integer> lazyIterable = new LazyIterable<>(0, () -> 42);

    int count = 0;
    for (final int value : lazyIterable) {
      count++;
    }
    assertEquals(0, count, "Expected no elements for size 0.");
  }

  @Test
  public void testLazyIterableWithOneElement() {
    final LazyIterable<String> lazyIterable = new LazyIterable<>(1, () -> "Single Element");

    final Iterator<String> iterator = lazyIterable.iterator();
    assertTrue(iterator.hasNext(), "Expected one element.");
    assertEquals("Single Element", iterator.next());
    assertFalse(iterator.hasNext(), "Expected only one element.");
  }

  @Test
  public void testLazyIterableHandlesNullSupplier() {
    final LazyIterable<String> lazyIterable = new LazyIterable<>(3, () -> null);

    int count = 0;
    for (final String value : lazyIterable) {
      assertNull(value, "Expected null value.");
      count++;
    }
    assertEquals(3, count, "Expected 3 elements, all null.");
  }

  @Test
  public void testIteratorThrowsExceptionWhenNoElementsLeft() {
    final LazyIterable<Integer> lazyIterable = new LazyIterable<>(2, () -> 42);
    final Iterator<Integer> iterator = lazyIterable.iterator();
    iterator.next(); // First element
    iterator.next(); // Second element
    assertThrows(NoSuchElementException.class, iterator::next,
        "Expected NoSuchElementException after last element.");
  }

  @Test
  public void testMultipleIterationOverLazyIterable() {
    final int size = 3;
    final Supplier<String> supplier = () -> "Repeated";

    final LazyIterable<String> lazyIterable = new LazyIterable<>(size, supplier);

    for (final String value : lazyIterable) {
      assertEquals("Repeated", value);
    }

    // Test again to ensure lazy loading works across multiple iterations
    for (final String value : lazyIterable) {
      assertEquals("Repeated", value);
    }
  }

  @Test
  public void testLazyIterableGeneratesElementsOnlyWhenAccessed() {
    final int size = 5;
    final AtomicInteger counter = new AtomicInteger(0);
    // Supplier increments counter each time an element is generated
    final LazyIterable<Integer> lazyIterable = new LazyIterable<>(size, counter::incrementAndGet);
    // At this point, no elements should have been generated
    assertEquals(0, counter.get(), "Expected no elements generated yet.");
    // Access each element individually using iterator
    final Iterator<Integer> iterator = lazyIterable.iterator();
    for (int i = 1; i <= size; i++) {
      assertTrue(iterator.hasNext());
      final int value = iterator.next();
      assertEquals(i, value, "Expected element to match the order of generation.");
      assertEquals(i, counter.get(), "Expected the counter to be incremented exactly once per access.");
    }
    // After accessing all elements, counter should equal to size
    assertEquals(size, counter.get(),
        "Expected elements to be generated exactly as many times as they were accessed.");
  }

  @Test
  void testLazyIterableWithOrderedCollection() {
    final List<Integer> sourceList = Arrays.asList(1, 2, 3, 4, 5);
    final LazyIterable<String> lazyIterable = new LazyIterable<>(sourceList, Object::toString);
    final Iterator<String> iterator = lazyIterable.iterator();

    assertTrue(iterator.hasNext());
    assertEquals("1", iterator.next());
    assertEquals("2", iterator.next());
    assertEquals("3", iterator.next());
    assertEquals("4", iterator.next());
    assertEquals("5", iterator.next());
    assertFalse(iterator.hasNext());
  }

  @Test
  void testLazyIterableWithUnorderedCollection() {
    final Set<Integer> sourceSet = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
    final LazyIterable<String> lazyIterable = new LazyIterable<>(sourceSet, Object::toString);

    final List<String> result = new ArrayList<>();
    for (final String s : lazyIterable) {
      result.add(s);
    }
    assertEquals(5, result.size());
    assertTrue(result.containsAll(Arrays.asList("1", "2", "3", "4", "5")));
  }

  @Test
  void testLazyIterableWithEmptyCollection() {
    final List<Integer> emptyList = Collections.emptyList();
    final LazyIterable<String> lazyIterable = new LazyIterable<>(emptyList, Object::toString);

    final Iterator<String> iterator = lazyIterable.iterator();
    assertFalse(iterator.hasNext(), "Expected no elements in the iterator.");
    assertThrows(NoSuchElementException.class, iterator::next, "Expected NoSuchElementException when calling next() on an empty iterator.");
  }

  @Test
  void testLazyIterableWithSingleElementCollection() {
    final List<Integer> singleElementList = Collections.singletonList(42);
    final LazyIterable<String> lazyIterable = new LazyIterable<>(singleElementList, Object::toString);

    final Iterator<String> iterator = lazyIterable.iterator();
    assertTrue(iterator.hasNext());
    assertEquals("42", iterator.next());
    assertFalse(iterator.hasNext());
    assertThrows(NoSuchElementException.class, iterator::next, "Expected NoSuchElementException when calling next() after the last element.");
  }

  @Test
  void testLazyIterableWithNullElements() {
    final List<Integer> listWithNulls = Arrays.asList(1, null, 3, null, 5);
    final LazyIterable<String> lazyIterable = new LazyIterable<>(listWithNulls, Objects::toString);

    final Iterator<String> iterator = lazyIterable.iterator();

    assertTrue(iterator.hasNext());
    assertEquals("1", iterator.next());
    assertEquals("null", iterator.next());
    assertEquals("3", iterator.next());
    assertEquals("null", iterator.next());
    assertEquals("5", iterator.next());
    assertFalse(iterator.hasNext());
  }

  @Test
  void testLazyIterableWithUnorderedCollectionWithDuplicates() {
    final Set<Integer> sourceSet = new HashSet<>(Arrays.asList(1, 2, 2, 3, 3, 3));
    final LazyIterable<String> lazyIterable = new LazyIterable<>(sourceSet, Object::toString);

    final List<String> result = new ArrayList<>();
    for (final String s : lazyIterable) {
      result.add(s);
    }

    assertEquals(3, result.size());
    assertTrue(result.containsAll(Arrays.asList("1", "2", "3")));
  }

  @Test
  void testLazyIterableWithOrderedCollectionAndSideEffects() {
    final List<Integer> sourceList = Arrays.asList(1, 2, 3, 4, 5);
    final LazyIterable<String> lazyIterable = new LazyIterable<>(sourceList, index -> {
      System.out.println("Processing: " + index);
      return index.toString();
    });
    final Iterator<String> iterator = lazyIterable.iterator();
    assertTrue(iterator.hasNext());
    assertEquals("1", iterator.next());
    assertEquals("2", iterator.next());
    assertEquals("3", iterator.next());
    assertEquals("4", iterator.next());
    assertEquals("5", iterator.next());
    assertFalse(iterator.hasNext());
  }
}