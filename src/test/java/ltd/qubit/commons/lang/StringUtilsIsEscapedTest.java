////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test of the {@link StringUtils#isEscaped(String, char, int)} function.
 *
 * @author Haixing Hu
 */
public class StringUtilsIsEscapedTest {

  @Test
  public void testIsEscaped() {
    assertEquals(false, StringUtils.isEscaped("\\", '\\', 0));
    assertEquals(false, StringUtils.isEscaped("x\\\\y", '\\', 3));
    assertEquals(true, StringUtils.isEscaped("x\\'", '\\', 2));
    assertEquals(true, StringUtils.isEscaped("x\\'\\'", '\\', 4));
  }
}
