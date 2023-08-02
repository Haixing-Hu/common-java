////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CharsetCodecTest {

  @Test
  public void testEncode() {
    final CharsetCodec codec = new CharsetCodec();
    assertEquals("UTF-8", codec.encode(StandardCharsets.UTF_8));
    assertEquals("ISO-8859-1", codec.encode(StandardCharsets.ISO_8859_1));
    assertEquals("US-ASCII", codec.encode(StandardCharsets.US_ASCII));
    assertEquals("UTF-16", codec.encode(StandardCharsets.UTF_16));
    assertEquals("UTF-16LE", codec.encode(StandardCharsets.UTF_16LE));
    assertEquals("UTF-16BE", codec.encode(StandardCharsets.UTF_16BE));
  }

  @Test
  public void testDecode() throws DecodingException {
    final CharsetCodec codec = new CharsetCodec();
    assertEquals(StandardCharsets.UTF_8, codec.decode("UTF-8"));
    assertEquals(StandardCharsets.ISO_8859_1, codec.decode("ISO-8859-1"));
    assertEquals(StandardCharsets.US_ASCII, codec.decode("US-ASCII"));
    assertEquals(StandardCharsets.UTF_16, codec.decode("UTF-16"));
    assertEquals(StandardCharsets.UTF_16LE, codec.decode("UTF-16LE"));
    assertEquals(StandardCharsets.UTF_16BE, codec.decode("UTF-16BE"));
  }
}
