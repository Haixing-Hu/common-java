////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RawEnumSerializerTest {

  enum TestEnum {
    VALUE_1,
    VALUE_2,
  }

  enum TestEnum2 {
    ENUM_1("x1"),
    ENUM_2("x2");

    private final String value;

    TestEnum2(final String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }
  }

  @JsonAutoDetect(
      fieldVisibility = Visibility.ANY,
      getterVisibility = Visibility.NONE,
      setterVisibility = Visibility.NONE
  )
  static class TheFoo {
    @JsonSerialize(using = RawEnumSerializer.class)
    private TestEnum value;

    @JsonSerialize(using = RawEnumSerializer.class)
    private TestEnum2 value2;

    public TestEnum getValue() {
      return value;
    }

    public void setValue(final TestEnum value) {
      this.value = value;
    }

    public TestEnum2 getValue2() {
      return value2;
    }

    public void setValue2(final TestEnum2 value2) {
      this.value2 = value2;
    }
  }

  @Test
  public void testJsonSerialize() throws JsonProcessingException {
    final TheFoo foo = new TheFoo();
    foo.setValue(TestEnum.VALUE_1);
    foo.setValue2(TestEnum2.ENUM_2);
    final JsonMapper mapper = new JsonMapper();
    final String json = mapper.writeValueAsString(foo);
    System.out.println(json);
    assertEquals("{\"value\":\"VALUE_1\",\"value2\":\"x2\"}", json);
  }

  @Test
  public void testXmlSerialize() throws JsonProcessingException {
    final TheFoo foo = new TheFoo();
    foo.setValue(TestEnum.VALUE_1);
    foo.setValue2(TestEnum2.ENUM_2);
    final XmlMapper mapper = new XmlMapper();
    final String json = mapper.writeValueAsString(foo);
    System.out.println(json);
    assertEquals("<TheFoo><value>VALUE_1</value><value2>x2</value2></TheFoo>", json);
  }
}
