////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.serializer;

import java.io.IOException;
import java.io.Serial;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import static ltd.qubit.commons.text.jackson.deserializer.MapXmlDeserializer.ENTRY_FIELD_NAME;
import static ltd.qubit.commons.text.jackson.deserializer.MapXmlDeserializer.KEY_FIELD_NAME;
import static ltd.qubit.commons.text.jackson.deserializer.MapXmlDeserializer.VALUE_FIELD_NAME;

/**
 * 用于以XML格式序列化{@link Map}对象的{@link JsonSerializer}。
 *
 * @author 胡海星
 */
@SuppressWarnings("rawtypes")
public class MapXmlSerializer extends JsonSerializer<Map> {

  /**
   * MapXmlSerializer的单例实例。
   */
  public static final MapXmlSerializer INSTANCE = new MapXmlSerializer();

  /**
   * 构造一个新的MapXmlSerializer实例。
   */
  public MapXmlSerializer() {
    // 默认构造函数
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void serialize(final Map map, final JsonGenerator gen,
      final SerializerProvider serializers) throws IOException {
    gen.writeStartObject();
    gen.writeFieldName(ENTRY_FIELD_NAME);
    gen.writeStartArray();
    for (final Object entry : map.entrySet()) {
      gen.writeStartObject();
      final Map.Entry<?, ?> e = (Map.Entry<?, ?>) entry;
      gen.writeObjectField(KEY_FIELD_NAME, e.getKey());
      gen.writeObjectField(VALUE_FIELD_NAME, e.getValue());
      gen.writeEndObject();
    }
    gen.writeEndArray();
    gen.writeEndObject();
  }
}