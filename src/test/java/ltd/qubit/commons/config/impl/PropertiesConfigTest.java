////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for the {@link PropertiesConfig} class.
 *
 * @author Haixing Hu
 */
public class PropertiesConfigTest {

  @SuppressWarnings("unused")
  private static final String PACKAGE_PATH = "com/qubit/commons/config/impl/";

  @SuppressWarnings("unused")
  private static final String TEST_DATA = "properties-config.properties";

  @Test
  public void testDefaultConstructor() {
    final PropertiesConfig config = new PropertiesConfig();
    assertEquals(PropertiesConfig.DEFAULT_CHARSET, config.getCharset());
    assertEquals(true, config.isEmpty());
  }

  //  @Test
  //  public void testConstructorString() {
  //    final PropertiesConfig config = new PropertiesConfig(PACKAGE_PATH
  //        + TEST_DATA);
  //    assertEquals(PropertiesConfig.DEFAULT_CHARSET, config.getCharset());
  //
  //    verifyConfig(config);
  //  }
  //
  //  private void verifyConfig(final Config config) {
  //    assertEquals("Hello world!", config.getString("com.github.prop1"));
  //
  //    assertArrayEquals(new String[] {"value 2.1", "value 2.2"},
  //        config.getStrings("com.github.prop2"));
  //
  //    assertEquals(12345, config.getInt("com.github.prop3"));
  //
  //    assertEquals("中文汉字", config.getString("com.github.prop4"));
  //  }
}
