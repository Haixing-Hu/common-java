////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.datastructure.list.StringList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StringListCodecTest {

  @Test
  public void testEncodeDecode() throws Exception {
    final StringListCodec codec = new StringListCodec();

    final StringList l1 = new StringList();
    l1.add("a");
    l1.add("b");
    l1.add("ab\"cd\"ef");
    l1.add("ab'cd'ef");
    l1.add("ab'cd'\nef");
    final String s1 = codec.encode(l1);
    System.out.println(s1);
    final StringList r1 = codec.decode(s1);
    assertEquals(l1, r1);

    final StringList l2 = new StringList();
    final String s2 = codec.encode(l2);
    System.out.println(s2);
    final StringList r2 = codec.decode(s2);
    assertEquals(l2, r2);

    assertNull(codec.encode(null));
    assertNull(codec.decode(null));
  }
}