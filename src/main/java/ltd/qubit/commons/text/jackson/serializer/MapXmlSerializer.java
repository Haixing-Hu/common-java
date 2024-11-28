////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * The {@link JsonSerializer} for serializing {@link Map} objects in XML format.
 *
 * @author Haixing Hu
 */
@SuppressWarnings("rawtypes")
public class MapXmlSerializer extends JsonSerializer<Map> {

  public static final MapXmlSerializer INSTANCE = new MapXmlSerializer();

  @Override
  public void serialize(final Map map, final JsonGenerator gen,
      final SerializerProvider serializers) throws IOException {
    gen.writeStartObject();
    gen.writeFieldName("entry");
    gen.writeStartArray();
    for (final Object entry : map.entrySet()) {
      gen.writeStartObject();
      final Map.Entry<?, ?> e = (Map.Entry<?, ?>) entry;
      gen.writeObjectField("key", e.getKey());
      gen.writeObjectField("value", e.getValue());
      gen.writeEndObject();
    }
    gen.writeEndArray();
    gen.writeEndObject();
  }
}
