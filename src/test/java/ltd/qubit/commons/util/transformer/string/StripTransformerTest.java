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
 * Unit test for the {@link StripTransformer} class.
 *
 * @author Haixing Hu
 */
public class StripTransformerTest {

  @Test
  public void testXmlSerialization() throws Exception {

    final String xml = "<strip-transformer>"
            + "</strip-transformer>";
    final StripTransformer transformer = new StripTransformer();
    assertXmlSerializeEquals(StripTransformer.class, transformer, xml);
    assertXmlDeserializeEquals(StripTransformer.class, xml, transformer);
  }

}
