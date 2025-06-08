////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberFormatTest {

  @Test
  public void testParseIntWithGrouping() {
    final NumberFormat nf = new NumberFormat();
    nf.getOptions().setGrouping(true);
    assertEquals(5380, nf.parseInt("5,380"));
    assertEquals(2115380, nf.parseInt("2,115,380"));
  }
}