////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.xml;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test of the {@link XmlUtils} class.
 *
 * @author Haixing Hu
 */
public class XmlUtilsTest {

  @Test
  public void testParse() throws XmlException {
    final Document doc = XmlUtils.parse("utf8-with-bom.xml", XmlUtilsTest.class);
    assertNotNull(doc);
    XmlUtils.print(doc, System.out);
  }
}
