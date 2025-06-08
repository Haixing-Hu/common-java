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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test of the {@link BooleanCodec}.
 *
 * @author Haixing Hu
 */
public class BooleanCodecTest {

  @Test
  public void testDefaultConfiguration() {
    assertEquals("true", BooleanCodec.DEFAULT_TRUE_VALUE);
    assertEquals("false", BooleanCodec.DEFAULT_FALSE_VALUE);
    assertEquals(false, BooleanCodec.DEFAULT_EMPTY_FOR_NULL);
    assertEquals(true, BooleanCodec.DEFAULT_IGNORE_CASE);
    assertEquals(true, BooleanCodec.DEFAULT_TRIM);
    assertEquals(false, BooleanCodec.DEFAULT_STRICT_FALSE);
  }

  @Test
  public void testDefaultConstructor() {
    final BooleanCodec codec = new BooleanCodec();
    assertEquals("true", codec.getTrueValue());
    assertEquals("false", codec.getFalseValue());
    assertEquals(false, codec.isEmptyForNull());
    assertEquals(true, codec.isIgnoreCase());
    assertEquals(true, codec.isTrim());
    assertEquals(false, codec.isStrictFalse());
  }

  @Test
  public void tetConstructor_String_String() {
    final BooleanCodec codec = new BooleanCodec("1", "0");
    assertEquals("1", codec.getTrueValue());
    assertEquals("0", codec.getFalseValue());
    assertEquals(false, codec.isEmptyForNull());
    assertEquals(true, codec.isIgnoreCase());
    assertEquals(true, codec.isTrim());
    assertEquals(false, codec.isStrictFalse());
  }

  @Test
  public void testEncode() throws Exception {
    final BooleanCodec codec = new BooleanCodec();

    assertEquals("true", codec.encode(true));
    assertEquals("false", codec.encode(false));
    assertNull(codec.encode(null));

    codec.setEmptyForNull(true);
    assertEquals("true", codec.encode(true));
    assertEquals("false", codec.encode(false));
    assertEquals("", codec.encode(null));

    codec.setTrueValue("1");
    codec.setFalseValue("0");
    assertEquals("1", codec.encode(true));
    assertEquals("0", codec.encode(false));
    assertEquals("", codec.encode(null));

    codec.setEmptyForNull(false);
    assertEquals("1", codec.encode(true));
    assertEquals("0", codec.encode(false));
    assertNull(codec.encode(null));
  }

  @Test
  public void testDecode() throws Exception  {
    final BooleanCodec codec = new BooleanCodec();
    assertNull(codec.decode(null));
    assertEquals(true, codec.decode("true"));
    assertEquals(true, codec.decode("  true  "));
    assertEquals(true, codec.decode("True"));
    assertEquals(true, codec.decode("TRUE"));
    assertEquals(true, codec.decode("TrUe"));
    assertEquals(true, codec.decode("  TrUe  "));
    assertEquals(false, codec.decode("false"));
    assertEquals(false, codec.decode("  false  "));
    assertEquals(false, codec.decode("False"));
    assertEquals(false, codec.decode("FALSE"));
    assertEquals(false, codec.decode("FaLse"));
    assertEquals(false, codec.decode("  FaLse  "));
    assertEquals(false, codec.decode("  xxxx  "));
    assertEquals(false, codec.decode(""));
    assertEquals(false, codec.decode("     "));

    codec.setEmptyForNull(true);
    codec.setIgnoreCase(true);
    codec.setTrim(true);
    codec.setStrictFalse(false);
    assertNull(codec.decode(null));
    assertEquals(true, codec.decode("true"));
    assertEquals(true, codec.decode("  true  "));
    assertEquals(true, codec.decode("True"));
    assertEquals(true, codec.decode("TRUE"));
    assertEquals(true, codec.decode("TrUe"));
    assertEquals(true, codec.decode("  TrUe  "));
    assertEquals(false, codec.decode("false"));
    assertEquals(false, codec.decode("  false  "));
    assertEquals(false, codec.decode("False"));
    assertEquals(false, codec.decode("FALSE"));
    assertEquals(false, codec.decode("FaLse"));
    assertEquals(false, codec.decode("  FaLse  "));
    assertEquals(false, codec.decode("  xxxx  "));
    assertNull(codec.decode(""));
    assertNull(codec.decode("     "));

    codec.setEmptyForNull(true);
    codec.setIgnoreCase(true);
    codec.setTrim(false);
    codec.setStrictFalse(false);
    assertNull(codec.decode(null));
    assertEquals(true, codec.decode("true"));
    assertEquals(false, codec.decode("  true  "));
    assertEquals(true, codec.decode("True"));
    assertEquals(true, codec.decode("TRUE"));
    assertEquals(true, codec.decode("TrUe"));
    assertEquals(false, codec.decode("  TrUe  "));
    assertEquals(false, codec.decode("false"));
    assertEquals(false, codec.decode("  false  "));
    assertEquals(false, codec.decode("False"));
    assertEquals(false, codec.decode("FALSE"));
    assertEquals(false, codec.decode("FaLse"));
    assertEquals(false, codec.decode("  FaLse  "));
    assertEquals(false, codec.decode("  xxxx  "));
    assertNull(codec.decode(""));
    assertEquals(false, codec.decode("     "));

    codec.setEmptyForNull(true);
    codec.setIgnoreCase(false);
    codec.setTrim(true);
    codec.setStrictFalse(false);
    assertNull(codec.decode(null));
    assertEquals(true, codec.decode("true"));
    assertEquals(true, codec.decode("  true  "));
    assertEquals(false, codec.decode("True"));
    assertEquals(false, codec.decode("TRUE"));
    assertEquals(false, codec.decode("TrUe"));
    assertEquals(false, codec.decode("  TrUe  "));
    assertEquals(false, codec.decode("false"));
    assertEquals(false, codec.decode("  false  "));
    assertEquals(false, codec.decode("False"));
    assertEquals(false, codec.decode("FALSE"));
    assertEquals(false, codec.decode("FaLse"));
    assertEquals(false, codec.decode("  FaLse  "));
    assertEquals(false, codec.decode("  xxxx  "));
    assertNull(codec.decode(""));
    assertNull(codec.decode("     "));

    codec.setEmptyForNull(true);
    codec.setIgnoreCase(true);
    codec.setTrim(true);
    codec.setStrictFalse(true);
    assertNull(codec.decode(null));
    assertEquals(true, codec.decode("true"));
    assertEquals(true, codec.decode("  true  "));
    assertEquals(true, codec.decode("True"));
    assertEquals(true, codec.decode("TRUE"));
    assertEquals(true, codec.decode("TrUe"));
    assertEquals(true, codec.decode("  TrUe  "));
    assertEquals(false, codec.decode("false"));
    assertEquals(false, codec.decode("  false  "));
    assertEquals(false, codec.decode("False"));
    assertEquals(false, codec.decode("FALSE"));
    assertEquals(false, codec.decode("FaLse"));
    assertEquals(false, codec.decode("  FaLse  "));
    assertNull(codec.decode(""));
    try {
      codec.decode("  xxxx  ");
      fail("Should throw");
    } catch (final DecodingException e) {
      // pass
    }

    try {
      codec.setTrim(false);
      codec.decode("     ");
      fail("Should throw");
    } catch (final DecodingException e) {
      // pass
    }
  }
}