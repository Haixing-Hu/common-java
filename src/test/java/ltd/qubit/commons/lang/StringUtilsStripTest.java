////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit test for the {@link StringUtils} class.
 *
 * @author Haixing Hu
 */
public class StringUtilsStripTest {

  @Test
  public void testStripAll() {
    assertNull(StringUtils.stripAll(null));
    assertEquals("", StringUtils.stripAll(""));
    assertEquals("", StringUtils.stripAll("   "));
    assertEquals("abc", StringUtils.stripAll("  abc"));
    assertEquals("abc", StringUtils.stripAll("  abc  "));
    assertEquals("abc", StringUtils.stripAll("  a b c"));
    assertEquals("abc", StringUtils.stripAll("  a\f  b\nc\r"));
  }
}
