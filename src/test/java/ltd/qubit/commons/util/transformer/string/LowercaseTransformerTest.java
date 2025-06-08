////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.util.transformer.string;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import static ltd.qubit.commons.test.JacksonXmlTestUtils.assertXmlDeserializeEquals;
import static ltd.qubit.commons.test.JacksonXmlTestUtils.assertXmlSerializeEquals;

/**
 * Unit test for the {@link LowercaseTransformer} class.
 *
 * @author Haixing Hu
 */
public class LowercaseTransformerTest {

  @Test
  public void testXmlSerialization() throws Exception {

    String xml = "<lowercase-transformer>"
        + "  <locale>zh_CN</locale>"
        + "</lowercase-transformer>";
    LowercaseTransformer tr = new LowercaseTransformer(new Locale("zh", "CN"));
    assertXmlSerializeEquals(LowercaseTransformer.class, tr, xml);
    assertXmlDeserializeEquals(LowercaseTransformer.class, xml, tr);

    xml = "<lowercase-transformer>"
        + "  <locale>en</locale>"
        + "</lowercase-transformer>";
    tr = new LowercaseTransformer(new Locale("en"));
    assertXmlSerializeEquals(LowercaseTransformer.class, tr, xml);
    assertXmlDeserializeEquals(LowercaseTransformer.class, xml, tr);
  }

}