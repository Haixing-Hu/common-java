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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.util.pair.KeyValuePairList;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static ltd.qubit.commons.datastructure.CollectionUtils.constructSameTypeOfCollection;

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
}
