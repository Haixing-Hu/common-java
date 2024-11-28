////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.net;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test of the {@link DefaultPorts} class.
 *
 * @author Haixing Hu
 */
public class DefaultPorsTest {

  @Test
  public void testDefaultPorts() {
    assertEquals(21, DefaultPorts.get("ftp"));
    assertEquals(70, DefaultPorts.get("gopher"));
    assertEquals(80, DefaultPorts.get("http"));
    assertEquals(443, DefaultPorts.get("https"));
  }

}
