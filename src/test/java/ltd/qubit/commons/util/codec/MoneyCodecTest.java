////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.codec;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit test of the {@link MoneyCodec}.
 *
 * @author Haixing Hu
 */
public class MoneyCodecTest {

  @Test
  public void testFormat_default_constructor() throws Exception {
    final MoneyCodec c1 = new MoneyCodec();

    final BigDecimal v1 = new BigDecimal("1234.983");
    assertEquals("1234.9830", c1.encode(v1));

    final BigDecimal v2 = new BigDecimal("1.0");
    assertEquals("1.0000", c1.encode(v2));

    final BigDecimal v3 = new BigDecimal("0");
    assertEquals("0.0000", c1.encode(v3));

    final BigDecimal v4 = new BigDecimal("-123");
    assertEquals("-123.0000", c1.encode(v4));

    final BigDecimal v5 = new BigDecimal("-0");
    assertEquals("0.0000", c1.encode(v5));

    // round the number
    final BigDecimal v6 = new BigDecimal("1.23454");
    assertEquals("1.2345", c1.encode(v6));

    final BigDecimal v7 = new BigDecimal("1.2345555");
    assertEquals("1.2346", c1.encode(v7));
  }

  @Test
  public void testFormat_constructor_precision() throws Exception {
    final MoneyCodec c1 = new MoneyCodec(2);

    final BigDecimal v1 = new BigDecimal("1234.983");
    assertEquals("1234.98", c1.encode(v1));

    final BigDecimal v2 = new BigDecimal("1.0");
    assertEquals("1.00", c1.encode(v2));

    final BigDecimal v3 = new BigDecimal("0");
    assertEquals("0.00", c1.encode(v3));

    final BigDecimal v4 = new BigDecimal("-123");
    assertEquals("-123.00", c1.encode(v4));

    final BigDecimal v5 = new BigDecimal("-0");
    assertEquals("0.00", c1.encode(v5));

    // round the number
    final BigDecimal v6 = new BigDecimal("1.235");
    assertEquals("1.24", c1.encode(v6));

    final BigDecimal v7 = new BigDecimal("1.234");
    assertEquals("1.23", c1.encode(v7));
  }

  @Test
  public void testParse_default_constructor() throws Exception {
    final MoneyCodec c1 = new MoneyCodec();

    final BigDecimal v1 = c1.decode("1234.983");
    assertEquals(new BigDecimal("1234.9830"), v1);

    final BigDecimal v2 = c1.decode("1.0");
    assertEquals(new BigDecimal("1.0000"), v2);

    final BigDecimal v3 = c1.decode("0");
    assertEquals(new BigDecimal("0.0000"), v3);

    final BigDecimal v4 = c1.decode("-123");
    assertEquals(new BigDecimal("-123.0000"), v4);

    final BigDecimal v5 = c1.decode("-0");
    assertEquals(new BigDecimal("0.0000"), v5);

    final BigDecimal v6 = c1.decode("1.23454");
    assertEquals(new BigDecimal("1.2345"), v6);

    final BigDecimal v7 = c1.decode("1.2345555");
    assertEquals(new BigDecimal("1.2346"), v7);

    final BigDecimal v8 = c1.decode("0");
    assertEquals(new BigDecimal("0.0000"), v8);
    assertEquals("0.0000", v8.toString());
  }

  @Test
  public void testParse_constructor_precision() throws Exception {
    final MoneyCodec c1 = new MoneyCodec(2);

    final BigDecimal v1 = c1.decode("1234.983");
    assertEquals(new BigDecimal("1234.98"), v1);

    final BigDecimal v2 = c1.decode("1.0");
    assertEquals(new BigDecimal("1.00"), v2);

    final BigDecimal v3 = c1.decode("0");
    assertEquals(new BigDecimal("0.00"), v3);

    final BigDecimal v4 = c1.decode("-123");
    assertEquals(new BigDecimal("-123.00"), v4);

    final BigDecimal v5 = c1.decode("-0");
    assertEquals(new BigDecimal("0.00"), v5);

    final BigDecimal v6 = c1.decode("1.23454");
    assertEquals(new BigDecimal("1.23"), v6);

    final BigDecimal v7 = c1.decode("1.23554");
    assertEquals(new BigDecimal("1.24"), v7);
  }

  @Test
  public void testParse_emptyString() throws DecodingException {
    final MoneyCodec c1 = new MoneyCodec(4);
    assertNull(c1.decode(""));
  }

  // TODO: test the codec which enable grouping

}
