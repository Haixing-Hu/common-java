////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
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
 * Unit test for the {@link UppercaseTransformer} class.
 *
 * @author Haixing Hu
 */
public class UppercaseTransformerTest {

  @Test
  public void testXmlSerialization() throws Exception {

    String xml = "<uppercase-transformer>"
        + "  <locale>zh_CN</locale>"
        + "</uppercase-transformer>";
    UppercaseTransformer transformer = new UppercaseTransformer(
        new Locale("zh", "CN"));
    assertXmlSerializeEquals(UppercaseTransformer.class, transformer, xml);
    assertXmlDeserializeEquals(UppercaseTransformer.class, xml, transformer);

    xml = "<uppercase-transformer>"
        + "  <locale>en</locale>"
        + "</uppercase-transformer>";
    transformer = new UppercaseTransformer(new Locale("en"));
    assertXmlSerializeEquals(UppercaseTransformer.class, transformer, xml);
    assertXmlDeserializeEquals(UppercaseTransformer.class, xml, transformer);
  }

}
