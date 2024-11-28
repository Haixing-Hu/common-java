////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.jackson.serializer.RawEnumSerializer;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RawEnumDeserializerTest {

  enum ValueEnum {
    VALUE_1,
    VALUE_2,
    the_Value_3,
  }

  @JsonAutoDetect(
      fieldVisibility = Visibility.ANY,
      getterVisibility = Visibility.NONE,
      setterVisibility = Visibility.NONE
  )
  static class TheFoo {
    @JsonSerialize(using = RawEnumSerializer.class)
    @JsonDeserialize(using = RawEnumDeserializer.class)
    private ValueEnum value;

    public ValueEnum getValue() {
      return value;
    }

    public void setValue(final ValueEnum value) {
      this.value = value;
    }

    public boolean equals(final Object o) {
      if (this == o) {
        return true;
      }
      if ((o == null) || (getClass() != o.getClass())) {
        return false;
      }
      final TheFoo other = (TheFoo) o;
      return Equality.equals(value, other.value);
    }

    public int hashCode() {
      final int multiplier = 7;
      int result = 3;
      result = Hash.combine(result, multiplier, value);
      return result;
    }

    public String toString() {
      return new ToStringBuilder(this)
          .append("value", value)
          .toString();
    }
  }

  @Test
  public void testJsonDeserialize() throws JsonProcessingException {
    final TheFoo foo = new TheFoo();
    foo.setValue(ValueEnum.VALUE_1);
    final JsonMapper mapper = new JsonMapper();
    String json = mapper.writeValueAsString(foo);
    System.out.println(json);
    assertEquals("{\"value\":\"VALUE_1\"}", json);
    json = "{\"value\":\"VALUE_1\"}";
    final TheFoo f1 = mapper.readValue(json, TheFoo.class);
    assertEquals(foo, f1);
    json = "{\"value\":\"value_1\"}";
    final TheFoo f2 = mapper.readValue(json, TheFoo.class);
    assertEquals(foo, f2);
    json = "{\"value\":\"value-1\"}";
    final TheFoo f3 = mapper.readValue(json, TheFoo.class);
    assertEquals(foo, f3);
    json = "{\"value\":\"vaLue-1\"}";
    final TheFoo f4 = mapper.readValue(json, TheFoo.class);
    assertEquals(foo, f4);

    json = "{\"value\":\"\"}";
    final TheFoo f5 = mapper.readValue(json, TheFoo.class);
    foo.setValue(null);
    assertEquals(foo, f5);

    json = "{\"value\":\"  \"}";
    final TheFoo f6 = mapper.readValue(json, TheFoo.class);
    foo.setValue(null);
    assertEquals(foo, f6);

    json = "{}";
    final TheFoo f7 = mapper.readValue(json, TheFoo.class);
    foo.setValue(null);
    assertEquals(foo, f7);

    json = "{\"value\":\"the-value-3\"}";
    final TheFoo f8 = mapper.readValue(json, TheFoo.class);
    foo.setValue(ValueEnum.the_Value_3);
    assertEquals(foo, f8);

    final String errorJson = "{\"value\":\"value-3\"}";
    assertThrows(InvalidFormatException.class,
        () -> mapper.readValue(errorJson, TheFoo.class));
  }

  @Test
  public void testXmlDeserialize() throws JsonProcessingException {
    final TheFoo foo = new TheFoo();
    foo.setValue(ValueEnum.VALUE_1);
    final XmlMapper mapper = new XmlMapper();
    String json = mapper.writeValueAsString(foo);
    System.out.println(json);
    assertEquals("<TheFoo><value>VALUE_1</value></TheFoo>", json);
    json = "<TheFoo><value>VALUE_1</value></TheFoo>";
    final TheFoo f1 = mapper.readValue(json, TheFoo.class);
    assertEquals(foo, f1);
    json = "<TheFoo><value>value_1</value></TheFoo>";
    final TheFoo f2 = mapper.readValue(json, TheFoo.class);
    assertEquals(foo, f2);
    json = "<TheFoo><value>value-1</value></TheFoo>";
    final TheFoo f3 = mapper.readValue(json, TheFoo.class);
    assertEquals(foo, f3);
    json = "<TheFoo><value>vaLue-1</value></TheFoo>";
    final TheFoo f4 = mapper.readValue(json, TheFoo.class);
    assertEquals(foo, f4);

    json = "<TheFoo><value></value></TheFoo>";
    final TheFoo f5 = mapper.readValue(json, TheFoo.class);
    foo.setValue(null);
    assertEquals(foo, f5);

    json = "<TheFoo><value>  </value></TheFoo>";
    final TheFoo f6 = mapper.readValue(json, TheFoo.class);
    foo.setValue(null);
    assertEquals(foo, f6);

    json = "<TheFoo></TheFoo>";
    final TheFoo f7 = mapper.readValue(json, TheFoo.class);
    foo.setValue(null);
    assertEquals(foo, f7);

    json = "<TheFoo><value>the-value-3</value></TheFoo>";
    final TheFoo f8 = mapper.readValue(json, TheFoo.class);
    foo.setValue(ValueEnum.the_Value_3);
    assertEquals(foo, f8);

    final String errorJson = "<TheFoo><value>value-3</value></TheFoo>";
    assertThrows(InvalidFormatException.class,
        () -> mapper.readValue(errorJson, TheFoo.class));
  }
}
