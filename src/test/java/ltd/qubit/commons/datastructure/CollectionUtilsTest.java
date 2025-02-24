////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.datastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.util.pair.KeyValuePairList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static ltd.qubit.commons.datastructure.CollectionUtils.constructSameTypeOfCollection;
import static ltd.qubit.commons.datastructure.CollectionUtils.isEmpty;

public class CollectionUtilsTest {

  @Test
  public void testConstructSameTypeOfCollection() {
    final List<String> c1 = new ArrayList<>();
    final List<String> r1 = constructSameTypeOfCollection(c1);
    assertEquals(c1.getClass(), r1.getClass());

    final Queue<String> c2 = new LinkedList<>();
    final Queue<String> r2 = constructSameTypeOfCollection(c2);
    assertEquals(c2.getClass(), r2.getClass());

    final SortedSet<String> c3 = new TreeSet<>();
    final SortedSet<String> r3 = constructSameTypeOfCollection(c3);
    assertEquals(c3.getClass(), r3.getClass());

    final KeyValuePairList c4 = new KeyValuePairList();
    final KeyValuePairList r4 = (KeyValuePairList) constructSameTypeOfCollection(c4);
    assertEquals(c4.getClass(), r4.getClass());
  }

  @Test
  public void isEmptyCollectionReturnsTrueForNull() {
    assertTrue(isEmpty((Collection<?>) null));
  }

  @Test
  public void isEmptyCollectionReturnsTrueForEmptyCollection() {
    assertTrue(isEmpty(new ArrayList<>()));
  }

  @Test
  public void isEmptyCollectionReturnsFalseForNonEmptyCollection() {
    final List<String> list = new ArrayList<>();
    list.add("item");
    assertFalse(isEmpty(list));
  }

  @Test
  public void isEmptyMapReturnsTrueForNull() {
    assertTrue(isEmpty((Map<?, ?>) null));
  }

  @Test
  public void isEmptyMapReturnsTrueForEmptyMap() {
    assertTrue(isEmpty(new HashMap<>()));
  }

  @Test
  public void isEmptyMapReturnsFalseForNonEmptyMap() {
    final Map<String, String> map = new HashMap<>();
    map.put("key", "value");
    assertFalse(isEmpty(map));
  }

  @Test
  public void uniqueReturnsNullForNullList() {
    assertNull(CollectionUtils.unique(null));
  }

  @Test
  public void uniqueReturnsEmptyListForEmptyList() {
    assertEquals(new ArrayList<>(), CollectionUtils.unique(new ArrayList<>()));
  }

  @Test
  public void uniqueReturnsListWithUniqueElements() {
    final List<String> list = Arrays.asList("a", "b", "a", "c");
    final List<String> expected = Arrays.asList("a", "b", "c");
    assertEquals(expected, CollectionUtils.unique(list));
  }

  @Test
  public void findFirstReturnsNullForNullCollection() {
    assertNull(CollectionUtils.findFirst(null, item -> true));
  }

  @Test
  public void findFirstReturnsNullForEmptyCollection() {
    assertNull(CollectionUtils.findFirst(new ArrayList<>(), item -> true));
  }

  @Test
  public void findFirstReturnsFirstMatchingElement() {
    final List<String> list = Arrays.asList("a", "b", "c");
    assertEquals("b", CollectionUtils.findFirst(list, "b"::equals));
  }

  @Test
  public void findAllReturnsEmptyListForNullCollection() {
    assertEquals(new ArrayList<>(), CollectionUtils.findAll(null, item -> true));
  }

  @Test
  public void findAllReturnsEmptyListForEmptyCollection() {
    assertEquals(new ArrayList<>(), CollectionUtils.findAll(new ArrayList<>(), item -> true));
  }

  @Test
  public void findAllReturnsAllMatchingElements() {
    final List<String> list = Arrays.asList("a", "b", "a", "c");
    final List<String> expected = Arrays.asList("a", "a");
    assertEquals(expected, CollectionUtils.findAll(list, "a"::equals));
  }

