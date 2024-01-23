////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * Unit test for the {@link ChainedStringTransformer} class.
 *
 * @author Haixing Hu
 */
public class ChainedStringTransformerTest {

  @Test
  public void testXmlSerialization() throws Exception {

    String xml = "<chained-string-transformer>\n"
        + "  <transformers>\n"
        + "    <transformer>\n"
        + "    <lowercase-transformer>\n"
        + "      <locale>zh_CN</locale>\n"
        + "    </lowercase-transformer>\n"
        + "    </transformer>\n"
        + "    <transformer>\n"
        + "    <uppercase-transformer>\n"
        + "      <locale>zh_CN</locale>\n"
        + "    </uppercase-transformer>\n"
        + "    </transformer>\n"
        + "    <transformer>\n"
        + "    <strip-start-transformer>\n"
        + "    </strip-start-transformer>\n"
        + "    </transformer>\n"
        + "    <transformer>\n"
        + "    <strip-end-transformer>\n"
        + "    </strip-end-transformer>\n"
        + "    </transformer>\n"
        + "    <transformer>\n"
        + "    <strip-transformer>\n"
        + "    </strip-transformer>\n"
        + "    </transformer>\n"
        + "    <transformer>\n"
        + "    <regex-transformer>\n"
        + "      <input-pattern>([0-9]+)-([0-9]+)-([0-9]+)</input-pattern>\n"
        + "      <output-pattern>[year: ${1}, month: ${2}, day: ${3}, day again: ${3}]</output-pattern>\n"
        + "    </regex-transformer>\n"
        + "    </transformer>\n"
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
        + "<transformers/>"
        + "</chained-string-transformer>";
    tr = new ChainedStringTransformer();
    assertXmlSerializeEquals(ChainedStringTransformer.class, tr, xml);
    assertXmlDeserializeEquals(ChainedStringTransformer.class, xml, tr);
  }

  // @Test
  // public void test() throws JAXBException {
  //   final ChainedStringTransformer tr = new ChainedStringTransformer();
  //   tr.add(new LowercaseTransformer(new Locale("zh", "CN")))
  //     .add(new UppercaseTransformer(new Locale("zh", "CN")))
  //     .add(new StripStartTransformer())
  //     .add(new StripEndTransformer())
  //     .add(new StripTransformer())
  //     .add(new RegexTransformer("([0-9]+)-([0-9]+)-([0-9]+)",
  //         "[year: ${1}, month: ${2}, day: ${3}, day again: ${3}]"));
  //   final String xml = JaxbUtils.marshal(tr, ChainedStringTransformer.class);
  //   System.out.println(xml);
  // }
  //
  // @Test
  // public void test2() throws JsonProcessingException {
  //   final ChainedStringTransformer tr = new ChainedStringTransformer();
  //   tr.add(new LowercaseTransformer(new Locale("zh", "CN")))
  //     .add(new UppercaseTransformer(new Locale("zh", "CN")))
  //     .add(new StripStartTransformer())
  //     .add(new StripEndTransformer())
  //     .add(new StripTransformer())
  //     .add(new RegexTransformer("([0-9]+)-([0-9]+)-([0-9]+)",
  //         "[year: ${1}, month: ${2}, day: ${3}, day again: ${3}]"));
  //   final XmlMapper mapper = new CustomizedXmlMapper();
  //   final String xml = mapper.writeValueAsString(tr);
  //   System.out.println(xml);
  // }
}
