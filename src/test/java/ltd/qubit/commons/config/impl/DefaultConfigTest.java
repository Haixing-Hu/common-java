////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.impl;

import org.junit.jupiter.api.Test;

import ltd.qubit.commons.io.serialize.XmlSerialization;
import ltd.qubit.commons.text.xml.XmlException;

/**
 * Unit test of the DefaultConfig class.
 *
 * @author Haixing Hu
 */
public class DefaultConfigTest extends ConfigTestBase {

  @Test
  public void testStringProperties() throws XmlException {
    final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class,
        STRING_CONFIG, DefaultConfigTest.class);
    verifyStringProperties(config);
  }

  @Test
  public void testCharProperties() throws XmlException {
    final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class,
        CHAR_CONFIG, DefaultConfigTest.class);
    verifyCharProperties(config);
  }

  @Test
  public void testBooleanProperties() throws XmlException {
    final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class,
        BOOLEAN_CONFIG, DefaultConfigTest.class);
    verifyBooleanProperties(config);
  }

  @Test
  public void testByteProperties() throws XmlException {
    final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class,
        BYTE_CONFIG, DefaultConfigTest.class);
    verifyByteProperties(config);
  }

  @Test
  public void testClassProperties() throws XmlException {
    final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class,
        CLASS_CONFIG, DefaultConfigTest.class);
    verifyClassProperties(config);
  }

  @Test
  public void testInstanceProperties() throws XmlException {
    final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class,
        INSTANCE_CONFIG, DefaultConfigTest.class);
    verifyInstanceProperties(config);
  }
}