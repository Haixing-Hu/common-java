////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.string;

import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.test.JacksonXmlTestUtils.assertXmlDeserializeEquals;
import static ltd.qubit.commons.test.JacksonXmlTestUtils.assertXmlSerializeEquals;

/**
 * Unit test for the {@link StripEndTransformer} class.
 *
 * @author Haixing Hu
 */
public class StripEndTransformerTest {

  @Test
  public void testXmlSerialization() throws Exception {

    final String xml = "<strip-end-transformer>"
        + "</strip-end-transformer>";
    final StripEndTransformer transformer = new StripEndTransformer();
    assertXmlSerializeEquals(StripEndTransformer.class, transformer, xml);
    assertXmlDeserializeEquals(StripEndTransformer.class, xml, transformer);
  }

}