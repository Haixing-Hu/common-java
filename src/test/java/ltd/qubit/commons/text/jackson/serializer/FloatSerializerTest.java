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

import com.fasterxml.jackson.databind.json.JsonMapper;

import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

/**
 * Unit test of the {@link FloatSerializer}.
 *
 * @author Haixing Hu
 */
public class FloatSerializerTest {

  class Obj {

    public float v;

    public Obj() {
      v = 0;
    }

    public Obj(final float v) {
      this.v = v;
    }
  }

  @Test
  public void testSerialize() throws Exception {
    final JsonMapper mapper = new CustomizedJsonMapper();
    Obj obj = new Obj(0.31f);
    String json = mapper.writeValueAsString(obj);
    assertJsonEquals("{\"v\":0.31}", json);

    obj = new Obj(3.1415926f);
    json = mapper.writeValueAsString(obj);
    assertJsonEquals("{\"v\":3.14}", json);

    obj = new Obj(123.15f);
    json = mapper.writeValueAsString(obj);
    assertJsonEquals("{\"v\":123.15}", json);

    obj = new Obj(123.144f);
    json = mapper.writeValueAsString(obj);
    assertJsonEquals("{\"v\":123.14}", json);
  }

  @Test
  public void testSerialize_2() throws Exception {
    final JsonMapper mapper = new CustomizedJsonMapper();
    Obj obj = new Obj(123.145001f);
    String json = mapper.writeValueAsString(obj);
    assertJsonEquals("{\"v\":123.15}", json);

    obj = new Obj(123.144f);
    json = mapper.writeValueAsString(obj);
    assertJsonEquals("{\"v\":123.14}", json);

    obj = new Obj(123.145f);  // NOTE that 123.145f is actually 123.1449999999....
    json = mapper.writeValueAsString(obj);
    assertJsonEquals("{\"v\":123.14}", json);
  }
}
