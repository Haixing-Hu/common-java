////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2017 - 2022.
//    Nanjing Smart Medical Investment Operation Service Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test of the {@link BigDecimalSerializer}.
 *
 * @author Haixing Hu
 */
public class BigDecimalSerializerTest {

  class Obj {
    @JsonSerialize(using = BigDecimalSerializer.class)
    public BigDecimal v;

    public Obj() {
      v = new BigDecimal(0);
    }

    public Obj(final BigDecimal v) {
      this.v = v;
    }
  }

  @Test
  public void testSerialize() throws Exception {
    final JsonMapper mapper = new JsonMapper();
    Obj obj = new Obj(new BigDecimal("0.31"));
    String json = mapper.writeValueAsString(obj);
    assertEquals("{\"v\":0.310000}", json);

    obj = new Obj(new BigDecimal("3.1415926"));
    json = mapper.writeValueAsString(obj);
    assertEquals("{\"v\":3.141593}", json);

    obj = new Obj(new BigDecimal("3.1415925"));
    json = mapper.writeValueAsString(obj);
    assertEquals("{\"v\":3.141593}", json);

    obj = new Obj(new BigDecimal("3.1415924"));
    json = mapper.writeValueAsString(obj);
    assertEquals("{\"v\":3.141592}", json);

  }
}
