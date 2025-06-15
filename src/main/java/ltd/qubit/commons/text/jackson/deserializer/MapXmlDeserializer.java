////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.text.jackson.deserializer;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ltd.qubit.commons.datastructure.CollectionUtils;

import static ltd.qubit.commons.text.jackson.JacksonUtils.ensureCurrentName;
import static ltd.qubit.commons.text.jackson.JacksonUtils.ensureNextToken;

/**
 * 用于反序列化 XML 格式的 {@link Map} 对象的 {@link JsonDeserializer}。
 * <p>
 * FIXME: 存在 bugs
 *
 * @author 胡海星
 */
@SuppressWarnings("rawtypes")
public class MapXmlDeserializer extends JsonDeserializer<Map> implements ContextualDeserializer {

  /**
   * 条目字段名称。
   */
  public static final String ENTRY_FIELD_NAME = "entry";

  /**
   * 键字段名称。
   */
  public static final String KEY_FIELD_NAME = "key";

  /**
   * 值字段名称。
   */
  public static final String VALUE_FIELD_NAME = "value";

  /**
   * Map 类型。
   */
  private final JavaType mapType;

  /**
   * 键类型。
   */
  private final JavaType keyType;

  /**
   * 值类型。
   */
  private final JavaType valueType;

  /**
   * 构造一个 {@link MapXmlDeserializer} 对象。
   *
   * @param mapType
   *     指定的 Map 类型。
   */
  public MapXmlDeserializer(final JavaType mapType) {
    this.mapType = mapType;
    if (mapType != null) {
      keyType = mapType.getKeyType();
      valueType = mapType.getContentType();
    } else {
      keyType = null;
      valueType = null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public Map deserialize(final JsonParser jp, final DeserializationContext context)
      throws IOException {
    if (mapType == null) {
      throw new IllegalStateException("The name and type must be set before deserialization");
    }
    final XmlMapper mapper = (XmlMapper) jp.getCodec();
    final Class<? extends Map> mapClass = (Class<? extends Map>) mapType.getRawClass();
    // construct a map object with the same type as the mapType
    final Map map = CollectionUtils.constructSameTypeOfMap(mapClass);
    final JsonToken token = jp.currentToken();
    if (token == JsonToken.VALUE_NULL) {
      return null;
    } else if (token == JsonToken.VALUE_STRING && jp.getText().isEmpty()) {
      // Treat empty string as an empty map
      return map;
    } else if (token != JsonToken.START_OBJECT) {
      // Ensure we're at the start of the map field
      throw new IOException("The map must be represented as an object");
    }
    // move to the start of entry array
    while (jp.nextToken() != JsonToken.END_OBJECT) {
      ensureCurrentName(jp, ENTRY_FIELD_NAME);
      ensureNextToken(jp, JsonToken.START_OBJECT);
      ensureNextToken(jp, JsonToken.FIELD_NAME);
      ensureCurrentName(jp, KEY_FIELD_NAME);
      jp.nextToken();
      final Object key = mapper.readValue(jp, keyType);
      ensureNextToken(jp, JsonToken.FIELD_NAME);
      ensureCurrentName(jp, VALUE_FIELD_NAME);
      jp.nextToken();
      final Object value = mapper.readValue(jp, valueType);
      ensureNextToken(jp, JsonToken.END_OBJECT);
      map.put(key, value);
    }
    return map;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JsonDeserializer<?> createContextual(final DeserializationContext context,
      final BeanProperty property) throws JsonMappingException {
    return new MapXmlDeserializer(property.getType());
  }
}