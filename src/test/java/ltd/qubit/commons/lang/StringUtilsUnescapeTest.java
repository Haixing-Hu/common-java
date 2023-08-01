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

/**
 * Unit test of the {@link StringUtils#unescape()} functions.
 *
 * @author Haixing Hu
 */
public class StringUtilsUnescapeTest {

  @Test
  public void testUnescape() {
    assertEquals("", StringUtils.unescape("", '\\'));

    assertEquals("h\\ello' wo%rld'",
        StringUtils.unescape("h\\\\ello\\' wo\\%rld\\'", '\\'));

    assertEquals("h%ello' wo%rld'",
        StringUtils.unescape("h%%ello%' wo%%rld%'", '%'));

    assertEquals("h%ello' wo%rld'",
        StringUtils.unescape("h%%ello%' wo%%rld%'%", '%'));
  }

  @Test
  public void testUnescapeStringBuilder() {
    final StringBuilder builder1 = new StringBuilder("abc");
    StringUtils.unescape("", '\\', builder1);
    assertEquals("abc", builder1.toString());

    final StringBuilder builder2 = new StringBuilder();
    StringUtils.unescape("h\\\\ello\\' wo\\%rld\\'", '\\', builder2);
    assertEquals("h\\ello' wo%rld'", builder2.toString());

    final StringBuilder builder3 = new StringBuilder("xyz");
    StringUtils.unescape("h%%ello%' wo%%rld%'", '%', builder3);
    assertEquals("xyzh%ello' wo%rld'", builder3.toString());

    final StringBuilder builder4 = new StringBuilder("xyz");
    StringUtils.unescape("h%%ello%' wo%%rld%'%", '%', builder4);
    assertEquals("xyzh%ello' wo%rld'", builder4.toString());
  }
}
