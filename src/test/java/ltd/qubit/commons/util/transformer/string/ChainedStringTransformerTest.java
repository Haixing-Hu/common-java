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
 * Unit test for the {@link ChainedStringTransformer} class.
 *
 * @author Haixing Hu
 */
public class ChainedStringTransformerTest {

  @Test
  public void testXmlSerialization() throws Exception {

    String xml = "<chained-string-transformer>\n"
        + "  <transformers>\n"
        + "    <lowercase-transformer>\n"
        + "      <locale>zh_CN</locale>\n"
        + "    </lowercase-transformer>\n"
        + "    <uppercase-transformer>\n"
        + "      <locale>zh_CN</locale>\n"
        + "    </uppercase-transformer>\n"
        + "    <strip-start-transformer>\n"
        + "    </strip-start-transformer>\n"
        + "    <strip-end-transformer>\n"
        + "    </strip-end-transformer>\n"
        + "    <strip-transformer>\n"
        + "    </strip-transformer>\n"
        + "    <regex-transformer>\n"
        + "      <input-pattern>([0-9]+)-([0-9]+)-([0-9]+)</input-pattern>\n"
        + "      <output-pattern>[year: ${1}, month: ${2}, day: ${3}, day again: ${3}]</output-pattern>\n"
        + "    </regex-transformer>\n"
        + "  </transformers>\n"
        + "</chained-string-transformer>";
    ChainedStringTransformer tr = new ChainedStringTransformer();
    tr.add(new LowercaseTransformer(new Locale("zh", "CN")))
        .add(new UppercaseTransformer(new Locale("zh", "CN")))
        .add(new StripStartTransformer())
        .add(new StripEndTransformer())
        .add(new StripTransformer())
        .add(new RegexTransformer("([0-9]+)-([0-9]+)-([0-9]+)",
            "[year: ${1}, month: ${2}, day: ${3}, day again: ${3}]"));
    assertXmlSerializeEquals(ChainedStringTransformer.class, tr, xml);
    assertXmlDeserializeEquals(ChainedStringTransformer.class, xml, tr);

    xml = "<chained-string-transformer>"
        + "</chained-string-transformer>";
    tr = new ChainedStringTransformer();
    assertXmlSerializeEquals(ChainedStringTransformer.class, tr, xml);
    assertXmlDeserializeEquals(ChainedStringTransformer.class, xml, tr);
  }

}
