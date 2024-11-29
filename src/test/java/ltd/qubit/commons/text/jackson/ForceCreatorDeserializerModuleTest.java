////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.RequestEntity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import ltd.qubit.commons.text.jackson.module.ForceCreatorDeserializerModule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ForceCreatorDeserializerModuleTest {

  @Test
  public void shouldFailDeserialize() throws IOException {
    final ObjectMapper objectMapper = new ObjectMapper();
    final String ser = objectMapper.writeValueAsString(new ImmutableClass("Hi")); // => { "str": "Hi" }
    assertThrows(MismatchedInputException.class, () -> objectMapper.readValue(ser, ImmutableClass.class));
  }

  @Test
  public void shouldSuccessDeserialize() throws IOException {
    final ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new ForceCreatorDeserializerModule());
    final String ser = objectMapper.writeValueAsString(new ImmutableClass("Hi")); // => { "str": "Hi" }
    final ImmutableClass deser = objectMapper.readValue(ser, ImmutableClass.class);

    assertEquals(deser.getStr(), "Hi");
  }

  @Test
  public void shouldSuccessDeserializeImmutableMap() throws IOException {
    final ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new ForceCreatorDeserializerModule());
    final String ser = objectMapper.writeValueAsString(new ImmutableMap(Map.of("key", "value"))); // => { "params": { "key": "value" } }
    final ImmutableMap deser = objectMapper.readValue(ser, ImmutableMap.class);
    assertEquals(deser.getParams(), Map.of("key", "value"));
  }

  @Disabled
  @Test
  public void shouldSuccessDeserializeCustomImmutableMap() throws IOException {
    // RequestEntity class have ReadOnlyHttpHeaders extending HttpHeaders implementing Map.
    final String json = "{\"@class\":\"org.springframework.http.RequestEntity\","
        + "\"headers\":{\"@class\":\"org.springframework.http.ReadOnlyHttpHeaders\","
        + "\"host\":[\"java.util.LinkedList\",[\"localhost:8080\"]],"
        + "\"user-agent\":[\"java.util.LinkedList\",[\"insomnia/7.0.1\"]],"
        + "\"cookie\":[\"java.util.LinkedList\",[\"SESSIONID=abcdefg\"]],"
        + "\"accept\":[\"java.util.LinkedList\",[\"*/*\"]],"
        + "\"content-length\":[\"java.util.LinkedList\",[\"100\"]],"
        + "\"Content-Type\":[\"java.util.LinkedList\",[\"application/json;charset=UTF-8\"]]},"
        + "\"body\":{\"@class\":\"java.util.LinkedHashMap\","
        + "\"params\":[\"java.util.ArrayList\",[{\"@class\":\"java.util.LinkedHashMap\","
        + "\"email\":\"wndtn853@gmail.com\"}]]},\"method\":\"POST\",\"url\":\"http://localhost:8080/\",\"type\":null}";

    final ObjectMapper objectMapper = new ObjectMapper()
        .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
        .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
        .enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.NON_FINAL, "@class")
        .registerModule(new ForceCreatorDeserializerModule());

    final RequestEntity deser = objectMapper.readValue(json, RequestEntity.class);

    assertEquals(objectMapper.writeValueAsString(deser), json);
  }

  private static class ImmutableClass {
    private String str;

    public ImmutableClass(final String someStr) {
      this.str = someStr;
    }

    public String getStr() {
      return str;
    }
  }

  private static class ImmutableMap {
    private final Map<String, Object> params;

    public ImmutableMap(final Map<String, Object> params) {
      this.params = params;
    }

    public Map<String, Object> getParams() {
      return params;
    }
  }
}
