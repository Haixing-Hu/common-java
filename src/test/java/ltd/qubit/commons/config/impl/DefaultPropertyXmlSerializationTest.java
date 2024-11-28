////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.impl;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import ltd.qubit.commons.io.io.serialize.XmlSerialization;
import ltd.qubit.commons.text.xml.XmlException;
import ltd.qubit.commons.text.xml.XmlUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for the XML serialization of {@link DefaultProperty}.
 *
 * @author Haixing Hu
 */
public class DefaultPropertyXmlSerializationTest {

  private static final String BUG1 = "defaultproperty-bug1.xml";

  @Test
  public void testDeserializeBug1() throws XmlException {
    final Document doc = XmlUtils
            .parse(BUG1, DefaultPropertyXmlSerializationTest.class);

    final DefaultProperty prop = XmlSerialization.deserialize(DefaultProperty.class,
        doc.getDocumentElement());
    assertEquals("", prop.getDescription());

    final String actual = XmlSerialization.serialize(DefaultProperty.class, prop);
    final DefaultProperty prop2 = XmlSerialization.deserialize(DefaultProperty.class, actual);
    assertEquals("", prop2.getDescription());
    assertEquals(prop, prop2);
  }

}