  @Test
  public void containsIfReturnsFalseForNullCollection() {
    assertFalse(CollectionUtils.containsIf(null, item -> true));
  }

  @Test
  public void containsIfReturnsFalseForEmptyCollection() {
    assertFalse(CollectionUtils.containsIf(new ArrayList<>(), item -> true));
  }

  @Test
  public void containsIfReturnsTrueIfAnyElementMatches() {
    final List<String> list = Arrays.asList("a", "b", "c");
    assertTrue(CollectionUtils.containsIf(list, "b"::equals));
  }

  @Test
  public void containsIfReturnsFalseIfNoElementMatches() {
    final List<String> list = Arrays.asList("a", "b", "c");
    assertFalse(CollectionUtils.containsIf(list, "d"::equals));
  }
  @Test
  public void batchProcessReturnsZeroForNullCollection() {
    final int result = CollectionUtils.batchProcess(null, 10, batch -> batch.size());
    assertEquals(0, result);
  }

  @Test
  public void batchProcessReturnsZeroForEmptyCollection() {
    final int result = CollectionUtils.batchProcess(new ArrayList<>(), 10, batch -> batch.size());
    assertEquals(0, result);
  }

  @Test
  public void batchProcessThrowsExceptionForNonPositiveBatchSize() {
    final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      CollectionUtils.batchProcess(Arrays.asList(1, 2, 3), 0, batch -> batch.size());
    });
    assertEquals("The batch size must be positive.", exception.getMessage());
  }

  @Test
  public void batchProcessProcessesAllElementsInSingleBatch() {
    final List<Integer> list = Arrays.asList(1, 2, 3);
    final int result = CollectionUtils.batchProcess(list, 10, Collection::size);
    assertEquals(3, result);
  }

  @Test
  public void batchProcessProcessesElementsInMultipleBatches() {
    final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
    final int result = CollectionUtils.batchProcess(list, 2, Collection::size);
    assertEquals(5, result);
  }

  @Test
  public void batchProcessProcessesElementsInExactBatches() {
    final List<Integer> list = Arrays.asList(1, 2, 3, 4);
    final int result = CollectionUtils.batchProcess(list, 2, Collection::size);
    assertEquals(4, result);
  }

  @Test
  public void batchProcessProcessesElementsWithRemainingBatch() {
    final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
    final int result = CollectionUtils.batchProcess(list, 3, Collection::size);
    assertEquals(5, result);
  }

  @Test
  public void batchProcessCallsActionCorrectNumberOfTimesForSingleBatch() {
    final List<Integer> list = Arrays.asList(1, 2, 3);
    final AtomicInteger callCount = new AtomicInteger(0);
    CollectionUtils.batchProcess(list, 10, batch -> {
      callCount.incrementAndGet();
      return batch.size();
    });
    assertEquals(1, callCount.get());
  }

  @Test
  public void batchProcessCallsActionCorrectNumberOfTimesForMultipleBatches() {
    final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
    final AtomicInteger callCount = new AtomicInteger(0);
    CollectionUtils.batchProcess(list, 2, batch -> {
      callCount.incrementAndGet();
      return batch.size();
    });
    assertEquals(3, callCount.get());
  }

  @Test
  public void batchProcessCallsActionCorrectNumberOfTimesForExactBatches() {
    final List<Integer> list = Arrays.asList(1, 2, 3, 4);
    final AtomicInteger callCount = new AtomicInteger(0);
    CollectionUtils.batchProcess(list, 2, batch -> {
      callCount.incrementAndGet();
      return batch.size();
    });
    assertEquals(2, callCount.get());
  }

  @Test
  public void batchProcessCallsActionCorrectNumberOfTimesWithRemainingBatch() {
    final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
    final AtomicInteger callCount = new AtomicInteger(0);
    CollectionUtils.batchProcess(list, 3, batch -> {
      callCount.incrementAndGet();
      return batch.size();
    });
    assertEquals(2, callCount.get());
  }
}
