////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
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
 * Unit test for the {@link StripStartTransformer} class.
 *
 * @author Haixing Hu
 */
public class StripStartTransformerTest {

  @Test
  public void testXmlSerialization() throws Exception {

    final String xml = "<strip-start-transformer>"
            + "</strip-start-transformer>";
    final StripStartTransformer transformer = new StripStartTransformer();
    assertXmlSerializeEquals(StripStartTransformer.class, transformer, xml);
    assertXmlDeserializeEquals(StripStartTransformer.class, xml, transformer);
  }

}
