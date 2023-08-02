////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import org.junit.jupiter.api.Test;

import static java.nio.charset.StandardCharsets.UTF_8;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Base64CodecTest {

  @Test
  public void testEncode() throws EncodingException {
    final Base64Codec codec = new Base64Codec();

    final String s1 = "Hello world!";
    final String b1 = codec.encode(s1.getBytes(UTF_8));
    assertEquals("SGVsbG8gd29ybGQh", b1);

    assertEquals("", codec.encode(new byte[0]));
    assertNull(codec.encode(null));
  }

  @Test
  public void testDecode() throws DecodingException {
    final Base64Codec codec = new Base64Codec();

    final String b1 = "SGVsbG8gd29ybGQh";
    final String s1 = new String(codec.decode(b1), UTF_8);
    assertEquals("Hello world!", s1);

    assertArrayEquals(new byte[0], codec.decode(""));
    assertNull(codec.decode(null));
  }
}
