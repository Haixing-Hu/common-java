////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JacksonBugTest {

  @Retention(RUNTIME)
  @Target({FIELD})
  @Documented
  @interface Text {
    String value();
  }

  static class Foo {
    @Text("name")
    private String name = "unknown";
    @Text("code")
    private String code = "unknown";

    public String getName() {
      return name;
    }

    public void setName(final String name) {
      this.name = name;
    }

    public String getCode() {
      return code;
    }

    public void setCode(final String code) {
      this.code = code;
    }
  }

  static class CustomizedSerializer extends StdSerializer<String> implements
      ContextualSerializer {

    private static final long serialVersionUID = 2912487869826221662L;

    private String text = "unknown";

    public CustomizedSerializer() {
      super(String.class);
    }

    @Override
    public JsonSerializer<?> createContextual(final SerializerProvider prov,
        final BeanProperty property) throws JsonMappingException {
      final Text annotation = property.getAnnotation(Text.class);
      if (annotation != null) {
        this.text = annotation.value();
      } else {
        this.text = "unknown";
      }
      return this;
    }

    @Override
    public void serialize(final String value, final JsonGenerator gen,
        final SerializerProvider provider) throws IOException {
      gen.writeString(text);
    }
  }

  @Disabled
  @Test
  public void testJacksonBug_1() throws JsonProcessingException {
    final SimpleModule module = new SimpleModule();
    module.addSerializer(String.class, new CustomizedSerializer());
    final Foo obj = new Foo();

    final ObjectMapper jsonMapper = new ObjectMapper();
    jsonMapper.registerModule(module);
    final String s1 = jsonMapper.writeValueAsString(obj);
    System.out.println(s1);
    assertEquals("{\"name\":\"name\",\"code\":\"code\"}", s1);
  }

  static class MyType {
    public String value = "";
  }

  static class Goo {
    @Text("name")
    private MyType name = new MyType();
    @Text("code")
    private MyType code = new MyType();

    public MyType getName() {
      return name;
    }

    public void setName(final MyType name) {
      this.name = name;
    }

    public MyType getCode() {
      return code;
    }

    public void setCode(final MyType code) {
      this.code = code;
    }
  }

  static class MyTypeSerializer extends StdSerializer<MyType> implements
      ContextualSerializer {

    private static final long serialVersionUID = -551191466304820788L;

    private String text = "unknown";

    public MyTypeSerializer() {
      super(MyType.class);
    }

    @Override
    public JsonSerializer<?> createContextual(final SerializerProvider prov,
        final BeanProperty property) throws JsonMappingException {
      final Text annotation = property.getAnnotation(Text.class);
      if (annotation != null) {
        this.text = annotation.value();
      } else {
        this.text = "unknown";
      }
      return this;
    }

    @Override
    public void serialize(final MyType value, final JsonGenerator gen,
        final SerializerProvider provider) throws IOException {
      gen.writeString(text);
    }
  }

  @Disabled
  @Test
  public void testJacksonBug_2() throws JsonProcessingException {
    final SimpleModule module = new SimpleModule();
    module.addSerializer(MyType.class, new MyTypeSerializer());
    final Goo obj = new Goo();

    final ObjectMapper jsonMapper = new ObjectMapper();
    jsonMapper.registerModule(module);
    final String s1 = jsonMapper.writeValueAsString(obj);
    System.out.println(s1);
    assertEquals("{\"name\":\"name\",\"code\":\"code\"}", s1);
    final String s2 = jsonMapper.writeValueAsString(obj);
    System.out.println(s2);
    assertEquals(s1, s2);
  }

  static class Too {
    @JsonSerialize(using = MyTypeSerializer.class)
    @Text("name")
    private MyType name = new MyType();
    @JsonSerialize(using = MyTypeSerializer.class)
    @Text("code")
    private MyType code = new MyType();

    public MyType getName() {
      return name;
    }

    public void setName(final MyType name) {
      this.name = name;
    }

    public MyType getCode() {
      return code;
    }

    public void setCode(final MyType code) {
      this.code = code;
    }
  }

  @Test
  public void testJacksonBug_3() throws JsonProcessingException {
    final Too obj = new Too();
    final ObjectMapper jsonMapper = new ObjectMapper();
    final String s1 = jsonMapper.writeValueAsString(obj);
    System.out.println(s1);
    assertEquals("{\"name\":\"name\",\"code\":\"code\"}", s1);
    final String s2 = jsonMapper.writeValueAsString(obj);
    System.out.println(s2);
    assertEquals(s1, s2);
  }

  static class MyTypeSerializer2 extends StdSerializer<MyType> implements
      ContextualSerializer {

    private static final long serialVersionUID = 808967166629149117L;

    private String text = "unknown";

    public MyTypeSerializer2() {
      super(MyType.class);
    }

    public MyTypeSerializer2(final String text) {
      super(MyType.class);
      this.text = text;
    }

    @Override
    public JsonSerializer<?> createContextual(final SerializerProvider prov,
        final BeanProperty property) throws JsonMappingException {
      final Text annotation = property.getAnnotation(Text.class);
      if (annotation != null) {
        return new MyTypeSerializer2(annotation.value());
      }
      return this;
    }

    @Override
    public void serialize(final MyType value, final JsonGenerator gen,
        final SerializerProvider provider) throws IOException {
      gen.writeString(text);
    }
  }


  @Test
  public void testJacksonBug_4() throws JsonProcessingException {
    final SimpleModule module = new SimpleModule();
    module.addSerializer(MyType.class, new MyTypeSerializer2());
    final Goo obj = new Goo();
    final ObjectMapper jsonMapper = new ObjectMapper();
    jsonMapper.registerModule(module);
    final String s1 = jsonMapper.writeValueAsString(obj);
    System.out.println(s1);
    assertEquals("{\"name\":\"name\",\"code\":\"code\"}", s1);
    final String s2 = jsonMapper.writeValueAsString(obj);
    System.out.println(s2);
    assertEquals(s1, s2);
  }
}
