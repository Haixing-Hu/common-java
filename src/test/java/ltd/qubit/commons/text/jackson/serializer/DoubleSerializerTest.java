////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import ltd.qubit.commons.text.jackson.CustomizedJsonMapper;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.json.JsonMapper;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

/**
 * Unit test of the {@link DoubleSerializer}.
 *
 * @author Haixing Hu
 */
public class DoubleSerializerTest {

  class Obj {
        public double v;

    public Obj() {
      v = 0;
    }

    public Obj(final double v) {
      this.v = v;
    }
  }

  @Test
  public void testSerialize() throws Exception {
    final JsonMapper mapper = new CustomizedJsonMapper();
    Obj obj = new Obj(0.31);
    String json = mapper.writeValueAsString(obj);
    assertJsonEquals("{\"v\":0.3100}", json);

    obj = new Obj(3.1415926);
    json = mapper.writeValueAsString(obj);
    assertJsonEquals("{\"v\":3.1416}", json);

    obj = new Obj(3.14155);
    json = mapper.writeValueAsString(obj);
    assertJsonEquals("{\"v\":3.1416}", json);

    obj = new Obj(3.14154999);
    json = mapper.writeValueAsString(obj);
    assertJsonEquals("{\"v\":3.1415}", json);

  }
}
