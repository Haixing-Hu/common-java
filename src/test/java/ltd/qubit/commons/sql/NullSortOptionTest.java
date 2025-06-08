////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.sql;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NullSortOptionTest {

  @Test
  public void testCompare() {
    assertEquals(-1, NullSortOption.NULL_FIRST.compare(null, 100l, SortOrder.ASC));
    assertEquals(0, NullSortOption.NULL_FIRST.compare(null, null, SortOrder.ASC));
    assertEquals(+1, NullSortOption.NULL_FIRST.compare(100l, null, SortOrder.ASC));
    assertEquals(-1, NullSortOption.NULL_FIRST.compare(null, 100l, SortOrder.DESC));
    assertEquals(0, NullSortOption.NULL_FIRST.compare(null, null, SortOrder.DESC));
    assertEquals(+1, NullSortOption.NULL_FIRST.compare(100l, null, SortOrder.DESC));

    assertEquals(+1, NullSortOption.NULL_LAST.compare(null, 100l, SortOrder.ASC));
    assertEquals(0, NullSortOption.NULL_LAST.compare(null, null, SortOrder.ASC));
    assertEquals(-1, NullSortOption.NULL_LAST.compare(100l, null, SortOrder.ASC));
    assertEquals(+1, NullSortOption.NULL_LAST.compare(null, 100l, SortOrder.DESC));
    assertEquals(0, NullSortOption.NULL_LAST.compare(null, null, SortOrder.DESC));
    assertEquals(-1, NullSortOption.NULL_LAST.compare(100l, null, SortOrder.DESC));

    assertEquals(-1, NullSortOption.NULL_SMALLEST.compare(null, 100l, SortOrder.ASC));
    assertEquals(0, NullSortOption.NULL_SMALLEST.compare(null, null, SortOrder.ASC));
    assertEquals(+1, NullSortOption.NULL_SMALLEST.compare(100l, null, SortOrder.ASC));
    assertEquals(+1, NullSortOption.NULL_SMALLEST.compare(null, 100l, SortOrder.DESC));
    assertEquals(0, NullSortOption.NULL_SMALLEST.compare(null, null, SortOrder.DESC));
    assertEquals(-1, NullSortOption.NULL_SMALLEST.compare(100l, null, SortOrder.DESC));

    assertEquals(+1, NullSortOption.NULL_LARGEST.compare(null, 100l, SortOrder.ASC));
    assertEquals(0, NullSortOption.NULL_LARGEST.compare(null, null, SortOrder.ASC));
    assertEquals(-1, NullSortOption.NULL_LARGEST.compare(100l, null, SortOrder.ASC));
    assertEquals(-1, NullSortOption.NULL_LARGEST.compare(null, 100l, SortOrder.DESC));
    assertEquals(0, NullSortOption.NULL_LARGEST.compare(null, null, SortOrder.DESC));
    assertEquals(+1, NullSortOption.NULL_LARGEST.compare(100l, null, SortOrder.DESC));
  }
}