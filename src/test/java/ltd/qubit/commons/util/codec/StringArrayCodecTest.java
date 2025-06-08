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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StringArrayCodecTest {

  @Test
  public void testEncodeDecode() throws Exception {
    final StringArrayCodec codec = new StringArrayCodec();

    final String[] l1 = new String[] {"a", "b", "ab\"cd\"ef", "ab'cd'ef", "ab'cd'\nef"};
    final String s1 = codec.encode(l1);
    System.out.println(s1);
    final String[] r1 = codec.decode(s1);
    assertArrayEquals(l1, r1);

    final String[] l2 = new String[0];
    final String s2 = codec.encode(l2);
    System.out.println(s2);
    final String[] r2 = codec.decode(s2);
    assertArrayEquals(l2, r2);

    assertNull(codec.encode(null));
    assertNull(codec.decode(null));
  }
}